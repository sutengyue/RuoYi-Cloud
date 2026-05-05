import request from '@/utils/request'

export function getProcessList() {
  return request({
    url: '/flowable/process/list',
    method: 'get'
  })
}

export function startProcess(data) {
  return request({
    url: '/flowable/process/start',
    method: 'post',
    data: data
  })
}

export function suspendProcess(data) {
  return request({
    url: '/flowable/process/suspend',
    method: 'post',
    data: data
  })
}

export function activateProcess(data) {
  return request({
    url: '/flowable/process/activate',
    method: 'post',
    data: data
  })
}