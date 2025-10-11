package com.ruoyi.employee.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 员工基本信息对象 employee
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
public class Employee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 职工编号 */
    private Long empId;

    /** 职工姓名 */
    @Excel(name = "职工姓名")
    private String empName;

    /** 职工性别 */
    @Excel(name = "职工性别")
    private String empGender;

    /** 职工年龄 */
    @Excel(name = "职工年龄")
    private Long empAge;

    /** 职工电话 */
    @Excel(name = "职工电话")
    private String empPhone;

    /** 所在部门编号 */
    @Excel(name = "所在部门编号")
    private Long deptId;

    /** 担任职位编号 */
    @Excel(name = "担任职位编号")
    private Long posId;

    public void setEmpId(Long empId) 
    {
        this.empId = empId;
    }

    public Long getEmpId() 
    {
        return empId;
    }

    public void setEmpName(String empName) 
    {
        this.empName = empName;
    }

    public String getEmpName() 
    {
        return empName;
    }

    public void setEmpGender(String empGender) 
    {
        this.empGender = empGender;
    }

    public String getEmpGender() 
    {
        return empGender;
    }

    public void setEmpAge(Long empAge) 
    {
        this.empAge = empAge;
    }

    public Long getEmpAge() 
    {
        return empAge;
    }

    public void setEmpPhone(String empPhone) 
    {
        this.empPhone = empPhone;
    }

    public String getEmpPhone() 
    {
        return empPhone;
    }

    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    public void setPosId(Long posId) 
    {
        this.posId = posId;
    }

    public Long getPosId() 
    {
        return posId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("empId", getEmpId())
            .append("empName", getEmpName())
            .append("empGender", getEmpGender())
            .append("empAge", getEmpAge())
            .append("empPhone", getEmpPhone())
            .append("deptId", getDeptId())
            .append("posId", getPosId())
            .toString();
    }
}
