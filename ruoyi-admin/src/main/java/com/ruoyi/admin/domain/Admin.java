package com.ruoyi.admin.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 系统管理员对象 admin
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
public class Admin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 管理员用户名 */
    private String adminUsername;

    /** 管理员密码（建议加密存储） */
    @Excel(name = "管理员密码", readConverterExp = "建=议加密存储")
    private String adminPassword;

    /** 管理员手机号 */
    @Excel(name = "管理员手机号")
    private String adminPhone;

    public void setAdminUsername(String adminUsername) 
    {
        this.adminUsername = adminUsername;
    }

    public String getAdminUsername() 
    {
        return adminUsername;
    }

    public void setAdminPassword(String adminPassword) 
    {
        this.adminPassword = adminPassword;
    }

    public String getAdminPassword() 
    {
        return adminPassword;
    }

    public void setAdminPhone(String adminPhone) 
    {
        this.adminPhone = adminPhone;
    }

    public String getAdminPhone() 
    {
        return adminPhone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("adminUsername", getAdminUsername())
            .append("adminPassword", getAdminPassword())
            .append("adminPhone", getAdminPhone())
            .toString();
    }
}
