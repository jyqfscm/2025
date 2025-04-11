<template>
  <div style="margin: 30px 20px">
    <el-steps :active="active" finish-status="success" align-center>
      <el-step title="验证电子邮件"/>
      <el-step title="重新设置密码"/>
    </el-steps>
  </div>
  <div style="text-align: center;margin: 0 20px" v-if="active === 0">
    <div style="margin-top: 150px">
      <div style="font-size: 25px;font-weight: bold">重置密码</div>
      <div style="font-size: 14px;color: grey;margin-top: 10px">请输入需要重置密码的电子邮件</div>
    </div>

      <div style="margin-top: 50px">
        <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
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
      <el-button @click="startReset" style="width: 270px;margin-top: 20px" type="warning"  plain>重置密码</el-button>
    </div>
    <div style="margin-top: 50px;font-size: 14px" >
      <el-link type="primary" @click="router.push('/')">放弃重置，返回登录</el-link>
    </div>
  </div>

    <div style="text-align: center;margin: 0 20px" v-if="active === 1">
      <div style="margin-top: 150px">
        <div style="font-size: 25px;font-weight: bold">重置密码</div>
        <div style="font-size: 14px;color: grey;margin-top: 10px">请输入你需要设置的新密码</div>
      </div>

      <div style="margin-top: 50px">
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="新密码">
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
      </div>
      <div>
        <el-button @click="doReset" style="width: 270px;margin-top: 20px" type="warning"  plain>立即重置</el-button>
      </div>
      <div style="margin-top: 50px;font-size: 14px" >
        <el-link type="primary" @click="router.push('/')">放弃重置，返回登录</el-link>
      </div>
  </div>

</template>

<script setup>
import {reactive} from "vue";
import {EditPen, Lock, Message} from "@element-plus/icons-vue";
import { ref } from 'vue';
import {post} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";

const formRef = ref()
const coolTime = ref(0)
const active = ref(0)
const isEmailValid = ref(false)
const form = reactive({
  password: '',
  password_repeat: '',
  email: '',
  code: '',
})
const onValidate = (prop,isValid) =>{
  if(prop === 'email'){
    isEmailValid.value = isValid
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
  ]
}
const validateEmail = () =>{
  post("api/auth/vaild-reset-email",{
    email: form.email
  },(message) =>{
    ElMessage.success(message)
    coolTime.value = 60
    setInterval(() => coolTime.value--,1000)
  })
}
const startReset = () =>{
  formRef.value.validate((isValid) => {
    if(isValid){
      post("api/auth/start-reset",{
        email: form.email,
        code: form.code
      },(message) =>{
        ElMessage.success(message)
        active.value++
      })
    }else{
      ElMessage.warning("请输入完整表单内容")
    }
  })
}
const doReset = () =>{
  post("api/auth/do-reset",{
    email: form.email,
    code: form.code
  },(message) =>{
    ElMessage.success(message)
    router.push('/')
  })
}
</script>

<style scoped>

</style>