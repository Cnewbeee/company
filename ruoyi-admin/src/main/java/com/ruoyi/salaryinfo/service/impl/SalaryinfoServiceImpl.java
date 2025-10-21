package com.ruoyi.salaryinfo.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public String getTotalSalaryByYear(String formattedOneYearAgo) {
        BigDecimal totalSalary = salaryinfoMapper.getTotalSalaryByYear(formattedOneYearAgo);
        return totalSalary != null ? totalSalary.toString() : "0";
    }

    public Map<String, String> getTotalSalaryByMonth(String formattedOneYearAgo) {
        List<Map<String, Object>> monthlySalaryList  = salaryinfoMapper.getTotalSalaryByMonth(formattedOneYearAgo);
        Map<String, String> result = new LinkedHashMap<>();

        // 先将查询结果转换为月份-工资的映射
        Map<String, BigDecimal> monthlySalaryMap = new HashMap<>();
        for (Map<String, Object> row : monthlySalaryList) {
            String month = row.get("month").toString();
            BigDecimal salary = (BigDecimal) row.get("total_salary");
            monthlySalaryMap.put(month, salary);
        }


        // 确保返回12个月的数据，即使某些月份没有数据也显示0
        for (int month = 1; month <= 12; month++) {
            String monthKey = String.format("%02d月", month);
            BigDecimal salary = monthlySalaryMap.getOrDefault(String.valueOf(month), BigDecimal.ZERO);
            result.put(monthKey, salary.toString());
        }

        return result;
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