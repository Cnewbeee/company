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
        <el-input
          v-model="queryParams.empGender"
          placeholder="请输入职工性别"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-form-item label="所在部门编号" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入所在部门编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="担任职位编号" prop="posId">
        <el-input
          v-model="queryParams.posId"
          placeholder="请输入担任职位编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-table-column label="所在部门编号" align="center" prop="deptId" />
      <el-table-column label="担任职位编号" align="center" prop="posId" />
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
          <el-input v-model="form.empGender" placeholder="请输入职工性别" />
        </el-form-item>
        <el-form-item label="职工年龄" prop="empAge">
          <el-input v-model="form.empAge" placeholder="请输入职工年龄" />
        </el-form-item>
        <el-form-item label="职工电话" prop="empPhone">
          <el-input v-model="form.empPhone" placeholder="请输入职工电话" />
        </el-form-item>
        <el-form-item label="所在部门编号" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入所在部门编号" />
        </el-form-item>
        <el-form-item label="担任职位编号" prop="posId">
          <el-input v-model="form.posId" placeholder="请输入担任职位编号" />
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
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
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
