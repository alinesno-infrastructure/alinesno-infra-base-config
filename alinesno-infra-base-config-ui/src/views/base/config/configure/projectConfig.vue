<template>
   <div>
      <el-row :gutter="20">
         <!--类型数据-->
         <el-col :span="4" :xs="24">
            <div class="head-container">
               <el-input
                  v-model="deptName"
                  placeholder="请输入类型名称"
                  clearable
                  prefix-icon="Search"
                  style="margin-bottom: 20px"
               />
            </div>
            <div class="head-container">
               <el-tree
                  :data="deptOptions"
                  :props="{ label: 'label', children: 'children' }"
                  :expand-on-click-node="false"
                  :filter-node-method="filterNode"
                  ref="deptTreeRef"
                  node-key="id"
                  highlight-current
                  default-expand-all
                  @node-click="handleNodeClick"
               />
            </div>
         </el-col>
         <!--应用数据-->
         <el-col :span="20" :xs="24">
            <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
               <el-form-item label="配置名称" prop="projectId">
                  <el-input v-model="queryParams.projectId" placeholder="请输入配置名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
               </el-form-item>
               <el-form-item>
                  <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                  <el-button icon="Refresh" @click="resetQuery">重置</el-button>
               </el-form-item>
            </el-form>

            <el-table v-loading="loading" ref="multipleTableRef" :data="ConfigureList" @selection-change="handleSelectionChange">
               <el-table-column type="selection" width="50" align="center" />
              
              <el-table-column label="图标" align="center" width="50" key="icon" v-if="columns[5].visible">
                 <template #default="scope">
                    <span style="font-size:25px;color:#3b5998">
                       <i class="fa-solid fa-file-word" />
                    </span>
                 </template>
              </el-table-column>

               <!-- 业务字段-->
               <el-table-column label="应用名称" align="left" key="projectId" prop="projectId" v-if="columns[0].visible" >
                  <template #default="scope">
                     {{ scope.row.name }}
                  </template>
               </el-table-column>

               <el-table-column label="所属环境" align="left" key="remarks" prop="remarks" v-if="columns[2].visible" :show-overflow-tooltip="true">
                  <template #default="scope">
                     <el-button type="primary" bg text> <i class="fa-brands fa-shopify"></i> {{ scope.row.env }}</el-button>
                  </template>
               </el-table-column>

               <el-table-column label="配置类型" align="center" key="type" prop="type" v-if="columns[4].visible" :show-overflow-tooltip="true">
                     <template #default="scope">
                        <div style="margin-top: 5px;" v-if="scope.row.type == 1">
                           <el-button type="primary" bg text> <i class="fa-solid fa-credit-card"></i> Properties</el-button>
                        </div>
                        <div style="margin-top: 5px;" v-if="scope.row.type == 3 || scope.row.type == 2">
                           <el-button type="success" bg text> <i class="fa-brands fa-shopify"></i> Yaml</el-button>
                        </div>
                        <div style="margin-top: 5px;" v-if="scope.row.type == 4">
                           <el-button type="success" bg text> <i class="fa-solid fa-lemon"></i> JSON</el-button>
                        </div>
                        <div style="margin-top: 5px;" v-if="scope.row.type == 0">
                           <el-button type="danger" bg text> <i class="fa-solid fa-lemon"></i> 未配置</el-button>
                        </div>
                     </template>
                  </el-table-column>

            </el-table>
            <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
         </el-col>
      </el-row>

   </div>
</template>

<script setup name="Configure">

import {
   listConfigure,
   delConfigure,
   getConfigure,
   updateConfigure,
   getProjectAndEnv,
   addConfigure,
   catalogTreeSelect,
   changStatusField 
} from "@/api/base/config/configure";

import ContentEditor from "./editor.vue"

const router = useRouter();
const { proxy } = getCurrentInstance();

// 接收传值
const props  = defineProps({
	currentProjectId: Number
})

// 定义变量
const multipleTableRef = ref(null);
const ConfigureList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dateRange = ref([]);

const deptOptions = ref(undefined);
const projectOptions = ref([]);
const envOptions = ref([]);

// 编辑配置
const promptTitle = ref("");
const currentPostId = ref("");
const currentPromptContent = ref([]);
const promptOpen = ref(false);

// 列显隐信息
const columns = ref([
   { key: 0, label: `应用名称`, visible: true },
   { key: 1, label: `应用描述`, visible: true },
   { key: 2, label: `表数据量`, visible: true },
   { key: 3, label: `类型`, visible: true },
   { key: 4, label: `应用地址`, visible: true },
   { key: 5, label: `状态`, visible: true },
   { key: 6, label: `更新时间`, visible: true }
]);

