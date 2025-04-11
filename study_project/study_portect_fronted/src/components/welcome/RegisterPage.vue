<template xmlns:el-col="http://www.w3.org/1999/html">
  <div style="text-align: center;margin: 0 20px">
    <div style="margin-top: 100px">
      <div style="font-size: 25px;font-weight: bold">注册</div>
      <div style="font-size: 14px;color: grey;margin-top: 10px">请在此注册您的专属账号</div>
    </div>
    <div style="margin-top: 50px">
      <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" type="text" placeholder="用户名/邮箱">
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="form.password" type="password" placeholder="密码">
          <template #prefix>
            <el-icon><Lock /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password_repeat">
        <el-input v-model="form.password_repeat" type="password" placeholder="重复密码">
          <template #prefix>
            <el-icon><Lock /></el-icon>
          </template>
        </el-input>
      </el-form-item>
        <el-form-item prop="text">
          <el-input v-model="form.text" type="text" placeholder="助记符">
            <template #prefix>
              <el-icon><Document /></el-icon>
            </template>
          </el-input>
        </el-form-item>
      <el-form-item prop="email">
        <el-input v-model="form.email" type="email" placeholder="电子邮件">
          <template #prefix>
            <el-icon><Message /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-row :gutter="10" style="width: 100%">
          <el-col :span="18">
            <el-input v-model="form.code" :maxlength="6" type="text" placeholder="验证码">
              <template #prefix>
                <el-icon><EditPen /></el-icon>
              </template>
            </el-input>
          </el-col>
          <el-col :span="6">
            <el-button type="success" @click="validateEmail" :disabled="!isEmailValid || coolTime > 0">
              {{ coolTime <= 0 ? '获取验证码' : coolTime }}
            </el-button>
          </el-col>
        </el-row>
      </el-form-item>
      </el-form>
    </div>
    <div>
      <el-button style="margin-top: 50px" type="warning" @click="register" plain>立即注册</el-button>
    </div>
    <div style="margin-top: 50px;font-size: 14px" >
      已有帐号？<el-link type="primary" @click="router.push('/')">立即登录</el-link>
    </div>
  </div>
</template>

<script setup>

import {EditPen, Lock, Message, User,Document} from "@element-plus/icons-vue";
import router from "@/router";
import {reactive} from "vue";
import { ref } from 'vue';
import {ElMessage} from "element-plus";
import {post} from "@/net";
import CryptoJS from 'crypto-js';
import {sm2} from "sm-crypto";
import * as OSS from 'ali-oss';
const form = reactive({
  username: '',
  password: '',
  password_repeat: '',
  text: '',
  email: '',
  code: '',
})

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else if(!/^[a-zA-z0-9\u4e00-\u9fa5]+$/.test(value)){
    callback(new Error('用户名不能包含特殊字符，只能是中文或英文'))
    }else{
    callback()
  }
}

const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.password) {
    callback(new Error("两次密码不一致"))
  } else {
    callback()
  }
}

const rules = {
  username:[
    { validator:validateUsername, trigger: ['blur','change']},
    { min: 3, max: 8, message: '用户名的长度只能是3-8位', trigger: ['blur','change']},
  ],
  password:[
    { required: true, message: '请输入密码', trigger: ['blur','change']},
    { min: 6, max: 15, message: '密码的长度只能是6-15位', trigger: ['blur','change']},
  ],
  password_repeat:[
    { validator:validatePass, trigger: ['blur','change']},
  ],
  email:[
    { required: true, message: '请输入邮件', trigger: ['blur','change']},
    { type:'email', message: '请输入合法的邮件地址', trigger: ['blur','change']},
  ],
  code:[
    { required: true, message: '请输入获取的验证码', trigger: ['blur','change']},
  ],
  text: [
    {
      required: true,
      message: '请输入助记符',
      trigger: ['blur','change']
    },
    {
      validator: (rule, value, callback) => {
        const chineseCharRegex = /[\u4e00-\u9fa5]/; // Regular expression to match Chinese characters
        if (chineseCharRegex.test(value)) {
          callback(new Error('助记符中不能包含汉字'));
        } else {
          callback();
        }
      },
      trigger: ['blur','change']
    }
  ]
}

const formRef = ref()
const isEmailValid = ref(false)
const coolTime = ref(0)
const onValidate = (prop,isValid) =>{
  if(prop === 'email'){
    isEmailValid.value = isValid
  }
}

const register = () => {
  formRef.value.validate(async (isValid) => {
    if (isValid) {
      //给予公私钥对
      const {publicKey, privateKey} = sm2.generateKeyPairHex()
      window.localStorage.setItem("publicKey", publicKey)
      window.localStorage.setItem("privateKey", privateKey)

      post("api/auth/register",{
        username: form.username,
        password: form.password,
        email: form.email,
        code: form.code,
        publicKey: publicKey
      },async (message) => {
        ElMessage.success(message)
        // 助记符加密私钥，保存在文件中
        // Encrypt the private key using form.text as the passphrase
        const encryptedPrivateKey = CryptoJS.AES.encrypt(privateKey, form.text).toString();
        console.log(encryptedPrivateKey);

        // Create a Blob (or File) object from the encrypted private key
        const privateKeyBlob = new Blob([encryptedPrivateKey], {type: "text/plain"});

        // 配置你的OSS信息
        const client = new OSS({
          region: 'oss-cn-beijing',
          accessKeyId: 'LTAI5t77jmJxoVKoF1FRjJMi',
          accessKeySecret: 'dZRtYoX56grRhjJc6pLQnRt18R84At',
          bucket: 'my-face-home'
        });

        try {
          // 上传Blob对象到OSS
          const result = await client.put("zhujifu/" + form.username + ".txt", privateKeyBlob);
          console.log(result);
        } catch (e) {
          console.error(e);
        }

        //提醒用户保存
        const alertMessage = '注册成功！请务必保存好您的助记词:' + form.text;
        alert(alertMessage);
        router.push("/")
      })
    }else{
      ElMessage.warning("请输入完整表单内容")
    }
  })
}
const validateEmail = () =>{
  post("api/auth/vaild-register-email",{
    email: form.email
  },(message) =>{
    ElMessage.success(message)
    coolTime.value = 60
    setInterval(() => coolTime.value--,1000)
  })
}

</script>

<style scoped>

</style>
