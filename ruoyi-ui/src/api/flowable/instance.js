import request from '@/utils/request'

export function getInstanceList() {
  return request({
    url: '/flowable/instance/list',
    method: 'get'
  })
}

export function terminateInstance(data) {
  return request({
    url: '/flowable/instance/terminate',
    method: 'post',
    data: data
  })
}