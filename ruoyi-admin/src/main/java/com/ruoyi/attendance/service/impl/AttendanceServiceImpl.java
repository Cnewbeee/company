package com.ruoyi.attendance.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.attendance.mapper.AttendanceMapper;
import com.ruoyi.attendance.domain.Attendance;
import com.ruoyi.attendance.service.IAttendanceService;
import com.ruoyi.employee.domain.Employee;
import com.ruoyi.employee.service.IEmployeeService;
import com.ruoyi.salaryinfo.domain.Salaryinfo;
import com.ruoyi.salaryinfo.service.ISalaryinfoService;
import com.ruoyi.salarystandard.domain.Salarystandard;
import com.ruoyi.salarystandard.service.ISalarystandardService;

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

    @Autowired
    private ISalaryinfoService salaryinfoService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private ISalarystandardService salarystandardService;

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
     * 查询员工考勤信息
     *
     * @param recordId 记录编号
     * @return 员工考勤信息
     */
    @Override
    public Attendance selectAttendanceByRecordId(Long recordId)
    {
        return attendanceMapper.selectAttendanceByRecordId(recordId);
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
    @Transactional
    public int insertAttendance(Attendance attendance)
    {
        System.out.println("开始添加考勤信息，员工ID: " + attendance);

        // 计算并设置出勤奖金：1000 - 缺勤天数 * 100
        calculateAttendanceBonus(attendance);

        int result = attendanceMapper.insertAttendance(attendance);

        // 添加考勤信息后，自动添加工资信息
        if (result > 0) {
            createSalaryInfoFromAttendance(attendance);
        }

        return result;
    }

    /**
     * 修改员工考勤信息
     *
     * @param attendance 员工考勤信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateAttendance(Attendance attendance)
    {
        // 计算并设置出勤奖金：1000 - 缺勤天数 * 100
        calculateAttendanceBonus(attendance);

        int result = attendanceMapper.updateAttendance(attendance);

        // 更新考勤信息后，同步更新工资信息
        if (result > 0) {
            updateSalaryInfoFromAttendance(attendance);
        }

        return result;
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
    /**
     * 删除员工考勤信息信息
     *
     * @param recordId 员工考勤信息主键
     * @return 结果
     */
    @Override
    public int deleteAttendanceByRecordId(Long recordId)
    {
        return attendanceMapper.deleteAttendanceByEmpId(recordId);
    }

    /**
     * 删除员工考勤信息信息
     *
     * @param recordIds 记录编号
     * @return 结果
     */

    @Override
    public int deleteAttendanceByRecordIds(Long[] recordIds)
    {
        return attendanceMapper.deleteAttendanceByRecordIds(recordIds);
    }

    /**
     * 计算出勤奖金：1000 - 缺勤天数 * 100
     * @param attendance 考勤信息
     */
    private void calculateAttendanceBonus(Attendance attendance) {
        if (attendance.getAbsentDays() != null) {
            BigDecimal bonus = new BigDecimal("1000")
                    .subtract(new BigDecimal(attendance.getAbsentDays()).multiply(new BigDecimal("100")));
            // 确保奖金不为负数
            if (bonus.compareTo(BigDecimal.ZERO) < 0) {
                bonus = BigDecimal.ZERO;
            }
            attendance.setAttendBonus(bonus);
        } else {
            attendance.setAttendBonus(new BigDecimal("1000"));
        }
    }

    /**
     * 根据考勤信息创建工资信息
     * @param attendance 考勤信息
     */
    private void createSalaryInfoFromAttendance(Attendance attendance) {
        try {
            //System.out.println("开始创建工资信息，考勤记录ID: " + attendance.getRecordId() + ", 员工ID: " + attendance.getEmpId());

            // 获取员工信息
            Employee employee = employeeService.selectEmployeeByEmpId(attendance.getEmpId());
            if (employee == null) {
                //System.err.println("员工信息不存在，empId: " + attendance.getEmpId());
                return;
            }

            attendance.getCreateTime();
            System.out.println("考勤时间: " + attendance.getCreateTime());


            // 获取工资标准 - 按职位ID查询
            Salarystandard salarystandard = salarystandardService.selectSalarystandardByPosId(employee.getPosId());


            if (salarystandard == null) {
                //System.err.println("工资标准不存在，posId: " + employee.getPosId());
                return;
            }
            //System.out.println("获取到工资标准，基本工资: " + salarystandard.getBasicSalary() + ", 职务补贴: " + salarystandard.getPositionAllowance());

            // 创建工资信息
            Salaryinfo salaryinfo = new Salaryinfo();
            salaryinfo.setCreateTime(attendance.getCreateTime());
            salaryinfo.setRecordId(attendance.getRecordId()); // 使用相同的记录编号
            salaryinfo.setEmpId(attendance.getEmpId());
            salaryinfo.setEmpName(employee.getEmpName());

            // 设置应发工资（基本工资 + 职务补贴）
            BigDecimal payableSalary = salarystandard.getBasicSalary().add(salarystandard.getPositionAllowance());
            salaryinfo.setPayableSalary(payableSalary);

            // 设置出勤奖金
            salaryinfo.setAttendanceBonus(attendance.getAttendBonus());

            // 其他奖金或处罚默认为0
            salaryinfo.setOtherBonusPenalty(BigDecimal.ZERO);
            salaryinfo.setBonusPenaltyRemark("");

            // 计算实发工资
            salaryinfo.setActualSalary(payableSalary.add(attendance.getAttendBonus()).add(BigDecimal.ZERO));

            // 插入工资信息
            int insertResult = salaryinfoService.insertSalaryinfo(salaryinfo);
            if (insertResult > 0) {
                //System.out.println("成功创建工资信息，recordId: " + attendance.getRecordId());
            } else {
                //System.err.println("创建工资信息失败，recordId: " + attendance.getRecordId());
            }
        } catch (Exception e) {
            // 记录详细的错误信息
            System.err.println("创建工资信息时发生异常: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 根据考勤信息更新工资信息
     * @param attendance 考勤信息
     */
    private void updateSalaryInfoFromAttendance(Attendance attendance) {
        try {
            // 获取工资信息
            Salaryinfo salaryinfo = salaryinfoService.selectSalaryinfoByRecordId(attendance.getRecordId());
            if (salaryinfo == null) {
                return;
            }

            // 更新出勤奖金
            salaryinfo.setAttendanceBonus(attendance.getAttendBonus());

            // 重新计算实发工资
            BigDecimal actualSalary = salaryinfo.getPayableSalary()
                    .add(attendance.getAttendBonus())
                    .add(salaryinfo.getOtherBonusPenalty());
            salaryinfo.setActualSalary(actualSalary);

            // 更新工资信息
            salaryinfoService.updateSalaryinfo(salaryinfo);
        } catch (Exception e) {
            // 记录日志，但不影响考勤信息的更新
            e.printStackTrace();
        }
    }
}