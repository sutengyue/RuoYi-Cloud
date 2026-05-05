import request from '@/utils/request'

export function getHistoryList() {
  return request({
    url: '/flowable/history/list',
    method: 'get'
  })
}