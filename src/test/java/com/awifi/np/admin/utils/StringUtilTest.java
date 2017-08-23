package com.awifi.np.admin.utils;

import org.junit.Test;

public class StringUtilTest {

	
	
	@Test
	public void test(){
		
		StringUtil.getChineseTextLen("你好");
		StringUtil.getNumberRandomString(2);
		StringUtil.getRandomString(2);
		StringUtil.isEmpty("");
		StringUtil.isNotEmpty("");
		
	}
}
