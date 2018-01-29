import request from '@/utils/request'

export function fetchUserList(query) {
  return request({
    url: '/user/all',
    method: 'get',
    params: query
  })
}

export function createUser(data) {
  return request({
    url: '/user/add',
    method: 'post',
    data
  })
}

export function deleteUser(data) {
  return request({
    url: '/user/del',
    method: 'post',
    data
  })
}

export function fetchActivityList(query) {
  return request({
    url: '/activity/all',
    method: 'get',
    params: query
  })
}
