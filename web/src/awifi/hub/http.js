/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-07 14:17
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: http
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

import querystring from 'querystring';
import axios from 'axios';
import query from './query';
import cookies from './cookie';

// 获取cookies对象
let accessToken = cookies.accessToken;

// access_token为空时，跳转至登录页
if (!accessToken) {
    top.location.href = '/login';
}

/**
 *
 * @param reqUrl 请求url
 * @param params 传参数值
 * @returns {string} 过滤后url和参数字符
 */
function reqUrlParam(reqUrl, params) {
    if (params) {
        let reqParam = '';
        // 过滤value=空字符串、null、undefined 键值对
        let configParams = query.paramFilter(params);
        reqParam = querystring.stringify(configParams);
        reqUrl += (reqParam ? `&${reqParam}` : reqParam);
    }

    return reqUrl;
}

/**
 * 按钮loading
 * @param _this
 */
function loading(_this) {
    // 请求中
    if (_this.hasOwnProperty('loading')) {
        _this.loading = false;
    }

}

/**
 * 操作完成后返回上一页
 * @param _this
 */
function goback(_this) {
    // 是否返回
    if (_this.hasOwnProperty('goback')) {
        if (_this.goback) {
            setTimeout(function () {
                history.back();
            }, 0);
        }
    }
}

/**
 * 检验access_token是否有效
 * @param respData {Object} 接口返回值
 * @param _this {vm} vue实例对象
 * @returns {boolean} 有效为真
 */
function checkAccessToken(respData, _this) {
    if (respData.code === 'E0000001') {
        top.location.href = '/login';
        return false;
    } else if (respData.code != '0') {
        // 错误结束进度条
        _this.$Notice.error({
            title: respData.code,
            desc: respData.msg
        });
        // 请求中恢复
        loading(_this);
        return false;
    }
    return true;
}

/**
 * http GET 请求接口数据
 * @param url: string 请求url地址 如 /pubsrv/industrys/
 * @param config?: object={} 配置项 如 { params： {name: value}, _this: vue.$this, _key: vue.$data.keyName} }
 */
function get(url, config = {params: null, _this: null, _key: null, loading: false}) {
    // params 传参对象
    // _this vue实例对象
    // _key vue实例data.key名称
    // loading 是否加载动画 view页面请求应用
    let {params, _this, _key, loading = false} = config;

    // 开启进度条
    if (loading) {
        _this.$Loading.start();
    }

    let reqUrl = `${url}?access_token=${accessToken}`;
    reqUrl = reqUrlParam(reqUrl, params);

    axios.get(reqUrl).then(function (resp) {

        let respData = resp.data;

        if (!checkAccessToken(respData, _this)) {
            return;
        }

        if (respData.code === '0') {
            _this[_key] = respData.data || {};
        }

        // 结束进度条
        if (loading) {
            _this.$Loading.finish();
        }
    }).catch(function (err) {
        // 错误结束进度条
        if (loading) {
            _this.$Loading.error();
        }
    });
}

/**
 * http GET 请求Menu接口数据
 * @param url: string 请求url地址 如 /pubsrv/industrys/
 * @param config?: object={} 配置项 如 { params： {name: value}, _this: vue.$this, _key: vue.$data.keyName} }
 */
function menu (url, config = {_this: null, _key: null, _index: -1}) {
    // _this vue实例对象
    // _key vue实例data.key名称
    // _index vue实例data.key数组下标
    let {_this, _key, _index = -1} = config;

    let reqUrl = `${url}?access_token=${accessToken}`;

    axios.get(reqUrl).then(function (resp) {
        let respData = resp.data;

        if (!checkAccessToken(respData, _this)) {
            return;
        }

        if (respData.code === '0') {
            if (_index === -1) {
                _this[_key] = _this[_key].concat(resp.data.data);
            } else {
                _this[_key][_index].subMenus = resp.data.data || [];
                _this[_key][_index].fetch = true;
                _this[_key][_index].open = true;
                _this.current = _this[_key][_index];
                // 强制刷新menus值
                _this[_key] = _this[_key].splice(0);
            }
        }
    }).catch(function (err) {

    });
}

/**
 * http GET 列表请求接口数据
 * @param url: string 请求url地址 如 /pubsrv/industrys/
 * @param config?: object={} 配置项 如 { params： {name: value}, _this: vue.$this, _tblData: vue.$data.keyName} }
 */
