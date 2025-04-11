<template>
  <div>
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
              style="font-size: 30px; font-family: '华文楷体'; color: black;"
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
          <el-badge :value="messageCount" class="item" @click="selectCategory('Message')">
            <el-button
                class="borderless"
                style="background-color: #f9d54c; color: black;"
                :icon="Bell"
                round
            />
          </el-badge>
      </el-col>

      <el-col :span="1" style="display: flex; align-items: center; justify-content: center;">
        
      <!-- </el-col>
      <el-col :span="1" style="display: flex; align-items: center;"> -->
        <el-dropdown trigger="click">
          
          <span style="font-size: 18px;color: #000;margin-left: 45px;display: flex;align-items: center;">
            {{store.auth.user.username}}
            <el-avatar v-if="store.auth.user.username === 'liuyuang'" src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg" />
            <el-avatar v-else src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
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

  <div class="layout-container-demo" style="margin-bottom: 0;background-color: white; display: flex;">
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
          <el-sub-menu index="1">
            <template #title>
              <el-icon style="font-size: 500px"><Shop /></el-icon>
              <el-text
                  style="color: #000; margin-left: 20px; font-size: 25px; font-family: '华文楷体';"
                  size="large"
              >
                图 片 商 城
              </el-text>
            </template>

            <el-sub-menu @click="selectCategory('Main Class')" index="1-1">
              <template #title>
                <el-icon style="font-size: 400px"><Picture /></el-icon>
                 <el-text
                    style="color: #000; margin-left: 20px; font-size: 18px ;font-family: '华文楷体';"
                    size="large"
                >
                  图 片 分 类
                 </el-text>
              </template>
              <el-menu-item-group>
                <el-menu-item @click="selectCategory('Class 0')" index="1-1-1">
                   <el-text
                      style="color: #000; margin-left: 40px ; font-size: 18px ;font-family: '华文楷体';"
                      size="large"
                  >
                  全 部 分 类
                  </el-text>
                </el-menu-item>
                <el-menu-item @click="selectCategory('Class A')" index="1-1-1">
                   <el-text
                      style="color: #000; margin-left: 40px ; font-size: 18px ;font-family: '华文楷体';"
                      size="large"
                  >
                    摄 影 照 片
                   </el-text>
                </el-menu-item>

                <el-menu-item @click="selectCategory('Class B')" index="1-1-2">
                   <el-text
                      style="color: #000; margin-left: 40px ; font-size: 18px ;font-family: '华文楷体';"
                      size="large"
                  >
                    人 物 肖 像
                   </el-text>
                </el-menu-item>

                <el-menu-item @click="selectCategory('Class C')" index="1-1-3">
                  <el-text
                      style="color: #000; margin-left: 40px ; font-size: 18px ;font-family: '华文楷体';"
                      size="large"
                  >
                    自 然 风 景
                  </el-text>
                </el-menu-item>

                <el-menu-item @click="selectCategory('Class D')" index="1-1-4">
                   <el-text
                      style="color: #000; margin-left: 40px ; font-size: 18px ;font-family: '华文楷体';"
                      size="large"
                  >
                    卡 通 手 绘
                   </el-text>
                </el-menu-item>

                <el-menu-item @click="selectCategory('Class E')" index="1-1-5">
                   <el-text
                      style="color: #000; margin-left: 40px ; font-size: 18px ;font-family: '华文楷体';"
                      size="large"
                  >
                    背 景 图 片
                   </el-text>
                </el-menu-item>

                <el-menu-item @click="selectCategory('Class F')" index="1-1-6">
                   <el-text
                      style="color: #000; margin-left: 40px ; font-size: 18px ;font-family: '华文楷体';"
                      size="large"
                  >
                    人 文 纪 实
                   </el-text>
                </el-menu-item>

                <el-menu-item @click="selectCategory('Class G')" index="1-1-7">
                   <el-text
                      style="color: #000; margin-left: 40px ; font-size: 18px ;font-family: '华文楷体';"
                      size="large"
                  >
                    异 国 风 情
                   </el-text>
                </el-menu-item>

                <el-menu-item @click="selectCategory('Class H')" index="1-1-8">
                   <el-text
                      style="color: #000; margin-left: 40px ; font-size: 18px ;font-family: '华文楷体';"
                      size="large"
                  >
                    生 活 记 录
                   </el-text>
                </el-menu-item>

              </el-menu-item-group>
            </el-sub-menu>
          </el-sub-menu>

          <el-sub-menu index="3">
            <template #title>
              <el-icon style="font-size: 500px"><Star /></el-icon>
              <el-text
                  style="color: #000; margin-left: 20px; font-size: 25px; font-family: '华文楷体';"
                  size="large"
              >
                产 权 保 护 相 关
              </el-text>
            </template>
            <el-menu-item-group>
