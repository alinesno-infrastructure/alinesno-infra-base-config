import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

/**
 * 数据库接口操作
 * 
 * @author luoxiaodong
 * @since 1.0.0
 */

// 接口配置项
var prefix = '/api/infra/base/config/env/' ;
var managerUrl = {
    datatables : prefix +"datatables" ,
    createUrl: prefix + 'add' ,
    saveUrl: prefix + 'save' ,
    updateUrl: prefix +"modify" ,
    statusUrl: prefix +"changeStatus" ,
    cleanUrl: prefix + "clean",
    detailUrl: prefix +"detail",
    removeUrl: prefix + "delete" ,
    exportUrl: prefix + "exportExcel",
    choiceEnv: prefix + "choiceEnv",
    changeField: prefix + "changeField",
    defaultEnv: prefix + "defaultEnv",
    downloadfile: prefix + "downloadfile",
    currentEnv: prefix + "currentEnv",
}

// 配置当前应用
export function choiceEnv(id){
  return request({
      url: managerUrl.choiceEnv + '?EnvId=' + parseStrEmpty(id),
      method: 'get'
  })
}

// 选择当前应用
export function getCurrentEnv(){
  return request({
      url: managerUrl.currentEnv , 
      method: 'get'
  })
}

// 获取默认应用
export function getDefaultEnv(){
  return request({
    url: managerUrl.defaultEnv ,
    method: 'get'
  })
}

// 修改字段
export function changStatusField(data){
  return request({
    url: managerUrl.changeField ,
    method: 'post',
    data: data
  })
}

// 查询数据库列表
export function listEnv(query) {
  return request({
    url: managerUrl.datatables ,
    method: 'post',
    params: query
  })
}

// 查询数据库详细
export function getEnv(databaseId) {
  return request({
    url: managerUrl.detailUrl + '/' + parseStrEmpty(databaseId),
    method: 'get'
  })
}

// 新增数据库
export function addEnv(data) {
  return request({
    url: managerUrl.saveUrl ,
    method: 'post',
    data: data
  })
}

// 修改数据库
export function updateEnv(data) {
  return request({
    url: managerUrl.updateUrl ,
    method: 'put',
    data: data
  })
}

// 删除数据库
export function delEnv(databaseId) {
  return request({
    url: managerUrl.removeUrl + '/' + parseStrEmpty(databaseId),
    method: 'delete'
  })
}
