<template>
  <div class="login v2">
    <div class="wrapper">
      <div class="dialog dialog-shadow" style="display: block; margin-top: -235px;">
        <div class="title" v-if="loginPage">
          <h4>使用 Chimelu ID 登录官网</h4></div>
        <div v-if="loginPage" class="content">
          <ul class="common-form">
            <li class="username border-1p">
              <div class="input">
                <input type="text" v-model="ruleForm.account" placeholder="账号">
              </div>
            </li>
            <li>
              <div class="input">
                <input type="password" v-model="ruleForm.password" @keyup.enter="login" placeholder="密码">
              </div>
            </li>
            <li style="text-align: right" class="pr">
              <span class="pa" style="top: 0;left: 0;color: #d44d44">{{ruleForm.errMsg}}</span>
              <a href="javascript:;" style="padding: 0 5px" @click="loginPage = false">注册 Chimelu ID</a>
            </li>
          </ul>
          <!--登陆-->
          <div>
            <y-button text="登陆" :classStyle="isLoginOk" @btnClick="login" class="btn"></y-button>
          </div>
        </div>
        <div class="registered" v-else>
          <h4>注册 Chimelu ID</h4>
          <div class="content" style="margin-top: 20px;">
            <ul class="common-form">
              <li class="username border-1p">
                <div class="input">
                  <input type="text"
                         v-model="registered.account" placeholder="账号"
                         @keyup="registered.account = registered.account.replace(/[^\w\.\/]/ig,'')">
                </div>
              </li>
              <li>
                <div class="input">
                  <input type="password"
                         v-model="registered.password"
                         placeholder="密码">
                </div>
              </li>
              <li>
                <div class="input">
                  <input type="password" v-model="registered.userPwd2" placeholder="重复密码">
                </div>
              </li>
            </ul>
            <div>
              <y-button :classStyle="isRegOk" text="注册" class="btn" @btnClick="regist"></y-button>
            </div>
            <ul class="common-form pr">
              <li class="pa" style="left: 0;top: 0;margin: 0;color: #d44d44">{{registered.errMsg}}</li>
              <li style="text-align: center;line-height: 48px;margin-bottom: 0;">
                <span>如果您已拥有 Chimelu ID，则可在此</span>
                <a href="javascript:;"
                   style="margin: 0 5px"
                   @click="loginPage = true">登陆</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import YButton from '../components/YButton'
  import { register , userLogin} from '../api/index'
  import { setStore } from '../utils/storage'
  // import MyFooter from '../components/MyFooter'
  export default {
    data () {
      return {
        cart: [],
        loginPage: true,
        ruleForm: {
          account: '',
          password: '',
          errMsg: ''
        },
        registered: {
          account: '',
          password: '',
          userPwd2: '',
          errMsg: ''
        }
      }
    },
    computed: {
      // 可点击注册
      isRegOk (rules) {
        const {password, userPwd2, account} = this.registered
        return password && userPwd2 && account ? 'main-btn' : 'disabled-btn'
      },
      isLoginOk () {
        const {password, account} = this.ruleForm
        return password && account ? 'main-btn' : 'disabled-btn'
      }
    },
    methods: {

      login () {
        const {account, password} = this.ruleForm
        if (!account || !password) {
          this.ruleForm.errMsg = '账号或者密码不能为空!'
        } else {
          userLogin({account, password}).then(function (response) {
            if (response.code === "000000") {
              console.log("qwer")
              console.log(response)
              setStore('account', response.account)
              window.location.href = '/'
            }
          })

        }
      },
      regist () {
        const {account, password, userPwd2} = this.registered
        if (!account || !password || !userPwd2) {
          this.registered.errMsg = '账号密码不能为空'
          console.log(account)
          console.log(password)
          console.log(userPwd2)
          return false
        }
        if (userPwd2 !== password) {
          this.registered.errMsg = '两次输入的密码不相同'
          return false
        }
        let data = {"account": account, "password": password}

        register(data).then(function (response) {
          if (response.code === "000000") {
            alert("注册成功！")
          }
      })
      }
    },
    mounted () {

    },
    components: {
      // MyFooter,
      YButton
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  * {
    box-sizing: content-box;
  }

  li{
    list-style-type:none;
  }

  .login {
    overflow-x: hidden;
    overflow-y: hidden;
    .input {
      height: 50px;
      display: flex;
      align-items: center;
      padding-left: 0;
      input {
        font-size: 16px;
        width: 100%;
        height: 100%;
        padding: 10px 15px;
        box-sizing: border-box;
        border: 1px solid #ccc;
        border-radius: 6px;
      }
    }
    .wrapper {
      background: url(/static/images/bg_9b9dcb65ff.png) repeat;
      background-size: 100px;
      min-height: 800px;
      min-width: 630px;
    }
    .btn {
      margin: 0;
      width: 100%;
      height: 48px;
      font-size: 18px;
      line-height: 48px
    }
  }

  .v2 .dialog {
    width: 450px;
    border: 1px solid #dadada;
    border-radius: 10px;
    top: 50%;
    left: 50%;
    margin-left: -225px;
    position: absolute;
    .title {
      background: linear-gradient(#fff, #f5f5f5);
      overflow: visible;
      position: relative;
      background-image: url(/static/images/smartisan_4ada7fecea.png);
      background-size: 160px;
      background-position: top center;
      background-repeat: no-repeat;
      height: 92px;
      margin: 23px 0 50px;
      padding: 75px 0 0;
      box-shadow: none;
      h4 {
        padding: 0;
        text-align: center;
        border-bottom: 1px solid #dcdcdc;
        position: absolute;
        bottom: 0;
        width: 100%;
        margin: 0;
        border-bottom: 0;
        box-shadow: none;
        line-height: 1em;
        height: auto;
        color: #333;
        font-weight: 400;
      }
    }
    .content {
      padding: 0 40px 22px;
      height: auto;
      .common-form {
        li {
          clear: both;
          margin-bottom: 15px;
          position: relative;
        }
      }
    }
  }

  .dialog-shadow, .v2 .bbs .dialog-shadow, .v2 .dialog-shadow {
    -webkit-box-shadow: 0 9px 30px -6px rgba(0, 0, 0, .2), 0 18px 20px -10px rgba(0, 0, 0, .04), 0 18px 20px -10px rgba(0, 0, 0, .04), 0 10px 20px -10px rgba(0, 0, 0, .04);
    -moz-box-shadow: 0 9px 30px -6px rgba(0, 0, 0, .2), 0 18px 20px -10px rgba(0, 0, 0, .04), 0 18px 20px -10px rgba(0, 0, 0, .04), 0 10px 20px -10px rgba(0, 0, 0, .04);
    box-shadow: 0 9px 30px -6px rgba(0, 0, 0, .2), 0 18px 20px -10px rgba(0, 0, 0, .04), 0 18px 20px -10px rgba(0, 0, 0, .04), 0 10px 20px -10px rgba(0, 0, 0, .04);
  }

  @media screen and (min-width: 737px), screen and (-webkit-max-device-pixel-ratio: 1.9) and (max-width: 736px) and (min-device-width: 737px) {
    .wrapper {
      background: url(/static/images/con-bg_04f25dbf8e.jpg) repeat-x;
      padding-top: 50px !important;
      /*position: absolute;*/
      /*top: 0;*/
      /*bottom: 0;*/
      /*left: 0;*/
      /*right: 0;*/
    }
    .dialog {
      background: url(/static/images/dialog-gray-bg.png) #fff bottom repeat-x;
      border-radius: 12px;
      /*display: none;*/
      /*<!--margin: -163px 0 0 -218px;-->*/
      /*width: 436px;*/
      /*position: fixed;*/
      /*left: 50%;*/
      /*top: 50%;*/
    }
    .dialog .title h4 {
      border-bottom: #d1d1d1 solid 1px;
      box-shadow: 0 2px 6px #d1d1d1;
      color: #666;
      font-size: 20px;
      height: 61px;
      line-height: 61px;
      padding: 0 0 0 35px;
    }
    .common-form li {
      clear: both;
      margin-bottom: 15px;
      position: relative;
    }
  }

  .registered {
    h4 {
      padding: 0;
      text-align: center;
      color: #666;
      border-bottom: 1px solid #dcdcdc;
      -webkit-box-shadow: none;
      -moz-box-shadow: none;
      box-shadow: none;
      font-weight: 700;
      font-size: 20px;
      height: 60px;
      line-height: 60px;
    }
  }

</style>