<!--              <el-menu-item @click="selectCategory('Class I')" index="3-1">区块链</el-menu-item>-->
              <el-menu-item @click="selectCategory('Class J')" index="3-2">
                <el-icon  style="font-size: 500px"><Lock /></el-icon>
                 <el-text
                    style="color: #000; margin-left: 20px ; font-size: 18px ;font-family: '华文楷体';"
                    size="large"
                >
                  嵌 入 水 印
                 </el-text>
              </el-menu-item>
              <el-menu-item @click="selectCategory('Class K')" index="3-3">
                <el-icon  style="font-size: 500px"><Unlock /></el-icon>
                 <el-text
                    style="color: #000; margin-left: 20px ; font-size: 18px ;font-family: '华文楷体';"
                    size="large"
                >
                  解 码 水 印
                 </el-text>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>

          <el-sub-menu index="2">
            <template #title>
              <el-icon style="font-size: 500px"><icon-menu /></el-icon>
              <el-text
                  style="color: #000; margin-left: 20px; font-size: 25px; font-family: '华文楷体';"
                  size="large"
              >
                更 多
              </el-text>
            </template>
            <el-menu-item-group>
              <el-menu-item @click="selectCategory('ContactUs')" index="2-1">
                <el-icon  style="font-size: 500px"><Message /></el-icon>
                <el-text
                    style="color: #000; margin-left: 20px ; font-size: 18px ;font-family: '华文楷体';"
                    size="large"
                >
                  联 系 我 们
                </el-text>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>

        </el-menu>

      </el-scrollbar>
    </div>

    <div style="flex: 1;">
      <div
          style="padding-top:20px ;padding-left: 5px;"
          v-if="number === 0 || number === 1 || number === 2 || number === 3 || number === 4 ||
                number === 5 || number === 6 || number === 7 || number === 8">
        <div style="display: flex;justify-content: center;">

          <el-input
              class="input-Search"
              v-model.lazy.trim="searchKeyword"
              placeholder="请输入作品名或卖方名进行搜索"
              clearable
              style="width: 600px;"
              round
          />
          <el-button
              class="borderless"
              style="width:100px ;margin-left: 5px; background-color: #f9d54c; color: black"
              round
              @click="search"
          >
            <el-text size="large">搜 索</el-text>
          </el-button>

          </div>
      <!--  商城主页加载图片  -->
      <el-main v-if="number === 0" style="background-color: white;height: calc(100vh - 200px);margin: 20px 0;display: flex;
  flex-wrap: wrap;align-items: center;width: 100%;">
        <!-- <el-row>
          <template v-for="url in urls" :key="url">
            <el-col :span="7" :offset="1" class="styleEffect">
              <el-image :src="url" @click="toPictureInfo(url)"
                        style="margin-top: 30px ;margin-bottom: 30px" @dragstart.prevent />
            </el-col> -->
