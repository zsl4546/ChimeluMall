<template>
  <div class="footer">
    <div class="tag">
      <a href="javascript:;">简体</a>
      <a href="javascript:;">繁体</a>
      <a href="javascript:;">English</a>
      <a href="javascript:;">常见问题</a></div>
    <div class="cop"><p>Copyright © 2020-2021  chimelu.com 版权所有</p></div>
    <a href="https://beian.miit.gov.cn/" style="text-decoration: none">蜀ICP备2021000191号</a>
  </div>
</template>
<script>
  export default {
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  .footer {
    height: 100px;
    padding: 50px 0 20px;
    background: #42b983;
    margin-top: 20px;
  }

  .tag {
    display: flex;
    justify-content: center;
    a {
      height: 14px;
      padding: 0 10px;
      line-height: 14px;
      text-decoration: none;
    }
    a + a {
      border-left: 1px solid #666;
    }
  }

  .cop {
    text-align: center;
    margin: 10px 0;
  }
</style>
