package com.ruoyi.employee.service;

import java.util.List;
import com.ruoyi.employee.domain.Employee;

/**
 * 员工基本信息Service接口
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
public interface IEmployeeService 
{
    /**
     * 查询员工基本信息
     * 
     * @param empId 员工基本信息主键
     * @return 员工基本信息
     */
    public Employee selectEmployeeByEmpId(Long empId);

    /**
     * 查询员工基本信息列表
     * 
     * @param employee 员工基本信息
     * @return 员工基本信息集合
     */
    public List<Employee> selectEmployeeList(Employee employee);

    /**
     * 新增员工基本信息
     * 
     * @param employee 员工基本信息
     * @return 结果
     */
    public int insertEmployee(Employee employee);

    /**
     * 修改员工基本信息
     * 
     * @param employee 员工基本信息
     * @return 结果
     */
    public int updateEmployee(Employee employee);

    /**
     * 批量删除员工基本信息
     * 
     * @param empIds 需要删除的员工基本信息主键集合
     * @return 结果
     */
    public int deleteEmployeeByEmpIds(Long[] empIds);

    /**
     * 删除员工基本信息信息
     * 
     * @param empId 员工基本信息主键
     * @return 结果
     */
    public int deleteEmployeeByEmpId(Long empId);
}
