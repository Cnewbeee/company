package com.ruoyi.salarystandard.service;

import java.util.List;
import com.ruoyi.salarystandard.domain.Salarystandard;

/**
 * 工资标准Service接口
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
public interface ISalarystandardService 
{
    /**
     * 查询工资标准
     * 
     * @param posId 工资标准主键
     * @return 工资标准
     */
    public Salarystandard selectSalarystandardByPosId(Long posId);

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
     * 批量删除工资标准
     * 
     * @param salary_id 需要删除的工资标准主键集合
     * @return 结果
     */
    public int deleteSalarystandardByPosIds(Long[] salary_id);

    /**
     * 删除工资标准信息
     * 
     * @param salary_id 工资标准主键
     * @return 结果
     */
    public int deleteSalarystandardByPosId(Long salary_id);
}