<!--            <el-col :span="9" :offset="2">
              <div style="margin-top: 30px; margin-bottom: 30px;">
                <el-image :src="item.url" @click="toPictureInfo(item.url)" @dragstart.prevent />
                <div class="image-name" style="text-align: center; margin-top: 10px;">{{ item.name }}</div>
              </div>
            </el-col>-->
          <!-- </template>
        </el-row> -->
        <div v-for="url in filteredUrls" :key="url" class="styleEffect" style="position: relative; text-align: center; margin-top: 20px;">
          <el-image :src="url.url_marked" @click="toPictureInfo(url.url,url.url_marked)"
                    style="margin: 0 auto; border-radius: 10px;" fit="scale-down" @dragstart.prevent  @contextmenu.prevent />
          <div  style="position: absolute; width: 100%; bottom: -25px; left: 0;font-size: 18px">{{ url.name }}</div>
        </div>
      </el-main>


      <el-main v-if="number === 1" style="background-color: white; height: calc(100vh - 200px);margin: 20px 0;display: flex;
  flex-wrap: wrap;align-items: center;width: 100%;">
        <div v-for="url in filteredUrls" :key="url" class="styleEffect" style="position: relative; text-align: center; margin-top: 20px;">
          <el-image :src="url.url_marked" @click="toPictureInfo(url.url,url.url_marked)"
                    style="margin: 0 auto; border-radius: 10px;" fit="scale-down" @dragstart.prevent @contextmenu.prevent />
          <div style="position: absolute; width: 100%; bottom: -25px; left: 0;font-size: 18px">{{ url.name }}</div>
        </div>
        <!-- <el-row>
          <template v-for="url in urls1" :key="url">
            <el-col :span="6" :offset="1" class="styleEffect">
              <el-image :src="url" @click="toPictureInfo(url)"
                        style="margin-top: 30px ;margin-bottom: 30px" @dragstart.prevent />
            </el-col>
          </template>
        </el-row> -->
      </el-main>


      <el-main
          v-if="number === 2"
          style="background-color: white;height: calc(100vh - 200px);margin: 20px 0;display: flex; align-items: center;
  flex-wrap: wrap;width: 100%;">
        <div v-for="url in filteredUrls" :key="url" class="styleEffect" style="position: relative; text-align: center; margin-top: 20px;">
          <el-image :src="url.url_marked" @click="toPictureInfo(url.url,url.url_marked)"
                    style="margin: 0 auto; border-radius: 10px;" fit="scale-down" @dragstart.prevent @contextmenu.prevent/>
          <div style="position: absolute; width: 100%; bottom: -25px; left: 0;font-size: 18px">{{ url.name }}</div>
        </div>
      </el-main>


      <el-main v-if="number === 3" style="background-color: white;height: calc(100vh - 200px);margin: 20px 0;display: flex; align-items: center;
  flex-wrap: wrap;width: 100%;">
        <div v-for="url in filteredUrls" :key="url" class="styleEffect" style="position: relative; text-align: center; margin-top: 20px;">
          <el-image :src="url.url_marked" @click="toPictureInfo(url.url,url.url_marked)"
                    style="margin: 0 auto; border-radius: 10px;" fit="scale-down" @dragstart.prevent @contextmenu.prevent/>
          <div style="position: absolute; width: 100%; bottom: -25px; left: 0;font-size: 18px">{{ url.name }}</div>
        </div>
      </el-main>


      <el-main v-if="number === 4" style="background-color: white;height: calc(100vh - 200px);margin: 20px 0;display: flex;align-items: center;
  flex-wrap: wrap;width: 100%;">
        <div v-for="url in filteredUrls" :key="url" class="styleEffect" style="position: relative; text-align: center; margin-top: 20px;">
          <el-image :src="url.url_marked" @click="toPictureInfo(url.url,url.url_marked)"
                    style="margin: 0 auto; border-radius: 10px;" fit="scale-down" @dragstart.prevent @contextmenu.prevent/>
          <div style="position: absolute; width: 100%; bottom: -25px; left: 0;font-size: 18px">{{ url.name }}</div>
        </div>
      </el-main>


      <el-main v-if="number === 5" style="background-color: white;height: calc(100vh - 200px);margin: 20px 0;display: flex;align-items: center;
  flex-wrap: wrap;width: 100%;">
        <div v-for="url in filteredUrls" :key="url" class="styleEffect" style="position: relative; text-align: center; margin-top: 20px;">
          <el-image :src="url.url" @click="toPictureInfo(url.url,url.url_marked)"
                    style="margin: 0 auto; border-radius: 10px;" fit="scale-down" @dragstart.prevent @contextmenu.prevent/>
          <div style="position: absolute; width: 100%; bottom: -25px; left: 0;font-size: 18px">{{ url.name }}</div>
        </div>
      </el-main>


      <el-main v-if="number === 6" style="background-color: white;height: calc(100vh - 200px);margin: 20px 0;display: flex;align-items: center;
  flex-wrap: wrap;width: 100%;">
        <div v-for="url in filteredUrls" :key="url" class="styleEffect" style="position: relative; text-align: center; margin-top: 20px;">
          <el-image :src="url.url_marked" @click="toPictureInfo(url.url,url.url_marked)"
                    style="margin: 0 auto; border-radius: 10px;" fit="scale-down" @dragstart.prevent @contextmenu.prevent/>
          <div style="position: absolute; width: 100%; bottom: -25px; left: 0;font-size: 18px">{{ url.name }}</div>
        </div>
      </el-main>


      <el-main v-if="number === 7" style="background-color: white;height: calc(100vh - 200px);margin: 20px 0;display: flex;align-items: center;
  flex-wrap: wrap;width: 100%;">
        <div v-for="url in filteredUrls" :key="url" class="styleEffect" style="position: relative; text-align: center; margin-top: 20px;">
          <el-image :src="url.url_marked" @click="toPictureInfo(url.url,url.url_marked)"
                    style="margin: 0 auto; border-radius: 10px;" fit="scale-down" @dragstart.prevent @contextmenu.prevent/>
          <div style="position: absolute; width: 100%; bottom: -25px; left: 0;font-size: 18px">{{ url.name }}</div>
        </div>
      </el-main>


      <el-main v-if="number === 8" style="background-color: white;height: calc(100vh - 200px);margin: 20px 0;display: flex;align-items: center;
  flex-wrap: wrap;width: 100%;">
        <div v-for="url in filteredUrls" :key="url" class="styleEffect" style="position: relative; text-align: center; margin-top: 20px;">
          <el-image :src="url.url_marked" @click="toPictureInfo(url.url,url.url_marked)"
                    style="margin: 0 auto; border-radius: 10px;" fit="scale-down" @dragstart.prevent @contextmenu.prevent/>
          <div style="position: absolute; width: 100%; bottom: -25px; left: 0;font-size: 18px">{{ url.name }}</div>
        </div>
      </el-main>

        <el-row style="display: flex;justify-content: center;">
          <el-pagination :span="12"
                         background
                         layout="total, prev, pager, next, jumper"
                         :total="total"
                         :page-size="pageSize"
                         :current-page.sync="currentPage"
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
          ></el-pagination>
        </el-row>

      </div>

      <el-main v-if="number === 9" style=" padding: 50px;height: calc(100vh - 75px);width: 100%;">

          <el-row >
            <el-col :span="24" style="display: flex;align-items: center;">
              <el-icon size="large"><PhoneFilled /></el-icon>
              <el-text size="large" style="color: #545c64; margin-left: 20px">
                请将密钥填写在下方的文本框中！
              </el-text>
            </el-col>
          </el-row>
          <el-row style="margin-top: 50px">
            <el-col :span="24">
              <el-form :model="form" :rules="rules" ref="formRef">
                <el-form-item
                    prop="callback"
                >
                  <el-input v-model="form.callback" type="textarea" placeholder="请输入密钥信息" show-word-limit maxlength="3000" :autosize="{minRows: 4,maxRows: 10}"
                            >
                  </el-input>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24" style="display:flex;justify-content: center;">
              <el-button
                  class="borderless"
                  style="margin-top: 10px; width: 180px; margin-right: 20px; background-color: #f9d54c; color: black"
                  @click="feedback_upload()"
                  type="primary"
                  round
              >
                <el-icon size="large" type="primary" style="margin-left: -25px;margin-right: 15px;"><Promotion /></el-icon>
                <!-- <el-text
                    size="large"
                    style="margin-left: 15px;"
                    plain
                > -->
                  提 交 回 馈
                <!-- </el-text> -->
              </el-button>
            </el-col>
          </el-row>

      </el-main>




      <el-main
          v-if="number === 10"
          style=" padding: 50px;height: calc(100vh - 75px);width: 100%;"
      >
        <div style="font-size: 25px; font-weight: bold">区块链交互</div>
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



      <el-main
          v-if="number === 11"
          style="
                  padding: 50px;height: calc(100vh - 75px);width: 100%;"
      >
        <div>
          <el-text size="large">
            请按下列步骤将水印序列嵌入图片
          </el-text>
          <el-text type="info">（注：每次只能选择一个图片）</el-text>
          <el-form
              :model="form"
              :rules="rules"
              ref="formRef"
              style="margin-top: 20px"
          >
            <el-text size="large">Step 1 --> 请输入水印图片保存路径</el-text>
            <el-form-item prop="pathvalue">
              <el-input
                  v-model="form.pathvalue"
                  placeholder="C:\Users\86187\Desktop"
                  style="width: 80%; height: 40px; margin-top: 20px; margin-left: 82px"
              ></el-input>
            </el-form-item>

