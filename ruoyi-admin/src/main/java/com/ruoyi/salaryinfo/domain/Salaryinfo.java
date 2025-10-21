package com.ruoyi.salaryinfo.domain;

import java.math.BigDecimal;
import java.util.Date;

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

    /** 记录编号（主键） */
    @Excel(name = "记录编号")
    private Long recordId;

    /** 职工编号（关联员工） */
    @Excel(name = "职工编号")
    private Long empId;

    /** 应发工资 */
    @Excel(name = "应发工资")
    private BigDecimal payableSalary;

    /** 出勤奖金 */
    @Excel(name = "出勤奖金")
    private BigDecimal attendanceBonus;

    /** 职工姓名 */
    @Excel(name = "职工姓名")
    private String empName;

    /** 实发工资（应发+出勤奖金+其他奖金或处罚） */
    @Excel(name = "实发工资")
    private BigDecimal actualSalary;

    /** 其他奖金或处罚（默认为0） */
    @Excel(name = "其他奖金或处罚")
    private BigDecimal otherBonusPenalty = new BigDecimal("0");

    /** 奖惩说明（默认为空） */
    @Excel(name = "奖惩说明")
    private String bonusPenaltyRemark = "";

    /**创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public Salaryinfo()
    {
    }


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

    public void setPayableSalary(BigDecimal payableSalary)
    {
        this.payableSalary = payableSalary;
    }

    public BigDecimal getPayableSalary()
    {
        return payableSalary;
    }

    public void setAttendanceBonus(BigDecimal attendanceBonus)
    {
        this.attendanceBonus = attendanceBonus;
    }

    public BigDecimal getAttendanceBonus()
    {
        return attendanceBonus;
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

    public void setOtherBonusPenalty(BigDecimal otherBonusPenalty)
    {
        this.otherBonusPenalty = otherBonusPenalty;
    }

    public BigDecimal getOtherBonusPenalty()
    {
        return otherBonusPenalty;
    }

    public void setBonusPenaltyRemark(String bonusPenaltyRemark)
    {
        this.bonusPenaltyRemark = bonusPenaltyRemark;
    }

    public String getBonusPenaltyRemark()
    {
        return bonusPenaltyRemark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("recordId", getRecordId())
                .append("empId", getEmpId())
                .append("payableSalary", getPayableSalary())
                .append("attendanceBonus", getAttendanceBonus())
                .append("empName", getEmpName())
                .append("actualSalary", getActualSalary())
                .append("otherBonusPenalty", getOtherBonusPenalty())
                .append("bonusPenaltyRemark", getBonusPenaltyRemark())
                .toString();
    }
}
