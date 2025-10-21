package com.ruoyi.salarystandard.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.ruoyi.salarystandard.domain.Salarystandard;

/**
 * 工资标准Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
public interface SalarystandardMapper 
{
    /**
     * 查询工资标准
     * 
     * @param salaryId 工资标准主键
     * @return 工资标准
     */
    public Salarystandard selectSalarystandardByPosId(Long salaryId);

    /**
     * 查询工资标准列表
     * 
     * @param salarystandard 工资标准
     * @return 工资标准集合
     */
    public List<Salarystandard> selectSalarystandardList(Salarystandard salarystandard);

    /**
     * 新增工资标准
     * 
     * @param salarystandard 工资标准
     * @return 结果
     */
    public int insertSalarystandard(Salarystandard salarystandard);

    /**
     * 修改工资标准
     * 
     * @param salarystandard 工资标准
     * @return 结果
     */
    public int updateSalarystandard(Salarystandard salarystandard);

    /**
     * 删除工资标准
     * 
     * @param posId 工资标准主键
     * @return 结果
     */
    public int deleteSalarystandardByPosId(Long posId);

    /**
     * 批量删除工资标准
     * 
     * @param posIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSalarystandardByPosIds(Long[] posIds);

}
