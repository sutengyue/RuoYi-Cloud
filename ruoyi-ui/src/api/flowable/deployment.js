import request from '@/utils/request'

export function getDeploymentList() {
  return request({
    url: '/flowable/deployment/list',
    method: 'get'
  })
}

export function deleteDeployment(deploymentId) {
  return request({
    url: '/flowable/deployment/' + deploymentId,
    method: 'delete'
  })
}