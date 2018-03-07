import request from '@/utils/request'

export function fetchCourtJoinData(query) {
  return request({
    url: '/activity/join',
    method: 'get',
    params: query
  })
}

export function fetchCourtList(query) {
  return request({
    url: '/court',
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

export function getChargeHistory(query) {
  return request({
    url: '/court/charge',
    method: 'get',
    params: query
  })
}

export function cancel_activity(username, activityID) {
  return request({
    url: '/activity/join/' + username + '/' + activityID,
    method: 'delete'
  })
}

export function create_activity(data) {
  return request({
    url: '/activity',
    method: 'post',
    data
  })
}

export function JoinActivityAPI(data) {
  return request({
    url: '/activity/join',
    method: 'post',
    data
  })
}


export function fetchActivityList(query) {
  return request({
    url: '/activity',
    method: 'get',
    params: query
  })
}
