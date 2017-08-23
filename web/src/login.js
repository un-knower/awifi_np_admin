/**
 * 功能: 登录页js入口
 * 修改记录: xx
 * @author 朱学煌
 * @date 2017-01-10 14:08
 */

import Vue from 'vue';
import App from './login.vue';

import './style/comm.scss';
import "./style/login.scss";

const app = new Vue({
    el: '#app',
    render: h => h(App)
});
