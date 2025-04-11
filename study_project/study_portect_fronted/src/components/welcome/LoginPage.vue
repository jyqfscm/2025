<template>
  <div style="text-align: center;margin: 0 20px">
    <div style="margin-top: 150px">
      <div style="font-size: 25px;font-weight: bold">登录</div>
      <div style="font-size: 14px;color: grey;margin-top: 10px">在进入系统之前请先输入用户名和密码进行登录</div>
    </div>
    <div style="margin-top: 50px">
      <el-input v-model="form.username" type="text" placeholder="用户名/邮箱">
        <template #prefix>
          <el-icon><User /></el-icon>
        </template>
      </el-input>
      <el-input v-model="form.password" type="password" style="margin-top: 10px" placeholder="密码">
        <template #prefix>
          <el-icon><Lock /></el-icon>
        </template>
      </el-input>
    </div>
    <el-row style="margin-top: 5px">
      <el-col :span="12" style="text-align: left">
        <el-checkbox v-model="form.remember" label="记住我"/>
      </el-col>
      <el-col :span="12" style="text-align: right">
        <el-link @click="router.push('/forget')">忘记密码？</el-link>
      </el-col>
    </el-row>
    <div style="margin-top: 40px">
      <el-button @click="login()" style="width: 200px" type="success" plain>登录</el-button>
    </div>
    <div style="margin-top: 20px">
      <el-button style="width: 200px" @click="router.push('/register')" type="warning" plain>注册</el-button>
    </div>
  </div>
</template>

<script setup>
import {User,Lock} from '@element-plus/icons-vue'
import {reactive} from "vue";
import {ElMessage} from "element-plus"
import router from "@/router";
import {get, post} from "@/net";
import {useStore} from "@/stores";
import {sm2} from "sm-crypto";

const form = reactive({
  username: '',
  password: '',
  remember: false
})
const store = useStore()
const login = () =>{
  if(!form.username || !form.password){
     ElMessage.warning("请填写用户名和密码！")
  }else{
    post('/api/auth/login',{
      username: form.username,
      password: form.password,
      remember: form.remember
    },(message) => {
      ElMessage.success(message)
      get('/api/user/me',(message)=>{
        console.log(message)
        store.auth.user = message.user
        window.localStorage.setItem("username", message.user.username)
        window.localStorage.setItem("markcode", message.user.markcode)
        // const { publicKey, privateKey } = sm2.generateKeyPairHex()
        // window.localStorage.setItem("publicKey", publicKey)
        // window.localStorage.setItem("privateKey", privateKey)
        window.localStorage.setItem("publicKeyHex", message.publicKeyHex)

        // console.log(publicKey)
        // console.log(privateKey)

        router.push('/index')
      },()=>{
        store.auth.user = null
      })
    })
  }
}
</script>

<style scoped>

</style>
