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
      <el-form-item label="记录编号" prop="recordId">
        <el-input
          v-model="queryParams.recordId"
          placeholder="请输入记录编号"
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
      <el-form-item label="出勤奖金" prop="attendanceBonus">
        <el-input
          v-model="queryParams.attendanceBonus"
          placeholder="请输入出勤奖金"
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
      <el-table-column label="职工姓名" align="center" prop="empName" />
      <el-table-column label="应发工资" align="center" prop="payableSalary" />
      <el-table-column label="出勤奖金" align="center" prop="attendanceBonus" />
      <el-table-column label="其他奖金或处罚" align="center" prop="otherBonusPenalty" />
      <el-table-column label="实发工资" align="center" prop="actualSalary" />
      <el-table-column label="奖惩说明" align="center" prop="bonusPenaltyRemark" />
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime">
        <template slot-scope="scope">
          {{ scope.row.createTime ? new Date(scope.row.createTime).toLocaleString('zh-CN') : '' }}
        </template>
      </el-table-column>
      <el-table-column label="修改人" align="center" prop="updateBy" />
      <el-table-column label="修改时间" align="center" prop="updateTime">
        <template slot-scope="scope">
          {{ scope.row.updateTime ? new Date(scope.row.updateTime).toLocaleString('zh-CN') : '' }}
        </template>
      </el-table-column>
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
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="职工姓名" prop="empName">
          <el-input v-model="form.empName" placeholder="请输入职工姓名" />
        </el-form-item>
        <el-form-item label="应发工资" prop="payableSalary">
          <el-input v-model="form.payableSalary" placeholder="请输入应发工资" />
        </el-form-item>
        <el-form-item label="出勤奖金" prop="attendanceBonus">
          <el-input v-model="form.attendanceBonus" placeholder="请输入出勤奖金" />
        </el-form-item>
        <el-form-item label="其他奖金或处罚" prop="otherBonusPenalty">
          <el-input v-model="form.otherBonusPenalty" placeholder="请输入其他奖金或处罚，默认为0" />
        </el-form-item>
        <el-form-item label="实发工资" prop="actualSalary">
          <el-input v-model="form.actualSalary" placeholder="实发工资由系统自动计算" :disabled="true" />
        </el-form-item>
        <el-form-item label="奖惩说明" prop="bonusPenaltyRemark">
          <el-input v-model="form.bonusPenaltyRemark" placeholder="请输入奖惩说明，默认为空" />
        </el-form-item>
        <!-- 创建时显示创建人，修改时显示修改人 -->
        <el-form-item v-if="!form.recordId" label="创建人" prop="createBy">
          <el-input v-model="form.createBy" :disabled="true" placeholder="创建人" />
        </el-form-item>
        <el-form-item v-if="!form.recordId" label="创建时间" prop="createTime">
          <el-date-picker
            v-model="form.createTime"
            type="datetime"
            placeholder="选择创建时间"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 100%;"
          />
        </el-form-item>
        <el-form-item v-if="form.recordId" label="修改人" prop="updateBy">
          <el-input v-model="form.updateBy" :disabled="true" placeholder="修改人" />
        </el-form-item>
        <el-form-item v-if="form.recordId" label="修改时间" prop="updateTime">
          <el-date-picker
            v-model="form.updateTime"
            type="datetime"
            placeholder="选择修改时间"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 100%;"
          />
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
import eventBus from '@/utils/eventBus'
import { mapGetters } from 'vuex'

export default {
  name: "Salaryinfo",
  computed: {
    ...mapGetters(['name'])
  },
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
        recordId: null,
        empId: null,
        payableSalary: null,
        attendanceBonus: null,
        otherBonusPenalty: null,
        empName: null,
        actualSalary: null,
        bonusPenaltyRemark: null
      },
      // 表单参数
      form: {
        recordId: null,
        empId: null,
        payableSalary: null,
        attendanceBonus: null,
        otherBonusPenalty: 0,
        empName: null,
        actualSalary: null,
        bonusPenaltyRemark: '',
        createBy: null,
        updateBy: null,
        createTime: null,
        updateTime: null
      },
      // 表单校验
      rules: {
        recordId: [
          { required: true, message: "记录编号不能为空", trigger: "blur" }
        ],
        empId: [
          { required: true, message: "职工编号不能为空", trigger: "blur" }
        ],
        payableSalary: [
          { required: true, message: "应发工资不能为空", trigger: "blur" }
        ],
        attendanceBonus: [
          { required: true, message: "出勤奖金不能为空", trigger: "blur" }
        ],
        empName: [
          { required: true, message: "职工姓名不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    eventBus.$on('refresh-salary-info', () => {
      this.getList() // 刷新工资信息列表
    })
    this.getList()
  },
  beforeDestroy() {
    eventBus.$off('refresh-salary-info')
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
        recordId: null,
        empId: null,
        payableSalary: null,
        attendanceBonus: null,
        otherBonusPenalty: 0,
        empName: null,
        actualSalary: null,
        bonusPenaltyRemark: '',
        createBy: null,
        updateBy: null,
        createTime: null,
        updateTime: null
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
      this.ids = selection.map(item => item.recordId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      // 创建时自动填充创建人和创建时间
      this.form.createBy = this.name
      this.form.createTime = this.getCurrentDateTime()
      this.open = true
      this.title = "添加员工工资信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const recordId = row.recordId || this.ids
      getSalaryinfo(recordId).then(response => {
        this.form = response.data
        // 修改时自动填充修改人和修改时间
        this.form.updateBy = this.name
        this.form.updateTime = this.getCurrentDateTime()
        this.open = true
        this.title = "修改员工工资信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
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
      const recordIds = row.recordId || this.ids
      this.$modal.confirm('是否确认删除员工工资信息编号为"' + recordIds + '"的数据项？').then(function() {
        return delSalaryinfo(recordIds)
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
    },
    /** 获取当前日期时间 */
    getCurrentDateTime() {
      const now = new Date()
      const year = now.getFullYear()
      const month = String(now.getMonth() + 1).padStart(2, '0')
      const day = String(now.getDate()).padStart(2, '0')
      const hours = String(now.getHours()).padStart(2, '0')
      const minutes = String(now.getMinutes()).padStart(2, '0')
      const seconds = String(now.getSeconds()).padStart(2, '0')
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
    }
  }
}
</script>
