<template>
    <el-container>
      <el-header style="height: 75px;">
        <el-row align="bottom" style="display: flex;align-items: center;height: 100%;">
          <el-col :span="5" class="centered-text">
            <!--        <el-text
                        style="font-size: 3em; color: black; font-family: 宋体, sans-serif"
                        @click="Renovate"
                    >
                      图片交易与确权系统
                    </el-text>-->
            <el-text
                style="font-size: 25px; font-family: '华文楷体'; color: black;"
            >
              图片交易与确权系统
            </el-text>

          </el-col>
          <el-col :span="4">
            <el-button
                type="info"
                v-if="showSidebar" @click="showSidebarMethod"
                round
            >
              <el-text
                  size="large"
                  style="color: white"
              >
                侧边栏
              </el-text>
              <el-icon size="25" v-if="showSidebar"><Fold /></el-icon>
              <el-icon size="25" v-else @click="showSidebarMethod"><Expand /></el-icon>
            </el-button>

            <el-button
                type="info"
                v-else @click="showSidebarMethod"
                round
            >
              <el-icon size="25" v-if="showSidebar" @click="showSidebarMethod"><Fold /></el-icon>
              <el-icon size="25" v-else @click="showSidebarMethod"><Expand /></el-icon>
              <el-text
                  size="large"
                  style="color: white"
              >
                侧边栏
              </el-text>
            </el-button>
          </el-col>
          <el-col :span= 2 :offset="11">

          </el-col>

          <el-col :span="1" style="display: flex; align-items: center; justify-content: center;">

            <!-- </el-col>
            <el-col :span="1" style="display: flex; align-items: center;"> -->
            <el-dropdown trigger="click">

          <span style="font-size: 18px;color: #000;margin-left: 45px;display: flex;align-items: center;">
            {{store.auth.user.username}}
            <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
            <el-icon><arrow-down /></el-icon>
          </span>
              <template #dropdown>
                <el-dropdown-menu style="font-size: 150px">
                  <el-dropdown-item
                      @click="toPublishWorks"
                      style="font-size: 50px"
                      divided
                  >
                    <el-text size="large"> 发 表 作 品 </el-text>
                  </el-dropdown-item>

                  <el-dropdown-item
                      @click="toMyWorks"
                      style="font-size: 50px"
                      divided
                  >
                    <el-text size="large"> 我 的 作 品 </el-text>
                  </el-dropdown-item>

                  <el-dropdown-item
                      @click="toPurchaseRecord"
                      style="font-size: 50px"
                      divided
                  >
                    <el-text size="large"> 购 买 记 录 </el-text>
                  </el-dropdown-item>

                  <el-dropdown-item
                      @click="toManageWeb"
                      style="font-size: 50px"
                      divided
                  >
                    <el-text size="large"> 区 块 链 管 理 </el-text>
                  </el-dropdown-item>

                  <el-dropdown-item
                      @click="logout()"
                      style="font-size: 50px"
                      divided
                  >
                    <el-text size="large" type="danger"> 退 出 登 录 </el-text>
                  </el-dropdown-item>

                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-col>

        </el-row>
      </el-header>

    <div class="layout-container-demo" style="margin-bottom: 0;height: calc(100vh - 75px);background-color: #f1f3f5; display: flex;">
      <div
      style="background-color: white;height: calc(100vh - 75px);width:300px"
        id="sidebar"
        ref="get_sidebar_state"
      >
        <el-scrollbar>
          <el-menu
            active-text-color="#d9a834"
            background-color="white"
            class="el-menu-vertical-demo"
            default-active="1"
            text-color="#000"
          >
            <el-menu-item @click="toIndex()" index="1-1-1">
              <el-icon><Shop /></el-icon>
              <el-text
                  style="margin-left: 40px"
                  size="large"
              >
                回 到 主 页
              </el-text>
            </el-menu-item>
          </el-menu>

        </el-scrollbar>
      </div>

      <el-main
          style="height: 100%; background-color: white;
                 padding: 50px;box-sizing: border-box;width: calc(100vw - 300px);flex: 1;"
      >
        <el-text size="large">
          请按下列步骤上传图片
        </el-text>
        <el-text type="info">（注：每次只能选择一个图片）</el-text>
        <!--  用户上传的表单  -->
        <el-form
            :model="publish_works_form"
            style="margin-top: 20px"
        >

          <!--  上传用户的作品图片 jpg png bmp  -->
          <el-text size="large">
            Step 1 --> 请选择您的作品
          </el-text>

          <el-form-item>
            <el-upload
                class="upload-demo"
                drag
                action="http://localhost:8080/api/file/uploadoss"
                :on-success="handleAvatarSuccessformer"
                :before-upload="beforeAvatarUpload"
                :multiple="false"
                accept="image/jpeg,image/png"
                style="margin-top: 20px; width: 1100px; margin-left: 82px"
            >
              <el-image v-if="imageUrlformer" :src="imageUrlformer" class="avatar">
              </el-image>
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过2MB</div>
            </el-upload>
          </el-form-item>

          <!--  上传用户的作品图片 jpg png bmp  -->
          <el-text size="large">
            Step 2 --> 请上传已添加水印的作品
          </el-text>

          <el-form-item>
            <el-upload
                class="upload-demo"
                drag
                action="http://localhost:8080/api/file/uploadossmarked"
                :on-success="handleAvatarSuccessmarked"
                :before-upload="beforeAvatarUpload"
                :multiple="false"
                accept="image/jpeg,image/png"
                style="margin-top: 20px; width: 1100px; margin-left: 82px"
            >
              <img v-if="imageUrlmarked" :src="imageUrlmarked" class="avatar">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过2MB</div>
            </el-upload>
          </el-form-item>

          <!--  输入嵌入水印的密钥  -->
          <el-text size="large">Step 3 --> 请输入作品名称</el-text>

          <el-form-item
              prop="input_password"
          >
            <el-input
                v-model.number="publish_works_form.work_name"
                type="text"
                autocomplete="off"
                style="width: 80%; height: 40px; margin-top: 20px; margin-left: 82px"
                clearable
            />
          </el-form-item>

          <!--  输入嵌入水印的密钥  -->
