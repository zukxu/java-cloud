import request from "@/utils/request.js";

var api = "/ceva-mutirent"; //线上环境
if (process.env.NODE_ENV === "development") {
  api = "" //开发
}

// 完成任务
export function complete(data) {
  return request({
    url: api + '/flowable/task/complete',
    method: 'post',
    data: data
  })
}

// 退回任务
export function revoke(data) {
  return request({
    url: api + '/flowable/task/revoke',
    method: 'post',
    data: data
  })
}

// 转办任务
export function turnTo(data) {
  return request({
    url: api + '/flowable/task/turnTo',
    method: 'post',
    data: data
  })
}

// 委派任务
export function delegate(data) {
  return request({
    url: api + '/flowable/task/delegate',
    method: 'post',
    data: data
  })
}

// 签收任务
export function claim(data) {
  return request({
    url: api + '/flowable/task/claim',
    method: 'post',
    data: data
  })
}

// 反签收任务
export function unClaim(data) {
  return request({
    url: api + '/flowable/task/unClaim',
    method: 'post',
    data: data
  })
}

// 前加签
export function beforeAddSign(data) {
  return request({
    url: api + '/flowable/task/beforeAddSignTask',
    method: 'post',
    data: data
  })
}

// 后加签
export function afterAddSign(data) {
  return request({
    url: api + '/flowable/task/afterAddSignTask',
    method: 'post',
    data: data
  })
}
