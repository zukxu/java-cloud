import request from "@/utils/request.js";
var api = "/ceva-mutirent"; //线上环境
if (process.env.NODE_ENV === "development") {
  api = "/api/ceva-mutirent" //开发
}
// 查询流程分类列表
export function listCategory(query) {
  return request({
    url: api+'/flowable/category/list',
    method: 'get',
    params: query
  })
}

// 查询流程分类详细
export function getCategory(categoryId) {
  return request({
    url: api+'/flowable/category/' + categoryId,
    method: 'get'
  })
}

// 新增流程分类
export function addCategory(data) {
  return request({
    url: api+'/flowable/category',
    method: 'post',
    data: data
  })
}

// 修改流程分类
export function updateCategory(data) {
  return request({
    url: api+'/flowable/category',
    method: 'put',
    data: data
  })
}

// 删除流程分类
export function delCategory(categoryId) {
  return request({
    url: api+'/flowable/category/' + categoryId,
    method: 'delete'
  })
}
