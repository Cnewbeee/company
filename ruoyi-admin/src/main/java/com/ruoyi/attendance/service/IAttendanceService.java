package com.ruoyi.attendance.service;

import java.util.List;
import com.ruoyi.attendance.domain.Attendance;

/**
 * 员工考勤信息Service接口
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
public interface IAttendanceService 
{
    /**
     * 查询员工考勤信息
     * 
     * @param empId 员工考勤信息主键
     * @return 员工考勤信息
     */
    public Attendance selectAttendanceByEmpId(Long empId);

    /**
     * 查询员工考勤信息列表
     * 
     * @param attendance 员工考勤信息
     * @return 员工考勤信息集合
     */
    public List<Attendance> selectAttendanceList(Attendance attendance);

    /**
     * 新增员工考勤信息
     * 
     * @param attendance 员工考勤信息
     * @return 结果
     */
    public int insertAttendance(Attendance attendance);

    /**
     * 修改员工考勤信息
     * 
     * @param attendance 员工考勤信息
     * @return 结果
     */
    public int updateAttendance(Attendance attendance);

    /**
     * 批量删除员工考勤信息
     * 
     * @param empIds 需要删除的员工考勤信息主键集合
     * @return 结果
     */
    public int deleteAttendanceByEmpIds(Long[] empIds);

    /**
     * 删除员工考勤信息信息
     * 
     * @param empId 员工考勤信息主键
     * @return 结果
     */
    public int deleteAttendanceByEmpId(Long empId);
}
