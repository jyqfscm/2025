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
            <el-icon size="25" v-if="showSidebar" @click="showSidebarMethod"><Fold /></el-icon>
            <el-icon size="25" v-else @click="showSidebarMethod"><Expand /></el-icon>
            <el-text
                size="large"
                style="color: white"
            >
              侧边栏
            </el-text>
          </el-button>

          <el-button
              type="info"
              v-else @click="showSidebarMethod"
              round
          >
            <el-text
                size="large"
                style="color: white"
            >
              侧边栏
            </el-text>
            <el-icon size="25" v-if="showSidebar" @click="showSidebarMethod"><Fold /></el-icon>
            <el-icon size="25" v-else @click="showSidebarMethod"><Expand /></el-icon>
          </el-button>
        </el-col>
        <el-col :span= 2 :offset="11">

        </el-col>

        <el-col :span="1" style="display: flex; align-items: center; justify-content: center;">

<!--          </el-col>-->
<!--          <el-col :span="1" style="display: flex; align-items: center;">-->
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
        <el-scrollbar style="width: 300px;">
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
          style="background-color: white;
                 height: calc(100vh - 75px);"
      >
        <el-row style="margin-bottom: 20px;">
          <el-col :span="1" style="margin-left: 75px">
            <el-avatar
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            />

          </el-col>
          <el-text
              size="large"
              type="primary"
          >
            {{store.auth.user.username}}
          </el-text>
          <el-text
              size="large"
              style="margin-left: 20px"
          >
            购买的作品
          </el-text>

        </el-row>

         <el-divider />

        <el-main style="height: calc(100vh - 205px); margin-left: 45px">
          <div style="display: flex;flex-wrap: wrap;">
              <div v-for="(url, index) in urls" :key=index style="width: 47%;margin-right: 3%;">
                <div>
                  <el-image :src="url" fit="scale-down" style="margin-bottom: 25px; vertical-align: middle;" :preview-src-list="[url]"/>
                </div>

                <div style="justify-content: center; margin-top: 30px">
                  <div style="height: 50px">
                    <div><el-text style="font-size: 20px">订单号：</el-text></div>
                    <div>
                      <el-text type="primary"  style="font-size: 20px">{{ receiptID[index] }}</el-text>
                    </div>
                  </div>
                  <div style="height: 50px">
                    <div><el-text style="font-size: 20px">作品名称：</el-text></div>
                    <div>
                      <el-text type="primary"  style="font-size: 20px">{{ work_name[index] }}</el-text>
                    </div>
                  </div>

                  <div style="height: 50px">
                    <div><el-text style="font-size: 20px">交易方：</el-text></div>
                    <div>
                      <el-text type="primary" style="font-size: 20px">{{ to[index] }}</el-text>
                    </div>
                  </div>

                  <div style="height: 50px">
                    <div><el-text style="font-size: 20px">交易时间：</el-text></div>
                    <div>
                      <el-text type="primary" style="font-size: 20px">{{ time[index] }}</el-text>
                    </div>
                  </div>

                  <div style="height: 50px">
                    <div><el-text style="font-size: 20px">订单状态：</el-text></div>
                    <div>
                      <el-text type="primary" style="font-size: 20px">{{ status[index] }}</el-text>
                    </div>
                  </div>
                </div>
                <el-divider />
              </div>
          </div>
        </el-main>
      </el-main>
    </div>
  </el-container>
</template>

<style scoped>

</style>
<style>
body{
  margin: 0;
}
#sidebar{
  transition: transform 1.5s ease;
  display: none;
}
</style>
<script setup>
import {useStore} from "@/stores";
import router from "../../router";

import {ElButton, ElMessage, ElMessageBox} from 'element-plus';
import {ArrowDown, Shop, Expand, Fold, Bell} from "@element-plus/icons-vue";
import {onMounted, ref} from "vue";
import {post} from "@/net";

const store = useStore()
const from = ref([])
const to = ref([])
const work_name = ref([])
const receiptID = ref([])
const recordId = ref([])
const urls = ref([])
const time = ref([])
const status = ref([])
// const urls = [
//  'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
//  'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg',
//  'https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg',
//  'https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg',
//  'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg',
//  'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg',
//  'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg',
// ]

// 回 到 首 页
const BackToIndex = () => {
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
onMounted(async () => {
  try {
    const dataList = ref([]);
    post('api/power/look4', {
      from: store.auth.user.username,
      to: store.auth.user.username,
      username: store.auth.user.username
    }, (message) => {
      console.log(message.sa);

      // 根据序号查询message.sa里的相关信息
      for (let index = 0; index < message.sa.length; index++) {
        // 将每个 work_name 添加到 work_name 数组中
        work_name.value.push(message.sa[index].work_name);
        from.value.push(message.sa[index].from);
        to.value.push(message.sa[index].to);
        receiptID.value.push(message.sa[index].receiptID);
        recordId.value.push(message.sa[index].recordId);
        time.value.push(message.sa[index].time);
        urls.value.push(message.sa[index].recordId);
        if(message.sa[index].status === '0'){
          status.value.push("正在交易中");
        }
        if(message.sa[index].status === '2'){
          status.value.push("交易被拒绝");
        }
        if(message.sa[index].status === '1'){
          status.value.push("交易成功");
        }
      }
    });
  } catch (error) {
    console.error('请求失败:', error);
  }
});
// 删 除 我 的 作 品
const delete_my_work = (url) =>{
  ElMessageBox.confirm(
      '您的购买记录将会被删除，将无法找回，是否要继续?',
      'Warning',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(() => {
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '删除已取消',
        })
      })
}

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