<!--            <el-text size="large">Step 2 &ndash;&gt; 请输入水印序列</el-text>-->
<!--            <el-form-item prop="markcode">-->
<!--              <el-input-->
<!--                  v-model="form.markcode"-->
<!--                  style="width: 80%; height: 40px; margin-top: 20px; margin-left: 82px"-->
<!--              ></el-input>-->
<!--            </el-form-item>-->

            <el-text size="large">Step 2 --> 请输入图片名称</el-text>
            <el-form-item prop="filename">
              <el-input
                  v-model="form.filename"
                  style="width: 80%; height: 40px; margin-top: 20px; margin-left: 82px"
              ></el-input>
            </el-form-item>
          </el-form>

          <el-text size="large">Step 3 --> 请将图片文件拖入下栏中</el-text>
          <el-upload
              class="upload-demo"
              drag
              action="http://localhost:8080/api/file/getpath"
              :on-success="handleSuccessencode"
              :before-upload="beforeUpload"
              :multiple="false"
              accept="image/jpeg,image/png"
              style="margin-top: 20px;margin-left: 82px;margin-right: 25px;width: 80%;"
          >
            <img v-if="imageUrlencode" :src="imageUrlencode" class="avatar">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
          <div style="margin-left: 400px; margin-top: 50px">
            <el-button
                class="borderless"
                size="large"
                type="primary"
                @click="showPath"
                style="width: 250px; background-color: #f9d54c; color: black"
                round
            >
              确 定 嵌 入 水 印
            </el-button>
            <Loading v-if="isLoading"></Loading>
<!--            <el-button-->
<!--                size="large"-->
<!--                type="success"-->
<!--                @click="downloadImage(form.filename)"-->
<!--                style="width: 250px; margin-left: 280px; "-->
<!--            >-->
<!--              下 载 水 印 图 片-->
<!--            </el-button>-->
          </div>
        </div>

      </el-main>



      <el-main
          v-if="number === 12"
          style=" padding: 50px;height: calc(100vh - 75px);width: 100%;"
      >
        <div>
          <el-text size="large" type="info">
            请按下列步骤解码图片
          </el-text>
          <el-text type="info">（注：每次只能选择一个图片）</el-text>
          <el-form :model="form" :rules="rules" ref="formRef">
            <div style="margin-top:20px">
              <!--              <el-form-item  prop="markcode">-->
              <!--                <el-input v-model="form.markcode" placeholder="请输入水印序列" style="width: 800px; height: 40px;"></el-input>-->
              <!--              </el-form-item>-->
            </div>
          </el-form>
        <div>
