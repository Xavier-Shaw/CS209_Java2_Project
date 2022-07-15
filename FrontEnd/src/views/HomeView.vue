<template>
  <div class="main">
    <el-carousel class="cards" :interval="4000" type="card" height="400px">
      <el-carousel-item  v-for="item in topics" :key="item">
        <router-link :to="'Topic' + item.index">
          <h3 text="2xl" justify="center">
            {{ item.text }}
          </h3>
        </router-link>
      </el-carousel-item>
    </el-carousel>


  </div>
</template>

<script>

export default {
  name: 'HomeView',

  data() {
    return {
      topics: [
        {text: "Fields that are related to Java", index: 1},
        {text: "High Quality Questions of Java", index: 2},
        {text: "Java is an Utility language", index: 3},
        {text: "Trend changing in Java developing", index: 4}
      ]
    }
  },

}
</script>


<style scoped>

.main {
  margin: 70px;
}

.el-carousel__item h3 {
  color: #e05032;
  opacity: 0.75;
  line-height: 400px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #b6b3de;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #b4e8c6;
}


</style>
