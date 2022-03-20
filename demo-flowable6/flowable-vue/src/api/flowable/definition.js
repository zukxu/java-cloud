import request from "@/utils/request.js";
var api = "/ceva-mutirent"; //线上环境
if (process.env.NODE_ENV === "development") {
  api = "" //开发
}

// 查询流程定义列表
export function listDefinition(query) {
  return request({
    url: api + '/flowable/definition/list',
    method: 'get',
    params: query
  })
}

//查看流程定义图
export function definitionView(query) {
  return request({
    url: api + '/flowable/definition/view',
    method: 'get',
    params: query
  })
}


// 激活/挂起流程
export function updateState(params) {
  return request({
    url: api + '/flowable/definition/updateState',
    method: 'put',
    params: params
  })
}

// 读取xml文件
export function readXml(deployId) {
  return request({
    url: api+'/flowable/definition/readXml/' + deployId,
    method: 'get'
  })
}

// 读取image文件
export function readImage(deployId) {
  return request({
    url: api+'/flowable/definition/readImage/' + deployId,
    method: 'get'
  })
}


// 新增定义
export function saveXml(data) {
  return request({
    url: api + '/flowable/definition/add',
    method: 'post',
    data: data
  })
}

// 删除流程定义
export function delDefinition(query) {
  return request({
    url: api+'/flowable/definition/delete/',
    method: 'delete',
    params: query
  })
}

// 导出流程定义
export function exportDeployment(query) {
  return request({
    url: api+'/flowable/definition/export',
    method: 'get',
    params: query
  })
}
