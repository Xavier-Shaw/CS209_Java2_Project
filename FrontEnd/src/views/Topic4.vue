<template>
  <div>
    <el-container>
      <el-main>
        <div style="font-size: 30px; background-color: lightgoldenrodyellow; color: darkblue; font-family: 'Avenir', Helvetica, Arial, sans-serif " >
          Trend changing in Java developing
        </div>

        <div class="info">
          <el-steps style="margin: 30px" align-center active="3">
            <el-step title="Select the Date"></el-step>
            <el-step title="Select the Period"></el-step>
            <el-step title="Query" description="show the chart"></el-step>
          </el-steps>
        </div>

        <el-form label-width="350px" @submit.prevent>

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

          <el-row>
            <el-col span=12 >
              <el-form-item label="Period:">
                <el-select v-model="delta" placeholder="Select">
                  <el-option label="1 month" value="1"></el-option>
                  <el-option label="3 months" value="3"></el-option>
                  <el-option label="6 months" value="6"></el-option>
                  <el-option label="1 year" value="12"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col span=12>
              <el-form-item>
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

let myChart;
let option;
let interval_func;
let now_date;
let next_Date;


const updateFrequency = 2000;
const dimension = 0;
const countryColors = {
  Australia: '#00008b',
  Canada: '#f00',
  China: '#ffde00',
  Cuba: '#002a8f',
  Finland: '#003580',
  France: '#ed2939',
  Germany: '#000',
  Iceland: '#003897',
  India: '#f93',
  Japan: '#bc002d',
  'North Korea': '#024fa2',
  'South Korea': '#000',
  'New Zealand': '#00247d',
  Norway: '#ef2b2d',
  Poland: '#dc143c',
  Russia: '#d52b1e',
  Turkey: '#e30a17',
  'United Kingdom': '#00247d',
  'United States': '#b22234'
};


export default {
  name: "Topic4",

  mounted() {
    myChart = echarts.init(document.getElementById("myChart"))
    console.log("already here")
  },

  data() {
    return {
      RelativeTags: [],
      fromDate: '2012-05-01',
      toDate: '2022-05-01',
      delta: ''
    }
  },

  methods: {

    restart() {
      clearInterval(interval_func);
      myChart.clear();
      this.RelativeTags = [];
      now_date = new Date(this.fromDate);
    },

    showChart() {
      console.log("here---------")
      const _this = this;
      now_date = new Date(_this.fromDate);
      this.GetData(_this);
      interval_func = setInterval(  //设置定时器，1s更新一次
          function () {
            _this.GetData(_this);
          }, 3000
      );
    },

    GetData(obj) {
      console.log("data data data")
      const _this = obj;
      next_Date = new Date(now_date);
      next_Date.setMonth(next_Date.getMonth() + parseInt(_this.delta));
      obj.axios.get('/sortTag/getTrendTopKWithDate/' + _this.dateToString(now_date) + '/' + _this.dateToString(next_Date)).then((resp) => {
        console.log(resp.data);
        _this.RelativeTags = resp.data;
        _this.changeChart(_this);
      })
    },

    changeChart(obj) {
      console.log("changeChart");
      const option = {
        grid: {
          top: 10,
          bottom: 30,
          left: 150,
          right: 80
        },
        dataset: {
          dimensions: ['name', 'count'],
          source: obj.RelativeTags
        },

        tooltip: {
          show: true
        },

        xAxis: {
          type: 'value',
          max: 'dataMax',
        },

        yAxis: {
          type: 'category',
          inverse: true,
          max: 10,
          animationDuration: 150,
          animationDurationUpdate: 150,
        },

        encode: {
          x: 1,
          y: 0
        },

        series: [
          {
            realtimeSort: true,
            seriesLayoutBy: 'column',
            type: 'bar',
            itemStyle: {
              normal: {
                label: {
                  show: true,
                  position: 'right',
                  // valueAnimation: true,
                  textStyle: {
                    color: 'black',
                    fontSize: 12
                  }
                }
              }
            }
          }
        ],

        // Disable init animation.
        animationDuration: 0,
        animationDurationUpdate: updateFrequency,
        animationEasing: 'linear',
        animationEasingUpdate: 'linear',
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
      };
      // console.log(option);
      myChart.setOption(option);
      if (now_date === new Date(obj.toDate)){
        clearInterval(interval_func);
      }
      now_date.setMonth(now_date.getMonth() + parseInt(obj.delta));
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

</style>