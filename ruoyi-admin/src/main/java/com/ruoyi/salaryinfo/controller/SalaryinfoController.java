package com.ruoyi.salaryinfo.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import org.junit.Test;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping(value = "/emp/{empId}")
    public AjaxResult getInfoByEmpId(@PathVariable("empId") Long empId)
    {
        return success(salaryinfoService.selectSalaryinfoByEmpId(empId));
    }

    /**
     * 获取员工工资信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('salaryinfo:salaryinfo:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfoByRecordId(@PathVariable("recordId") Long recordId)
    {
        return success(salaryinfoService.selectSalaryinfoByRecordId(recordId));
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
        Date currentDate = new Date();
        salaryinfo.setUpdateTime(currentDate);
        return toAjax(salaryinfoService.updateSalaryinfo(salaryinfo));
    }

    /**
     * 删除员工工资信息
     */
    @PreAuthorize("@ss.hasPermi('salaryinfo:salaryinfo:remove')")
    @Log(title = "员工工资信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/emp/{empIds}")
    public AjaxResult removeByEmpIds(@PathVariable Long[] empIds)
    {
        return toAjax(salaryinfoService.deleteSalaryinfoByEmpIds(empIds));
    }

    /**
     * 删除员工工资信息
     */
    @PreAuthorize("@ss.hasPermi('salaryinfo:salaryinfo:remove')")
    @Log(title = "员工工资信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    public AjaxResult removeByRecordIds(@PathVariable Long[] recordIds)
    {
        return toAjax(salaryinfoService.deleteSalaryinfoByRecordIds(recordIds));
    }


    /**
     * 统计一年的员工工资
     */
     @PreAuthorize("@ss.hasPermi('salaryinfo:salaryinfo:query')")
     @GetMapping("/total")
     public AjaxResult getTotalSalaryByYear()
     {

         SimpleDateFormat excelDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date currentDate = new Date();
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(currentDate);
         calendar.add(Calendar.YEAR, -1);
         Date oneYearAgo = calendar.getTime();
         String formattedOneYearAgo = excelDateFormat.format(oneYearAgo);

        Map<String, String> map = new HashMap<>();
        map.put("totalSalary", salaryinfoService.getTotalSalaryByYear(formattedOneYearAgo));

         return success(map);
     }


    /**
     * 统计一年每个月的员工总工资
     */
    @PreAuthorize("@ss.hasPermi('salaryinfo:salaryinfo:query')")
    @GetMapping("/total/month")
    public AjaxResult getTotalSalaryByMonth()
    {
        SimpleDateFormat excelDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, -1);
        Date oneYearAgo = calendar.getTime();
        String formattedOneYearAgo = excelDateFormat.format(oneYearAgo);

        return success(salaryinfoService.getTotalSalaryByMonth(formattedOneYearAgo));
    }
}