import Vue from 'vue'
import VueRouter from 'vue-router'
import HghssPage from '../views/HghssPage.vue'
import RecruitPage from '../views/RecruitPage'
import ResetPasswd from '../components/template/ResetPasswd'
import ChangePasswd from '../components/template/ChangePasswd'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'HghssPage',
    component: HghssPage
  },
  {
    path: '/recruit',
    name: 'RecruitPage',
    component: RecruitPage
  },
  {
    path: '/resume',
    name: 'ResumePage',
    component: RecruitPage
  },
  {
    path: '/password/reset',
    name: 'ResetPasswd',
    component: ResetPasswd
  },
  {
    path: '/password/change',
    name: 'ChangePasswd',
    component: ChangePasswd
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
