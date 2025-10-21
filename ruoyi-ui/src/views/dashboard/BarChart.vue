<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import { getMonthSalary } from '@/api/salaryinfo/salaryinfo'

const animationDuration = 2000

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '350px'
    },
    // 图表数据作为props传入
    chartData: {
      type: Object,
      default: () => null
    },
    // 是否显示图例
    showLegend: {
      type: Boolean,
      default: true
    },
    // 是否显示网格线
    showGrid: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      chart: null,
      monthData: {} // 存储月度工资数据
    }
  },
  watch: {
    // 监听数据变化，自动更新图表
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.fetchMonthSalaryData()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    // 获取月度工资数据
    async fetchMonthSalaryData() {
      try {
        const response = await getMonthSalary()
        if (response.code === 200 && response.data) {
          this.monthData = response.data
          this.processChartData()
        } else {
          console.error('获取月度工资数据失败:', response.msg)
          this.processChartData() // 使用默认数据
        }
      } catch (error) {
        console.error('获取月度工资数据异常:', error)
        this.processChartData() // 使用默认数据
      }
    },
    
    // 处理图表数据
    processChartData() {
      const categories = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
      const currentMonth = new Date().getMonth() // 获取当前月份 (0-11)
      
      // 从接口数据中提取月度工资
      const monthlySalary = categories.map((month, index) => {
        const monthKey = month.padStart(3, '0') // 转换为"01月"格式
        return parseFloat(this.monthData[monthKey] || '0')
      })
      
      // 创建图表数据
      const chartData = {
        categories: categories,
        series: [
          {
            name: '月度工资总额',
            data: monthlySalary,
            color: function(params) {
              // 当前月份高亮显示
              return params.dataIndex === currentMonth ? '#ff4d4f' : '#1890ff'
            },
            // 当前月份添加特殊样式
            itemStyle: {
              color: function(params) {
                return params.dataIndex === currentMonth ? '#ff4d4f' : '#1890ff'
              },
              borderRadius: function(params) {
                return params.dataIndex === currentMonth ? [4, 4, 0, 0] : [0, 0, 0, 0]
              }
            },
            // 当前月份添加阴影效果
            emphasis: {
              itemStyle: {
                shadowBlur: params => params.dataIndex === currentMonth ? 20 : 10,
                shadowColor: params => params.dataIndex === currentMonth ? 'rgba(255, 77, 79, 0.5)' : 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      
      // 初始化图表
      this.initChart(chartData)
    },
    
    initChart(chartData) {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(chartData)
    },
    
    // 设置图表选项
    setOptions(chartData) {
      if (!chartData || !chartData.categories || !chartData.series) {
        return
      }

      const series = chartData.series.map(item => ({
        name: item.name,
        type: 'bar',
        barWidth: '60%',
        data: item.data,
        itemStyle: item.itemStyle || {},
        animationDuration,
        animationEasing: 'cubicInOut',
        emphasis: item.emphasis || {}
      }))

      this.chart.setOption({
        title: {
          text: '月度工资统计报表',
          subtext: `当前月份: ${new Date().getMonth() + 1}月 (高亮显示)`,
          left: 'center',
          top: 10,
          textStyle: {
            fontSize: 18,
            fontWeight: 'bold',
            color: '#333'
          },
          subtextStyle: {
            fontSize: 12,
            color: '#666'
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter: (params) => {
            const param = params[0]
            const isCurrentMonth = param.dataIndex === new Date().getMonth()
            const monthText = isCurrentMonth ? ' (本月)' : ''
            
            return `
              <div style="padding: 8px;">
                <div style="font-weight: bold; margin-bottom: 4px;">
                  ${param.name}${monthText}
                </div>
                <div style="display: flex; align-items: center;">
                  <span style="display:inline-block;margin-right:8px;border-radius:50%;width:10px;height:10px;background-color:${param.color};"></span>
                  <span style="font-weight: bold;">工资总额: ¥${param.value.toLocaleString()}</span>
                </div>
                ${isCurrentMonth ? '<div style="color: #ff4d4f; font-size: 12px; margin-top: 4px;">📍 当前月份</div>' : ''}
              </div>
            `
          },
          backgroundColor: 'rgba(50,50,50,0.9)',
          borderColor: '#333',
          textStyle: {
            color: '#fff'
          }
        },
        legend: {
          show: this.showLegend,
          data: chartData.series.map(item => item.name),
          left: 'center',
          bottom: 10,
          textStyle: {
            fontSize: 12
          }
        },
        grid: {
          show: this.showGrid,
          top: 80,
          left: '3%',
          right: '4%',
          bottom: '15%',
          containLabel: true,
          borderColor: '#e4e7ed'
        },
        xAxis: [{
          type: 'category',
          data: chartData.categories,
          axisTick: {
            alignWithLabel: true
          },
          axisLine: {
            lineStyle: {
              color: '#e4e7ed'
            }
          },
          axisLabel: {
            color: '#606266',
            fontSize: 12,
            fontWeight: function(value, index) {
              return index === new Date().getMonth() ? 'bold' : 'normal'
            },
            color: function(value, index) {
              return index === new Date().getMonth() ? '#ff4d4f' : '#606266'
            }
          }
        }],
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          axisLabel: {
            color: '#606266',
            fontSize: 12,
            formatter: '¥{value}'
          },
          splitLine: {
            lineStyle: {
              color: '#e4e7ed',
              type: 'dashed'
            }
          }
        }],
        series: series,
        backgroundColor: '#fff'
      }, true)
    },
    
    // 手动刷新图表
    refresh() {
      if (this.chart) {
        this.chart.resize()
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.chart {
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>
