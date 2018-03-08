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
export function fetchFundsList(query) {
  return request({
    url: '/funds/all',
    method: 'get',
    params: query
  })
}

export function getUserChargeHistory(username) {
  return request({
    url: '/finance/recharge/user/' + username,
    method: 'get'
  })
}

export function getUserConsumeHistory(username) {
  return request({
    url: '/finance/expenditure/user/' + username ,
    method: 'get'
  })
}

export function reChargeBill(data) {
  return request({
    url: '/finance/recharge/user',
    method: 'post',
    data
  })
}


