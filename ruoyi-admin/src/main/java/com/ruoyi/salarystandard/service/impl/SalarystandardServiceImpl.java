package com.ruoyi.salarystandard.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.salarystandard.mapper.SalarystandardMapper;
import com.ruoyi.salarystandard.domain.Salarystandard;
import com.ruoyi.salarystandard.service.ISalarystandardService;

/**
 * 工资标准Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
@Service
public class SalarystandardServiceImpl implements ISalarystandardService 
{
    @Autowired
    private SalarystandardMapper salarystandardMapper;

    /**
     * 查询工资标准
     * 
     * @param salaryId 工资标准主键
     * @return 工资标准
     */
    @Override
    public Salarystandard selectSalarystandardByPosId(Long salaryId)
    {
        return salarystandardMapper.selectSalarystandardByPosId(salaryId);
    }

    /**
     * 查询工资标准列表
     * 
     * @param salarystandard 工资标准
     * @return 工资标准
     */
    @Override
    public List<Salarystandard> selectSalarystandardList(Salarystandard salarystandard)
    {
        return salarystandardMapper.selectSalarystandardList(salarystandard);
    }

    /**
     * 新增工资标准
     * 
     * @param salarystandard 工资标准
     * @return 结果
     */
    @Override
    public int insertSalarystandard(Salarystandard salarystandard)
    {
        return salarystandardMapper.insertSalarystandard(salarystandard);
    }

    /**
     * 修改工资标准
     * 
     * @param salarystandard 工资标准
     * @return 结果
     */
    @Override
    public int updateSalarystandard(Salarystandard salarystandard)
    {
        return salarystandardMapper.updateSalarystandard(salarystandard);
    }

    /**
     * 批量删除工资标准
     * 
     * @param salary_id 需要删除的工资标准主键
     * @return 结果
     */
    @Override
    public int deleteSalarystandardByPosIds(Long[] salary_id)
    {
        return salarystandardMapper.deleteSalarystandardByPosIds(salary_id);
    }

    /**
     * 删除工资标准信息
     * 
     * @param salary_id 工资标准主键
     * @return 结果
     */
    @Override
    public int deleteSalarystandardByPosId(Long salary_id)
    {
        return salarystandardMapper.deleteSalarystandardByPosId(salary_id);
    }
}
