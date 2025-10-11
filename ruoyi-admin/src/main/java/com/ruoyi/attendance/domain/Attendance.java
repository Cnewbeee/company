package com.ruoyi.attendance.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 员工考勤信息对象 attendance
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
public class Attendance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 职工编号（关联员工） */
    private Long empId;

    /** 月出勤天数 */
    @Excel(name = "月出勤天数")
    private Long attendDays;

    /** 月加班次数 */
    @Excel(name = "月加班次数")
    private Long overtimeTimes;

    /** 出勤奖金 */
    @Excel(name = "出勤奖金")
    private BigDecimal attendBonus;

    public void setEmpId(Long empId) 
    {
        this.empId = empId;
    }

    public Long getEmpId() 
    {
        return empId;
    }

    public void setAttendDays(Long attendDays) 
    {
        this.attendDays = attendDays;
    }

    public Long getAttendDays() 
    {
        return attendDays;
    }

    public void setOvertimeTimes(Long overtimeTimes) 
    {
        this.overtimeTimes = overtimeTimes;
    }

    public Long getOvertimeTimes() 
    {
        return overtimeTimes;
    }

    public void setAttendBonus(BigDecimal attendBonus) 
    {
        this.attendBonus = attendBonus;
    }

    public BigDecimal getAttendBonus() 
    {
        return attendBonus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("empId", getEmpId())
            .append("attendDays", getAttendDays())
            .append("overtimeTimes", getOvertimeTimes())
            .append("attendBonus", getAttendBonus())
            .toString();
    }
}
