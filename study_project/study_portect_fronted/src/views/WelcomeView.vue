<template>
   <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex">
    <div style="flex: 1">
      <el-image style="width: 100%;height: 100%" fit="cover"
                src="https://img0.baidu.com/it/u=4167836184,3069690856&fm=253&fmt=auto&app=120&f=JPEG?w=1200&h=800"/>
    </div>
     <div class="welcome-title">
       <div style="font-size: 30px;font-weight: bold">欢迎来到图片确权与交易系统</div>
       <div style="margin-top: 10px">在这里你将进行数字资产的确权与交易，方便快捷。</div>
     </div>
    <div style="width: 500px;background-color: white">
      <router-view/>
    </div>

  </div>
</template>

<script setup>

</script>

<style scoped>
.welcome-title{
  position: absolute;
  bottom: 30px;
  left: 30px;
  color: white;
  text-shadow: 0 0 10px black;
}
</style>