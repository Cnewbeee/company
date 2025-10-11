import request from '@/utils/request'

// 查询职务信息列表
export function listJobposition(query) {
  return request({
    url: '/jobposition/jobposition/list',
    method: 'get',
    params: query
  })
}

// 查询职务信息详细
export function getJobposition(posId) {
  return request({
    url: '/jobposition/jobposition/' + posId,
    method: 'get'
  })
}

// 新增职务信息
export function addJobposition(data) {
  return request({
    url: '/jobposition/jobposition',
    method: 'post',
    data: data
  })
}

// 修改职务信息
export function updateJobposition(data) {
  return request({
    url: '/jobposition/jobposition',
    method: 'put',
    data: data
  })
}

// 删除职务信息
export function delJobposition(posId) {
  return request({
    url: '/jobposition/jobposition/' + posId,
    method: 'delete'
  })
}
