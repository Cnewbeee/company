package com.ruoyi.admin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.admin.mapper.AdminMapper;
import com.ruoyi.admin.domain.Admin;
import com.ruoyi.admin.service.IAdminService;

/**
 * 系统管理员Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
@Service
public class AdminServiceImpl implements IAdminService 
{
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 查询系统管理员
     * 
     * @param adminUsername 系统管理员主键
     * @return 系统管理员
     */
    @Override
    public Admin selectAdminByAdminUsername(String adminUsername)
    {
        return adminMapper.selectAdminByAdminUsername(adminUsername);
    }

    /**
     * 查询系统管理员列表
     * 
     * @param admin 系统管理员
     * @return 系统管理员
     */
    @Override
    public List<Admin> selectAdminList(Admin admin)
    {
        return adminMapper.selectAdminList(admin);
    }

    /**
     * 新增系统管理员
     * 
     * @param admin 系统管理员
     * @return 结果
     */
    @Override
    public int insertAdmin(Admin admin)
    {
        return adminMapper.insertAdmin(admin);
    }

    /**
     * 修改系统管理员
     * 
     * @param admin 系统管理员
     * @return 结果
     */
    @Override
    public int updateAdmin(Admin admin)
    {
        return adminMapper.updateAdmin(admin);
    }

    /**
     * 批量删除系统管理员
     * 
     * @param adminUsernames 需要删除的系统管理员主键
     * @return 结果
     */
    @Override
    public int deleteAdminByAdminUsernames(String[] adminUsernames)
    {
        return adminMapper.deleteAdminByAdminUsernames(adminUsernames);
    }

    /**
     * 删除系统管理员信息
     * 
     * @param adminUsername 系统管理员主键
     * @return 结果
     */
    @Override
    public int deleteAdminByAdminUsername(String adminUsername)
    {
        return adminMapper.deleteAdminByAdminUsername(adminUsername);
    }
}
