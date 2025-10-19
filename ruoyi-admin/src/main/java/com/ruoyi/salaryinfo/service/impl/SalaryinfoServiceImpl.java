package com.ruoyi.salaryinfo.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.salaryinfo.mapper.SalaryinfoMapper;
import com.ruoyi.salaryinfo.domain.Salaryinfo;
import com.ruoyi.salaryinfo.service.ISalaryinfoService;

/**
 * 员工工资信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-10-11
 */
@Service
public class SalaryinfoServiceImpl implements ISalaryinfoService
{
    @Autowired
    private SalaryinfoMapper salaryinfoMapper;

    /**
     * 查询员工工资信息
     *
     * @param empId 员工工资信息主键
     * @return 员工工资信息
     */
    @Override
    public Salaryinfo selectSalaryinfoByEmpId(Long empId)
    {
        return salaryinfoMapper.selectSalaryinfoByEmpId(empId);
    }

    /**
     * 查询员工工资信息
     *
     * @param recordId 记录编号
     * @return 员工工资信息
     */
    @Override
    public Salaryinfo selectSalaryinfoByRecordId(Long recordId)
    {
        return salaryinfoMapper.selectSalaryinfoByRecordId(recordId);
    }

    /**
     * 查询员工工资信息列表
     *
     * @param salaryinfo 员工工资信息
     * @return 员工工资信息
     */
    @Override
    public List<Salaryinfo> selectSalaryinfoList(Salaryinfo salaryinfo)
    {
        return salaryinfoMapper.selectSalaryinfoList(salaryinfo);
    }

    /**
     * 新增员工工资信息
     *
     * @param salaryinfo 员工工资信息
     * @return 结果
     */
    @Override
    public int insertSalaryinfo(Salaryinfo salaryinfo)
    {
        // 计算实发工资: 应发工资 + 出勤奖金 + 其他奖金或处罚
        calculateActualSalary(salaryinfo);
        return salaryinfoMapper.insertSalaryinfo(salaryinfo);
    }

    /**
     * 修改员工工资信息
     *
     * @param salaryinfo 员工工资信息
     * @return 结果
     */
    @Override
    public int updateSalaryinfo(Salaryinfo salaryinfo)
    {
        // 计算实发工资: 应发工资 + 出勤奖金 + 其他奖金或处罚
        calculateActualSalary(salaryinfo);
        return salaryinfoMapper.updateSalaryinfo(salaryinfo);
    }

    /**
     * 批量删除员工工资信息
     *
     * @param empIds 需要删除的员工工资信息主键
     * @return 结果
     */
    @Override
    public int deleteSalaryinfoByEmpIds(Long[] empIds)
    {
        return salaryinfoMapper.deleteSalaryinfoByEmpIds(empIds);
    }

    /**
     * 批量删除员工工资信息
     *
     * @param recordIds 需要删除的记录编号
     * @return 结果
     */
    @Override
    public int deleteSalaryinfoByRecordIds(Long[] recordIds)
    {
        return salaryinfoMapper.deleteSalaryinfoByRecordIds(recordIds);
    }

    /**
     * 删除员工工资信息信息
     *
     * @param empId 员工工资信息主键
     * @return 结果
     */
    @Override
    public int deleteSalaryinfoByEmpId(Long empId)
    {
        return salaryinfoMapper.deleteSalaryinfoByEmpId(empId);
    }

    /**
     * 删除员工工资信息信息
     *
     * @param recordId 记录编号
     * @return 结果
     */
    @Override
    public int deleteSalaryinfoByRecordId(Long recordId)
    {
        return salaryinfoMapper.deleteSalaryinfoByRecordId(recordId);
    }

    /**
     * 计算实发工资
     * 实发工资 = 应发工资 + 出勤奖金 + 其他奖金或处罚
     */
    private void calculateActualSalary(Salaryinfo salaryinfo) {
        BigDecimal payableSalary = salaryinfo.getPayableSalary() != null ? salaryinfo.getPayableSalary() : BigDecimal.ZERO;
        BigDecimal attendanceBonus = salaryinfo.getAttendanceBonus() != null ? salaryinfo.getAttendanceBonus() : BigDecimal.ZERO;
        BigDecimal otherBonusPenalty = salaryinfo.getOtherBonusPenalty() != null ? salaryinfo.getOtherBonusPenalty() : BigDecimal.ZERO;

        // 计算实发工资
        BigDecimal actualSalary = payableSalary.add(attendanceBonus).add(otherBonusPenalty);
        salaryinfo.setActualSalary(actualSalary);
    }
}