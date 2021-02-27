import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login'
import test from '../views/test'
import BackManage from '../views/BackManage'
import account from '../views/back/account'
import content from '../views/back/content'

Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/backManage',
    name: "后台管理",
    component: BackManage,
    redirect: '/account',
    children: [
      {
        path: '/account',
        name: "账户管理",
        component: account
      },
      {
        path: '/content',
        name: "内容管理",
        component: content
      }
    ]
  },
  {
    path: '/test',
    name: "test",
    component: test
  },
  {
    path: '/login',
    name: "login",
    component: Login
  },
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
