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
                <el-menu-item index="1-1" @click.native="BarType">Bar Chart</el-menu-item>
                <el-menu-item index="1-2" @click.native="LineType">Line Chart</el-menu-item>
                <el-menu-item index="1-3" @click.native="PieType">Pie Chart</el-menu-item>
                <el-menu-item index="1-4" @click.native="WordCloudType">Word Cloud</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
          </el-menu>
        </el-scrollbar>
      </el-aside>

      <el-main>
        <div style="font-size: 30px; background-color: aliceblue; color: firebrick; font-family: 'Avenir', Helvetica, Arial, sans-serif " >
          Fields that are related to Java
        </div>

        <div class="info">
          <el-steps style="margin: 30px" align-center active="5">
            <el-step title="Input a Tag Name" description="search relative tags"></el-step>
            <el-step title="Input a number K" description="get the Top-K tags"></el-step>
            <el-step title="Select the Date"></el-step>
            <el-step title="Choose a chart type from the left"></el-step>
            <el-step title="Query" description="show the chart"></el-step>
          </el-steps>
        </div>

        <el-form :v-model="formData" label-width="350px" @submit.prevent>
          <el-row>
            <el-col span="16">
              <el-form-item label="Tag Name:" placeholder="Input a name">
                <el-input v-model="formData.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item label="Top K:">
                <el-input v-model="formData.K"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col span="12">
              <el-form-item label="From Date:">
                <el-date-picker value-format="YYYY-MM-DD" v-model="fromDate" type="date" placeholder="Pick a day"/>
              </el-form-item>
            </el-col>

            <el-col span="12">
              <el-form-item label="To Date:">
                <el-date-picker value-format="YYYY-MM-DD" v-model="toDate" type="date" placeholder="Pick a day"/>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item style="margin-top: 20px; margin-left: 320px">
            <el-button type="primary" @click="searchRelativeTag">Query</el-button>
          </el-form-item>

        </el-form>

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
  name: "Topic1",

  mounted() {
    myChart = echarts.init(document.getElementById("myChart"))
    console.log("already here")
  },

  data() {
    return {
      chartType: null,
      RelativeTags: [],
      myChartShow: false,
      formData: {name: 'java', K: 'all'},
      fromDate: '2012-05-01',
      toDate: '2022-05-01'
    }
  },

  methods: {

    BarType() {
      this.chartType = 'bar'
    },

    LineType() {
      this.chartType = 'line'
    },

    PieType() {
      this.chartType = 'pie'
    },

    WordCloudType() {
      this.chartType = 'wordCloud'
    },

    searchRelativeTag() {
      myChart.clear();
      const _this = this;

      if (_this.formData.K === 'all') {
        _this.axios.get('/sortTag/searchRelativeTagWithDate/' + _this.formData.name + '/' + _this.fromDate + '/' + _this.toDate).then(function (resp) {
          console.log(resp);
          _this.RelativeTags = resp.data;
          _this.myChartShow = true;
          if (_this.chartType === 'pie') {
            _this.showPieChart();
          } else if (_this.chartType === 'wordCloud') {
            _this.showWordCloud();
          } else {
            _this.showChart();
          }
        })
      } else {
        _this.axios.get('/sortTag/searchRelativeTagTopKWithDate/' + _this.formData.name + '/' + _this.formData.K + '/' + _this.fromDate + '/' + _this.toDate).then(function (resp) {
          console.log(resp);
          _this.RelativeTags = resp.data;
          _this.myChartShow = true;
          if (_this.chartType === 'pie') {
            _this.showPieChart();
          } else if (_this.chartType === 'wordCloud') {
            _this.showWordCloud();
          } else {
            _this.showChart();
          }
        })
      }


    },

    showChart() {
      // 基于准备好的dom，初始化echarts实例
      const option =
          {
            title: {
              text: "Chart",
            },
            tooltip: {},
            dimensions: ['name', 'count'],
            dataset: {
              source: this.RelativeTags
            },
            xAxis: {type: 'category'},
            yAxis: {type: 'value'},
            series: [
              {type: this.chartType}]
            ,
            encode: {
              x: 'name',
              y: 'count'
            }
          };

      myChart.setOption(option)

      // 绘制图表
    },

    showPieChart() {
      const option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          show: true
        },
        dataset: {
          dimensions: ['name', 'count'],
          source: this.RelativeTags
        },

        series: [
          {
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '40',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },

            encode: {
              itemName: 0,
              value: 1
            }
          }
        ]
      };

      myChart.setOption(option);
    },

    showWordCloud() {

      const newData = [];
      for (let i = 0; i < this.RelativeTags.length; i++){
        newData.push(
            {
              name: this.RelativeTags[i].name,
              value: parseFloat(this.RelativeTags[i].count)
            }
        )
      }

      const option = {

        series: [{
          type: 'wordCloud',
          shape: 'pentagon', //circle cardioid diamond triangle-forward triangle
          left: 'center',
          top: 'center',
          right: null,
          bottom: null,
          width: '80%',
          height: '80%',
          keepAspect: false,
          labelLine: {
            show: false
          },
          gridSize: 2, //值越大，word间的距离越大，单位像素
          sizeRange: [18, 60], //word的字体大小区间，单位像素
          rotationRange: [-90, 90], //word的可旋转角度区间
          rotationStep: 45,
          drawOutOfBound: false,
          layoutAnimation: true,
          textStyle: {
            fontFamily: 'Helvetica Neue',
            fontWeight: 'bold',
            // Color can be a callback function or a color string
            color: function () {
              // Random color
              return 'rgb(' + [
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160)
              ].join(',') + ')';
            }
          },
          emphasis: {
            focus: 'self',

            textStyle: {
              textShadowBlur: 10,
              textShadowColor: '#333'
            }
          },
          data: newData,
          encode: {
            name: 'name',
            value: 'value'
          }
        }]
      }

      myChart.setOption(option);
    }

  }
}
</script>

<style scoped>
.el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}
</style>