package com.ruoyi.attendance.controller;

import java.util.List;
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
import com.ruoyi.attendance.domain.Attendance;
import com.ruoyi.attendance.service.IAttendanceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * 员工考勤信息Controller
 *
 * @author ruoyi
 * @date 2025-10-11
 */
@RestController
@RequestMapping("/attendance/attendance")
public class AttendanceController extends BaseController
{
    @Autowired
    private IAttendanceService attendanceService;

    /**
     * 查询员工考勤信息列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:attendance:list')")
    @GetMapping("/list")
    public TableDataInfo list(Attendance attendance)
    {
        startPage();
        List<Attendance> list = attendanceService.selectAttendanceList(attendance);
        return getDataTable(list);
    }

    /**
     * 导出员工考勤信息列表
     */
    @PreAuthorize("@ss.hasPermi('attendance:attendance:export')")
    @Log(title = "员工考勤信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Attendance attendance)
    {
        List<Attendance> list = attendanceService.selectAttendanceList(attendance);
        ExcelUtil<Attendance> util = new ExcelUtil<Attendance>(Attendance.class);
        util.exportExcel(response, list, "员工考勤信息数据");
    }

    /**
     * 获取员工考勤信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:attendance:query')")
    @GetMapping(value = "/{empId}")
    public AjaxResult getInfo(@PathVariable("empId") Long empId)
    {
        return success(attendanceService.selectAttendanceByEmpId(empId));
    }

    /**
     * 获取员工考勤信息详细信息（通过记录编号）
     */
    @PreAuthorize("@ss.hasPermi('attendance:attendance:query')")
    @GetMapping(value = "/record/{recordId}")
    public AjaxResult getInfoByRecordId(@PathVariable("recordId") Long recordId)
    {
        return success(attendanceService.selectAttendanceByRecordId(recordId));
    }

    /**
     * 新增员工考勤信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:attendance:add')")
    @Log(title = "员工考勤信息", businessType = BusinessType.INSERT)
    @PostMapping(value = "/add")
    public AjaxResult add(@RequestBody Attendance attendance)
    {
        return toAjax(attendanceService.insertAttendance(attendance));
    }

    /**
     * 修改员工考勤信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:attendance:edit')")
    @Log(title = "员工考勤信息", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/update")
    public AjaxResult edit(@RequestBody Attendance attendance)
    {
        return toAjax(attendanceService.updateAttendance(attendance));
    }

    /**
     * 删除员工考勤信息
     */
    @PreAuthorize("@ss.hasPermi('attendance:attendance:remove')")
    @Log(title = "员工考勤信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{empIds}")
    public AjaxResult remove(@PathVariable Long[] empIds)
    {
        return toAjax(attendanceService.deleteAttendanceByEmpIds(empIds));
}

    /**
     * 删除员工考勤信息（通过记录编号）
     */
    @PreAuthorize("@ss.hasPermi('attendance:attendance:remove')")
    @Log(title = "员工考勤信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/record/{recordIds}")
    public AjaxResult removeByRecordIds(@PathVariable Long[] recordIds)
    {
        return toAjax(attendanceService.deleteAttendanceByRecordIds(recordIds));
    }
}