<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="职工编号" prop="empId">
        <el-input v-model="queryParams.empId" placeholder="请输入职工编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="月出勤天数" prop="attendDays">
        <el-input v-model="queryParams.attendDays" placeholder="请输入月出勤天数" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="月加班次数" prop="overtimeTimes">
        <el-input v-model="queryParams.overtimeTimes" placeholder="请输入月加班次数" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="出勤奖金" prop="attendBonus">
        <el-input v-model="queryParams.attendBonus" placeholder="请输入出勤奖金" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="缺勤天数" prop="absentDays">
        <el-input v-model="queryParams.absentDays" placeholder="请输入缺勤天数" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['attendance:attendance:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['attendance:attendance:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['attendance:attendance:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['attendance:attendance:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="attendanceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="职工编号" align="center" prop="empId" />
      <el-table-column label="月出勤天数" align="center" prop="attendDays" />
      <el-table-column label="月加班次数" align="center" prop="overtimeTimes" />
      <el-table-column label="出勤奖金" align="center" prop="attendBonus" />
      <el-table-column label="缺勤天数" align="center" prop="absentDays" />
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime">
        <template slot-scope="scope">
          {{ scope.row.createTime ? new Date(scope.row.createTime).toLocaleString('zh-CN') : '' }}
        </template>
      </el-table-column> 
      <el-table-column label="更新人" align="center" prop="updateBy" />
      <el-table-column label="更新时间" align="center" prop="updateTime">
        <template slot-scope="scope">
          {{ scope.row.updateTime ? new Date(scope.row.updateTime).toLocaleString('zh-CN') : '' }}
        </template>
      </el-table-column> 
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['attendance:attendance:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['attendance:attendance:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改员工考勤信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="职工编号" prop="empId">
          <el-input v-model="form.empId" placeholder="请输入职工编号" />
        </el-form-item>
        <el-form-item label="月出勤天数" prop="attendDays">
          <el-input v-model="form.attendDays" placeholder="请输入月出勤天数" />
        </el-form-item>
        <el-form-item label="月加班次数" prop="overtimeTimes">
          <el-input v-model="form.overtimeTimes" placeholder="请输入月加班次数" />
        </el-form-item>
        <el-form-item label="缺勤天数" prop="absentDays">
          <el-input v-model="form.absentDays" placeholder="请输入缺勤天数" />
        </el-form-item>
        <el-form-item label="日期" prop="attendDate">
          <el-date-picker v-model="form.attendDate" type="date" placeholder="请选择日期" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="创建人" prop="createBy" v-if="!form.recordId">
          <el-input v-model="form.createBy" placeholder="自动获取" :disabled="true" />
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
// 时间格式化工具
function formatDateTime(val) {
  if (!val) return ''
  const date = new Date(val)
  const pad = n => n < 10 ? '0' + n : n
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`
}
import { listAttendance, getAttendance, delAttendance, addAttendance, updateAttendance } from "@/api/attendance/attendance"

export default {
  name: "Attendance",
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
      // 员工考勤信息表格数据
      attendanceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId: null,
        attendDays: null,
        overtimeTimes: null,
        attendBonus: null,
        absentDays: null
      },
      // 表单参数
      form: {
        recordId: null,
        empId: null,
        attendDays: null,
        overtimeTimes: null,
        attendBonus: null,
        absentDays: null,
        attendDate: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      },
      // 表单校验
      rules: {
        empId: [
          { required: true, message: "职工编号不能为空", trigger: "blur" }
        ],
        attendDays: [
          { required: true, message: "月出勤天数不能为空", trigger: "blur" }
        ],
        overtimeTimes: [
          { required: true, message: "月加班次数不能为空", trigger: "blur" }
        ],
        absentDays: [
          { required: true, message: "缺勤天数不能为空", trigger: "blur" }
        ],
        attendDate: [
          { required: true, message: "日期不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询员工考勤信息列表 */
    getList() {
      this.loading = true
      listAttendance(this.queryParams).then(response => {
        this.attendanceList = response.rows
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
        attendDays: null,
        overtimeTimes: null,
        attendBonus: null,
        absentDays: null,
        attendDate: null,
        createBy: null,
        createTime: null,
        updateBy: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      // 获取当前登录用户作为创建人
      const userName = this.$store.state.user.name || 'system'
      this.form.createBy = userName
      this.open = true
      this.title = "添加员工考勤信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const recordId = row.recordId || this.ids
      getAttendance(recordId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改员工考勤信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recordId != null) {
            // 更新时设置更新人和更新时间
            this.form.updateBy = this.$store.state.user.name || 'system'
            this.form.updateTime = new Date()
            updateAttendance(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            // 新增时设置创建时间
            this.form.createTime = new Date()
            addAttendance(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除员工考勤信息编号为"' + recordIds + '"的数据项？').then(function () {
        return delAttendance(recordIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => { })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('attendance/attendance/export', {
        ...this.queryParams
      }, `attendance_${new Date().getTime()}.xlsx`)
    },
    formatDateTime,
  }
}
</script>
