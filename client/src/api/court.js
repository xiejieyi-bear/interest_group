import request from '@/utils/request'

export function fetchCourtJoinData(query) {
  return request({
    url: '/court/join',
    method: 'get',
    params: query
  })
}

export function fetchCourtList(query) {
  return request({
    url: '/court/all',
    method: 'get',
    params: query
  })
}

export function createCourt(data) {
  return request({
    url: '/court/add',
    method: 'post',
    data
  })
}

export function deleteCourt(data) {
  return request({
    url: '/court/del',
    method: 'post',
    data
  })
}
