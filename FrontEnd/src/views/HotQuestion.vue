<template>
  <div>

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
        <el-button type="primary" @click="getHotQuestion">Query</el-button>
      </el-form-item>

    </el-form>

    <el-table v-loading = "loading"
              element-loading-text="loading"
              v-show="table_visible" :data="Tags" border stripe>
      <el-table-column index="1" prop="id" align="center" label="id"/>
      <el-table-column index="2" prop="name" align="center" label="name"/>
      <el-table-column index="3" prop="title" align="center" label="title"/>
      <el-table-column index="4" prop="creation_date" align="center" label="creation_date"/>
      <el-table-column index="5" prop="question_id" align="center" label="question_id"/>
    </el-table>
  </div>
</template>

<script>
import {ref} from "vue";

export default {


  name: "Question",
  data() {
    return {
      Tags: [],
      table_visible: false,
      loading: true,
      chartType: null,
      RelativeTags: [],
      myChartShow: false,
      formData: {name: 'java', K: 'all'},
      fromDate: '2012-05-01',
      toDate: '2022-05-01'
    }
  },

  methods: {
    getHotQuestion() {
      const _this = this;
      _this.table_visible = true;
      _this.loading = true;

      _this.axios.get('/webScrap/start').then(function (resp) {
        console.log(resp);
        _this.Tags = resp.data;
        _this.loading = false;
      })
    }
  }
}
</script>


<style scoped>
.el-table {
  margin: 50px;
}

.el-input {
  width: 40%;
}


</style>