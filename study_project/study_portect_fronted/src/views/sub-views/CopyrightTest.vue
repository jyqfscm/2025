<template>
  <el-container>
    <el-header style="height: 100px; background-color: #ccefe4; margin-bottom: -25px">
      <el-row style="height: 50px"></el-row>
      <el-row>
        <el-col :offset="1">
          <el-text size="large"></el-text>
        </el-col>
      </el-row>
    </el-header>

    <el-container>
      <el-aside width="120px" style="height: 850px; margin-top: -25px; background-color: #ccefe4">
      </el-aside>

      <el-main>

        <!--  用户上传的表单  -->
        <el-form :model="publish_works_form" style="margin-left: 50px; margin-top: 50px">

          <!--  上传用户的作品图片 jpg png bmp  -->
          <el-row style="height: 60px"><el-text type="info" size="large">请选择您的作品：</el-text></el-row>


          <el-row>
            <el-col :offset="2">

              <el-form-item>
                <el-upload
                    class="upload-demo"
                    drag
                    action="http://localhost:8080/api/file/getpath"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                    :multiple="false"
                    accept="image/jpeg,image/png"
                    style="width: 700px"
                >
                  <img v-if="imageUrl" :src="imageUrl" class="avatar">
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                  <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过2MB</div>
                </el-upload>
              </el-form-item>

            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="4">
              <el-button type="primary" style="width: 100px; height: 40px" @click="Submit()">确认上传</el-button>
            </el-col>
            <el-col :span="6">
              <el-button type="danger" style="width: 100px; height: 40px"
                         @click="BackToIndexView">返回前页</el-button>
            </el-col>
          </el-row>

        </el-form>
      </el-main>
    </el-container>
  </el-container>
</template>


<script lang="ts" setup>
import {reactive} from 'vue'
// 返回上一界面
import router from "../../router"
import {ElMessage} from "element-plus";
import {get, post} from "../../net";
let imageUrl;
let imageBase64;
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

const beforeAvatarUpload = (file) => {
  const isJPG = file.type === "image/jpeg";
  const isLt2M = file.size / 1024 / 1024 < 2;
  const isPNG = file.type === 'image/png';

  if (!isJPG && !isPNG) {
    ElMessage.error('只能上传 jpg/png 文件!');
  }
  if (!isLt2M) {
    ElMessage.error("上传图片大小不能超过 2MB!");
  }
  return isJPG && isLt2M && isPNG;
}

const handleAvatarSuccess = (res, file) => {
  imageUrl = URL.createObjectURL(file.raw);
  const reader = new FileReader();
  reader.onload = (event) => {
    // 将图片转换为Base64编码的字符串
    imageBase64 = event.target.result;
  };
  reader.readAsDataURL(file.raw);
}

let dataObj = {
  face_user: publish_works_form.input_password,
  face_sex: publish_works_form.theme,
  face_base64: imageBase64,
};
const Submit = () => {
  post("",{
  },(message) =>{
    ElMessage.success(message)
  })
};

</script>

<style scoped>

</style>