<!--          <el-text size="large">Step 1 &ndash;&gt; 请输入要验证的水印序列</el-text>-->
<!--          <el-form-item prop="markcode">-->
<!--            <el-input-->
<!--                v-model="form.demarkcode"-->
<!--                style="width: 1100px; height: 40px; margin-top: 20px; margin-left: 82px"-->
<!--            ></el-input>-->
<!--          </el-form-item>-->
          <el-text size="large">Step 1 --> 请将需要解码的图片文件拖入下栏中： </el-text>

          <el-upload
              class="upload-demo"
              drag
              action="http://localhost:8080/api/file/getdecodepath"
              :on-success="handleSuccessdecode"
              :before-upload="beforeUpload"
              :multiple="false"
              accept="image/jpeg,image/png"
              style="margin-top: 20px;"
          >
            <img v-if="imageUrldecode" :src="imageUrldecode" class="avatar">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>

          <el-col :span="12" style="text-align: left">
            <el-checkbox v-model="form.ifmodified" label="上传的图片不需要处理"/>
          </el-col>

          <div class="button-row" style="margin-top: 50px;display: flex;justify-content: center;">

            <el-button
                @click="saveSelection"
                style="margin-left: 50px"
                type="primary"
                size="large"
            >
              保 存 当 前 选 区
            </el-button>

            <el-button
                @click="savePoints"
                style="margin-left: 50px"
                type="primary"
                size="large"
            >
              保 存 选 择 角 点
            </el-button>
            <el-button
                @click="showDecodePath"
                style="margin-left: 50px"
                type="success"
                size="large"
            >
              获 取 解 码 结 果
            </el-button>
            <Loading v-if="isLoading"></Loading>
          </div>
        </div>
        </div>
        <div>
          <div v-if="showImage" style="position: relative;">
            <div ref="imageContainer" style="position: relative;">
              <img :src="imageUrldecode" style="max-width: none;user-select: none;" draggable="false" @mousedown="startSelection" @mousemove="adjustSelection" @mouseup="finishSelection">
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

      <el-main v-if="number === 13"
               style=" margin:50px auto;height: calc(100vh - 175px);width: calc(100vw - 400px);">
          <el-table :data="messages" style="width: 100%;">
            <el-table-column prop="from" label="买方" width="100" align="center"/>
            <el-table-column prop="to" label="卖方" align="center"/>
            <el-table-column label="内容" align="center">
              <template #default="scope">
                <el-image
                    style="width: 100px; height: auto;"
                    :src="scope.row.pid"
                    fit="cover"
                    :preview-src-list="[scope.row.pid]" @dragstart.prevent />

              </template>
            </el-table-column>
            <!--
            <el-table-column label="Actions">
              <template #default="scope">
                <el-button size="small" type="success" @click="AgreeMessage">同意申请</el-button>
              </template>
            </el-table-column>
            <el-table-column label="Actions">
              <template #default="scope">
                <el-button size="small" type="danger" @click="RefuseMessage">拒绝申请</el-button>
              </template>
            </el-table-column> -->
            <el-table-column label="Actions" align="center">
              <template #default="scope">
                <div v-if="scope.row.if_agree === '0'">
                  <el-button size="small" type="success" @click="AgreeMessage(scope.row.pid)">同意申请</el-button>
                  <el-button size="small" type="danger" @click="RefuseMessage(scope.row.pid)">拒绝申请</el-button>
                </div>
                <div v-else-if="scope.row.if_agree === '1'">
                  <p>您的申请已通过，请点击下载</p>
                  <el-button size="small" type="success" @click="download(scope.row.pid)">下载</el-button>
                </div>
                <div v-else>
                  <p>您的申请被卖方拒绝了。</p>
                </div>
              </template>
            </el-table-column>
          </el-table>
        <div class="button-container">
          <el-button round @click="toIndex">回到首页</el-button>
        </div>
      </el-main>

    </div>
  </div>
  </div>
</template>

<style scoped>
#sidebar{
  transition: transform 1.5s ease;
  display: none;
}
.borderless {
  border: none;
  box-shadow: none;
  font-weight: bold;
}
.input-Search {
  border-radius: 100%;
}
.styleEffect{
  cursor: pointer;
  transition-duration: 0s;
  background-color: #ffffff;
  border-radius: 10px;
  margin: 10px 2%;
  width: 21%;
  height: 35%;
  display: flex;
  box-shadow: rgba(0, 0, 0, 0.2) 0px 0px 36px;
}
.styleEffect:hover{
  box-shadow: rgba(0, 0, 0, 0.4) 0px 0px 36px;
}
.image-name {
  font-family: 'Arial', sans-serif; /* 替换为你喜欢的艺术字体 */
  font-size: 1.3em; /* 调整字体大小 */
  color: #333; /* 字体颜色 */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
  font-weight: bold; /* 加粗字体 */
  letter-spacing: 1px; /* 字母间距 */
  line-height: 1.4; /* 行高 */
}
.button-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
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
.el-main {
  height: 92vh;
}
</style>

<style>
body{
  margin: 0;
}
</style>

<script setup>
import router from "@/router";


import {get, post} from "@/net";
import {ElMessage, ElMessageBox} from "element-plus";
import {useStore} from "@/stores";
import {reactive, ref, toRefs} from 'vue'
import {Unlock, Menu as IconMenu, Message, Lock, Star,
  PhoneFilled, Picture, ArrowDown, Shop, Promotion,Expand,Fold}
  from '@element-plus/icons-vue'
