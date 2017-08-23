/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-01-16 16:35
 * 创建作者: haoxu
 * 文件名称: config.js
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */
import Qs from 'qs'
export default{
    url: '/route',
    baseURL: 'http://localhost/',
    method:'get',
    params: {
    },
    data: {
    },
    transformRequest: [function (data) {
        //为了避免qs格式化时对内层对象的格式化先把内层的对象转为
        data.CustData = JSON.stringify(data.CustData);
        //由于使用的form-data传数据所以要格式化
        data = Qs.stringify(data);
        return data;
    }],


    transformResponse: [function (data) {
        return data;
    }],

    headers: {'Content-Type':'application/x-www-form-urlencoded'},

    paramsSerializer: function(params) {
        return Qs.stringify(params)
    },

    timeout: 1000,

    withCredentials: false, // default

    responseType: 'json', // default

    onUploadProgress: function (progressEvent) {
        // Do whatever you want with the native progress event
    },

    onDownloadProgress: function (progressEvent) {
        // Do whatever you want with the native progress event
    },

    maxContentLength: 2000,

    validateStatus: function (status) {
        return status >= 200 && status < 300; // default
    },

    maxRedirects: 5 // default
}