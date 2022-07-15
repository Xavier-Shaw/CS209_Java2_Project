import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import VueAxios from 'vue-axios'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as echarts from 'echarts'

axios.defaults.baseURL = "http://localhost:8181"

const app = createApp(App)
app.config.globalProperties.$echarts = echarts
app.use(store).use(router).use(VueAxios,axios).use(ElementPlus)
app.mount('#app')
