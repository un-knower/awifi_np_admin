/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-22 10:36
 * 创建作者: haoxu
 * 文件名称: copy
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */
function copyArr(arr){
    return arr.map((e)=>{
        if(typeof e === 'object'){
            return Object.assign({},e)
        }else{
            return e
        }
    })
}
