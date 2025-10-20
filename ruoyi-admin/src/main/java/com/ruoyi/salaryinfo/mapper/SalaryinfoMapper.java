package com.ruoyi.salaryinfo.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.ruoyi.salaryinfo.domain.Salaryinfo;

/**
 * 员工工资信息Mapper接口
 *
 * @author ruoyi
 * @date 2025-10-11
 */
public interface SalaryinfoMapper
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
     * 删除员工工资信息
     *
     * @param recordId 记录编号
     * @return 结果
     */
    public int deleteSalaryinfoByRecordId(Long recordId);

    /**
     * 删除员工工资信息
     *
     * @param empId 员工工资信息主键
     * @return 结果
     */
    public int deleteSalaryinfoByEmpId(Long empId);

    /**
     * 批量删除员工工资信息
     *
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSalaryinfoByRecordIds(Long[] recordIds);

    /**
     * 批量删除员工工资信息
     *
     * @param empIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSalaryinfoByEmpIds(Long[] empIds);



    /**
     * 获取指定年份的部门员工工资信息
     *
     * @param formattedOneYearAgo 指定年份
     * @return 部门员工工资信息列表
     */
    // 在 SalaryinfoMapper 接口中修改方法签名
    BigDecimal getTotalSalaryByYear(String formattedOneYearAgo);

}