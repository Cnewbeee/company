package com.ruoyi.salaryinfo.controller;

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
import com.ruoyi.salaryinfo.domain.Salaryinfo;
import com.ruoyi.salaryinfo.service.ISalaryinfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工工资信息Controller
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
@RestController
@RequestMapping("/salaryinfo/salaryinfo")
public class SalaryinfoController extends BaseController
{
    @Autowired
    private ISalaryinfoService salaryinfoService;

    /**
     * 查询员工工资信息列表
     */
    @PreAuthorize("@ss.hasPermi('salaryinfo:salaryinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Salaryinfo salaryinfo)
    {
        startPage();
        List<Salaryinfo> list = salaryinfoService.selectSalaryinfoList(salaryinfo);
        return getDataTable(list);
    }

    /**
     * 导出员工工资信息列表
     */
    @PreAuthorize("@ss.hasPermi('salaryinfo:salaryinfo:export')")
    @Log(title = "员工工资信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Salaryinfo salaryinfo)
    {
        List<Salaryinfo> list = salaryinfoService.selectSalaryinfoList(salaryinfo);
        ExcelUtil<Salaryinfo> util = new ExcelUtil<Salaryinfo>(Salaryinfo.class);
        util.exportExcel(response, list, "员工工资信息数据");
    }

    /**
     * 获取员工工资信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('salaryinfo:salaryinfo:query')")
    @GetMapping(value = "/{empId}")
    public AjaxResult getInfo(@PathVariable("empId") Long empId)
    {
        return success(salaryinfoService.selectSalaryinfoByEmpId(empId));
    }

    /**
     * 新增员工工资信息
     */
    @PreAuthorize("@ss.hasPermi('salaryinfo:salaryinfo:add')")
    @Log(title = "员工工资信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Salaryinfo salaryinfo)
    {
        return toAjax(salaryinfoService.insertSalaryinfo(salaryinfo));
    }

    /**
     * 修改员工工资信息
     */
    @PreAuthorize("@ss.hasPermi('salaryinfo:salaryinfo:edit')")
    @Log(title = "员工工资信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Salaryinfo salaryinfo)
    {
        return toAjax(salaryinfoService.updateSalaryinfo(salaryinfo));
    }

    /**
     * 删除员工工资信息
     */
    @PreAuthorize("@ss.hasPermi('salaryinfo:salaryinfo:remove')")
    @Log(title = "员工工资信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{empIds}")
    public AjaxResult remove(@PathVariable Long[] empIds)
    {
        return toAjax(salaryinfoService.deleteSalaryinfoByEmpIds(empIds));
    }
}
