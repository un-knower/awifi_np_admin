/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-03-03 11:01
 * 创建作者: 朱学煌 17705818601@189.cn
 * 文件名称: validate
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 * @doc https://github.com/yiminghe/async-validator
 */

//是否特通
const _notTT = (userName) => {
    return !/^(sc|wic).*/.test(userName);
};

// 验证码校验
function validateAuthcode(rule, value, callback, source, options) {
    if (value === '') {
        callback(new Error('请输入密码'));
    } else if (!/^\d{4}$/.test(value)) {
        callback(new Error('验证码格式为4位数字'));
    } else {
        callback();
    }
}

// 密码校验
function validatePass(rule, value, callback, source, options) {
    if (value === '') {
        callback(new Error('请输入密码'));
    } else if (!/^[0-9a-zA-Z_@\$\-]{6,50}$/.test(value)) {
        callback(new Error('密码格式为6-50位字符，包含字母、数字、下划线、连接符、@、$'));
    } else {
        callback();
    }
}

// 再次输入密码校验
function validatePassCheck(rule, value, callback, source, options) {
    if (value === '') {
        callback(new Error('请再次输入密码'));
    } else if (!/^[0-9a-zA-Z_@\$\-]{6,50}$/.test(value)) {
        callback(new Error('密码格式为6-50位字符，包含字母、数字、下划线、连接符、@、$'));
    } else if (value !== source.newPassword) {
        callback(new Error('两次输入密码不一致!'));
    } else {
        callback();
    }
}

// 用户名校验
function validateUserName(rule, value, callback) {
    //不允许为空，正则校验[1-50位字符，包括字母、数字、下划线、连接符]
    if (value === '') {
        callback(new Error('请输入用户名'));
    } else if (!/^[0-9a-zA-Z\-\_]{1,50}$/.test(value)) {
        callback(new Error('用户名格式为1-50位字符，包括字母、数字、下划线、连接符'));
    } else {
        callback();
    }
}

// 密码校验
function validatePassWord(rule, value, callback) {
    //不允许为空正则校验[1-50位字符，包含字母、数字、下划线、连接符、@、$]
    if (value === '') {
        callback(new Error('请输入密码'));
    } else if (!/^[0-9a-zA-Z_@\$\-]{1,50}$/.test(value)) {
        callback(new Error('密码格式为1-50位字符，包含字母、数字、下划线、连接符、@、$'));
    } else {
        callback();
    }
}

// 手机号校验
function validateCellphone(rule, value = '', callback, source) {
    //当userInfoType==1且不为特通时，不允许为空，
    // 其它情况，允许为空正则校验[1开头的11位符合手机号码规则的数字]
    if (_notTT(source.userName) && source.userInfoType === 1 && value === '') {
        callback(new Error('请输入手机号'));
    } else if (value !== '' && !/^(1[0-9]{10})?$/.test(value)) {
        callback(new Error('手机号格式为11位以1开头符合手机号码规则的数字'));
    } else {
        callback();
    }
}

//匹配规则中的手机号校验
function validateMatchCellphone(rule, value, callback, source) {
    // 手机号或号段，不允许为空，当matchRule==1时，正则校验[1开头的11位符合手机号码规则的数字]当matchRule==2时，正则校验[1-10位数字]
    if (value === '') {
        callback(new Error('请输入手机号'));
    } else if (source.matchRule && source.matchRule === 1 && !/^(1[0-9]{10})?$/.test(value)) {
        callback(new Error('手机号格式为11位以1开头符合手机号码规则的数字'));
    } else if (source.matchRule && source.matchRule === 2 && !/^(1[0-9]{0,9})?$/.test(value)) {
        callback(new Error('号段格式为10位以1开头符合号段规则的数字'));
    } else {
        callback();
    }
}

// 护照校验
function validatePassport(rule, value = '', callback, source) {
    //当userInfoType==2且不为特通时，不允许为空，其它情况，允许为空
    //正则校验[1-20位字母、数字]
    if (_notTT(source.userName) && source.userInfoType === 2 && value === '') {
        callback(new Error('请输入护照'));
    } else if (value !== '' && !/^[0-9a-zA-Z]{1,20}$/.test(value)) {
        callback(new Error('护照格式为1-20位字母、数字'));
    } else {
        callback();
    }
}

// 身份证校验
function validateIdentityCard(rule, value = '', callback, source) {
    // 当userInfoType==3且不为特通时，不允许为空，其它情况，允许为空
    // 正则校验[18位数字或17位数字最后一位字母X]
    if (_notTT(source.userName) && source.userInfoType === 3 && value === '') {
        callback(new Error('请输入身份证'));
    } else if (value !== '' && !/^[0-9]{17}([0-9]|X){1}$/.test(value)) {
        callback(new Error('身份证格式为18位数字或17位数字最后一位字母X'));
    } else {
        callback();
    }
}

// 姓名校验
function validateRealName(rule, value = '', callback) {
    // 允许为空，正则校验[1-20位字符，包括字母、汉字]
    if (value !== '' && !/^[a-zA-Z\u4e00-\u9fa5]{1,20}$/.test(value)) {
        callback(new Error('姓名格式为1-20位字符，包括字母、汉字'));
    } else {
        callback();
    }
}

