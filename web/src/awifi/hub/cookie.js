/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-03-08 19:31
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: cookie
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

import cookie from 'cookie';

// 获取cookies对象
function getCookies() {
    return cookie.parse(document.cookie);
}

// access_token为空时，跳转至登录页
// if (!accessToken) {
//     location.href = '/login';
// }

export default {
    getCookies: getCookies(),
    accessToken: getCookies().access_token || '',
}