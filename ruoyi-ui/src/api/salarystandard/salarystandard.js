import request from '@/utils/request'

// 查询工资标准列表
export function listSalarystandard(query) {
  return request({
    url: '/salarystandard/salarystandard/list',
    method: 'get',
    params: query
  })
}

// 查询工资标准详细
export function getSalarystandard(posId) {
  return request({
    url: '/salarystandard/salarystandard/' + posId,
    method: 'get'
  })
}

// 新增工资标准
export function addSalarystandard(data) {
  return request({
    url: '/salarystandard/salarystandard',
    method: 'post',
    data: data
  })
}

// 修改工资标准
export function updateSalarystandard(data) {
  return request({
    url: '/salarystandard/salarystandard',
    method: 'put',
    data: data
  })
}

// 删除工资标准
export function delSalarystandard(posId) {
  return request({
    url: '/salarystandard/salarystandard/' + posId,
    method: 'delete'
  })
}
