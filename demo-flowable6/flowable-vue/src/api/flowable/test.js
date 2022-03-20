import request from "@/utils/request.js";

var api = "/ceva-mutirent"; //线上环境
if (process.env.NODE_ENV === "development") {
  api = "" //开发
}

// 查询流程分类列表
export function sysUserList(query) {
  return request({
    url: api + '/sys/user/list',
    method: 'get',
    params: query
  })
}
