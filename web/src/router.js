/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期: 2017-01-10 14:11
 * 创建作者： 朱学煌
 * 文件名称： router.js
 * 版本：  v1.0
 * 功能： 请求路由配置
 * 修改记录：
 */

import VueRouter from 'vue-router';

const routes = [
    { path: '/platform/list', component:  require('./components/platform/list.vue') ,name: '平台管理'},
    { path: '/platform/relevant/:id/:name', component:  require('./components/platform/relevant.vue'), name: '平台管理->关联服务' },
    { path: '/service/list', component:  require('./components/service/service-list.vue'), name: '服务管理->服务列表' },
    { path: '/service/interface', component:  require('./components/service/interface-list.vue'), name:'服务管理->接口列表' },
    { path: '/service/menu', component:  require('./components/service/menu-list.vue'),name:'服务管理->服务菜单列表' },
    { path: '/online/list', component: require('./components/online/upload-list.vue'),name:'上线管理->上线列表' },
    { path: '/online/log', component: require('./components/online/upload-log.vue'),name:'上线管理->上线日志' },
    { path: '/online/config/:logid/:sid/:publishStatus', component: require('./components/online/upload-config.vue'),name:'上线管理->配置服务' },
    { path: '/online/test/:logid/:publishStatus', component: require('./components/online/upload-test.vue'), name:'上线管理->测试服务'},
    { path: '/online/review/:logid', component: require('./components/online/upload-review.vue'),name:'上线管理->审核服务' },
    { path: '/online/success/:logid', component: require('./components/online/upload-success.vue'),name:'上线管理->上线服务' },
    { path: '/system/role', component: require('./components/role/system-role.vue'),name:'系统角色管理->系统角色' },
    { path: '/system/role/create/:id', component: require('./components/role/create-role.vue'),name:'系统角色管理->系统角色->创建角色' },
    { path: '/system/account', component: require('./components/role/system-account.vue'),name:'系统角色管理->系统账号' },
    { path: '/information/suit', component: require('./components/information/suit-code.vue'), name:'业务信息管理->套码管理'},
    { path: '/information/template', component: require('./components/information/template.vue'),name:'业务信息管理->模板管理' },
    { path: '/information/role', component: require('./components/information/role.vue'), name:'业务信息管理->角色管理'},
    { path: '/information/createRole/:id', component: require('./components/information/create-role.vue'), name:'业务信息管理->角色管理->创建角色'},
    { path: '/information/account', component: require('./components/information/account.vue'), name:'业务信息管理->账号管理'},
    { path: '/information/createAccount/:id', component: require('./components/information/create-account.vue'), name:'业务信息管理->账号管理->创建账号'},
    { path: '/information/menu/auth/:id', component: require('./components/information/menu-auth.vue'), name:'业务信息管理->角色管理->菜单授权'},
    { path: '/information/interface/auth/:id', component: require('./components/information/interface-auth.vue'), name:'业务信息管理->角色管理->接口授权'},
    { path: '/modify/pwd', component: require('./components/modify-pwd.vue'), name:'修改密码'},
    { path: '/modify/account', component: require('./components/modify-account.vue'), name:'修改账号信息'},
    { path: '/util/transfer', component: require('./components/util/transfer.vue'), name:'系统角色管理->系统账号'},
    { path: '/util/myTransfer', component: require('./components/util/myTransfer.vue'), name:'系统角色管理->系统账号'},
    { path: '/util/tree', component: require('./components/util/tree.vue'), name:'系统角色管理->系统账号'},
    { path: '/', redirect: '/platform/list/' }
];

const router = new VueRouter({
    routes
});

export default router;