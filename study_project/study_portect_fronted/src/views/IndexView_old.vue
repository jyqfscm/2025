<template>
  <div>
    <div v-if="store.auth.user">{{ store.auth.user.username }}，欢迎进入平台</div>
    <el-container class="layout-container-demo" style="margin-top: 20px; height: 500px">
      <el-aside width="200px">
        <el-scrollbar>
          <el-menu :default-openeds="['1', '3']">
            <el-sub-menu index="1">
              <template #title>
                <el-icon><message /></el-icon>选择功能
              </template>
              <el-menu-item-group>
                <el-menu-item @click="selectCategory('class1')" index="1-1">加水印</el-menu-item>
                <el-menu-item @click="selectCategory('class2')" index="1-2">解水印</el-menu-item>
                <el-menu-item @click="selectCategory('class3')" index="1-3">返回首页</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
            <el-sub-menu index="2">
              <template #title>
                <el-icon><icon-menu /></el-icon>更多
              </template>
              <el-menu-item-group>
                <el-menu-item @click="selectCategory('pattern2')" index="2-1">联系我们</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
          </el-menu>
        </el-scrollbar>
      </el-aside>
      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <div class="toolbar">
            <span>{{ store.auth.user.username }}</span>
          </div>
        </el-header>
        <el-main v-if="number === 0">
          <div style="font-size: 25px; font-weight: bold">欢迎使用我们的平台</div>
          <div style="margin-top: 10px">请选择你需要的功能！</div>
          <div>
            <el-button style="margin-top: 50px" @click="one()" type="success" plain>上传信息</el-button>
          </div>
          <div>
            <el-button style="margin-top: 50px" @click="two()" type="success" plain>查询信息</el-button>
          </div>
          <div>
            <el-button style="margin-top: 50px" @click="three()" type="success" plain>上传交易记录</el-button>
          </div>
        </el-main>
        <el-main v-if="number === 1">
          <div style="margin-top: 10px">
            注：每次只能选择一张图片！
            <el-form :model="form" :rules="rules" ref="formRef">
              <div style="margin-top: 20px">
                <el-form-item prop="pathvalue">
                  <el-input v-model="form.pathvalue" placeholder="请输入水印图片保存路径" style="width: 800px; height: 40px;"></el-input>
                </el-form-item>
                <el-form-item prop="markcode">
                  <el-input v-model="form.markcode" placeholder="请输入水印序列" style="width: 800px; height: 40px;"></el-input>
                </el-form-item>
                <el-form-item prop="filename">
                  <el-input v-model="form.filename" placeholder="请输入水印序列" style="width: 800px; height: 40px;"></el-input>
                </el-form-item>
              </div>
            </el-form>
            <el-upload
                class="upload-demo"
                drag
                action="http://localhost:8080/api/file/getpath"
                :on-success="handleSuccess"
                :before-upload="beforeUpload"
                :multiple="false"
                accept="image/jpeg,image/png"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
            <div>
              <el-button @click="showPath">加水印</el-button>
              <el-button @click="downloadImage(form.filename)">下载水印图片</el-button>
            </div>
          </div>
        </el-main>
        <el-main v-if="number === 2">
          <div style="margin-top:10px">
            注：请输入你要解码的图片，每次只能选择一张图片！
            <el-form :model="form" :rules="rules" ref="formRef">
              <div style="margin-top:20px">
                <el-form-item  prop="markcode">
                  <el-input v-model="form.markcode" placeholder="请输入水印序列" style="width: 800px; height: 40px;"></el-input>
                </el-form-item>
              </div>
            </el-form>

            <el-upload
                class="upload-demo"
                drag
                action="http://localhost:8080/api/file/getdecodepath"
                :on-success="handleSuccess"
                :before-upload="beforeUpload"
                :multiple="false"
                accept="image/jpeg,image/png"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
            <div class="button-row">
              <el-button @click="showDecodePath">立即获取解码结果</el-button>
              <el-button @click="saveSelection">保存选区</el-button>
              <el-button @click="savePoints">保存选择角点</el-button>
            </div>
          </div>
          <div>
            <div v-if="showImage" style="position: relative;">
              <div ref="imageContainer" style="position: relative;">
                <img :src="imageUrl" style="max-width: none;user-select: none;" draggable="false" @mousedown="startSelection" @mousemove="adjustSelection" @mouseup="finishSelection">
                <div
                    v-for="(region, index) in regions"
                    :key="index"
                    style="position: absolute; border: 2px solid red;"
                    :style="{ left: region.left + 'px', top: region.top + 'px', width: region.width + 'px', height: region.height + 'px' }"
                ></div>
              </div>
              <div v-if="parsedCoordinates.length > 0" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%;">
                <div
                    v-for="(coord, index) in parsedCoordinates"
                    :key="index"
                    class="mark-point"
                    :style="{ left: coord[0] + 'px', top: coord[1] + 'px' }"
                    @click="handlePointClick(coord)"
                ></div>
              </div>
          </div>
          </div>
        </el-main>
        <el-main v-if="number === 3">
          <div style="margin-top: 10px">
            <el-form :model="form" :rules="rules" ref="formRef">
              <div style="margin-top: 20px">
                <el-form-item prop="callback">
                  <el-input v-model="form.callback" type="text" placeholder="请写出您的问题或建议" style="width: 300px; height: 300px;"></el-input>
                </el-form-item>
              </div>
            </el-form>
          </div>
          <div>
            <el-button style="margin-top: 10px" @click="upload()" type="success" plain>提交</el-button>
          </div>
        </el-main>
      </el-container>
    </el-container>
    <div>
      <el-button style="margin-top: 50px" @click="logout()" type="danger" plain>退出登录</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import router from '@/router';
