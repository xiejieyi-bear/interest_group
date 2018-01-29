import request from '@/utils/request'

export function fetchActivityList(query) {
  return request({
    url: '/activity/all',
    method: 'get',
    params: query
  })
}
