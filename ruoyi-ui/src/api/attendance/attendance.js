import request from '@/utils/request'

// 查询员工考勤信息列表
export function listAttendance(query) {
  return request({
    url: '/attendance/attendance/list',
    method: 'get',
    params: query
  })
}

// 查询员工考勤信息详细
export function getAttendance(empId) {
  return request({
    url: '/attendance/attendance/' + empId,
    method: 'get'
  })
}

// 新增员工考勤信息
export function addAttendance(data) {
  return request({
    url: '/attendance/attendance',
    method: 'post',
    data: data
  })
}

// 修改员工考勤信息
export function updateAttendance(data) {
  return request({
    url: '/attendance/attendance',
    method: 'put',
    data: data
  })
}

// 删除员工考勤信息
export function delAttendance(empId) {
  return request({
    url: '/attendance/attendance/' + empId,
    method: 'delete'
  })
}