import { useStore } from '@/stores';
import { post } from '@/net';
import { onMounted, onBeforeUnmount } from 'vue';

const number = ref(0);
const store = useStore();
let imageUrl = '';
const form = ref({
  pathvalue: '',
  markcode: '',
  callback: '',
});
const regions = ref([]);
const imageContainer = ref(null); // 引用新的绘制层容器
const showImage = ref(false);
const parsedCoordinates = ref([]);
const selectedPoints = ref([]);
let selecting = false;
let startX = 0;
let startY = 0;

const handlePointClick = (coord) => {
  // 处理点击标记点的逻辑，例如保存坐标等
  if (selectedPoints.value.length < 4) {
    selectedPoints.value.push(coord);
    // if (selectedPoints.value.length === 4) {
    //   // 执行保存四个点的逻辑
    //   savePoints(selectedPoints.value);
    // }
    if (selectedPoints.value.length > 4) {
      selectedPoints.value.shift();
      // saveSelection();

    }
  }
};

const savePoints = (points) => {
  console.log('Selected points:', selectedPoints.value);
  if(selectedPoints.value.length === 4)
  {
  post('api/file/savepoints', {
    savePoints: JSON.stringify(selectedPoints.value),
  }, (message) => {
    ElMessage.success("成功");
  });
  // 清空已选择的点，以便下一次选择
  selectedPoints.value = [];
  }
  else{
    console.log("Only",selectedPoints.value.length)
  }
};

// 在 setup 函数中添加事件监听器
onMounted(() => {
  document.addEventListener('mousedown', startSelection);
  document.addEventListener('mousemove', adjustSelection);
  document.addEventListener('mouseup', finishSelection);
});

// 在 unmounted 阶段移除事件监听器
onBeforeUnmount(() => {
  document.removeEventListener('mousedown', startSelection);
  document.removeEventListener('mousemove', adjustSelection);
  document.removeEventListener('mouseup', finishSelection);
});



const startSelection = (event) => {
  selecting = true;
  startX = event.offsetX;
  startY = event.offsetY;
};

const adjustSelection = (event) => {
  if (selecting) {
    const currentX = event.offsetX;
    const currentY = event.offsetY;
    const left = Math.min(startX, currentX);
    const top = Math.min(startY, currentY);
    const width = Math.abs(startX - currentX);
    const height = Math.abs(startY - currentY);
  }
};

const finishSelection = (event) => {
  if (selecting) {
    const currentX = event.offsetX;
    const currentY = event.offsetY;
    const left = Math.min(startX, currentX);
    const top = Math.min(startY, currentY);
    const width = Math.abs(startX - currentX);
    const height = Math.abs(startY - currentY);
    selecting = false;
    if (width !== 0 && height !== 0) {
      regions.value.push({left, top, width, height});
      if (regions.value.length > 4) {
        regions.value.shift();
        // saveSelection();

      }
    }
  }
};


const selectCategory = (category) => {
  switch (category) {
    case 'class1':
      number.value = 1;
      break;
    case 'class2':
      number.value = 2;
      break;
    case 'class3':
      number.value = 0;
      break;
    case 'pattern2':
      number.value = 3;
      break;
    default:
      break;
  }
};

const logout = () => {
  post('api/auth/logout', (message) => {
    ElMessage.success(message);
    store.auth.user = null;
    router.push('/');
  });
};

