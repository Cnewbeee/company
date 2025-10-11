package com.ruoyi.jobposition.mapper;

import java.util.List;
import com.ruoyi.jobposition.domain.Jobposition;

/**
 * 职务信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
public interface JobpositionMapper 
{
    /**
     * 查询职务信息
     * 
     * @param posId 职务信息主键
     * @return 职务信息
     */
    public Jobposition selectJobpositionByPosId(Long posId);

    /**
     * 查询职务信息列表
     * 
     * @param jobposition 职务信息
     * @return 职务信息集合
     */
    public List<Jobposition> selectJobpositionList(Jobposition jobposition);

    /**
     * 新增职务信息
     * 
     * @param jobposition 职务信息
     * @return 结果
     */
    public int insertJobposition(Jobposition jobposition);

    /**
     * 修改职务信息
     * 
     * @param jobposition 职务信息
     * @return 结果
     */
    public int updateJobposition(Jobposition jobposition);

    /**
     * 删除职务信息
     * 
     * @param posId 职务信息主键
     * @return 结果
     */
    public int deleteJobpositionByPosId(Long posId);

    /**
     * 批量删除职务信息
     * 
     * @param posIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJobpositionByPosIds(Long[] posIds);
}
