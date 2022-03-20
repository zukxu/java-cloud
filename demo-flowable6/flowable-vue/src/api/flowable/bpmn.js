import request from "@/utils/request.js";
var api = "/ceva-mutirent"; //线上环境
if (process.env.NODE_ENV === "development") {
  api = "" //开发
}

// 查询流程分类列表
export function bpmnListCategory(query) {
  return request({
    url: api + '/flowable/bpmn/category',
    method: 'get',
    params: query
  })
}
// 指定流程办理人员列表
export function bpmnListUser(query) {
  return request({
    url: api + '/flowable/bpmn/users',
    method: 'get',
    params: query
  })
}

// 指定流程办理组列表
export function bpmnListGroups(query) {
  return request({
    url: api + '/flowable/bpmn/groups',
    method: 'get',
    params: query
  })
}

// 指派部门树
export function bpmnTreeDept(query) {
  return request({
    url: api + '/flowable/bpmn/deptTree',
    method: 'get',
    params: query
  })
}
