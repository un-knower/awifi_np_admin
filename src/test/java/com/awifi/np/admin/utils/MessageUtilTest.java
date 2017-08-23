package com.awifi.np.admin.utils;

import org.junit.Test;

public class MessageUtilTest {

	@Test
	public void test(){
		
		MessageUtil.commonResult("E0000002","hello", "dd");
		MessageUtil.getMessage("E0000002");
		MessageUtil.getMessage("E0000002", null);
		MessageUtil.returnError("E0000002");
		MessageUtil.returnError("E0000002", "");
		MessageUtil.returnResult(null);
		MessageUtil.returnSuccess();
		MessageUtil.returnSuccess("success");
	}
}
