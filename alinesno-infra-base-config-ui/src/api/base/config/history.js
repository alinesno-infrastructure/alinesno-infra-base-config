import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

/**
 * 数据库接口操作
 * 
 * @author luoxiaodong
 * @since 1.0.0
 */

// 接口配置项
var prefix = '/api/infra/base/config/history/' ;
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
    choiceHistory: prefix + "choiceHistory",
    changeField: prefix + "changeField",
    defaultHistory: prefix + "defaultHistory",
    downloadfile: prefix + "downloadfile",
    currentHistory: prefix + "currentHistory",
}

// 配置当前应用
export function choiceHistory(id){
  return request({
      url: managerUrl.choiceHistory + '?HistoryId=' + parseStrEmpty(id),
      method: 'get'
  })
}

// 选择当前应用
export function getCurrentHistory(){
  return request({
      url: managerUrl.currentHistory , 
      method: 'get'
  })
}

// 获取默认应用
export function getDefaultHistory(){
  return request({
    url: managerUrl.defaultHistory ,
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
export function listHistory(query) {
  return request({
    url: managerUrl.datatables ,
    method: 'post',
    params: query
  })
}

// 查询数据库详细
export function getHistory(databaseId) {
  return request({
    url: managerUrl.detailUrl + '/' + parseStrEmpty(databaseId),
    method: 'get'
  })
}

// 新增数据库
export function addHistory(data) {
  return request({
    url: managerUrl.saveUrl ,
    method: 'post',
    data: data
  })
}

// 修改数据库
export function updateHistory(data) {
  return request({
    url: managerUrl.updateUrl ,
    method: 'put',
    data: data
  })
}

// 删除数据库
export function delHistory(databaseId) {
  return request({
    url: managerUrl.removeUrl + '/' + parseStrEmpty(databaseId),
    method: 'delete'
  })
}
