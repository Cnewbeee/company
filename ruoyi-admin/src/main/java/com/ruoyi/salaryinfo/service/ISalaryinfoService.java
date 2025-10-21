package com.ruoyi.salaryinfo.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.salaryinfo.domain.Salaryinfo;

/**
 * 员工工资信息Service接口
 *
 * @author ruoyi
 * @date 2025-10-11
 */
public interface ISalaryinfoService
{
    /**
     * 查询员工工资信息
     *
     * @param empId 员工工资信息主键
     * @return 员工工资信息
     */
    public Salaryinfo selectSalaryinfoByEmpId(Long empId);

    /**
     * 查询员工工资信息
     *
     * @param recordId 记录编号
     * @return 员工工资信息
     */
    public Salaryinfo selectSalaryinfoByRecordId(Long recordId);

    /**
     * 查询员工工资信息列表
     *
     * @param salaryinfo 员工工资信息
     * @return 员工工资信息集合
     */
    public List<Salaryinfo> selectSalaryinfoList(Salaryinfo salaryinfo);

    /**
     * 新增员工工资信息
     *
     * @param salaryinfo 员工工资信息
     * @return 结果
     */
    public int insertSalaryinfo(Salaryinfo salaryinfo);

    /**
     * 修改员工工资信息
     *
     * @param salaryinfo 员工工资信息
     * @return 结果
     */
    public int updateSalaryinfo(Salaryinfo salaryinfo);

    /**
     * 批量删除员工工资信息
     *
     * @param empIds 需要删除的员工工资信息主键集合
     * @return 结果
     */
    public int deleteSalaryinfoByEmpIds(Long[] empIds);

    /**
     * 批量删除员工工资信息
     *
     * @param recordIds 需要删除的记录编号集合
     * @return 结果
     */
    public int deleteSalaryinfoByRecordIds(Long[] recordIds);

    /**
     * 删除员工工资信息信息
     *
     * @param empId 员工工资信息主键
     * @return 结果
     */
    public int deleteSalaryinfoByEmpId(Long empId);

    /**
     * 删除员工工资信息信息
     *
     * @param recordId 记录编号
     * @return 结果
     */
    public int deleteSalaryinfoByRecordId(Long recordId);


    /**
     * 统计一年的员工工资
     *
     * @param formattedOneYearAgo
     * @return
     */
    String getTotalSalaryByYear(String formattedOneYearAgo);

    /**
     * 统计前一年每个月的员工总工资
     *
     * @param formattedOneYearAgo 一年前的日期
     * @return 月份-工资的映射关系
     */
    Map<String, String> getTotalSalaryByMonth(String formattedOneYearAgo);
}