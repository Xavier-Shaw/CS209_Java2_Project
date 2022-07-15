<template>
  <div id="app">

    <el-menu mode="horizontal">
      <el-menu-item index="1">
        <router-link to='/'>HomePage</router-link>
      </el-menu-item>
      <el-sub-menu index="2">
        <template #title>Topics</template>
        <el-menu-item index="2-1">
          <router-link to="/Topic1">Topic_1</router-link>
        </el-menu-item>
        <el-menu-item index="2-2">
          <router-link to="/Topic2">Topic_2</router-link>
        </el-menu-item>
        <el-menu-item index="2-3">
          <router-link to="/Topic3">Topic_3</router-link>
        </el-menu-item>
        <el-menu-item index="2-4">
          <router-link to="/Topic4">Topic_4</router-link>
        </el-menu-item>
      </el-sub-menu>
      <el-menu-item index="3">
        <router-link to="/question">UpdateData</router-link>
      </el-menu-item>
    </el-menu>
    <router-view/>

  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'

export default {
  name: 'app',
  components: {
    HelloWorld
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 10px;
}

</style>
