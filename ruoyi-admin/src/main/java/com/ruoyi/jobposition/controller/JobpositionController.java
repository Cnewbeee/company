package com.ruoyi.jobposition.controller;

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
import com.ruoyi.jobposition.domain.Jobposition;
import com.ruoyi.jobposition.service.IJobpositionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 职务信息Controller
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
@RestController
@RequestMapping("/jobposition/jobposition")
public class JobpositionController extends BaseController
{
    @Autowired
    private IJobpositionService jobpositionService;

    /**
     * 查询职务信息列表
     */
    @PreAuthorize("@ss.hasPermi('jobposition:jobposition:list')")
    @GetMapping("/list")
    public TableDataInfo list(Jobposition jobposition)
    {
        startPage();
        List<Jobposition> list = jobpositionService.selectJobpositionList(jobposition);
        return getDataTable(list);
    }

    /**
     * 导出职务信息列表
     */
    @PreAuthorize("@ss.hasPermi('jobposition:jobposition:export')")
    @Log(title = "职务信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Jobposition jobposition)
    {
        List<Jobposition> list = jobpositionService.selectJobpositionList(jobposition);
        ExcelUtil<Jobposition> util = new ExcelUtil<Jobposition>(Jobposition.class);
        util.exportExcel(response, list, "职务信息数据");
    }

    /**
     * 获取职务信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('jobposition:jobposition:query')")
    @GetMapping(value = "/{posId}")
    public AjaxResult getInfo(@PathVariable("posId") Long posId)
    {
        return success(jobpositionService.selectJobpositionByPosId(posId));
    }

    /**
     * 新增职务信息
     */
    @PreAuthorize("@ss.hasPermi('jobposition:jobposition:add')")
    @Log(title = "职务信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Jobposition jobposition)
    {
        return toAjax(jobpositionService.insertJobposition(jobposition));
    }

    /**
     * 修改职务信息
     */
    @PreAuthorize("@ss.hasPermi('jobposition:jobposition:edit')")
    @Log(title = "职务信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Jobposition jobposition)
    {
        return toAjax(jobpositionService.updateJobposition(jobposition));
    }

    /**
     * 删除职务信息
     */
    @PreAuthorize("@ss.hasPermi('jobposition:jobposition:remove')")
    @Log(title = "职务信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{posIds}")
    public AjaxResult remove(@PathVariable Long[] posIds)
    {
        return toAjax(jobpositionService.deleteJobpositionByPosIds(posIds));
    }
}
