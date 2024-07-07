<template>
  <!--
    配置 功能列表
 
    @author 苏泽 ${authorEmail}
    @date 2023-05-25 10:48:57
  -->
  <div class="app-container">
 
    <el-page-header @back="goBack" :content="title"></el-page-header>
 
    <el-form ref="databaseRef" :model="form" :rules="rules" label-width="80px" style="margin-top:20px;padding:10px" label-position="left">
          <el-form-item label="配置名称" prop="name" style="width: 40%">
            <el-input v-model="form.name" placeholder="请输入配置名称" style="width: 500px"/>
          </el-form-item>
          <el-form-item label="配置标识" prop="identity" style="width: 45%">
            <el-input v-model="form.identity" placeholder="请输入配置标识" style="width: 500px"/>
          </el-form-item>
        <el-form-item label="导入配置文件" label-width="102px">
            <el-upload action="#" :before-upload="handleFileUpload">
              <el-button>上传文件</el-button>
            </el-upload>
          </el-form-item>
 
      <!-- <el-form-item label-width="0px">
        <el-form :inline="true" :model="form" :rules="rules" label-position="left">
        </el-form>
      </el-form-item> -->
 
      <!-- <el-form-item label-width="0px">
        <el-form :inline="true" :model="form" :rules="rules" label-position="left">
        </el-form>
      </el-form-item> -->
 
          <!-- v-model="form.contents" -->
        <el-form-item label="导入配置文件" label-width="102px">
          <div class="cm-container">
            <!-- <codemirror
              class="CodeMirror"
              :options="editorOptions"
              ref="myEditor"
            ></codemirror> -->
            <code-mirror 
              basic 
              :lang="lang" 
              v-model="codeVal" 
              style="height: 400px;" 
              :theme="theme"
              :extensions="extensions" />
          </div>
        </el-form-item>
      <el-form-item label-width="0px" style="margin-top:20px;">
        <el-button type="primary" @click="submitForm" icon="Upload">保存配置</el-button>
      </el-form-item>
    </el-form>
 
  </div>
 </template>
 
 <script setup  name="EditConfigure">

import {
   getProjectConfig,
   addProjectConfig,
   updateProjectConfig 

} from "@/api/base/config/configure";

import { ref, onMounted } from 'vue';
import CodeMirror from 'vue-codemirror6'
import { oneDark } from '@codemirror/theme-one-dark'
import { json } from '@codemirror/lang-json';
import { yaml } from '@codemirror/lang-yaml';

const router = useRouter();
const { proxy } = getCurrentInstance();

const configId = router.currentRoute.value.params.configId ; 
const initJson = {
  name: "maybaby",
  year: 25,
  weight: 45,
  height: 165
};

// 初始化
let codeVal = ref('');
// 转成json字符串并格式化
codeVal.value = JSON.stringify(initJson, null, '\t')
// json
const lang = yaml() ; // json();

// 扩展
const extensions = [oneDark];

// 主题样式设置
const theme = {
  "&": {
    fontSize: "9.5pt",
    color: "white",
    backgroundColor: "#034"
  },
  ".cm-content": {
    caretColor: "#0e9"
  },
  "&.cm-focused .cm-cursor": {
    borderLeftColor: "#0e9"
  },
  "&.cm-focused .cm-selectionBackground, ::selection": {
    backgroundColor: "#074"
  },
  ".cm-gutters": {
    backgroundColor: "#045",
    color: "#ddd",
    border: "none"
  }
}

const data = reactive({
   form: {
      appId: null,
      name: null,
      type: null,
      contents: null,
      identity: null
   },
   queryParams: {
      pageNum: 1,
      pageSize: 10,
      projectId: undefined,
      identity: undefined
   },
   rules: {
      projectId: [{ required: true, message: "名称不能为空", trigger: "blur" }] , 
      env: [{ required: true, message: "连接不能为空", trigger: "blur" }],
      type: [{ required: true, message: "类型不能为空", trigger: "blur" }] , 
      contents: [{ required: true , message: "用户名不能为空", trigger: "blur"}],
      remarks: [{ required: true, message: "密码不能为空", trigger: "blur" }] , 
      identity: [{ required: true, message: "备注不能为空", trigger: "blur" }] 
   }
});

const { queryParams, form, rules } = toRefs(data);

function goBack() {
  router.back();
}

/** 应用启动环境 */
onMounted(() => {
  console.log('configId= ' + configId) ;
  getProjectConfig(configId).then(res => {
    console.log('res = ' + res);
    form.value = res.data ;

    // codeVal.value = JSON.stringify(res.data.contents , null, '\t') ;

    codeVal.value = res.data.contents ; 

    console.log('--->>>> identity = ' + form.value.identity);
  })
});

/** 提交按钮 */
function submitForm() {
   proxy.$refs["databaseRef"].validate(valid => {
      if (valid) {
        form.value.contents = codeVal.value ;
        updateProjectConfig(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
        });
      }
   });
};

</script>

<style >
/* required! */
.cm-editor {
  height: 100%;
}
</style>
