<template>
  <div>
    <router-link tag="div" class="header-logo-bar" to="/index">
      <div class="header-logo" v-if="enableLogo" @click="enterDomain">
        <img :src="saasLogoUrl" alt="" />
      </div>
      <a :title="saasTitle" target="_self" class="header-logo-label">
        <span>{{ saasTitle }} </span>
      </a>
      <!-- 
      <div v-if="saasUrl" class="dashboard-home" @click="dashboardHome()">
        <i class="fa-solid fa-house"></i> 工作台
      </div> 
      -->
    </router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue';

import AIPLogo from '@/assets/logo/logo.png';

// 定义组件数据
const saasTitle = ref('AIP智能设施');
const enableLogo = ref(true);
const saasUrl = ref('') ; 
const saasLogoUrl = ref(AIPLogo) ;
const domainName = ref(null);

// 定义组件方法
const dashboardHome = () => {
  window.location.href = saasUrl.value;
};

// 进入企业官网
const enterDomain = () => {
  if (domainName.value) { // 跳转进入官网
    window.open(domainName.value);
  }
};
</script>
