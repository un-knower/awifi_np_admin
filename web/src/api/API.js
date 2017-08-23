/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-01-16 16:35
 * 创建作者: haoxu
 * 文件名称: API
 * 版本: v1.0
 * 功能: axios统一配置
 * 修改记录: xx
 */
import axios from 'axios'
import qs from 'qs'
let config ={
    timeout: 1000,
    headers: {'Content-Type':'application/x-www-form-urlencoded'}
};
class API {
    interceptors(){
        let instance = axios.interceptors.request;
            return instance;
    }
    get(url,param) {
        let instance = axios.create(config).get(url,param);
        return instance;
    }
    post(url,param) {
        let instance = axios.create(config).post(url, qs.stringify(param));
        return instance;
    }
}
let api = new API;
export default api;