<!--          <el-text size="large">Step 3 &ndash;&gt; 请输入您需要嵌入的水印序列</el-text>-->
<!--          <el-form-item-->
<!--              prop="input_password"-->
<!--          >-->
<!--            <el-input-->
<!--                v-model.number="publish_works_form.input_password"-->
<!--                type="text"-->
<!--                autocomplete="off"-->
<!--                style="width: 1100px; height: 40px; margin-top: 20px; margin-left: 82px"-->
<!--                clearable-->
<!--            />-->
<!--          </el-form-item>-->




          <!--  选择作品的主题类别  -->
          <el-text size="large">Step 4 --> 请选择您的作品主题</el-text>

          <el-form-item>
            <el-checkbox-group v-model="publish_works_form.theme" style="width: 100%;">
              <div style="width: 80%;;height: 40px; margin-top: 20px; margin-left: 82px;display: flex;justify-content: space-around;">
                <el-checkbox label="摄影照片" value="1" />
                <el-checkbox label="人物肖像" value="2" />
                <el-checkbox label="自然风景" value="3" />
                <el-checkbox label="卡通手绘" value="4" />
              </div>
              <div style="width: 80%;;height: 40px; margin-top: 20px; margin-left: 82px;display: flex;justify-content: space-around;">
                <el-checkbox label="背景图片" value="5" />
                <el-checkbox label="人文纪实" value="6" />
                <el-checkbox label="异国风情" value="7" />
                <el-checkbox label="生活记录" value="8" />
              </div>
            </el-checkbox-group>
          </el-form-item>
          <el-text size="large">Step 5 --> 请核实上传的图片</el-text>
          <div>
            <el-card shadow="hover" style="margin: 20px; padding: 20px;">
              <div v-if = showImage style="position: relative;">
                <div style="position: relative; display: inline-block; margin: 20px;">
                  <el-text size="large" style="position: absolute; top: -40px; left: 50%; transform: translateX(-50%); font-size: 24px;">
                    原图片
                  </el-text>
                  <img :src="imageUrlformer" style="max-width: none; user-select: none;" draggable="false">
                </div>
                <div style="position: relative; display: inline-block; margin: 20px;">
                  <el-text size="large" style="position: absolute; top: -40px; left: 50%; transform: translateX(-50%); font-size: 24px;">
                    水印图片
                  </el-text>
                  <img :src="imageUrlmarked" style="max-width: none; user-select: none;" draggable="false">
                </div>
              </div>
            </el-card>
          </div>
          <!--  提交与返回上一界面的按钮  -->
          <div style="margin-left: 200px; margin-top: 50px">
            <el-button
                type="primary"
                style="width: 200px; height: 40px"
                @click="Submit()"
            >
              确 认 上 传
            </el-button>

            <el-button
                type="danger"
                style="width: 200px; height: 40px; margin-left: 200px;"
                @click="BackToIndexView"
            >
              返 回 前 页
            </el-button>
          </div>
        </el-form>
      </el-main>
    </div>
  </el-container>
</template>

<style scoped>
.el-aside {
  height: 92.1vh;
}
#sidebar{
  transition: transform 1.5s ease;
  display: none;
}
</style>
<style>
body{
  margin: 0;
}
</style>
<script lang="ts" setup>
// 返回上一界面
import {useStore} from "../../stores";
import router from "../../router";
import {ElButton, ElMessage} from "element-plus";
import {get, post} from "../../net";
import {reactive, ref} from 'vue';
import {ArrowDown, Lock, Message, Picture, Shop, Star, Unlock, Expand, Fold, Bell} from "@element-plus/icons-vue";
let imageUrlformer = '';
let imageUrlmarked = '';
let imagebase;
let imageBase64;

