<template class="a1">
  <div id="app">
    <my-header></my-header>
    <router-view/>
    <my-footer></my-footer>
  </div>
</template>

<script>
  import MyFooter from './components/MyFooter'
  import MyHeader from './components/MyHeader'
  import { getStore } from './utils/storage'
  export default {
    data () {
      return {
        //读取localStorage保存的数据
        account: getStore('account')
      }
    },

    watch: {
      account: {
        deep: true, //深度监视
        // handler的值应该是一个函数, 且函数应该要有一个形参(接收最新的数据)
        handler: function (val) {
          // 将数据(json)保存到localStorage
          // storageUtils.saveData('orderList'，val)
          console.log("触发！")
        }
      }
    },

    components:{
      MyHeader,
      MyFooter
    },
  }
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
*{
  margin:0;
  padding:0;
  border:0;
}
#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
