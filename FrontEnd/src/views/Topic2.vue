<template>
  <div>
    <el-container>
      <el-aside width="150px">
        <el-scrollbar>
          <el-menu :default-openeds="['1']">
            <el-sub-menu index="1">
              <template #title>ChartMenu</template>
              <el-menu-item-group>
                <template #title>Group 1</template>
                <el-menu-item index="1-1" @click.native="ScatterType">Scatter Chart</el-menu-item>
                <el-menu-item index="1-2" @click.native="PieType">Pie Chart</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
          </el-menu>
        </el-scrollbar>
      </el-aside>

      <el-main>
        <div
            style="font-size: 30px; background-color: antiquewhite; color: #e05032; font-family: 'Avenir', Helvetica, Arial, sans-serif ">
          High Quality Questions of Java
        </div>

        <div class="info">
          <el-steps style="margin: 30px" align-center active="2">
            <el-step title="Choose a chart type from the left"></el-step>
            <el-step title="Query" description="show the chart"></el-step>
          </el-steps>
        </div>


        <el-button type="primary" @click="searchQuestion">Query</el-button>
        <el-button type="info" @click.native="restart">Reset</el-button>


        <div id="myChart" style="width:1300px; height:700px"></div>
      </el-main>

    </el-container>
  </div>
</template>

<script>
import * as echarts from "echarts";
import 'echarts-wordcloud';

let myChart;
export default {
  name: "Topic2",

  mounted() {
    myChart = echarts.init(document.getElementById("myChart"))
    console.log("already here")
  },

  data() {
    return {
      chartType: null,
      Questions: [],
      HighQuestions: 0,
      MidQuestions: 0,
      LowQuestions: 0,
      myChartShow: false
    }
  },

  methods: {

    restart() {
      myChart.clear();
    },

    ScatterType() {
      this.chartType = 'scatter'
    },

    PieType() {
      this.chartType = 'pie'
    },


    searchQuestion() {
      const _this = this;

      if (_this.chartType === 'scatter') {
        _this.axios.get('/question/getQuestions').then(function (resp) {
          console.log(resp);
          _this.Questions = resp.data;
          _this.myChartShow = true;
          _this.showChart();
        })
      }
      else if (_this.chartType === 'pie'){
        _this.axios.get('/question/getHighViewQuestion').then(function (resp) {
          console.log(resp);
          _this.HighQuestions = parseInt(resp.data);
        })

        _this.axios.get('/question/getMidViewQuestion').then(function (resp) {
          console.log(resp);
          _this.MidQuestions = parseInt(resp.data);
        })

        _this.axios.get('/question/getLowViewQuestion').then(function (resp) {
          console.log(resp);
          _this.LowQuestions = parseInt(resp.data);
        })

        _this.myChartShow = true;
        setTimeout(_this.showPieChart, 100)
      }
    },


    showPieChart() {
      const option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: [
              {value: this.LowQuestions, name: 'Low Quality Question'},
              {value: this.MidQuestions, name: 'Mid Quality Question'},
              {value: this.HighQuestions, name: 'High Quality Question'}
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      myChart.setOption(option);
    },

    showChart() {
      // 基于准备好的dom，初始化echarts实例
      const option =
          {
            title: {
              text: "Scatter Chart",
            },
            tooltip: {},
            xAxis: {type: 'value', name: 'view'},
            yAxis: {type: 'value', name: 'answer'},
            dataset: {
              source: this.Questions
            },
            series: [
              {
                name: 'question',
                type: 'scatter',
                symbolSize: 20,
                encode: {
                  x: 'view_count',
                  y: 'answer_count'
                }
              }
            ]

          };

      myChart.setOption(option)

      // 绘制图表
    },


  }

}
</script>

<style scoped>

</style>