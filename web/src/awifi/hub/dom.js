/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-23 11:12
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: dom.js
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

/**
 * 通过name获取id、class DOM对象
 * @param name 元素名称
 * @param doc document
 * @returns {*} DOM对象
 */
function $ele(name, doc) {
    // 参数传值可缓存
    doc = doc || document;
    let prefix = name.substr(0, 1);
    let eleName = name.substr(1);
    // #开头是id
    if (prefix === '#') {
        return doc.getElementById(eleName);
    } else if (prefix === '.') {
        return doc.getElementsByClassName(eleName);
    } else {
        return null;
    }
}

export default {
    $ele: $ele,
}
