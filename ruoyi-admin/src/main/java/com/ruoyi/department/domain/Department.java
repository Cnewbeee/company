package com.ruoyi.department.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.Pattern;

/**
 * 部门信息对象 department
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
public class Department extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 部门编号 */
    @Excel(name = "部门编号")
    private Long deptId;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String deptName;

    /** 部门经理 */
    @Excel(name = "部门经理")
    private String deptManager;

    /** 部门联系电话 */
    @Excel(name = "部门联系电话")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "电话号码格式不正确")
    private String deptPhone;

    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    public void setDeptName(String deptName) 
    {
        this.deptName = deptName;
    }

    public String getDeptName() 
    {
        return deptName;
    }

    public void setDeptManager(String deptManager) 
    {
        this.deptManager = deptManager;
    }

    public String getDeptManager() 
    {
        return deptManager;
    }

    public void setDeptPhone(String deptPhone) 
    {
        this.deptPhone = deptPhone;
    }

    public String getDeptPhone() 
    {
        return deptPhone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("deptManager", getDeptManager())
            .append("deptPhone", getDeptPhone())
            .toString();
    }
}
