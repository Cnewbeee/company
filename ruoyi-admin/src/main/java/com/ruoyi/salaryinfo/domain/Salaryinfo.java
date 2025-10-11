package com.ruoyi.salaryinfo.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 员工工资信息对象 salaryinfo
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
public class Salaryinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 职工编号（关联员工） */
    private Long empId;

    /** 应发工资 */
    @Excel(name = "应发工资")
    private BigDecimal payableSalary;

    /** 应扣工资 */
    @Excel(name = "应扣工资")
    private BigDecimal deductionSalary;

    /** 职工姓名 */
    @Excel(name = "职工姓名")
    private String empName;

    /** 实发工资（应发-应扣） */
    @Excel(name = "实发工资", readConverterExp = "应=发-应扣")
    private BigDecimal actualSalary;

    public void setEmpId(Long empId) 
    {
        this.empId = empId;
    }

    public Long getEmpId() 
    {
        return empId;
    }

    public void setPayableSalary(BigDecimal payableSalary) 
    {
        this.payableSalary = payableSalary;
    }

    public BigDecimal getPayableSalary() 
    {
        return payableSalary;
    }

    public void setDeductionSalary(BigDecimal deductionSalary) 
    {
        this.deductionSalary = deductionSalary;
    }

    public BigDecimal getDeductionSalary() 
    {
        return deductionSalary;
    }

    public void setEmpName(String empName) 
    {
        this.empName = empName;
    }

    public String getEmpName() 
    {
        return empName;
    }

    public void setActualSalary(BigDecimal actualSalary) 
    {
        this.actualSalary = actualSalary;
    }

    public BigDecimal getActualSalary() 
    {
        return actualSalary;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("empId", getEmpId())
            .append("payableSalary", getPayableSalary())
            .append("deductionSalary", getDeductionSalary())
            .append("empName", getEmpName())
            .append("actualSalary", getActualSalary())
            .toString();
    }
}
