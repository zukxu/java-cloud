import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/test/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/test/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout(token) {
  return request({
    url: '/test/logout',
    method: 'post',
    data:token
  })
}
