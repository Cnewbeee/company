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
      <el-form-item label="应发工资" prop="payableSalary">
        <el-input
          v-model="queryParams.payableSalary"
          placeholder="请输入应发工资"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="应扣工资" prop="deductionSalary">
        <el-input
          v-model="queryParams.deductionSalary"
          placeholder="请输入应扣工资"
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
      <el-form-item label="实发工资" prop="actualSalary">
        <el-input
          v-model="queryParams.actualSalary"
          placeholder="请输入实发工资"
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
          v-hasPermi="['salaryinfo:salaryinfo:add']"
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
          v-hasPermi="['salaryinfo:salaryinfo:edit']"
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
          v-hasPermi="['salaryinfo:salaryinfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['salaryinfo:salaryinfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salaryinfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="职工编号" align="center" prop="empId" />
      <el-table-column label="应发工资" align="center" prop="payableSalary" />
      <el-table-column label="应扣工资" align="center" prop="deductionSalary" />
      <el-table-column label="职工姓名" align="center" prop="empName" />
      <el-table-column label="实发工资" align="center" prop="actualSalary" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['salaryinfo:salaryinfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['salaryinfo:salaryinfo:remove']"
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

    <!-- 添加或修改员工工资信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="应发工资" prop="payableSalary">
          <el-input v-model="form.payableSalary" placeholder="请输入应发工资" />
        </el-form-item>
        <el-form-item label="应扣工资" prop="deductionSalary">
          <el-input v-model="form.deductionSalary" placeholder="请输入应扣工资" />
        </el-form-item>
        <el-form-item label="职工姓名" prop="empName">
          <el-input v-model="form.empName" placeholder="请输入职工姓名" />
        </el-form-item>
        <el-form-item label="实发工资" prop="actualSalary">
          <el-input v-model="form.actualSalary" placeholder="请输入实发工资" />
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
import { listSalaryinfo, getSalaryinfo, delSalaryinfo, addSalaryinfo, updateSalaryinfo } from "@/api/salaryinfo/salaryinfo"

export default {
  name: "Salaryinfo",
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
      // 员工工资信息表格数据
      salaryinfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        payableSalary: null,
        deductionSalary: null,
        empName: null,
        actualSalary: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        payableSalary: [
          { required: true, message: "应发工资不能为空", trigger: "blur" }
        ],
        deductionSalary: [
          { required: true, message: "应扣工资不能为空", trigger: "blur" }
        ],
        empName: [
          { required: true, message: "职工姓名不能为空", trigger: "blur" }
        ],
        actualSalary: [
          { required: true, message: "实发工资不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询员工工资信息列表 */
    getList() {
      this.loading = true
      listSalaryinfo(this.queryParams).then(response => {
        this.salaryinfoList = response.rows
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
        payableSalary: null,
        deductionSalary: null,
        empName: null,
        actualSalary: null
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
      this.title = "添加员工工资信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const empId = row.empId || this.ids
      getSalaryinfo(empId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改员工工资信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.empId != null) {
            updateSalaryinfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addSalaryinfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除员工工资信息编号为"' + empIds + '"的数据项？').then(function() {
        return delSalaryinfo(empIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('salaryinfo/salaryinfo/export', {
        ...this.queryParams
      }, `salaryinfo_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
