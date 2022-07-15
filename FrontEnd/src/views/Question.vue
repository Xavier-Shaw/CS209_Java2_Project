<template>
  <div>

    <el-button style="margin-top: 60px" type="primary" @click.native="updateData">Update The Data</el-button>

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
      loading: true
    }
  },

  methods: {
    updateData() {
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