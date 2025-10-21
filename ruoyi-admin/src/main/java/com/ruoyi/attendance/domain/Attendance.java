package com.ruoyi.attendance.domain;

import java.math.BigDecimal;
import java.util.Date;

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

    /** 记录编号（主键） */
    @Excel(name = "记录编号")
    private Long recordId;

    /** 职工编号（关联员工） */
    @Excel(name = "职工编号")
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

    /** 缺勤天数 */
    @Excel(name = "缺勤天数")
    private Long absentDays;

    /** 创建者 */
    @Excel(name = "创建者")
    private String createBy;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    @Excel(name = "更新者")
    private String updateBy;

    /** 更新时间 */
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;



    public void setRecordId(Long recordId)
    {
        this.recordId = recordId;
    }

    public Long getRecordId()
    {
        return recordId;
    }

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

    public void setAbsentDays(Long absentDays)
    {
        this.absentDays = absentDays;
    }

    public Long getAbsentDays()
    {
        return absentDays;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("recordId", getRecordId())
                .append("empId", getEmpId())
                .append("attendDays", getAttendDays())
                .append("overtimeTimes", getOvertimeTimes())
                .append("attendBonus", getAttendBonus())
                .append("absentDays", getAbsentDays())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
