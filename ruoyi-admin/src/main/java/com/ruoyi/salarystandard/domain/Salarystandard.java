package com.ruoyi.salarystandard.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工资标准对象 salarystandard
 *
 * @author ruoyi
 * @date 2025-10-11
 */
public class Salarystandard extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 薪资编号
     */
    private Long salaryId;

    /**
     * 职位编号（关联职务）
     */
    private Long posId;

    /**
     * 基本工资
     */
    @Excel(name = "基本工资")
    private BigDecimal basicSalary;

    /**
     * 薪资等级
     */
    @Excel(name = "薪资等级")
    private String salaryLevel;

    /**
     * 职务补贴
     */
    @Excel(name = "职务补贴")
    private BigDecimal positionAllowance;

    // 添加无参构造函数
    public Salarystandard() {
    }

    public Salarystandard(Long salaryId) {
        this.salaryId = salaryId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }

    public Long getSalaryId() {
        return salaryId;
    }

    public Long getPosId() {
        return posId;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setSalaryLevel(String salaryLevel) {
        this.salaryLevel = salaryLevel;
    }

    public String getSalaryLevel() {
        return salaryLevel;
    }

    public void setPositionAllowance(BigDecimal positionAllowance) {
        this.positionAllowance = positionAllowance;
    }

    public BigDecimal getPositionAllowance() {
        return positionAllowance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("posId", getPosId())
                .append("basicSalary", getBasicSalary())
                .append("salaryLevel", getSalaryLevel())
                .append("positionAllowance", getPositionAllowance())
                .append("salaryId", getSalaryId())
                .toString();
    }
}
