package com.ruoyi.jobposition.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jobposition.mapper.JobpositionMapper;
import com.ruoyi.jobposition.domain.Jobposition;
import com.ruoyi.jobposition.service.IJobpositionService;

/**
 * 职务信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
@Service
public class JobpositionServiceImpl implements IJobpositionService 
{
    @Autowired
    private JobpositionMapper jobpositionMapper;

    /**
     * 查询职务信息
     * 
     * @param posId 职务信息主键
     * @return 职务信息
     */
    @Override
    public Jobposition selectJobpositionByPosId(Long posId)
    {
        return jobpositionMapper.selectJobpositionByPosId(posId);
    }

    /**
     * 查询职务信息列表
     * 
     * @param jobposition 职务信息
     * @return 职务信息
     */
    @Override
    public List<Jobposition> selectJobpositionList(Jobposition jobposition)
    {
        return jobpositionMapper.selectJobpositionList(jobposition);
    }

    /**
     * 新增职务信息
     * 
     * @param jobposition 职务信息
     * @return 结果
     */
    @Override
    public int insertJobposition(Jobposition jobposition)
    {
        return jobpositionMapper.insertJobposition(jobposition);
    }

    /**
     * 修改职务信息
     * 
     * @param jobposition 职务信息
     * @return 结果
     */
    @Override
    public int updateJobposition(Jobposition jobposition)
    {
        return jobpositionMapper.updateJobposition(jobposition);
    }

    /**
     * 批量删除职务信息
     * 
     * @param posIds 需要删除的职务信息主键
     * @return 结果
     */
    @Override
    public int deleteJobpositionByPosIds(Long[] posIds)
    {
        return jobpositionMapper.deleteJobpositionByPosIds(posIds);
    }

    /**
     * 删除职务信息信息
     * 
     * @param posId 职务信息主键
     * @return 结果
     */
    @Override
    public int deleteJobpositionByPosId(Long posId)
    {
        return jobpositionMapper.deleteJobpositionByPosId(posId);
    }
}