const store = useStore()
const showImage = ref(false)
const publish_works_form = reactive({
  // 作品名称
  work_name: '',

  // 用户输入的密钥
  input_password: '',

  // 用户选择的主题
  theme: [],
})

const BackToIndexView = () => {
  router.push('/index')
}
const showSidebar=ref(false)
const get_sidebar_state=ref(null)
const showSidebarMethod=()=>{
  if(!showSidebar.value){
    get_sidebar_state.value.style.display ='block'
    showSidebar.value=true
  }else{
    get_sidebar_state.value.style.display ='none'
    showSidebar.value=false
  }
}
const beforeAvatarUpload = (file) => {
  const isJPG = file.type === "image/jpeg";
  const isLt2M = file.size / 1024 / 1024 < 2;
  const isPNG = file.type === 'image/png';

  if (!isJPG && !isPNG) {
    ElMessage.error('只能上传 jpg/png 文件!');
    return false;
  }
  if (!isLt2M) {
    ElMessage.error("上传图片大小不能超过 2MB!");
    return false;
  }
  return true;
}

async function generateHash(imagebase) {
  // 去掉 `imagebase` 中的前缀部分 "data:image/png;base64,"
  const base64String = imagebase.split(',')[1];

  // 将 base64 字符串转换为 ArrayBuffer
  const binaryString = atob(base64String);
  const len = binaryString.length;
  const bytes = new Uint8Array(len);
  for (let i = 0; i < len; i++) {
    bytes[i] = binaryString.charCodeAt(i);
  }

  // 使用 SubtleCrypto API 生成 SHA-256 哈希值
  const hashBuffer = await crypto.subtle.digest('SHA-256', bytes);

  // 将 ArrayBuffer 转换为十六进制字符串
  const hashArray = Array.from(new Uint8Array(hashBuffer));
  const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');

  // 返回64个字符的哈希值
  return hashHex.slice(0, 64);
}

const handleAvatarSuccessformer = (res, file) => {
  console.log("success")
  imageUrlformer = URL.createObjectURL(file.raw);
  const reader = new FileReader();
  reader.onload = (event) => {
    // 将图片转换为Base64编码的字符串
    imageBase64 = event.target.result;
    imagebase =  generateHash(imageBase64);
    console.log("imagebase:"+imagebase)
  };
  // showImage.value = true;
  reader.readAsDataURL(file.raw);
}

const handleAvatarSuccessmarked = (res, file) => {
  console.log("success")
  imageUrlmarked = URL.createObjectURL(file.raw);
  const reader = new FileReader();
  reader.onload = (event) => {
    // 将图片转换为Base64编码的字符串
    imageBase64 = event.target.result;
  };
  showImage.value = true;
  reader.readAsDataURL(file.raw);
}

let dataObj = {
  face_user: publish_works_form.input_password,
  face_sex: publish_works_form.theme,
  face_base64: imageBase64,
};
const labelToValueMap = {
  '摄影照片': 1,
  '人物肖像': 2,
  '自然风景': 3,
  '卡通手绘': 4,
  '背景图片': 5,
  '人文纪实': 6,
  '异国风情': 7,
  '生活记录': 8
};
const Submit = () => {
  // 将 publish_works_form.theme 转换为数值数组
  const themeValues = publish_works_form.theme.map(label => labelToValueMap[label]);

  // 将数值数组转换为字符串
  const themeString = themeValues.join(',');

  console.log(themeString); // 输出转换后的字符串
  post("api/file/addorder",{
    to: store.auth.user.username,
    username: window.localStorage.getItem("username"),
    work_name: publish_works_form.work_name,
    theme: themeString,
    time: new Date().toLocaleString(),
  },(message) =>{
    // ElMessage.success(message)
    const [sb, sb_marked] = message.split('|');
    console.log(sb)
    console.log(sb_marked)
    post("api/fisco/one",{
      pid: sb,
      imagebase: publish_works_form.work_name,
      username: store.auth.user.username,
      theme: themeString,
      time: new Date().toLocaleString(),
      pid_marked:sb_marked,
      markcode:store.auth.user.markcode
    },(message) =>{
      ElMessage.success(message)
      toIndex();
    })
  })
};
// 路 径 跳 转
const toIndex = () => {
  router.push('/index')
}
const toPublishWorks = () => {
  router.push('/publish-works')
}
const toMyWorks = () => {
  router.push('/my-works')
}
const toCopyrightTest = () => {
  router.push('/copyright-test')
}
const toPurchaseRecord = () => {
  router.push('/purchase-record')
}
const toPictureInfo = (url) => {
  router.push(`/picture-info?url=${url}`)
}
const toManageWeb = () => {
  parent.location.href = "http://192.168.178.128:5000/"
}

const logout = () => {
  store.auth.user = null;
  router.push('/');
  window.close();
};

</script>

