/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-07 16:40
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: query
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

import querystring from 'querystring';

/**
 * 获取url query键值对Object
 * @returns {Object}
 * @author 朱学煌
 * @date 2017-02-09 16:21
 */
function queryData() {
    let queryObj = querystring.parse(location.search.substr(1));
    return queryObj;
}

/**
 * 过滤param.value===空字符串、null、undefined 键值对
 * @param param参数object
 * @returns {Object} 过滤后的param
 * @author 朱学煌
 * @date 2017-02-09 16:21
 */
function paramFilter(param) {
    // 仅过滤object对象
    if (typeof param === 'object' && param.constructor !== Array) {
        let keys = Object.keys(param).filter(function (k) {
            if (param[k] === '' || !param[k]) {
                return false;
            }
            return true;
        });

        let newParam = {};
        for (let k of keys) {
            newParam[k] = param[k];
        }

        return newParam;
    }
    return param;
}

export default {
    // 返回url.query数据对象
    data: queryData(),

    // 返回url.query参数中access_token值
    accessToken: queryData()['access_token'],

    // 参数值为空字符串、null、undefined过滤掉
    paramFilter: paramFilter,
}