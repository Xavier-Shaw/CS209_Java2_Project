import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Topic1 from "@/views/Topic1";
import Topic2 from "@/views/Topic2";
import Topic3 from "@/views/Topic3";
import Topic4 from "@/views/Topic4";
import Question from "@/views/Question";

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/question',
    name: 'question',
    component: Question
  },
  {
    path: '/Topic1',
    name: 'Topic1',
    component: Topic1
  },
  {
    path: '/Topic2',
    name: 'Topic2',
    component: Topic2
  },
  {
    path: '/Topic3',
    name: 'Topic3',
    component: Topic3
  },
  {
    path: '/Topic4',
    name: 'Topic4',
    component: Topic4
  }
]

const router = new createRouter({
  mode: 'hash',
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
