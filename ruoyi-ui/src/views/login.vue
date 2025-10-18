<template>
  <div class="login">
    <video class="login-bg-video" autoplay loop muted playsinline>
      <source src="../assets/images/bg.mp4" type="video/mp4" />
      您的浏览器不支持视频背景。
    </video>
    <div class="login-bg-mask"></div>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form login-card">
      <div class="login-logo">
        <img src="~@/assets/logo/logo.png" alt="logo" />
      </div>
      <h3 class="title">{{title}}</h3>
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          type="text"
          auto-complete="off"
          placeholder="账号"
          class="login-input"
        >
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
          class="login-input"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleLogin"
          class="login-input"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img"/>
        </div>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          class="login-btn"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2018-2025 All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login"
import Cookies from "js-cookie"
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      title: process.env.VUE_APP_TITLE,
      codeUrl: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getCode()
    this.getCookie()
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img
          this.loginForm.uuid = res.uuid
        }
      })
    },
    getCookie() {
      const username = Cookies.get("username")
      const password = Cookies.get("password")
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 })
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 })
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove("username")
            Cookies.remove("password")
            Cookies.remove('rememberMe')
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{})
          }).catch(() => {
            this.loading = false
            if (this.captchaEnabled) {
              this.getCode()
            }
          })
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
html, body, #app {
  height: 100%;
}
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  min-height: 600px;
  position: relative;
  overflow: hidden;
}
.login-bg-video {
  position: absolute;
  left: 0; top: 0; width: 100%; height: 100%;
  object-fit: cover;
  z-index: 0;
}
.login-bg-mask {
  position: absolute;
  left: 0; top: 0; right: 0; bottom: 0;
  background: rgba(255, 255, 255, 0.103);
  z-index: 0;
}
.login-card {
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.18);
  border-radius: 16px;
  background: rgba(255,255,255,0.92);
  width: 410px;
  padding: 32px 32px 10px 32px;
  z-index: 1;
  position: relative;
  border: 1px solid #e6e6e6;
}
.login-logo {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 18px;
}
.login-logo img {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(64,158,255,0.12);
}
.title {
  margin: 0px auto 28px auto;
  text-align: center;
  color: #409EFF;
  font-size: 22px;
  font-weight: bold;
  letter-spacing: 2px;
}
.login-input .el-input__inner {
  border-radius: 20px;
  background: #f7fafd;
  border: 1px solid #e6e6e6;
  height: 40px;
  font-size: 15px;
}
.login-input .el-input__prefix {
  left: 8px;
}
.input-icon {
  height: 39px;
  width: 16px;
  margin-left: 2px;
}
.login-btn {
  border-radius: 20px;
  background: linear-gradient(90deg, #409EFF 0%, #66b1ff 100%);
  border: none;
  color: #fff;
  font-size: 17px;
  font-weight: bold;
  letter-spacing: 2px;
  box-shadow: 0 2px 8px rgba(64,158,255,0.12);
  transition: background 0.3s;
}
.login-btn:hover {
  background: linear-gradient(90deg, #66b1ff 0%, #409EFF 100%);
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.login-code-img {
  height: 38px;
  border-radius: 8px;
  box-shadow: 0 1px 4px rgba(64,158,255,0.08);
  margin-left: 8px;
  cursor: pointer;
  background: #fff;
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
  z-index: 10;
  text-shadow: 0 1px 4px rgba(0,0,0,0.18);
}
</style>