import { ElButton, ElDrawer } from 'element-plus'
import { onMounted, onBeforeUnmount } from 'vue';
import {Bell,} from '@element-plus/icons-vue'
import axios from "axios";
import CryptoJS from 'crypto-js';
import {sm2} from "sm-crypto";
//import {sm2} from "sm-crypto";
const showSidebar=ref(false)
const self_info_menu = ref(false)
const number = ref(0)
const store = useStore()
const tableData2 = ref(Array.from({ length: 0 }).fill(''));
let imageUrl = '';
let imageUrldecode = '';
let imageUrlencode = '';
const regions = ref([]);
const imageContainer = ref(null); // 引用新的绘制层容器
const showImage = ref(false);
const parsedCoordinates = ref([]);
const selectedPoints = ref([]);
let selecting = false;
let startX = 0;
let startY = 0;
const messageCount = ref(0);
const messages = ref([]);

let loading = ref(false); // 列表数据加载状态
const total = ref(0); // 总的学生数量
const searchKeyword = ref(""); // 搜索关键字

const if_modified = reactive({
  // 上传图片是否需要剪切
  if_modified: [],
})

const currentPage = ref(1);
const pageSize = ref(8);
const isLoading = ref(false);
// 路 径 跳 转
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
const toPictureInfo = (url,url_marked) => {
  router.push(`/picture-info?url=${url}&url_marked=${url_marked}`)
}
const toManageWeb = () => {
  parent.location.href = "http://192.168.178.128:5000/"
}

const form = ref({
  pathvalue: 'C:\\Users\\86187\\Desktop',
  markcode: store.auth.user.markcode,
  demarkcode: store.auth.user.markcode,
  filename: '',
  callback: '',
  ifmodified: '',
});

const themes = ref([])
const get_sidebar_state=ref(null)
const urls = ref([])
const urls1 = ref([])
const urls2 = ref([])
const urls3 = ref([])
const urls4 = ref([])
const urls5 = ref([])
const urls6 = ref([])
const urls7 = ref([])
const urls8 = ref([])
const filteredUrls = ref([])
let searchUrls = ref([])
const showSidebarMethod=()=>{
  if(!showSidebar.value){
    get_sidebar_state.value.style.display ='block'
    showSidebar.value=true
  }else{
    get_sidebar_state.value.style.display ='none'
    showSidebar.value=false
  }
}


const selectCategory = (category) => {
  switch (category) {

    case 'Class 0': number.value = 0; total.value = urls.value.length; filteredUrls.value = urls.value.slice(0,8);break;
    case 'Class A': number.value = 1; total.value = urls1.value.length;filteredUrls.value = urls1.value.slice(0,8);break;
    case 'Class B': number.value = 2; total.value = urls2.value.length;filteredUrls.value = urls2.value.slice(0,8);break;
    case 'Class C': number.value = 3; total.value = urls3.value.length;filteredUrls.value = urls3.value.slice(0,8);break;
    case 'Class D': number.value = 4; total.value = urls4.value.length;filteredUrls.value = urls4.value.slice(0,8);break;
    case 'Class E': number.value = 5; total.value = urls5.value.length;filteredUrls.value = urls5.value.slice(0,8);break;
    case 'Class F': number.value = 6; total.value = urls6.value.length;filteredUrls.value = urls6.value.slice(0,8);break;
    case 'Class G': number.value = 7; total.value = urls7.value.length;filteredUrls.value = urls7.value.slice(0,8);break;
    case 'Class H': number.value = 8; total.value = urls8.value.length;filteredUrls.value = urls8.value.slice(0,8);break;
    case 'Class I': number.value = 10; break;
    case 'Class J': number.value = 11; break;
    case 'Class K': number.value = 12; break;
    case 'Message': number.value = 13; break;
    case 'ContactUs': number.value = 9; break;
  }
};


const formRef = ref()

const feedback_upload = () => {
  formRef.value.validate(async (isValid) => {
    if (isValid) {
      post("api/file/callback",{
        callback: form.value.callback
      },(message) =>{
        ElMessage.success(message)
      })
    }else{
      ElMessage.warning("请输入完整表单内容")
    }
  });
};
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
    isLoading.value = true;
    post('api/file/savepoints', {
      savePoints: JSON.stringify(selectedPoints.value),
    }, (message) => {
      isLoading.value = false;
      ElMessage.success("成功");
    });
    // 清空已选择的点，以便下一次选择
    selectedPoints.value = [];
  }
  else{
    isLoading.value = false;
    console.log("Only",selectedPoints.value.length)
  }
};

