import request from '@/utils/request'

export function getTaskList() {
  return request({
    url: '/flowable/task/list',
    method: 'get'
  })
}

export function claimTask(data) {
  return request({
    url: '/flowable/task/claim',
    method: 'post',
    data: data
  })
}

export function completeTask(data) {
  return request({
    url: '/flowable/task/complete',
    method: 'post',
    data: data
  })
}