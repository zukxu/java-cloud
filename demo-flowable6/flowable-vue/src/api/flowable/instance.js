import request from "@/utils/request.js";

var api = "/ceva-mutirent"; //线上环境
if (process.env.NODE_ENV === "development") {
  api = "" //开发
}

// 我的发起的流程
export function listProcess(query) {
  return request({
    url: api + '/flowable/instance/myProcess',
    method: 'get',
    params: query
  })
}

// 启动一个流程实例
export function startAndExecute(data) {
  return request({
    url: api + '/flowable/instance/startAndExecute',
    method: 'post',
    data: data
  })
}

// 取消申请
export function stopProcess(data) {
  return request({
    url: api + '/flowable/instance/stopProcess',
    method: 'post',
    data: data
  })
}

// 删除流程实例
export function deleteInstance(param) {
  return request({
    url: api + '/flowable/instance/delete/' + param,
    method: 'delete',
  })
}

// 挂起或者激活流程实例
export function susOrActInstance(param) {
  return request({
    url: api + '/flowable/instance/susOrAct',
    method: 'post',
  })
}

// 完成任务
export function complete(data) {
  return request({
    url: api + '/flowable/task/complete',
    method: 'post',
    data: data
  })
}


// 驳回任务
export function rejectTask(data) {
  return request({
    url: api + '/flowable/task/reject',
    method: 'post',
    data: data
  })
}

// 可退回任务列表
export function returnList(data) {
  return request({
    url: api + '/flowable/task/returnList',
    method: 'post',
    data: data
  })
}

// 部署流程实例
export function flowRecord(procInsId) {
  return request({
    url: api + 'flowable/instance/record',
    method: 'get',
    params: procInsId
  })
}