const beforeUpload = (file) => {
  const isJPG = file.type === 'image/jpeg';
  const isPNG = file.type === 'image/png';
  const isLt500K = file.size / 1024 < 500;

  if (!isJPG && !isPNG) {
    ElMessage.error('只能上传 jpg/png 文件!');
    return false;
  }
  // if (!isLt500K) {
  //   ElMessage.error('文件大小不能超过 500KB!');
  //   return false;
  // }
  return true;
};

const handleSuccess = (response, file) => {
  imageUrl = URL.createObjectURL(file.raw);
  showImage.value = true;
};

const validatePath = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入路径'));
  } else if (!/^[a-zA-Z]:[\\/](?:[\w\s]+[\\/])*[\w\s]+$/.test(value)) {
    callback(new Error('路径示例（C:/Program Files/Microsoft/Example）'));
  } else {
    callback();
  }
};

const validateCode = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入水印序列'));
  } else if (!/^\d+$/.test(value)) {
    callback(new Error('水印序列必须是数字'));
  } else if (value < 0 || value > 666) {
    callback(new Error('水印序列必须在0-666之间'));
  } else {
    callback();
  }
};

const rules = {
  pathvalue: [{ validator: validatePath, trigger: ['blur', 'change'] }],
  markcode: [{ validator: validateCode, trigger: ['blur', 'change'] }],
  callback: [{ required: true, trigger: ['blur', 'change'] }, { max: 50, message: '最多50字', trigger: ['blur', 'change'] }],
};

const formRef = ref();



const saveSelection = () => {
  if (regions.value.length === 4) {
    // Send the regions to your backend
    console.log('Selected regions:', JSON.stringify(regions.value));
    post('api/file/getarea', {
      regionvalue: JSON.stringify(regions.value),
    }, (message) => {
      ElMessage.success("成功");

      // Split the message by newline character
      const lines = message.split('\n');
      const coords = [];
      lines.forEach(line => {
        // Split each line by whitespace to get individual coordinates
        const lineCoords = line.split(' ').map(coord => parseFloat(coord));
        coords.push(lineCoords);
      });

      console.log('Parsed coordinates:', coords);
      // Update parsedCoordinates
      parsedCoordinates.value = coords;
    });
  }
};

const downloadImage = async (fileName) => {
  try {
    const response = await fetch(`api/file/download`);

    if (!response.ok) {
      throw new Error('Failed to download image');
    }
    console.log(response)

    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = fileName;
    document.body.appendChild(a);
    a.click();
    window.URL.revokeObjectURL(url);
    document.body.removeChild(a);
  } catch (error) {
    console.error('Error downloading image:', error);
  }
};






const showPath = () => {
  formRef.value.validate(async (isValid) => {
    if (isValid) {
      post('api/file/upload', {
        pathvalue: form.value.pathvalue,
        markcode: form.value.markcode,
      }, (message) => {
        ElMessage.success(message);
      });
    } else {
      ElMessage.warning('请输入完整表单内容');
    }
  });
};

const showDecodePath = () => {
  formRef.value.validate(async (isValid) => {
    if (isValid) {
      post('api/file/decodeupload', {
        pathvalue: form.value.pathvalue,
        markcode: form.value.markcode,
      }, (message) => {
        ElMessage.success(message);
      });
    } else {
      ElMessage.warning('请输入完整表单内容');
    }
  });
};

const upload = () => {
  formRef.value.validate(async (isValid) => {
    if (isValid) {
      post('api/file/callback', {
        callback: form.value.callback,
      }, (message) => {
        ElMessage.success(message);
      });
    } else {
      ElMessage.warning('请输入完整表单内容');
    }
  });
};

const one = () => {
  console.log(store.auth.user.username )
  post("api/fisco/one",{
    imagebase: "ss",
    username: store.auth.user.username
  },(message) =>{
    ElMessage.success(message)
  })

};
const two = () => {
  post("api/fisco/two",{
    username: store.auth.user.username
  },(message) =>{
    ElMessage.success(message)
  })

};
const three = () => {
  post("api/fisco/three",{
    from: store.auth.user.username,
    to: "不知道",
    imagebase:"ss"
  },(message) =>{
    ElMessage.success(message)
  })

};

</script>

<style scoped>
.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}
.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}
.layout-container-demo .el-menu {
  border-right: none;
}
.layout-container-demo .el-main {
  padding: 0;
}
.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
.mark-point {
  position: absolute;
  width: 10px;
  height: 10px;
  background-color: red;
  border-radius: 50%;
}
</style>
