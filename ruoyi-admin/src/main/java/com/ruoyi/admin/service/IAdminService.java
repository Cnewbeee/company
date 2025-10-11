package com.ruoyi.admin.service;

import java.util.List;
import com.ruoyi.admin.domain.Admin;

/**
 * 系统管理员Service接口
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
public interface IAdminService 
{
    /**
     * 查询系统管理员
     * 
     * @param adminUsername 系统管理员主键
     * @return 系统管理员
     */
    public Admin selectAdminByAdminUsername(String adminUsername);

    /**
     * 查询系统管理员列表
     * 
     * @param admin 系统管理员
     * @return 系统管理员集合
     */
    public List<Admin> selectAdminList(Admin admin);

    /**
     * 新增系统管理员
     * 
     * @param admin 系统管理员
     * @return 结果
     */
    public int insertAdmin(Admin admin);

    /**
     * 修改系统管理员
     * 
     * @param admin 系统管理员
     * @return 结果
     */
    public int updateAdmin(Admin admin);

    /**
     * 批量删除系统管理员
     * 
     * @param adminUsernames 需要删除的系统管理员主键集合
     * @return 结果
     */
    public int deleteAdminByAdminUsernames(String[] adminUsernames);

    /**
     * 删除系统管理员信息
     * 
     * @param adminUsername 系统管理员主键
     * @return 结果
     */
    public int deleteAdminByAdminUsername(String adminUsername);
}