//部门校验
function validateDeptName(rule, value = '', callback) {
    // 允许为空，正则校验[1-20位汉字、字母、数字]
    if (value !== '' && !/^[0-9a-zA-Z\u4e00-\u9fa5]{1,20}$/.test(value)) {
        callback(new Error('部门格式为1-20位汉字、字母、数字'));
    } else {
        callback();
    }
}

//商户选择校验
function validateMerchantInfo(rule, value, callback) {
    //不允许为空
    if (value === '') {
        callback(new Error('请选择商户'));
    } else {
        callback();
    }
}

//项目选择校验
function validateProject(rule, value, callback) {
    //不允许为空
    if (value === '') {
        callback(new Error('请选择项目'));
    } else {
        callback();
    }
}

//帐号校验
function validateAccount(rule, value = '', callback) {
    //不允许为空
    if (value === '') {
        callback(new Error('请输入帐号'));
    } else if (!/^[0-9a-zA-Z\_]{4,20}$/.test(value)) {
        callback(new Error('帐号格式为4-20位字母、下划线、数字'));
    } else {
        callback();
    }
}

//商户名称校验
function validateMerchantName(rule, value = '', callback) {
    if (value === '') {
        callback(new Error('请输入商户名称'));
    } else if (!/^[0-9a-zA-Z\u4e00-\u9fa5_\(\),\.（），。@]{1,50}$/.test(value)) {
        callback(new Error('商户名称格式为1-50位字符可由中英文、数字及“_”、()、（）、,、，、.、。、@组成'));
    } else {
        callback();
    }
}

// 角色必填
function validateRole(rule, value, callback, source) {
    //不允许为空
    if (source.roleIds === '') {
        callback(new Error('请选择角色'));
    } else {
        callback();
    }
}

// 行业必填
function validateIndusrty(rule, value, callback, source) {
    //不允许为空
    if (source.industryCode === '') {
        callback(new Error('请选择行业'));
    } else {
        callback();
    }
}

// 省份必填
function validateLocation(rule, value, callback, source) {
    //不允许为空
    if (source.provinceId === '') {
        callback(new Error('请选择省份'));
    } else {
        callback();
    }
}

//省市区必选
function validateAllLocation(rule, value, callback, source) {
    //不允许为空
    if (source.provinceId === '' || source.cityId === '' || source.areaId === '') {
        callback(new Error('省市区为必选项'));
    } else {
        callback();
    }
}

//地址校验
function validateAddress(rule, value = '', callback) {
    if (value !== '' && !/^[0-9a-zA-Z\_\u4e00-\u9fa5]{1,50}$/.test(value)) {
        callback(new Error('地址格式为1-50位汉字、字母、下划线、数字'));
    } else {
        callback();
    }
}

//地址必填
function validateAllAddress(rule, value = '', callback) {
    if (value === '') {
        callback(new Error('请填写详细地址'));
    } else if (value !== '' && !/^[0-9a-zA-Z\_\u4e00-\u9fa5]{1,50}$/.test(value)) {
        callback(new Error('地址格式为1-50位汉字、字母、下划线、数字'));
    } else {
        callback();
    }
}

//备注校验
function validateRemark(rule, value = '', callback) {
    if (value !== '' && !/^[0-9a-zA-Z\_\u4e00-\u9fa5]{1,50}$/.test(value)) {
        callback(new Error('备注格式为1-50位汉字、字母、下划线、数字'));
    } else {
        callback();
    }
}

//联系人
function validateContact(rule, value = '', callback) {
    if (value !== '' && !/^[a-zA-Z\u4e00-\u9fa5]{0,20}$/.test(value)) {
        callback(new Error('联系人格式为0-20位汉字、字母，不含特殊字符'));
    } else {
        callback();
    }
}

// 联系方式
function validateContactWay(rule, value = '', callback) {
    if (value !== '' && !/^(1[3|4|5|7|8][0-9][0-9]{8})?$/.test(value)) {
        callback(new Error('联系方式格式为11位以1开头符合手机号码规则的数字'));
    } else {
        callback();
    }
}

// 邮箱 {可选}
function validateEmail(rule, value = '', callback) {
    if (value && value !== '' && !/^[a-z0-9_\.]{1,}@[a-z0-9]{1,}([-\.]{1}[a-z0-9]{1,}){0,}[\.]{1}[a-z]{1,4}$/.test(value)) {
        callback(new Error('邮箱格式不正确'));
    } else {
        callback();
    }
}

module.exports = {
    validateAuthcode,
    validatePass,
    validatePassCheck,
    validateUserName,
    validatePassWord,
    validateCellphone,
    validateMatchCellphone,
    validatePassport,
    validateIdentityCard,
    validateRealName,
    validateDeptName,
    validateMerchantInfo,
    validateProject,
    validateAccount,
    validateMerchantName,
    validateRole,
    validateIndusrty,
    validateLocation,
    validateAllLocation,
    validateAddress,
    validateAllAddress,
    validateRemark,
    validateContact,
    validateContactWay,
    validateEmail,
};