const data = reactive({
   form: {},
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

/** 查询应用列表 */
function getList() {
   loading.value = true;

   handleProjectList() ; 

   console.log('currentProject.currentProjectId.value = ' + props.currentProjectId);

   queryParams.value.projectId = props.currentProjectId ;

   listConfigure(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
      loading.value = false;
      ConfigureList.value = res.rows;
      total.value = res.total;

      ConfigureList.value.forEach((row) => {
         console.log('row = ' + row);
         nextTick(() => {
            multipleTableRef.value.toggleRowSelection(row, row.selected);
         });
      });

   });
};

/** 查询所有的项目和环境列表 */
function handleProjectList(){
   getProjectAndEnv().then(res => {
      envOptions.value = res.envList ; 
      projectOptions.value = res.projectList ; 
   }) ;
}

/** 搜索按钮操作 */
function handleQuery() {
   queryParams.value.pageNum = 1;
   getList();
};

/** 重置按钮操作 */
function resetQuery() {
   dateRange.value = [];
   proxy.resetForm("queryRef");
   queryParams.value.deptId = undefined;
   proxy.$refs.deptTreeRef.setCurrentKey(null);
   handleQuery();
};
/** 删除按钮操作 */
function handleDelete(row) {
   const ids = row.id || ids.value;
   proxy.$modal.confirm('是否确认删除应用编号为"' + ids + '"的数据项？').then(function () {
      return delConfigure(ids);
   }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
   }).catch(() => { });
};

/** 选择条数  */
function handleSelectionChange(selection) {
   ids.value = selection.map(item => item.id);
   console.log('ids = ' + ids.value);
   single.value = selection.length != 1;
   multiple.value = !selection.length;
};

/** 获取到选择的id号 */
function handleSelectionIds(){
   return ids.value ; 
}

/** 重置操作表单 */
function reset() {
   form.value = {
      id: undefined,
      deptId: undefined,
      ConfigureName: undefined,
      remarks: undefined,
      password: undefined,
      phonenumber: undefined,
      status: "0",
      remark: undefined,
   };
   proxy.resetForm("databaseRef");
};
/** 取消按钮 */
function cancel() {
   open.value = false;
   reset();
};

/** 新增按钮操作 */
function handleAdd() {
   reset();
   open.value = true;
   title.value = "添加应用";
};

/** 修改状态 */
const handleChangStatusField = async(field , value , id) => {
   // 判断tags值 这样就不会进页面时调用了
     const res = await changStatusField({
        field: field,
        value: value?1:0,
        id: id
     }).catch(() => { })
     if (res && res.code == 200) {
        // 刷新表格
        getList()
     }
}

/** 修改按钮操作 */
function handleUpdate(row) {
   reset();
   const id = row.id || ids.value;
   getConfigure(id).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改应用";
   });
};

/** 提交按钮 */
function submitForm() {
   proxy.$refs["databaseRef"].validate(valid => {
      if (valid) {
         if (form.value.id != undefined) {
            updateConfigure(form.value).then(response => {
               proxy.$modal.msgSuccess("修改成功");
               open.value = false;
               getList();
            });
         } else {
            addConfigure(form.value).then(response => {
               proxy.$modal.msgSuccess("新增成功");
               open.value = false;
               getList();
            });
         }
      }
   });
};

/** 获取环境名称 */
function handleEnvNameById(envId){
   let name = "未配置" ; 
   // 使用 find 方法查找 identity 为 1 的元素
    const foundOption = envOptions.value.find(item=> item.id === envId);
    if(foundOption){
      name = foundOption.name ;
    }
    return name ; 
}

/** 修改配置信息 */
function editContent(row){

   // promptTitle.value = "配置角色Prompt";
   // promptOpen.value = true ;
   // currentPostId.value = row.id;

   // if(row.promptContent){
   //    currentPromptContent.value = JSON.parse(row.promptContent);
   // }

  router.push({ path: "/base/config/configure/edit/" + row.id });
}

/** 查询类型下拉树结构 */
function getDeptTree() {
  catalogTreeSelect().then(response => {
    deptOptions.value = response.data;
  });
};

// /** 处理行默认选中的问题 */
nextTick(() => {
   console.log('ConfigureList.value.length = ' + ConfigureList.value.length) ;
   // for(var i = 0 ; i < ConfigureList.value.length ; i ++){
   //    var row = ConfigureList.value[i] ;
   //    console.log('row.selected= ' + row.selected)
   //    tableRef.value.toggleRowSelection(row, row.selected);
   // };
}) ; 

getDeptTree() ;
getList() ;

//暴露方法
defineExpose({
   handleSelectionIds,
});

</script>
