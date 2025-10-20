import request from '@/utils/request'

// 查询系统管理员列表
export function listAdmin(query) {
  return request({
    url: '/admin/admin/list',
    method: 'get',
    params: query
  })
}

// 查询系统管理员详细
export function getAdmin(adminUsername) {
  return request({
    url: '/admin/admin/' + adminUsername,
    method: 'get'
  })
}

// 新增系统管理员
export function addAdmin(data) {
  return request({
    url: '/admin/admin',
    method: 'post',
    data: data
  })
}

// 修改系统管理员
export function updateAdmin(data) {
  return request({
    url: '/admin/admin',
    method: 'put',
    data: data
  })
}

// 删除系统管理员
export function delAdmin(adminUsername) {
  return request({
    url: '/admin/admin/' + adminUsername,
    method: 'delete'
  })
}
