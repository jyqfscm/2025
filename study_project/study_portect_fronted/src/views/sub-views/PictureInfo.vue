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

      <el-main style="height: 100%; background-color: white;
                 padding: 50px;box-sizing: border-box;width: calc(100vw - 300px);">
        <el-row style="margin-top: 30px; margin-left: 75px; margin-bottom: 10px;">
          <el-col :span="3" :offset="2">
            <el-text style="font-size: 25px">
              作品 ——
            </el-text>
          </el-col>
          <el-col :span="5" >
            <el-text style="font-size: 25px">
              {{work_name}}
            </el-text>
          </el-col>
        </el-row>

        <el-divider />

        <el-row style="margin-top: 45px; " align="middle">
          <el-col :span="12" :offset="2" >
            <el-image class="rotate-image" :src="$route.query.url_marked"  @dragstart.prevent @contextmenu.prevent @click.prevent ></el-image>
          </el-col>
          <el-col :span="8" :offset="2">
            <el-row>
              <el-col :span="8">
                <el-text style="font-size: 20px">作者ID：</el-text>
              </el-col>
              <el-col :span="16">
                <el-text style="font-size: 20px" type="primary">
                  {{username}}
                </el-text>
              </el-col>
            </el-row>

            <el-row style="margin-top: 50px">
              <el-col :span="8">
                <el-text style="font-size: 20px">发布时间：</el-text>
              </el-col>
              <el-col :span="16">
                <el-text style="font-size: 20px" type="primary">
                  {{ time }}
                </el-text>
              </el-col>
            </el-row>

            <el-row style="margin-top: 50px">
              <el-col :span="8">
                <el-text style="font-size: 20px">标注售价：</el-text>
              </el-col>
              <el-col :span="16">
                <el-text style="font-size: 20px" type="primary">
                  25 RMB
                </el-text>
              </el-col>
            </el-row>

            <el-row style="margin-top: 150px">
              <el-col :span="6">
                <el-button type="success" size="large"
                           style="font-size: 20px; width: 150px; background-color: #f9d54c; color: black"
                           class="borderless"
                           @click="BuyTheWork"
                           round>购 买</el-button>
              </el-col>
              <el-col :span="6" :offset="6">
                <el-button type="danger" size="large"
                           style="font-size: 20px; width: 150px; color: black"
                           class="borderless"
                           @click="BackToIndex"
                           round>返 回</el-button>
              </el-col>
            </el-row>
          </el-col>
        </el-row>

        <el-divider />

      </el-main>
    </div>
  </el-container>
</template>

<script>
export default {
  mounted() {
    console.log(this.$route.query.url_marked);
    console.log(this.$route.query.url);
    let pid = this.$route.query.url
  }
};

</script>

<script setup>
import {useStore} from "@/stores";
import router from "../../router"
import {ElButton, ElMessage, ElMessageBox} from "element-plus";
import {ref, onMounted } from 'vue'
import {post} from "@/net";
import {sm2} from 'sm-crypto'
import {useRoute} from "vue-router";
import {ArrowDown, Lock, Message, Picture, Shop, Star, Unlock, Expand, Fold, Bell} from "@element-plus/icons-vue";
import CryptoJS from "crypto-js";

const store = useStore()
const dataList = ref([]);
const username = ref('');
const work_name = ref('');
const time = ref('');
const markcode = ref('')
const route = useRoute();
const pid = ref('');
// 在onMounted生命周期钩子中设置pid
onMounted(() => {
  pid.value = route.query.url;
  console.log(pid.value); // 输出pid值到控制台
  post("api/fisco/two",{
    username: pid.value
  },(message) =>{
    // 拆分 message 字符串
    const [part1, part2, part3, part4, part5] = message.split('|');
    console.log("Part 1:", part1);
    console.log("Part 2:", part2);
    console.log("Part 3:", part3);
    console.log("Part 4:", part4);
    console.log("Part 5:", part5);
    // username.value = part1;
    // work_name.value = part2;
    // time.value = part3;
    // markcode.value = part4;
  })
});

