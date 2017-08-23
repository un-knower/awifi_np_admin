<template>
    <div id="login-wrap">
        <header id="header">
            <div id="header-logo"></div>
        </header>
        <div class="container">
            <section class="login-bg"></section>
            <section id="login-box">
            <ul>
                <li class="error" v-show="errorShowing">
                    <div>{{errorTip}}</div>
                </li>
                <li>
                    <input type="text" class="input" v-model="username" placeholder="用户名">
                </li>
                <li>
                    <input type="password" class="input" v-model="password" placeholder="密码">
                </li>
                <li class="code">
                    <input type="text" class="input" v-model="code" placeholder="验证码">
                    <div class="verify" @click="changeCode"><img src="/public/verifyCode" id="codeImg"></div>
                </li>
                <li class="center-text">
                    <button class="button" @click="login" id="login-btn">登录</button>
                </li>
               <!-- <li>
                    <a class="password" href="#">忘记密码？</a>
                </li>  -->
            </ul>
            </section>
        </div>
        <footer>版权所有：中国电信爱WiFi运营中心</footer>
    </div>
</template>

<script type="text/javascript">
import axios from 'axios';
import qs from 'qs'
export default {
  name: 'app',
  data () {
    return {
        loginUrl: '/public/login',
        codeUrl:'/public/verifyCode',
        username:'',
        password:'',
        code:'',
        errorTip:'',
        errorShowing:false
    }
  },
  methods: {
      login: function (item) {
            let vm = this;
            if(this.username ==""){
            vm.showError("用户名不能为空");
            return false;
            }
            if(this.password ==""){
              vm.showError("密码不能为空");
              return false;
            }
            if(this.code ==""||this.code.length!= 4){
              vm.showError("验证码不正确");
              return false;
            }
           let params = qs.stringify({ "loginAccount": this.username,"loginPwd": this.password,"vertifyCode": this.code});
          axios.post(this.loginUrl, params,{
             headers: {
                 'Content-Type': 'application/x-www-form-urlencoded',
             }
              }).then(function (resp) {
                     var resp = resp.data;
                      if(resp.code=="0"){
                          var  id = resp.data.id;
                          var name = resp.data.role.roleName;
                          var loginAccount = resp.data.loginAccount;
                          if(!window.localStorage){
                              alert("浏览器支持localstorage");
                              return false;
                          }else{
                              //主逻辑业务
                              var storage= window.localStorage;
                              storage.setItem("roleName",name);
                              storage.setItem("id",id);
                              storage.setItem("loginAccount",loginAccount);
                          }
                         window.location.href="index.html";

                      }else{
                         vm.showError(resp.msg);
                          vm.changeCode();
                     }
                  }).catch(function (error) {
                     vm.showError(error);
                 });
        },
        changeCode:function(){
           var img =document.getElementById("codeImg");
	       img.src=img.src+"?";
        },
        showError:function(tips){
              this.errorTip =tips;
              this.errorShowing = true;
        },
        hideError:function(){
            this.errorShowing = false;
        }
    },
    watch: {
    // 如果 errorShowing 发生改变，这个函数就会运行
    errorShowing: function () {
      if(this.errorShowing == true){
       let vm = this;
        setTimeout(function () {
           vm.errorShowing = false;
       }, 1500)
      }
    }
  },
    mounted() {
        let doc = document;
        doc.body.addEventListener('keyup', function (ev) {
            if (ev.keyCode == 13) {
                doc.getElementById('login-btn').click();
            }
        })
    }
}
</script>
