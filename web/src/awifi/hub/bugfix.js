/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-22 17:05
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: bugfix
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

import dom from './dom';

/**
 * 修复表格搜索条、表头固定
 * @param _this vue实例对象
 */
function tableFix(_this) {
    return _this.$nextTick(() => {
        // 修复表头固定
        let doc = document;
        let topHeader = dom.$ele('#top-header', doc);
        let searchBar = dom.$ele('#search-bar', doc);
        let tableHeader = dom.$ele('.ivu-table-header', doc)[0];
        let height = topHeader.offsetHeight + searchBar.offsetHeight;
        if (height) {
            tableHeader.style.top = `${height}px`;
            dom.$ele('.table-wrap', doc)[0].style.paddingTop = `${searchBar.offsetHeight + tableHeader.offsetHeight}px`;
        }
    });
}

export default {
    tableFix: tableFix,
}