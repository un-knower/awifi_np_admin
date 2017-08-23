package com.awifi.np.admin.utils;

import org.junit.Test;

import com.awifi.np.admin.entity.NPAdminLog;

public class ReflectHelperTest {
	
	
	
	@Test
	public void test(){
		
		
		ReflectHelper.getFieldByFieldName(new NPAdminLog(), "title");
		
		try {
			ReflectHelper.getValueByFieldName(new NPAdminLog(), "title");
			NPAdminLog log=	new NPAdminLog();
			ReflectHelper.setValueByFieldName(log, "title", "hello");
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
