import request from '@/utils/request'

// 查询员工工资信息列表
export function listSalaryinfo(query) {
  return request({
    url: '/salaryinfo/salaryinfo/list',
    method: 'get',
    params: query
  })
}

// 查询员工工资信息详细
export function getSalaryinfo(empId) {
  return request({
    url: '/salaryinfo/salaryinfo/' + empId,
    method: 'get'
  })
}

// 新增员工工资信息
export function addSalaryinfo(data) {
  return request({
    url: '/salaryinfo/salaryinfo',
    method: 'post',
    data: data
  })
}

// 修改员工工资信息
export function updateSalaryinfo(data) {
  return request({
    url: '/salaryinfo/salaryinfo',
    method: 'put',
    data: data
  })
}

// 删除员工工资信息
export function delSalaryinfo(empId) {
  return request({
    url: '/salaryinfo/salaryinfo/' + empId,
    method: 'delete'
  })
}
