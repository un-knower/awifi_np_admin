/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-28 17:19
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: notice
 * 版本: v1.0
 * 功能: 右侧提示框
 * 修改记录: xx
 */

import Vue from 'vue';
import AwifiUI from 'awifiui';
Vue.use(AwifiUI);

/**
 *
 * @param type {String} [info, success, warning, error]
 * @param data {Object} {code:'xx', msg: 'xx'}
 * @private
 */
function _notice(_this, type, data) {
    return new Vue({
        created: function () {
            _this.$Notice[type]({
                // key: 'notice-one',
                title: data.code,
                desc: data.msg,
            });
        }
    });
}

export default {
    // 提醒
    info(_this, data) {
        return _notice(_this, 'info', data);
    },
    // 成功
    success(_this, data) {
        return _notice(_this, 'success', data);
    },
    // 警告
    warning(_this, data) {
        return _notice(_this, 'warning', data);
    },
    // 错误
    error(_this, data) {
        return _notice(_this, 'error', data);
    }
}
