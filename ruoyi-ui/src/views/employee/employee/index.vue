<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="职工编号" prop="empId">
        <el-input
          v-model="queryParams.empId"
          placeholder="请输入职工编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="职工姓名" prop="empName">
        <el-input
          v-model="queryParams.empName"
          placeholder="请输入职工姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="职工性别" prop="empGender">
        <el-select
          v-model="queryParams.empGender"
          placeholder="请选择职工性别"
          clearable
          @change="handleQuery"
        >
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
      </el-form-item>
      <el-form-item label="职工年龄" prop="empAge">
        <el-input
          v-model="queryParams.empAge"
          placeholder="请输入职工年龄"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="职工电话" prop="empPhone">
        <el-input
          v-model="queryParams.empPhone"
          placeholder="请输入职工电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所在部门" prop="deptId">
        <el-select
          v-model="queryParams.deptId"
          placeholder="请选择所在部门"
          clearable
          filterable
          @change="handleQuery"
        >
          <el-option
            v-for="item in deptOptions"
            :key="item.deptId"
            :label="item.deptName"
            :value="item.deptId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="担任职位" prop="posId">
        <el-select
          v-model="queryParams.posId"
          placeholder="请选择担任职位"
          clearable
          filterable
          @change="handleQuery"
        >
          <el-option
            v-for="item in posOptions"
            :key="item.posId"
            :label="item.posName"
            :value="item.posId"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['employee:employee:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['employee:employee:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['employee:employee:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['employee:employee:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="employeeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="职工编号" align="center" prop="empId" />
      <el-table-column label="职工姓名" align="center" prop="empName" />
      <el-table-column label="职工性别" align="center" prop="empGender" />
      <el-table-column label="职工年龄" align="center" prop="empAge" />
      <el-table-column label="职工电话" align="center" prop="empPhone" />
      <el-table-column label="所在部门" align="center" prop="deptId" :formatter="formatDeptName" />
      <el-table-column label="担任职位" align="center" prop="posId" :formatter="formatPosName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['employee:employee:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['employee:employee:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改员工基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="职工姓名" prop="empName">
          <el-input v-model="form.empName" placeholder="请输入职工姓名" />
        </el-form-item>
        <el-form-item label="职工性别" prop="empGender">
          <el-select v-model="form.empGender" placeholder="请选择职工性别" clearable>
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="职工年龄" prop="empAge">
          <el-input v-model="form.empAge" placeholder="请输入职工年龄" />
        </el-form-item>
        <el-form-item label="职工电话" prop="empPhone">
          <el-input v-model="form.empPhone" placeholder="请输入职工电话" />
        </el-form-item>
        <el-form-item label="所在部门" prop="deptId">
          <el-select v-model="form.deptId" placeholder="请选择所在部门" clearable>
            <el-option
              v-for="item in deptOptions"
              :key="item.deptId"
              :label="item.deptName"
              :value="item.deptId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="担任职位" prop="posId">
          <el-select v-model="form.posId" placeholder="请选择担任职位" clearable>
            <el-option
              v-for="item in posOptions"
              :key="item.posId"
              :label="item.posName"
              :value="item.posId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listEmployee, getEmployee, delEmployee, addEmployee, updateEmployee } from "@/api/employee/employee"
import { listDepartment } from "@/api/department/department"
import { listJobposition } from "@/api/jobposition/jobposition"
import eventBus from '@/utils/eventBus'

export default {
  name: "Employee",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 员工基本信息表格数据
      employeeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 部门选项
      deptOptions: [],
      // 岗位选项
      posOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        empName: null,
        empGender: null,
        empAge: null,
        empPhone: null,
        deptId: null,
        posId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        empName: [
          { required: true, message: "职工姓名不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "请选择所在部门", trigger: "change" }
        ],
        posId: [
          { required: true, message: "请选择担任职位", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getDeptList()
    this.getPosList()
  },
  methods: {
    // 获取部门列表
    getDeptList() {
      listDepartment().then(response => {
        this.deptOptions = response.rows
      })
    },
    // 获取岗位列表
    getPosList() {
      listJobposition().then(response => {
        this.posOptions = response.rows
      })
    },
    // 部门名称格式化
    formatDeptName(row) {
      const dept = this.deptOptions.find(item => item.deptId === row.deptId)
      return dept ? dept.deptName : row.deptId
    },
    // 岗位名称格式化
    formatPosName(row) {
      const pos = this.posOptions.find(item => item.posId === row.posId)
      return pos ? pos.posName : row.posId
    },
    /** 查询员工基本信息列表 */
    getList() {
      this.loading = true
      listEmployee(this.queryParams).then(response => {
        this.employeeList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        empId: null,
        empName: null,
        empGender: null,
        empAge: null,
        empPhone: null,
        deptId: null,
        posId: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.empId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加员工基本信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const empId = row.empId || this.ids
      getEmployee(empId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改员工基本信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.empId != null) {
            updateEmployee(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addEmployee(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const empIds = row.empId || this.ids
      this.$modal.confirm('是否确认删除员工基本信息编号为"' + empIds + '"的数据项？').then(function() {
        return delEmployee(empIds)
      }).then(() => {
        this.getList()
        eventBus.$emit('refresh-salary-info')
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('employee/employee/export', {
        ...this.queryParams
      }, `employee_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
<!-- 取消员工工资信息的级联删除。
     员工信息表添加字段“是否离职”。
     新建员工时检测员工部门和职位部门是否一致，不一致则提示错误信息。
     员工工资信息表添加主键“记录编号”，添加字段“其他奖金或处罚”默认为0、“奖惩说明”默认为空，字段“应扣工资”改名为“出勤奖金”,根据应发工资和出勤奖金计算实发工资,应发工资=基本工资+职务补贴。
     员工考勤信息表添加主键“记录编号”和员工工资信息表的主键相同，添加字段"缺勤天数"，根据缺勤天数计算出勤奖金=“1000 - 缺勤天数 * 100” ,添加考勤信息时，顺便添加工资信息。
     -->
