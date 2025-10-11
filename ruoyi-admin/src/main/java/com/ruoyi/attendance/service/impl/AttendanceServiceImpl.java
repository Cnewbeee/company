package com.ruoyi.attendance.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.attendance.mapper.AttendanceMapper;
import com.ruoyi.attendance.domain.Attendance;
import com.ruoyi.attendance.service.IAttendanceService;

/**
 * 员工考勤信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
@Service
public class AttendanceServiceImpl implements IAttendanceService 
{
    @Autowired
    private AttendanceMapper attendanceMapper;

    /**
     * 查询员工考勤信息
     * 
     * @param empId 员工考勤信息主键
     * @return 员工考勤信息
     */
    @Override
    public Attendance selectAttendanceByEmpId(Long empId)
    {
        return attendanceMapper.selectAttendanceByEmpId(empId);
    }

    /**
     * 查询员工考勤信息列表
     * 
     * @param attendance 员工考勤信息
     * @return 员工考勤信息
     */
    @Override
    public List<Attendance> selectAttendanceList(Attendance attendance)
    {
        return attendanceMapper.selectAttendanceList(attendance);
    }

    /**
     * 新增员工考勤信息
     * 
     * @param attendance 员工考勤信息
     * @return 结果
     */
    @Override
    public int insertAttendance(Attendance attendance)
    {
        return attendanceMapper.insertAttendance(attendance);
    }

    /**
     * 修改员工考勤信息
     * 
     * @param attendance 员工考勤信息
     * @return 结果
     */
    @Override
    public int updateAttendance(Attendance attendance)
    {
        return attendanceMapper.updateAttendance(attendance);
    }

    /**
     * 批量删除员工考勤信息
     * 
     * @param empIds 需要删除的员工考勤信息主键
     * @return 结果
     */
    @Override
    public int deleteAttendanceByEmpIds(Long[] empIds)
    {
        return attendanceMapper.deleteAttendanceByEmpIds(empIds);
    }

    /**
     * 删除员工考勤信息信息
     * 
     * @param empId 员工考勤信息主键
     * @return 结果
     */
    @Override
    public int deleteAttendanceByEmpId(Long empId)
    {
        return attendanceMapper.deleteAttendanceByEmpId(empId);
    }
}