onMounted(async () => {
  try {
    post('api/power/look1', {
      from: "asd",
      to: "sada"
    }, (message) => {
      console.log(message.sa); // sa里是所有信息
      console.log(message.sb); // sb里是pid信息

      // 根据pid.value查询对应的序号
      const index = message.sb.findIndex(item => item === pid.value);

      if (index !== -1) {
        // 根据序号查询message.sa里的相关信息
        username.value = message.sa[index].username;
        work_name.value = message.sa[index].work_name;
        time.value = message.sa[index].time;
      } else {
        console.error('没有找到对应的pid');
      }
    });
  } catch (error) {
    console.error('请求失败:', error);
  }
});

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
function generateReceiptID(from, to, imagebase, recordId) {
  // Concatenate the parameters into a single string
  const inputString = from + to + imagebase + recordId;

  // Initialize a hash variable with a large prime number
  let hash = BigInt(5381);

  // Compute the hash using a simple but effective algorithm
  for (let i = 0; i < inputString.length; i++) {
    hash = (hash * BigInt(33)) ^ BigInt(inputString.charCodeAt(i));
  }

  // Convert the hash to a positive number and string format
  const hashString = (hash & BigInt("0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF")).toString();

  // Pad the string with zeros if necessary to ensure it has at least 30 digits
  return hashString.padStart(30, '0').substring(0, 30);
}

const BuyTheWork = (url) =>{
  //客户端私钥签名
  ElMessageBox.prompt('请输入助记符', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    customClass: 'my-custom-class',
    //inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
    //inputErrorMessage: '私钥格式不正确'
  }).then(({ value }) => {
    //window.localStorage.setItem("privateKey",value);
    //服务器公钥加密
    var publicKeyHex  = window.localStorage.getItem("publicKeyHex");
    console.log("publicKeyHex:",publicKeyHex)
    var encrText = "Tom";//例如var enxrText = this.phone;
    const cipherMode = 1;
    let decryptData = '04' + sm2.doEncrypt(encrText, publicKeyHex, cipherMode) // 加密结果
    console.log("decryptData:",decryptData)
    //私钥签名
    var zhujifu  = value//私钥加密来签名（用户输入）
    var url = 'https://my-face-home.oss-cn-beijing.aliyuncs.com/zhujifu/' + store.auth.user.username + '.txt';
    //AES解密助记符得到私钥
    // 使用fetch API下载URL对应的文本信息
    fetch(url)
        .then(response => response.text())
        .then(encryptedText => {
          // 使用助记符解密下载的文本信息以得到私钥
          const decrypted = CryptoJS.AES.decrypt(encryptedText, zhujifu);
          const privateKey = decrypted.toString(CryptoJS.enc.Utf8);
          var data =  decryptData;//例如var enxrText = this.phone;
          let signatureData;

          // 验证私钥解密是否成功
          if (privateKey) {
            console.log('私钥解密成功:', privateKey);
            signatureData = sm2.doSignature(data, privateKey, { hash:true}) // 加密结果
          } else {
            console.error('私钥解密失败');
          }

          console.log("signatureData:",signatureData)
          console.log("privateKey:",window.localStorage.getItem("privateKey"))
          console.log("piddddddd:",pid._rawValue)
          post("api/power/trade",{
            pid: pid._rawValue,
            decryptData: decryptData,
            signatureData: signatureData,
            username: store.auth.user.username,
          },(message) =>{
            const receiptID = generateReceiptID(username.value, store.auth.user.username, work_name.value, pid._rawValue);
            post("api/power/addorder",{
              from: store.auth.user.username,
              to: username.value,
              work_name:work_name.value,
              receiptID:receiptID,
              recordId:pid._rawValue,
              time:new Date().toLocaleString(),
              status:'0'
            },(message) =>{
              ElMessage.success("成功")
            })
            ElMessageBox.confirm('申请已发送至卖方，请耐心等待！', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'success'
            }).then(() => {
              // 用户点击了确定按钮，不进行任何操作
            }).catch(() => {
              // 用户点击了取消按钮，不进行任何操作
            })
                .catch(error => {
                  console.error('无法获取加密文本:', error);
                });
          })
        })
    post("api/fisco/three",{
      from: username.value,
      to: store.auth.user.username,
      imagebase:work_name.value,
      receiptID:receiptID,
      recordId:pid._rawValue
    },(message) =>{
    })

  }).catch((err) => {
    console.log(err)
  });

}

const two = () => {


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

<style>
body{
  margin: 0;
}
</style>

<style scoped>
.rotate-image{
  border-radius: 10px;

  box-shadow: rgba(0, 0, 0, 0.2) 0px 0px 36px;
}
.rotate-image:hover{
  transform: rotate(360deg);
}
#sidebar{
  transition: transform 1.5s ease;
  display: none;
}

.my-custom-class .el-message-box {
  width: 500px; /* 设置弹出框的宽度 */
  max-width: 80%; /* 设置最大宽度为80% */
}

.my-custom-class .el-message-box__wrapper {
  height: 300px; /* 设置弹出框的高度 */
  max-height: 80%; /* 设置最大高度为80% */
}

</style>


