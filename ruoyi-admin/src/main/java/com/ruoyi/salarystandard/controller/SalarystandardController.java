package com.ruoyi.salarystandard.controller;

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
import com.ruoyi.salarystandard.domain.Salarystandard;
import com.ruoyi.salarystandard.service.ISalarystandardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工资标准Controller
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
@RestController
@RequestMapping("/salarystandard/salarystandard")
public class SalarystandardController extends BaseController
{
    @Autowired
    private ISalarystandardService salarystandardService;

    /**
     * 查询工资标准列表
     */
    @PreAuthorize("@ss.hasPermi('salarystandard:salarystandard:list')")
    @GetMapping("/list")
    public TableDataInfo list(Salarystandard salarystandard)
    {
        startPage();
        List<Salarystandard> list = salarystandardService.selectSalarystandardList(salarystandard);
        return getDataTable(list);
    }

    /**
     * 导出工资标准列表
     */
    @PreAuthorize("@ss.hasPermi('salarystandard:salarystandard:export')")
    @Log(title = "工资标准", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Salarystandard salarystandard)
    {
        List<Salarystandard> list = salarystandardService.selectSalarystandardList(salarystandard);
        ExcelUtil<Salarystandard> util = new ExcelUtil<Salarystandard>(Salarystandard.class);
        util.exportExcel(response, list, "工资标准数据");
    }

    /**
     * 获取工资标准详细信息
     */
    @PreAuthorize("@ss.hasPermi('salarystandard:salarystandard:query')")
    @GetMapping(value = "/{posId}")
    public AjaxResult getInfo(@PathVariable("posId") Long posId)
    {
        return success(salarystandardService.selectSalarystandardByPosId(posId));
    }

    /**
     * 新增工资标准
     */
    @PreAuthorize("@ss.hasPermi('salarystandard:salarystandard:add')")
    @Log(title = "工资标准", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Salarystandard salarystandard)
    {

        return toAjax(salarystandardService.insertSalarystandard(salarystandard));
    }

    /**
     * 修改工资标准
     */
    @PreAuthorize("@ss.hasPermi('salarystandard:salarystandard:edit')")
    @Log(title = "工资标准", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Salarystandard salarystandard)
    {
        return toAjax(salarystandardService.updateSalarystandard(salarystandard));
    }

    /**
     * 删除工资标准
     */
    @PreAuthorize("@ss.hasPermi('salarystandard:salarystandard:remove')")
    @Log(title = "工资标准", businessType = BusinessType.DELETE)
	@DeleteMapping("/{salary_id}")
    public AjaxResult remove(@PathVariable Long[] salary_id)
    {
        return toAjax(salarystandardService.deleteSalarystandardByPosIds(salary_id));
    }
}
