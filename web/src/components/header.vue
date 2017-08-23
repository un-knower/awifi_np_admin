/**
* 功能: 公共头文件
* 修改记录: xx
* @author 朱学煌
* @date 2017-01-10 14:10
*/

<template>
    <div id="top-header">
        <div class="breadcrumb-wrap">
            当前位置:&nbsp;<span class="map">{{headerRoute}}</span>
        </div>

        <div id="head-member">
            <ul>
                <li><img alt="用户头像" src="../image/akamaru.jpg" width="80" height="80"></li>
                <transition name="slide-up">
                    <li @mouseover="menuShow" @mouseout="menuHide" class="member-menu-wrap"
                        :class="{'arrow-down-hover': isHover}">
                        <p v-if="userName">{{ userName }}
                            <a-icon type="ios-arrow-down"></a-icon>
                        </p>
                        <p v-else>superadmin
                            <a-icon type="ios-arrow-down"></a-icon>
                        </p>
                        <div v-show="memberMenu" class="member-menu">
                            <div>
                                <router-link :to="{path:'/modify/account', query:{id: id}}">
                                    <a-icon type="person"></a-icon>
                                    账号管理
                                </router-link>
                            </div>
                            <div>
                                <router-link :to="{path:'/modify/pwd', query:{id: id}}">
                                    <a-icon type="edit"></a-icon>
                                    修改密码
                                </router-link>
                            </div>
                            <div><a href="./login.html" @click="loginOut">
                                <a-icon type="power"></a-icon>
                                退出系统</a></div>
                        </div>
                    </li>
                </transition>
            </ul>
        </div>
    </div>
</template>

<script type="text/javascript">
    import axios from 'axios';
    import router from '../router';
    export default{
        name: "Header",
        props: {
            headerRoute: ''
        },
        data(){
            return {
                userName: '',
                memberMenu: false,
                isHover: false,
                id: ''
            }
        },
        created: function () {
            var storage = window.localStorage;
            this.userName = storage.getItem("loginAccount");
            this.id = storage.getItem("id");
        },
        methods: {
            menuHide() {
                this.memberMenu = false;
                this.isHover = false;
            },
            menuShow() {
                this.memberMenu = true;
                this.isHover = true;
            },
            delCookie(){
                var myDate = new Date();
                myDate.setTime(-1000);//设置时间
                var data = document.cookie;
                var dataArray = data.split("; ");
                for (var i = 0; i < dataArray.length; i++) {
                    var varName = dataArray[i].split("=");
                    document.cookie = varName[0] + "=''; expires=" + myDate.toGMTString();
                }
            },
            loginOut(){
                var storage = window.localStorage;
                storage.clear();
                this.delCookie();
                var url = '/admin/index/logout';
                axios.get(url).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == 0) {
                        window.location.href = "./login.html";
                    } else {
                        console.log("err=>", error);
                    }
                }).catch(function (error) {
                    console.log("err=>", error);
                });
            }
        },
        components: {}
    }
</script>
