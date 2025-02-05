<template>
   <div class="app-container">
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
                  @node-click="handleNodeClick"
               />
            </div>
         </el-col>
         <!--应用数据-->
         <el-col :span="20" :xs="24">
            <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
               <el-form-item label="应用名称" prop="projectId">
                  <el-input v-model="queryParams.projectId" placeholder="请输入应用名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
               </el-form-item>
               <el-form-item label="应用名称" prop="projectId">
                  <el-input v-model="queryParams['condition[projectId|like]']" placeholder="请输入应用名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
               </el-form-item>
               <el-form-item>
                  <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                  <el-button icon="Refresh" @click="resetQuery">重置</el-button>
               </el-form-item>
            </el-form>

            <el-row :gutter="10" class="mb8">

               <el-col :span="1.5">
                  <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
               </el-col>
               <el-col :span="1.5">
                  <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate">修改</el-button>
               </el-col>
               <el-col :span="1.5">
                  <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete">删除</el-button>
               </el-col>

               <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
            </el-row>

            <el-table v-loading="loading" :data="ConfigureList" @selection-change="handleSelectionChange">
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
                     <div>
                        {{ scope.row.name }}
                     </div>
                     <div style="font-size: 13px;color: #a5a5a5;cursor: pointer;" v-copyText="scope.row.promptId">
                        {{ scope.row.remarks }}
                     </div>
                  </template>
               </el-table-column>

               <!-- 
               <el-table-column label="配置标识" align="left" key="identity" prop="identity" v-if="columns[1].visible" :show-overflow-tooltip="true" >
                  <template #default="scope">
                     <div style="cursor: pointer;" v-copyText="scope.row.identity">
                        {{ scope.row.identity }} <el-icon><CopyDocument /></el-icon>
                     </div>
                  </template>
               </el-table-column> 
               -->

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
                           <el-button type="danger" bg text> <i class="fa-solid fa-lemon"></i> JSON</el-button>
                        </div>
                        <div style="margin-top: 5px;" v-if="scope.row.type == 0">
                           <el-button type="danger" bg text> <i class="fa-solid fa-lemon"></i> 未配置</el-button>
                        </div>
                     </template>
                  </el-table-column>

               <el-table-column label="内容配置" align="center" key="promptContent" prop="promptContent" v-if="columns[2].visible" :show-overflow-tooltip="true">
                  <template #default="scope">
                     <el-button type="primary" text bg @click="editContent(scope.row)"><i class="fa-solid fa-screwdriver-wrench"></i> &nbsp; 配置内容</el-button>
                  </template>
               </el-table-column>

              <el-table-column label="是否启用" align="center" width="100" key="hasStatus" prop="hasStatus" v-if="columns[1].visible" :show-overflow-tooltip="true" >
                 <template #default="scope">
                    <el-switch
                       v-model="scope.row.hasStatus"
                       :active-value="0"
                       :inactive-value="1"
                       @change="handleChangStatusField('hasStatus' , scope.row.hasStatus, scope.row.id)"
                    />
                 </template>
              </el-table-column>

               <el-table-column label="添加时间" align="center" prop="addTime" v-if="columns[6].visible" >
                  <template #default="scope">
                     <span>{{ parseTime(scope.row.addTime) }}</span>
                  </template>
               </el-table-column>

               <!-- 操作字段  -->
               <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
                  <template #default="scope">
                     <el-tooltip content="修改" placement="top" v-if="scope.row.id !== 1">
                        <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:Configure:edit']"></el-button>
                     </el-tooltip>
                     <el-tooltip content="删除" placement="top" v-if="scope.row.id !== 1">
                        <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:Configure:remove']"></el-button>
                     </el-tooltip>
                  </template>

               </el-table-column>
            </el-table>
            <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
         </el-col>
      </el-row>

      <!-- 添加或修改应用配置对话框 -->
      <el-dialog :title="title" v-model="open" width="900px" append-to-body>
         <el-form :model="form" :rules="rules" ref="databaseRef" label-width="100px">

            <!--
            <el-row>
               <el-col :span="24">
                  <el-form-item label="所属项目" prop="projectId">
                     <el-select v-model="form.projectId" placeholder="请选择用户状态">
                           <el-option
                              v-for="item in projectOptions"
                              :key="item.id"
                              :label="item.name"
                              :value="item.id"
                              ></el-option>
                     </el-select>
                  </el-form-item>
               </el-col>
            </el-row>
            -->

            <el-row>
              <el-col :span="24">
               <el-form-item label="类型" prop="icon">
                  <el-radio-group v-model="form.type">
                     <el-radio v-for="item in configTypeList"
                        :value="item.type"
                        :key="item.type"
                        :label="item.name"
                        >
                        <i :class="item.icon"></i> {{ item.name }}
                     </el-radio>
                     </el-radio-group>
                  </el-form-item>
              </el-col>   
               <el-col :span="24">
                  <el-form-item style="width: 100%;" label="分类" prop="catalogId">
                     <el-tree-select
                        v-model="form.catalogId"
                        :data="deptOptions"
                        :props="{ value: 'id', label: 'label', children: 'children' }"
                        value-key="id"
                        placeholder="请选择归属类型"
                        check-strictly
                     />
                  </el-form-item>
               </el-col>
            </el-row>
            <el-row>
               <el-col :span="24">
                  <el-form-item label="所属环境" prop="env">
                     <el-select v-model="form.env" placeholder="请选择用户状态">
                           <el-option
                              v-for="item in envOptions"
                              :key="item.code"
                              :label="item.name"
                              :value="item.code"
                              ></el-option>
                     </el-select>
                  </el-form-item>
               </el-col>
               <el-col :span="24">
                  <el-form-item label="配置名称" prop="name">
                     <el-input v-model="form.name" placeholder="请输入配置名称" maxlength="128" />
                  </el-form-item>
               </el-col>
            </el-row>
            <el-row>
               <el-col :span="24">
                  <el-form-item label="备注" prop="remarks">
                     <el-input v-model="form.remarks" placeholder="请输入备注信息" maxlength="30" />
                  </el-form-item>
               </el-col>
            </el-row>
         </el-form>
         <template #footer>
            <div class="dialog-footer">
               <el-button type="primary" @click="submitForm">确 定</el-button>
               <el-button @click="cancel">取 消</el-button>
            </div>
         </template>
      </el-dialog>

      <!-- 添加或修改指令配置对话框 -->
      <el-dialog :title="promptTitle" v-model="promptOpen" width="1024" destroy-on-close append-to-body>

         <ContentEditor :currentPostId="currentPostId" :currentPromptContent="currentPromptContent" />

      </el-dialog>

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

