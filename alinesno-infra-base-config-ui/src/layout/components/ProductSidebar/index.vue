<template>
  <div class="siderbar">
    <el-menu default-active="1" class="el-menu-vertical" :collapse="isCollapse" @open="handleOpen" @close="handleClose">
          <el-menu-item
              :index="item.id"
              @click="openServiceList(item.link)"
              v-for="item in menuItems"
              :key="item.id"
              class="aip-menu-item">
            <i :class="item.icon"></i>
            <span>
              {{ item.desc }}
            </span>
          </el-menu-item>

    </el-menu>

    <el-menu style="" class="el-menu-vertical acp-suggest" :collapse="isCollapse" @open="handleOpen" @close="handleClose">
          <el-menu-item
            :index="item.id"
            v-for="item in footerMenuItems"
            :key="item.id"
            @click="openServiceList(item.link)"
            class="aip-menu-item">
            <i :class="item.icon"></i>
            <span>
              {{ item.desc }}
            </span>
          </el-menu-item>
    </el-menu>

  </div>
</template>

<script setup>

const router = useRouter();

// 菜单列表
const menuItems = ref([
  { id: '19', icon: 'fa-solid fa-computer', link: '/index', desc: '仪盘表' },
  {id:'7' , icon:'fa-brands fa-slack' , link:'/base/config/project/index' , desc:'项目'},
  {id:'3' , icon:'fa-solid fa-list-check' , link:'/base/config/configure/index' , desc:'配置'},
  {id:'9' , icon:'fa-brands fa-skype' , link:'/base/config/catalog/index' , desc:'分类'},
  {id:'6' , icon:'fa-solid fa-file-word' , link:'/base/config/env/index' , desc:'环境'},
  {id:'2' , icon:'fa-solid fa-recycle' , link:'/base/config/history/index' , desc:'版本'},
]);

const footerMenuItems = ref([
  {id:'4' , icon:'fa-solid fa-computer' , link:'/base/config/analyse/index' , desc:'调用'},
  {id:'5' , icon:'fa-solid fa-code-pull-request' , link:'/base/config/apiRecord/index' , desc:'记录'},
])

// 打开服务市场
function openServiceList(_path) {
  router.push({ path: _path });
}

// 打开首页
function jumpTo() {
  router.push({ path: "/index" });
}

// 打开智能客服
function openSmartService() {
  router.push({ path: "/dashboard/smartService" });
}

</script>

<style lang="scss" scoped>
.el-menu-vertical:not(.el-menu--collapse) {
  width: 65px;
}

.acp-suggest {
  bottom: 20px;
  position: absolute;
}

.siderbar {
  float: left;
  height: 100%;
  width: 64px;
  border-right: 1px solid #e6e6e6;
  padding-top: 40px;
  overflow: hidden;
  background-color: #fff;
  position: fixed;
}

.aip-menu-item {

  display: flex;
  flex-direction: column;
  gap: 5px;
  line-height: 1.2rem;
  padding-top: 0px;
  margin: 5px;
  border-radius: 10px;
  justify-content: center;

  span{
    font-size:12px;
    color: #888;
  }
}
</style>