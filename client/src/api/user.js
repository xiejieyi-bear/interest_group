import request from '@/utils/request'

export function fetchUserList(query) {
  return request({
    url: '/user',
    method: 'get',
    params: query
  })
}

export function createUser(data) {
  return request({
    url: '/user',
    method: 'post',
    data
  })
}

export function deleteUser(usermark) {
  return request({
    url: '/user/' + usermark,
    method: 'delete'
  })
}

export function fetchActivityList(query) {
  return request({
    url: '/activity/all',
    method: 'get',
    params: query
  })
}

export function fetchFundsList(query) {
  return request({
    url: '/funds/all',
    method: 'get',
    params: query
  })
}

export function getUserChargeHistory(query) {
  return request({
    url: '/user/chargehistory',
    method: 'get',
    params: query
  })
}

export function getUserConsumeHistory(query) {
  return request({
    url: '/user/consumehistory',
    method: 'get',
    params: query
  })
}
