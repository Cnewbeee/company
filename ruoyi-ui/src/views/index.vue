<template>
  <div class="app-container home-index">
    <el-row :gutter="20" style="margin-bottom: 30px;">
      <el-col :span="24">
        <el-card shadow="hover" class="welcome-card">
          <div style="font-size: 22px; font-weight: bold;">欢迎使用企业工资管理系统</div>
          <div style="margin-top: 8px; color: #666; font-size: 15px;">高效、准确、智能的企业工资与人员管理平台</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-bottom: 30px;">
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-title">职工总数</div>
          <div class="stat-value">{{ stats.employeeCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-title">部门数量</div>
          <div class="stat-value">{{ stats.departmentCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-title">岗位数量</div>
          <div class="stat-value">{{ stats.positionCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-title">本月工资总额</div>
          <div class="stat-value">￥{{ stats.salaryTotal }}</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-bottom: 30px;">
      <el-col :span="8">
        <el-card shadow="hover">
          <div slot="header" class="clearfix">
            <span>人员管理</span>
          </div>
          <div>
            <el-button type="primary" icon="el-icon-user" @click="goEmployee">职工信息管理</el-button>
            <el-button type="success" icon="el-icon-s-custom" @click="goDepartment" style="margin-left: 10px;">部门管理</el-button>
            <el-button type="info" icon="el-icon-s-check" @click="goJobposition" style="margin-left: 10px;">岗位管理</el-button>
          </div>
          <div style="margin-top: 10px; color: #888; font-size: 13px;">支持职工调入、调出、内部调动等操作</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div slot="header" class="clearfix">
            <span>工资管理</span>
          </div>
          <div>
            <el-button type="primary" icon="el-icon-money" @click="goSalaryInfo">工资档案管理</el-button>
            <el-button type="warning" icon="el-icon-edit" @click="goSalaryStandard" style="margin-left: 10px;">工资标准设置</el-button>
          </div>
          <div style="margin-top: 10px; color: #888; font-size: 13px;">支持工资计算、修改、批量处理等功能</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div slot="header" class="clearfix">
            <span>统计与报表</span>
          </div>
          <div>
            <el-button type="primary" icon="el-icon-search" @click="goQuery">工资查询统计</el-button>
            <el-button type="success" icon="el-icon-printer" @click="goReport" style="margin-left: 10px;">工资报表打印</el-button>
          </div>
          <div style="margin-top: 10px; color: #888; font-size: 13px;">支持多条件查询、工资条和统计表打印</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>
          <div style="font-size: 16px; font-weight: bold; margin-bottom: 10px;">系统简介</div>
          <div style="color: #666; font-size: 14px;">
            企业工资管理系统用于对企业职工工资信息进行高效管理，包括人员变动、工资计算、查询统计、报表打印等功能。系统支持职工信息的动态管理，工资档案的批量处理，灵活的查询统计和多样化报表输出，帮助企业提升工资管理效率和准确性。
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { listEmployee } from '@/api/employee/employee'
import { listDepartment } from '@/api/department/department'
import { listJobposition } from '@/api/jobposition/jobposition'
import { listSalaryinfo } from '@/api/salaryinfo/salaryinfo'

export default {
  name: "Index",
  data() {
    return {
      version: "3.9.0",
      stats: {
        employeeCount: 0,
        departmentCount: 0,
        positionCount: 0,
        salaryTotal: 0
      }
    }
  },
  created() {
    this.fetchStats()
  },
  methods: {
    goEmployee() {
      this.$router.push({ path: '/employee/employee' })
    },
    goDepartment() {
      this.$router.push({ path: '/department/department' })
    },
    goJobposition() {
      this.$router.push({ path: '/jobposition/jobposition' })
    },
    goSalaryInfo() {
      this.$router.push({ path: '/salaryinfo/salaryinfo' })
    },
    goSalaryStandard() {
      this.$router.push({ path: '/salarystandard/salarystandard' })
    },
    goQuery() {
      this.$router.push({ path: '/salaryinfo/salaryinfo' })
    },
    goReport() {
      this.$router.push({ path: '/salaryinfo/salaryinfo' })
    },
    async fetchStats() {
      // 职工总数
      const empRes = await listEmployee({ pageNum: 1, pageSize: 1 })
      this.stats.employeeCount = empRes.total || 0
      // 部门数量
      const deptRes = await listDepartment()
      this.stats.departmentCount = deptRes.rows ? deptRes.rows.length : 0
      // 岗位数量
      const posRes = await listJobposition()
      this.stats.positionCount = posRes.rows ? posRes.rows.length : 0
      // 工资总额（本月）
      const salaryRes = await listSalaryinfo({ pageNum: 1, pageSize: 1000 })
      let total = 0
      if (salaryRes.rows) {
        salaryRes.rows.forEach(item => {
          if (item.salaryAmount) total += Number(item.salaryAmount)
        })
      }
      this.stats.salaryTotal = total.toFixed(2)
    }
  }
}
</script>
<style scoped>
.home blockquote {
  padding: 10px 20px;
  margin: 0 0 20px;
  font-size: 17.5px;
  border-left: 5px solid #eee;
}
.home hr {
  margin-top: 20px;
  margin-bottom: 20px;
  border: 0;
  border-top: 1px solid #eee;
}
.home .col-item {
  margin-bottom: 20px;
}
.home ul {
  padding: 0;
  margin: 0;
  list-style-type: none;
}
.home {
  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;
}
.home h4 {
  margin-top: 0px;
}
.home h2 {
  margin-top: 10px;
  font-size: 26px;
  font-weight: 100;
}
.home p {
  margin-top: 10px;
}
.home p b {
  font-weight: 700;
}
.home .update-log ol {
  display: block;
  list-style-type: decimal;
  margin-block-start: 1em;
  margin-block-end: 1em;
  margin-inline-start: 0;
  margin-inline-end: 0;
  padding-inline-start: 40px;
}
.home blockquote {
  padding: 10px 20px;
  margin: 0 0 20px;
  font-size: 17.5px;
  border-left: 5px solid #eee;
}
.home hr {
  margin-top: 20px;
  margin-bottom: 20px;
  border: 0;
  border-top: 1px solid #eee;
}
.home .col-item {
  margin-bottom: 20px;
}
.home ul {
  padding: 0;
  margin: 0;
  list-style-type: none;
}
.home {
  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;
}
.home h4 {
  margin-top: 0px;
}
.home h2 {
  margin-top: 10px;
  font-size: 26px;
  font-weight: 100;
}
.home p {
  margin-top: 10px;
}
.home p b {
  font-weight: 700;
}
.home .update-log ol {
  display: block;
  list-style-type: decimal;
  margin-block-start: 1em;
  margin-block-end: 1em;
  margin-inline-start: 0;
  margin-inline-end: 0;
  padding-inline-start: 40px;
}
/* 首页优化样式 */
.home-index .welcome-card {
  background: linear-gradient(90deg, #f0f7ff 0%, #e6f0fa 100%);
  border: none;
}
.home-index .stat-card {
  text-align: center;
  background: #f8fafc;
  border: none;
}
.home-index .stat-title {
  font-size: 15px;
  color: #888;
  margin-bottom: 6px;
}
.home-index .stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #409EFF;
}
</style>

