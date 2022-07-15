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
                <el-menu-item index="1-1" @click.native="LineType">Line Chart</el-menu-item>
                <el-menu-item index="1-2" @click.native="PieType">Pie Chart</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
          </el-menu>
        </el-scrollbar>
      </el-aside>

      <el-main>
        <div
            style="font-size: 30px; background-color: lightgray; color: deeppink; font-family: 'Avenir', Helvetica, Arial, sans-serif ">
          Java is an Utility language
        </div>

        <div class="info">
          <el-steps style="margin: 30px" align-center active="5">
            <el-step title="Select the Date"></el-step>
            <el-step title="Choose a chart type from the left"></el-step>
            <el-step title="Query" description="show the chart"></el-step>
          </el-steps>
        </div>

        <el-form label-width="350px" @submit.prevent>
          <el-row>
            <el-col span=12>
              <el-form-item label="From Date:">
                <el-date-picker value-format="YYYY-MM-DD" v-model="fromDate" type="date" placeholder="Pick a day"/>
              </el-form-item>
            </el-col>

            <el-col span=12>
              <el-form-item label="To Date:">
                <el-date-picker value-format="YYYY-MM-DD" v-model="toDate" type="date" placeholder="Pick a day"/>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item style="margin-left: 270px">
                <el-button type="primary" @click.native="showChart">Query</el-button>
                <el-button type="info" @click.native="restart">Reset</el-button>
              </el-form-item>
            </el-col>
          </el-row>

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
let now_date;
let interval_func;
let data_1 = [];
let data_2 = [];

export default {
  name: "Topic3",

  mounted() {
    myChart = echarts.init(document.getElementById("myChart"))
    console.log("already here")
  },

  data() {
    return {
      chartType: null,
      SingleTags: 0,
      MultiTags: 0,
      myChartShow: false,
      fromDate: '2012-05-01',
      toDate: '2022-05-01'
    }
  },

  methods: {

    restart() {
      clearInterval(interval_func);
      now_date = new Date(this.fromDate);
      this.SingleTags = 0;
      this.MultiTags = 0;
      data_1 = [];
      data_2 = [];
      myChart.clear();
    },

    LineType() {
      this.chartType = 'line'
    },

    PieType() {
      this.chartType = 'pie'
    },

    showChart() {
      console.log("here---------")
      const _this = this;
      now_date = new Date(_this.fromDate);
      this.GetData(_this);

      interval_func = setInterval(  //设置定时器，1s更新一次
          function () {
            _this.GetData(_this);
          }, 2000
      );
    },

    GetData(obj) {
      const _this = obj;

      _this.axios.get('/sortTag/getSingleTag/' + _this.fromDate + '/' + _this.dateToString(now_date)).then(function (resp) {
        console.log(resp);
        _this.SingleTags = parseInt(resp.data);
      })

      _this.axios.get('/sortTag/getMultiTag/' + _this.fromDate + '/' + _this.dateToString(now_date)).then(function (resp) {
        console.log(resp);
        _this.MultiTags = parseInt(resp.data);
      })

      console.log(now_date.toString())
      console.log(obj.dateToString(now_date))


      data_1.push({
        value: [obj.dateToString(now_date), obj.SingleTags]
      })

      console.log(data_1)

      data_2.push({
        value: [obj.dateToString(now_date), obj.MultiTags]
      })

      this.myChartShow = true;

      if (this.chartType === 'line'){
        this.showLineRace(_this);
      }
      else if (this.chartType === 'pie'){
        this.showPieRace(_this);
      }
    },

    showPieRace(obj) {
      const option = {
        title: {
          text: 'Number of Questions with SingleTags and MultiTags since 2012'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        tooltip: {
          axisPointer: {
            animation: false
          }
        },
        grid: {
          right: 140
        },
        series: [
          {
            name: obj.dateToString(now_date),
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
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
            data:[
              {value: obj.SingleTags, name: 'Single Tags'},
              {value: obj.MultiTags, name: 'Multi Tags'},
            ]
          }
        ],
        graphic: {
          elements: [
            {
              type: 'text',
              right: 160,
              bottom: 60,
              style: {
                text: obj.dateToString(now_date),
                font: 'bolder 80px monospace',
                fill: 'rgba(100, 100, 100, 0.25)'
              },
              z: 100
            }
          ]
        }
      }

      myChart.setOption(option);
      if (now_date === new Date(obj.toDate)) {
        clearInterval(interval_func);
      }
      now_date.setMonth(now_date.getMonth() + 6);
      if (now_date > new Date(obj.toDate)) {
        now_date = new Date(obj.toDate);
      }
    },

    showLineRace(obj) {

      const option = {
        title: {
          text: 'Number of Questions with SingleTags and MultiTags since 2012'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            animation: false
          }
        },
        xAxis: {
          type: 'time',
          splitLine: {
            show: false
          }
        },
        yAxis: {
          type: 'value',
          boundaryGap: [0, '100%'],
          splitLine: {
            show: false
          }
        },
        grid: {
          right: 140
        },
        series: [
          {
            name: 'SingleTags',
            type: 'line',
            showSymbol: false,
            data: data_1
          },
          {
            name: 'MultiTags',
            type: 'line',
            showSymbol: false,
            data: data_2
          }
        ]
      }

      myChart.setOption(option);
      if (now_date === new Date(obj.toDate)) {
        clearInterval(interval_func);
      }
      now_date.setMonth(now_date.getMonth() + 3);
      if (now_date > new Date(obj.toDate)) {
        now_date = new Date(obj.toDate);
      }
    },

    dateToString(date) {
      const year = date.getFullYear();
      let month = (date.getMonth() + 1).toString();
      let day = (date.getDate()).toString();
      if (month.length === 1) {
        month = "0" + month;
      }
      if (day.length === 1) {
        day = "0" + day;
      }

      return year + "-" + month + "-" + day;
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