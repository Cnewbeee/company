package com.ruoyi.admin.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.admin.domain.Admin;
import com.ruoyi.admin.service.IAdminService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 系统管理员Controller
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
@RestController
@RequestMapping("/admin/admin")
public class AdminController extends BaseController
{
    @Autowired
    private IAdminService adminService;

    /**
     * 查询系统管理员列表
     */
    @PreAuthorize("@ss.hasPermi('admin:admin:list')")
    @GetMapping("/list")
    public TableDataInfo list(Admin admin)
    {
        startPage();
        List<Admin> list = adminService.selectAdminList(admin);
        return getDataTable(list);
    }

    /**
     * 导出系统管理员列表
     */
    @PreAuthorize("@ss.hasPermi('admin:admin:export')")
    @Log(title = "系统管理员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Admin admin)
    {
        List<Admin> list = adminService.selectAdminList(admin);
        ExcelUtil<Admin> util = new ExcelUtil<Admin>(Admin.class);
        util.exportExcel(response, list, "系统管理员数据");
    }

    /**
     * 获取系统管理员详细信息
     */
    @PreAuthorize("@ss.hasPermi('admin:admin:query')")
    @GetMapping(value = "/{adminUsername}")
    public AjaxResult getInfo(@PathVariable("adminUsername") String adminUsername)
    {
        return success(adminService.selectAdminByAdminUsername(adminUsername));
    }

    /**
     * 新增系统管理员
     */
    @PreAuthorize("@ss.hasPermi('admin:admin:add')")
    @Log(title = "系统管理员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Admin admin)
    {
        return toAjax(adminService.insertAdmin(admin));
    }

    /**
     * 修改系统管理员
     */
    @PreAuthorize("@ss.hasPermi('admin:admin:edit')")
    @Log(title = "系统管理员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Admin admin)
    {
        return toAjax(adminService.updateAdmin(admin));
    }

    /**
     * 删除系统管理员
     */
    @PreAuthorize("@ss.hasPermi('admin:admin:remove')")
    @Log(title = "系统管理员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{adminUsernames}")
    public AjaxResult remove(@PathVariable String[] adminUsernames)
    {
        return toAjax(adminService.deleteAdminByAdminUsernames(adminUsernames));
    }
}
