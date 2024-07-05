import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

/**
 * 数据库接口操作
 * 
 * @author luoxiaodong
 * @since 1.0.0
 */

// 接口配置项
var prefix = '/api/infra/base/config/configure/' ;
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
    choiceConfigure: prefix + "choiceConfigure",
    changeField: prefix + "changeField",
    defaultConfigure: prefix + "defaultConfigure",
    downloadfile: prefix + "downloadfile",
    currentConfigure: prefix + "currentConfigure",
}

// 配置当前应用
export function choiceConfigure(id){
  return request({
      url: managerUrl.choiceConfigure + '?ConfigureId=' + parseStrEmpty(id),
      method: 'get'
  })
}

// 选择当前应用
export function getCurrentConfigure(){
  return request({
      url: managerUrl.currentConfigure , 
      method: 'get'
  })
}

// 获取默认应用
export function getDefaultConfigure(){
  return request({
    url: managerUrl.defaultConfigure ,
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
export function listConfigure(query) {
  return request({
    url: managerUrl.datatables ,
    method: 'post',
    params: query
  })
}

// 查询数据库详细
export function getConfigure(databaseId) {
  return request({
    url: managerUrl.detailUrl + '/' + parseStrEmpty(databaseId),
    method: 'get'
  })
}

// 新增数据库
export function addConfigure(data) {
  return request({
    url: managerUrl.saveUrl ,
    method: 'post',
    data: data
  })
}

// 修改数据库
export function updateConfigure(data) {
  return request({
    url: managerUrl.updateUrl ,
    method: 'put',
    data: data
  })
}

// 删除数据库
export function delConfigure(databaseId) {
  return request({
    url: managerUrl.removeUrl + '/' + parseStrEmpty(databaseId),
    method: 'delete'
  })
}
