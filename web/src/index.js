/**
 * 功能: 首页js入口
 * 修改记录: xx
 * @author 朱学煌
 * @date 2017-01-10 14:09
 */

import Vue from 'vue';
import VueRouter from 'vue-router';
import App from './index.vue';
import router from './router';

Vue.use(VueRouter);

import './style/index.scss';

const app = new Vue({
    router,
    el: '#app',
    render: h => h(App)
});