function list (url, config = {params: null, _this: null, _tblData: 'tblData', _totalRecord: 'totalRecord'}) {
    // params 传参值
    // _this vue实例对象
    // _tblData table数据键名
    // _totalRecord 分页总数量键名
    let {params, _this, _tblData, _totalRecord} = config;
    // 开启进度条
    _this.$Loading.start();

    let reqUrl = `${url}?access_token=${accessToken}`;
    reqUrl = reqUrlParam(reqUrl, params);

    axios.get(reqUrl).then(function (resp) {
        let respData = resp.data;

        if (!checkAccessToken(respData, _this)) {
            _this[_tblData] = [];
            _this[_totalRecord] = 0;
            return;
        }

        if (respData.code === '0') {
            _this[_tblData] = respData.data.records;
            _this[_totalRecord] = respData.data.totalRecord;
        }
        // 结束进度条
        _this.$Loading.finish();

        // 表格请求数据完成
        _this['tblLoading'] = false;
    }).catch(function (err) {
        _this[_tblData] = [];
        _this[_totalRecord] = 0;
    });
}

/**
 * http POST 请求接口数据
 * @param url: string 请求目标url
 * @param data?: {} 请求数据对象
 * @param config?: {} 配置项对象
 */
function post (url, data = null, config = {params: null, _this: null, msg: '添加成功'}, cb) {
    // params 传参对象
    // _this vue实例对象
    // msg 添加成功提示信息
    let {params, _this, msg} = config;

    let reqUrl = `${url}?access_token=${accessToken}`;
    reqUrl = reqUrlParam(reqUrl, params);

    axios.post(reqUrl, query.paramFilter(data)).then(function (resp) {
        let respData = resp.data;

        if (!checkAccessToken(respData, _this)) {
            return;
        }

        // 判断自定义回调方法
        if (cb && typeof cb === 'function') {
            cb(respData, _this);
        } else {
            if (respData.code === '0') {
                _this.$Notice.success({
                    title: msg
                });
                // 返回
                goback(_this);
            }
            // 请求中
            loading(_this);
        }
    }).catch(function (err) {

    });
}

/**
 * http PUT 请求接口数据
 * @param url: string 请求目标url
 * @param data?: {} 请求数据对象
 * @param config?: {} 配置项对象
 */
function put(url, data = null, config = {params: null, _this: null, msg: '编辑成功'}, cb) {
    // params 传参对象
    // _this vue实例对象
    // msg 编辑成功提示信息
    let {params, _this, msg} = config;

    let reqUrl = `${url}?access_token=${accessToken}`;
    reqUrl = reqUrlParam(reqUrl, params);
   
    axios.put(reqUrl, query.paramFilter(data)).then(function (resp) {
        let respData = resp.data;

        if (!checkAccessToken(respData, _this)) {
            return;
        }

        // 判断自定义回调方法
        if (cb && typeof cb === 'function') {
            cb(respData, _this);
        } else {
            if (respData.code === '0') {
                _this.$Notice.success({
                    title: msg
                });
                // 返回
                goback(_this);
            }
            // 请求中
            loading(_this);
        }        
    }).catch(function (err) {

    });
}

/**
 * http DELETE 请求接口数据
 * @param url: string 请求目标url
 * @param config?: {} 配置项对象
 */
function _delete (url, config = {params: null, _this: null, msg: '删除成功', time: 0}, cb) {
    // params 传参对象
    // _this vue实例对象
    // msg 删除成功提示信息
    // time 删除成功几秒后刷新当前页
    let {params, _this, msg, time} = config;

    let reqUrl = `${url}?access_token=${accessToken}`;
    reqUrl = reqUrlParam(reqUrl, params);

    axios.delete(reqUrl).then(function (resp) {
        let respData = resp.data;

        if (!checkAccessToken(respData, _this)) {
            return;
        }

        if (cb && typeof cb === 'function') {
            cb(respData);
        } else {
            if (respData.code === '0') {
                _this.$Notice.success({
                    title: msg
                });
                if (time > 0 && _this.hasOwnProperty('search')) {
                    setTimeout(function () {
                        _this.search();
                    }, time * 1000);
                }
            }
        }
    }).catch(function (err) {

    });
}

export default {
    // 过滤请求参数中空值
    reqUrlParam,
    get,
    menu,
    list,
    post,
    put,
    delete: _delete,
}
