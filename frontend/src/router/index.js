import Vue from 'vue'
import VueRouter from 'vue-router'
import HghssPage from '../views/HghssPage'
import CommonPage from '../views/CommonPage'
import ResetPasswd from '../components/template/ResetPasswd'
import ChangePasswd from '../components/template/ChangePasswd'
import AdminPage from '../views/AdminPage.vue'
import OAuth2Page from '../views/OAuth2Page.vue'

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
    component: CommonPage
  },
  {
    path: '/resume',
    name: 'ResumePage',
    component: CommonPage
  },
  {
    path: '/resume/write/:id',
    name: 'ResumeWrite',
    component: CommonPage
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
  {
    path: '/admin',
    name: 'Admin',
    component: AdminPage
  },
  {
    path: '/oauth2',
    name: 'OAuth2',
    component: OAuth2Page
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
