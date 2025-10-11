package com.ruoyi.jobposition.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 职务信息对象 jobposition
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
public class Jobposition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 职位编号 */
    private Long posId;

    /** 职位名称 */
    @Excel(name = "职位名称")
    private String posName;

    /** 所属部门编号 */
    @Excel(name = "所属部门编号")
    private Long deptId;

    public void setPosId(Long posId) 
    {
        this.posId = posId;
    }

    public Long getPosId() 
    {
        return posId;
    }

    public void setPosName(String posName) 
    {
        this.posName = posName;
    }

    public String getPosName() 
    {
        return posName;
    }

    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("posId", getPosId())
            .append("posName", getPosName())
            .append("deptId", getDeptId())
            .toString();
    }
}
