package com.awifi.np.admin.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月9日 下午7:42:37
 * 创建作者：沈叶峰
 * 文件名称：MD5Test.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class MD5Test {
    @Before
    public void before() {
//        ConfUtil = new ConfUtil();
        this.testMd5Ok();
    }
    
    /**
     * 测试md5方法
     * @author 沈叶峰 
     * @date 2017年1月9日 下午7:59:45
     */
    @Test
    public void testMd5Ok(){
        String trueMd5str = "e10adc3949ba59abbe56e057f20f883e";
        String testMd5Str = MD5.md5("123456");
        Assert.assertEquals(testMd5Str, trueMd5str);
    }
}