// 定义变量
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
      type: [{ required: true, message: "类型不能为空", trigger: "blur" }],
      catalogId: [{ required: true, message: "分类不能为空", trigger: "blur" }] , 
      name: [{ required: true, message: "名称不能为空", trigger: "blur" }] , 
      remarks: [{ required: true, message: "密码不能为空", trigger: "blur" }] , 
   }
});

const { queryParams, form, rules } = toRefs(data);

const configTypeList = ref([
  { id: 1, icon: 'fa-solid fa-charging-station' , 'name': 'yaml'} ,
  { id: 1, icon: 'fa-solid fa-truck' , 'name': 'properties'} ,
  { id: 2, icon: 'fa-solid fa-paper-plane' , 'name':'json'} ,
  { id: 9, icon: 'fa-solid fa-user-tie' , 'name':'xml'},
]);

/** 查询应用列表 */
function getList() {
   loading.value = true;

   handleProjectList() ; 

   listConfigure(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
      loading.value = false;
      ConfigureList.value = res.rows;
      total.value = res.total;
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
   const idList = row.id || ids.value;
   proxy.$modal.confirm('是否确认删除应用编号为"' + idList + '"的数据项？').then(function () {
      return delConfigure(idList);
   }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
   }).catch(() => { });
};

/** 选择条数  */
function handleSelectionChange(selection) {
   ids.value = selection.map(item => item.id);
   single.value = selection.length != 1;
   multiple.value = !selection.length;
};

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

getDeptTree() ;
getList();

</script>
