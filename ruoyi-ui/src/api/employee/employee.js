import request from '@/utils/request'

// 查询员工基本信息列表
export function listEmployee(query) {
  return request({
    url: '/employee/employee/list',
    method: 'get',
    params: query
  })
}

// 查询员工基本信息详细
export function getEmployee(empId) {
  return request({
    url: '/employee/employee/' + empId,
    method: 'get'
  })
}

// 新增员工基本信息
export function addEmployee(data) {
  return request({
    url: '/employee/employee',
    method: 'post',
    data: data
  })
}

// 修改员工基本信息
export function updateEmployee(data) {
  return request({
    url: '/employee/employee',
    method: 'put',
    data: data
  })
}

// 删除员工基本信息
export function delEmployee(empId) {
  return request({
    url: '/employee/employee/' + empId,
    method: 'delete'
  })
}
