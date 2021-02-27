import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {userInfo} from './api'

Vue.config.productionTip = false
Vue.use(ElementUI);

// 不需要登陆的页面 => 白名单
const whiteList = ['/home', '/goods', '/login', '/goodsDetails']
router.beforeEach(function (to, from, next) {
  userInfo().then(res => {
    // 没登录
    console.log(res)
    if (res.account === null) {
      // 白名单
      if (whiteList.indexOf(to.path) !== -1) {
        next()
      } else {
        next('/login')
      }
    } else {
      store.commit('RECORD_USERINFO', {info: res.result})
      //  跳转到
      if (to.path === '/login') {
        next({path: '/'})
      }
      next()
    }
  })
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')


