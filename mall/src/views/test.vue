<template>
<div>
  <button @click="test">test</button>
  <button @click="Out">test</button>
</div>
</template>

<script>
  // import axios from 'axios'
  import { loginOut, userInfo } from '../api'

  export default {
    name: 'test',
    methods:{
      test(){
        // eslint-disable-next-line no-undef
        // axios({
        //   url: '/api/users/check',
        //   method: 'get',
        // }).then(function (response) {
        //   console.log("duile")
        //   console.log(response)
        // }).catch(function () {
        //   console.log("cuole")
        // })
        userInfo().then(res =>{
          console.log(res)
        })
      },
      Out(){
        loginOut().then(res => {
          console.log("退出！")
          console.log(res)
        })
      }
    }
  }
</script>

<style scoped>

</style>