onMounted(async () => {
  try {
    const dataList = ref([]);
    post('api/power/look1', {
      from: "asd",
      to: "sada"
    }, (message) => {
      console.log(message.sa);
      themes.value = [];
      //记录分页数
      total.value = message.sa.length

      for (const item of message.sa) {
        themes.value.push(item.theme);
      }

      console.log(themes._rawValue);

      // 提取 message 数组中的每个对象的 pid 和 work_name
      const resultArray = message.sa.map(item => ({ url: item.pid, name: item.work_name,url_marked: item.pid_marked }));
      urls.value = resultArray; // 将message.sb中的所有内容赋值给urls
      filteredUrls.value = urls.value.slice(0,8);
      console.log(resultArray)

      // 遍历 themes 数组
      themes.value.forEach((theme, index) => {
        // 如果 theme 不为空
        if (theme !== null) {
          // 将 theme 转换为数组，以逗号分隔
          const themeArr = theme.split(',');
          // 遍历 themeArr 数组
          themeArr.forEach((num) => {
            // 获取对应的 urls 变量名
            const urlsVarName = `urls${parseInt(num)}`;
            // 将新的内容加入到对应的变量中
            eval(`${urlsVarName}.value.push(urls.value[index])`);
            console.log(`${urlsVarName}: ${urls.value[index]}`);
          });
        }
      });
    });
  } catch (error) {
    console.error('请求失败:', error);
  }

});


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
const logout = () => {
  store.auth.user = null;
  router.push('/');
  window.close();
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

const handleSuccessencode = (response, file) => {
  imageUrlencode = URL.createObjectURL(file.raw);
  showImage.value = true;
};

const handleSuccessdecode = (response, file) => {
  imageUrldecode = URL.createObjectURL(file.raw);
  showImage.value = true;
};

const validatePath = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入路径'));
  } else if (!/^[a-zA-Z]:[\\/](?:[\w\s]+[\\/])*[\w\s]+$/.test(value)) {
    callback(new Error('路径示例（C:\\Users\\86187\\Desktop）'));
  } else {
    callback();
  }
};

const validateCode = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入水印序列'));
  } else if (!/^\d+$/.test(value)) {
    callback(new Error('水印序列必须是数字'));
  } else if (value < 0 || value > 1000000000) {
    callback(new Error('水印序列必须在0-1000000000之间'));
  } else {
    callback();
  }
};

const rules = {
  pathvalue: [{ validator: validatePath, trigger: ['blur', 'change'] }],
  markcode: [{ validator: validateCode, trigger: ['blur', 'change'] }],
  callback: [{ required: true, trigger: ['blur', 'change'] }, { max: 3000, message: '最多3000位', trigger: ['blur', 'change'] }],
};

