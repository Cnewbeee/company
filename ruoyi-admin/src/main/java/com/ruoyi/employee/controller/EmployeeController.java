package com.ruoyi.employee.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.jobposition.domain.Jobposition;
import com.ruoyi.jobposition.service.IJobpositionService;
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
import com.ruoyi.employee.domain.Employee;
import com.ruoyi.employee.service.IEmployeeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工基本信息Controller
 * 
 * @author ruoyi
 * @date 2025-10-11
 */
@RestController
@RequestMapping("/employee/employee")
public class EmployeeController extends BaseController
{
    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IJobpositionService jobpositionService;

    /**
     * 查询员工基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:list')")
    @GetMapping("/list")
    public TableDataInfo list(Employee employee)
    {
        startPage();
        List<Employee> list = employeeService.selectEmployeeList(employee);
        return getDataTable(list);
    }

    /**
     * 导出员工基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:export')")
    @Log(title = "员工基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Employee employee)
    {
        List<Employee> list = employeeService.selectEmployeeList(employee);
        ExcelUtil<Employee> util = new ExcelUtil<Employee>(Employee.class);
        util.exportExcel(response, list, "员工基本信息数据");
    }

    /**
     * 获取员工基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:query')")
    @GetMapping(value = "/{empId}")
    public AjaxResult getInfo(@PathVariable("empId") Long empId)
    {
        return success(employeeService.selectEmployeeByEmpId(empId));
    }

    /**
     * 新增员工基本信息
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:add')")
    @Log(title = "员工基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody Employee employee)
    {
        // 验证员工部门和职位部门是否一致
        if (employee.getDeptId() != null && employee.getPosId() != null) {
            Jobposition jobposition = jobpositionService.selectJobpositionByPosId(employee.getPosId());
            if (jobposition != null && !employee.getDeptId().equals(jobposition.getDeptId())) {
                throw new ServiceException("员工部门与职位所属部门不一致，请重新选择");
            }
        }

        return toAjax(employeeService.insertEmployee(employee));
    }

    /**
     * 修改员工基本信息
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:edit')")
    @Log(title = "员工基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody Employee employee)
    {
        return toAjax(employeeService.updateEmployee(employee));
    }

    /**
     * 删除员工基本信息
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:remove')")
    @Log(title = "员工基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{empIds}")
    public AjaxResult remove(@PathVariable Long[] empIds)
    {
        return toAjax(employeeService.deleteEmployeeByEmpIds(empIds));
    }

    /**
     * 修改员工离职状态
     */
    @PreAuthorize("@ss.hasPermi('employee:employee:edit')")
    @Log(title = "员工基本信息", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult changeStatus(@RequestBody Employee employee)
    {
        return toAjax(employeeService.updateEmployee(employee));
    }
}
