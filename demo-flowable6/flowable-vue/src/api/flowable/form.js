import request from "@/utils/request.js";
var api = "/ceva-mutirent"; //线上环境
if (process.env.NODE_ENV === "development") {
  api = "" //开发
}
// 查询流程表单列表
export function listForm(query) {
  return request({
    url: api+'/flowable/form/list',
    method: 'get',
    params: query
  })
}

// 查询流程表单详细
export function getFormDetails(formId) {
  return request({
    url: api + '/flowable/form/' + formId,
    method: 'get'
  })
}

// 新增流程表单
export function addForm(data) {
  return request({
    url: api+'/flowable/form',
    method: 'post',
    data: data
  })
}

// 修改流程表单
export function updateForm(data) {
  return request({
    url: api+'/flowable/form',
    method: 'put',
    data: data
  })
}

// 删除流程表单
export function delForm(formId) {
  return request({
    url: api+'/flowable/form/' + formId,
    method: 'delete'
  })
}

// 导出流程表单
export function exportForm(query) {
  return request({
    url: api+'/flowable/form/export',
    method: 'get',
    params: query
  })
}
