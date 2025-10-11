package com.ruoyi.employee.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.employee.mapper.EmployeeMapper;
import com.ruoyi.employee.domain.Employee;
import com.ruoyi.employee.service.IEmployeeService;

/**
 * 员工基本信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService 
{
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询员工基本信息
     * 
     * @param empId 员工基本信息主键
     * @return 员工基本信息
     */
    @Override
    public Employee selectEmployeeByEmpId(Long empId)
    {
        return employeeMapper.selectEmployeeByEmpId(empId);
    }

    /**
     * 查询员工基本信息列表
     * 
     * @param employee 员工基本信息
     * @return 员工基本信息
     */
    @Override
    public List<Employee> selectEmployeeList(Employee employee)
    {
        return employeeMapper.selectEmployeeList(employee);
    }

    /**
     * 新增员工基本信息
     * 
     * @param employee 员工基本信息
     * @return 结果
     */
    @Override
    public int insertEmployee(Employee employee)
    {
        return employeeMapper.insertEmployee(employee);
    }

    /**
     * 修改员工基本信息
     * 
     * @param employee 员工基本信息
     * @return 结果
     */
    @Override
    public int updateEmployee(Employee employee)
    {
        return employeeMapper.updateEmployee(employee);
    }

    /**
     * 批量删除员工基本信息
     * 
     * @param empIds 需要删除的员工基本信息主键
     * @return 结果
     */
    @Override
    public int deleteEmployeeByEmpIds(Long[] empIds)
    {
        return employeeMapper.deleteEmployeeByEmpIds(empIds);
    }

    /**
     * 删除员工基本信息信息
     * 
     * @param empId 员工基本信息主键
     * @return 结果
     */
    @Override
    public int deleteEmployeeByEmpId(Long empId)
    {
        return employeeMapper.deleteEmployeeByEmpId(empId);
    }
}