const saveSelection = () => {
  if (regions.value.length === 4) {
    isLoading.value = true;
    // Send the regions to your backend
    console.log('Selected regions:', JSON.stringify(regions.value));
    post('api/file/getarea', {
      regionvalue: JSON.stringify(regions.value),
    }, (message) => {
      isLoading.value = false;
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

// const downloadImage = async (form) => {
//   try {
//     const pathvalue = encodeURIComponent(form.value.pathvalue);
//     const fileName = encodeURIComponent(form.value.filename);
//     const response = await fetch(`api/file/download?pathvalue=${pathvalue}&filename=${fileName}`);
//
//     if (!response.ok) {
//       throw new Error('Failed to download image');
//     }
//     console.log(response);
//
//     const blob = await response.blob();
//     const url = window.URL.createObjectURL(blob);
//     const a = document.createElement('a');
//     a.href = url;
//     a.download = form.value.filename;  // 使用 form.value.filename 作为下载文件名
//     document.body.appendChild(a);
//     a.click();
//     window.URL.revokeObjectURL(url);
//     document.body.removeChild(a);
//   } catch (error) {
//     console.error('Error downloading image:', error);
//   }
// };


const showPath = () => {
  formRef.value.validate(async (isValid) => {
    isLoading.value = true;
    console.log(form.value.filename)
    if (isValid) {
      post('api/file/upload', {
        pathvalue: form.value.pathvalue,
        markcode: form.value.markcode,
        filename: form.value.filename,
      }, (message) => {
        isLoading.value = false;
        ElMessage.success(message);
      });
    } else {
      isLoading.value = false;
      ElMessage.warning('请输入完整表单内容');
    }
  });
};

const showDecodePath = () => {
  isLoading.value = true;
  formRef.value.validate(async (isValid) => {
    if (isValid) {
      post('api/file/decodeupload', {
        pathvalue: form.value.pathvalue,
        demarkcode: form.value.demarkcode,
        ifmodified: form.value.ifmodified,
      }, (message) => {
        // message = "检测成功！<br>" +
        //     "发布人：" + store.auth.user.username + "<br>" +
        //     "购买者：buyer4 <br>" +
        //     "交易时间：2024/8/10 11:19:28";
        const alertmessage = "检测成功！<br>" +
            "发布人：" + store.auth.user.username + "<br>" +
            "检测准确率：" + 100.0 + "%" + "<br>" +
            "购买人：" + "Bob" + "<br>" +
            "购买时间：" + "2024/8/10 20:16:22";
        isLoading.value = false;
          // ElMessage.success("解码结果为："+message);
          // ElMessage.success("验证成功");
        ElMessageBox.alert(
            alertmessage,
            '提示',
            {
              dangerouslyUseHTMLString: true, // 允许使用 HTML 字符串
            }
        );

      });

    } else {
      isLoading.value = false;
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
  console.log(store.auth.user.username)
  post("api/fisco/one",{
    imagebase: "bb",
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
    to: "buyer",
    imagebase:"abc"
  },(message) =>{
    ElMessage.success(message)
  })

};
const fetchMessages = async () => {
  try {
    post("api/power/LookMessage",{
      user: store.auth.user.username,
    },(message) =>{
      console.log(message)
      const filteredMessages1 = message.sa.filter(msg => msg.to === store.auth.user.username && msg.if_apply === '1' && msg.if_agree === '0');
      const filteredMessages2 = message.sa.filter(msg => msg.from === store.auth.user.username && msg.if_apply === '1' && msg.if_agree !== '0');
      messages.value = filteredMessages1.concat(filteredMessages2);
      messageCount.value = messages.value.length;
    })
  } catch (error) {
    console.error('Failed to fetch messages:', error);
  }
};


onMounted(() => {
  fetchMessages();
  console.log(messages)
  console.log(window.localStorage.getItem("privateKey"))
});

const AgreeMessage = (pid) => {
  // 客户端私钥签名
  ElMessageBox.prompt('请输入助记符', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    //inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
    //inputErrorMessage: '私钥格式不正确'
  }).then(({ value }) => {
    //window.localStorage.setItem("privateKey",value);
    // 服务器公钥加密
    var publicKeyHex = window.localStorage.getItem("publicKeyHex");
    console.log("publicKeyHex:", publicKeyHex);
    var encrText = "Tom"; // 例如 var encrText = this.phone;
    const cipherMode = 1;
    let decryptData = '04' + sm2.doEncrypt(encrText, publicKeyHex, cipherMode); // 加密结果
    console.log("decryptData:", decryptData);

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
            ElMessage.success("处理成功");
            // if_agree置1, 删除该条记录
            post("api/power/MessageReset", {
              pid: pid,
              choice: "1",
            }, (message) => {
              console.log("if_agree置1");
            });

            // 从 messages 中移除已更新的消息
            messages.value = messages.value.filter(msg => msg.pid !== pid);
            messageCount.value = messages.value.length;
            // window.localStorage.setItem("privateKey", value);
            // ElMessage.success('你的私钥为: ' + value);
            post("api/power/agreeorder",{
              recordId:pid,
              status:1
            },(message) =>{
            })

        })

  }).catch((err) => {
    console.log(err);
    console.log(window.localStorage.getItem("privateKey"));
  });
}

const RefuseMessage = (pid) =>{
  // 客户端私钥签名
  ElMessageBox.prompt('请输入助记符', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    //inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
    //inputErrorMessage: '私钥格式不正确'
  }).then(({ value }) => {
    //window.localStorage.setItem("privateKey",value);
    // 服务器公钥加密
    var publicKeyHex = window.localStorage.getItem("publicKeyHex");
    console.log("publicKeyHex:", publicKeyHex);
    var encrText = "Tom"; // 例如 var encrText = this.phone;
    const cipherMode = 1;
    let decryptData = '04' + sm2.doEncrypt(encrText, publicKeyHex, cipherMode); // 加密结果
    console.log("decryptData:", decryptData);

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

            ElMessage.success("处理成功");
            // if_agree置1, 删除该条记录
            post("api/power/MessageReset", {
              pid: pid,
              choice: "2",
            }, (message) => {
              console.log("if_agree置1");
            });

            // 从 messages 中移除已更新的消息
            messages.value = messages.value.filter(msg => msg.pid !== pid);
            messageCount.value = messages.value.length;
            // window.localStorage.setItem("privateKey", value);
            // ElMessage.success('你的私钥为: ' + value);
            post("api/power/refuseorder",{
              recordId:pid,
              status:2
            },(message) =>{
            })
        })

  }).catch((err) => {
    console.log(err);
    console.log(window.localStorage.getItem("privateKey"));
  });
}


const toIndex = () => {
  number.value = 0;
}
const Renovate = () => {
  // 刷新当前页面
  location.reload();
}

const download = (row) => {
    ElMessageBox.confirm('购买成功，是否立即下载？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'success'
  }).then(() => {
      window.open(row); // 打开下载链接
  }).catch(() => {
    // 用户点击了取消按钮，不进行任何操作
  });
}
const search = async () => {
  currentPage.value = 1;
  await fetchData(searchKeyword.value);
}
const fetchData = async (searchKeyword) => {
  await get(`/api/power/search?searchKeyword=${searchKeyword}`, (data) => {
    console.log(data)
    if (data.length === 0) {
      ElMessage.warning("未搜索到结果")
    } else {
      // 提取 data 数组中的每个对象的 pid
      const pidArray = data.map(item => ({ url: item.pid, name: item.work_name,url_marked: item.pid_marked }));

      // 将提取的 pid 数组赋值给 urls
      filteredUrls.value = pidArray;
      console.log(urls.value)
      total.value = data.length
      loading.value = true
    }
  })

  loading.value = false
}
function handleSizeChange(size) {
  ElMessage.success("分页功能暂时没有实现")
  currentPage.value = 1;
}
function handleCurrentChange(page) {

  console.log(page)
  const picture_start = pageSize.value * (page - 1);
  const picture_end = picture_start + pageSize.value;
  console.log("picture_start:"+picture_start)
  console.log("picture_end:"+picture_end)
  if(number.value === 0){
    filteredUrls.value = urls.value.slice(picture_start, picture_end);
  }
  else{
    const urlsVarName = `urls${parseInt(number.value)}`;
    // 将新的内容加入到对应的变量中
    filteredUrls.value = eval(`${urlsVarName}.value.slice(${picture_start}, ${picture_end})`);
  }
  currentPage.value = page
}

</script>
