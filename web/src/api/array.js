/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-21 12:27
 * 创建作者: haoxu
 * 文件名称: array
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */
Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};