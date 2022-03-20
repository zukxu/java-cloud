import request from "@/utils/request.js";

var api = "/ceva-mutirent"; //线上环境
if (process.env.NODE_ENV === "development") {
  api = "" //开发
}

// 查询待办任务列表
export function todoList(query) {
  return request({
    url: api + '/flowable/task/todoList',
    method: 'get',
    params: query
  })
}

// 查询已办任务列表
export function finishedList(query) {
  return request({
    url: api + '/flowable/task/finishedList',
    method: 'get',
    params: query
  })
}

// 获取可驳回节点列表
export function getBackNodes(query) {
  return request({
    url: api + '/getBackNode',
    method: 'get',
    params: query
  })
}
