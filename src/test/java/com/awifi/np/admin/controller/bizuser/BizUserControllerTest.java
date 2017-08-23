package com.awifi.np.admin.controller.bizuser;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dbc.entity.CenterPubArea;
import com.awifi.np.admin.dbc.utils.AreaUtil;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPBizUser;
import com.awifi.np.admin.entity.NPBizUserRole;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.service.impl.BizRoleServiceImpl;
import com.awifi.np.admin.service.impl.BizUserRoleServiceImpl;
import com.awifi.np.admin.service.impl.BizUserServiceImpl;
import com.awifi.np.admin.tolls4test.TestDATA;



/**
 * 版权所有： 爱WiFi无线运营中心 创建日期：2017/2/17 创建作者：卢朱娜 文件名称：BizUserControllerTest.java 版本：
 * v1.0 功能： 修改记录：
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(AreaUtil.class)
public class BizUserControllerTest {
	private String urlprefix = TestDATA.bizUserUrl;
	@Mock
	private BizRoleServiceImpl bizRoleService;
	@Mock
	private BizUserServiceImpl bizUserService;
	@Mock
	private BizUserRoleServiceImpl bizUserRoleService;

	private MockHttpServletRequest request;
	@InjectMocks
	private BizUserController controller;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(AreaUtil.class);
	}

    @Test
	public void testList() throws Exception {
		NPPage page = new NPPage();
		page.setPage(2);
		List<Long> idList=new ArrayList<Long>();
		when(bizUserService.listPageBizUserId(anyObject(),anyString(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(null);

	   JSONObject result=controller.listBizUser(page, "", 1L, 1L, 1L, 1L);
       Assert.assertEquals(result.get("code"), "E1000004");
       
       when(bizUserService.listPageBizUserId(anyObject(),anyString(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(idList);

       JSONObject result1=controller.listBizUser(page, "", 1L, 1L, 1L, 1L);
       Assert.assertEquals(result1.get("code"), "0");
       
       idList.add(1L);
       List<NPBizUser> bizUserList = new ArrayList<NPBizUser>();
       List<NPBizUserRole> bizUserRoleList=new ArrayList<NPBizUserRole>();        
       when(bizUserService.listByIds(idList)).thenReturn(bizUserList);
       //获取角色
       when(bizUserRoleService.listByUserIds(idList)).thenReturn(bizUserRoleList);
       when(bizUserService.listPageBizUserId(anyObject(),anyString(),anyObject(),anyObject(),anyObject(),anyObject())).thenReturn(idList);
       JSONObject result2=controller.listBizUser(page, "", 1L, 1L, 1L, 1L);
       Assert.assertEquals(result2.get("code"), "0");
	}

	@Test
	public void getBizUser() throws Exception {
		NPBizUser bizUser = new NPBizUser();
		bizUser.setProvinceId(1L);
		bizUser.setCityId(1L);
		bizUser.setAreaId(1L);
		
		when(bizUserService.getById(11L)).thenReturn(null);
		JSONObject result=controller.getBizUser(11L);
		Assert.assertEquals(result.get("code"),Constants.ECodeBadParam);
		
		String longname="";
		when(AreaUtil.getLongName(bizUser.getProvinceId(),bizUser.getCityId(),bizUser.getAreaId())).thenReturn(longname);
		when(bizUserService.getById(11L)).thenReturn(bizUser);
		List<NPBizRole> roleList = new ArrayList<NPBizRole>();
		when(bizUserRoleService.listRoleByUserId(11L)).thenReturn(roleList);
		result=controller.getBizUser(11L);
        Assert.assertEquals(result.get("code"),"0");

		when(bizUserRoleService.listRoleByUserId(11L)).thenThrow(Exception.class);
		result=controller.getBizUser(11L);
        Assert.assertEquals(result.get("code"),Constants.ECodeException);
		
	}
//
	@Test
	public void testaddBizUser() throws Exception {

		NPBizUser npBizUser = new NPBizUser();
		
	    JSONObject result=controller.addBizUser(npBizUser, request);
	    Assert.assertEquals(result.get("code"),"E1000001");
		
		List<Long> roleIdList = new ArrayList<Long>();
		roleIdList.add(1L);
		npBizUser.setProvinceId(1L);
		npBizUser.setCityId(1L);
		npBizUser.setAreaId(1L);
		npBizUser.setRoleIdList(roleIdList);
		npBizUser.setUserName("dd");
		npBizUser.setContactWay("phone");
		
		CenterPubArea area=new CenterPubArea();
		when(AreaUtil.getByIdAndPid(anyObject(),anyObject())).thenReturn(area);
		result=controller.addBizUser(npBizUser, request);
        Assert.assertEquals(result.get("code"),Constants.ECodeBadParam);
        
        when(bizRoleService.countByIdList(anyList())).thenReturn(1);
        
        when(bizUserService.addBizUser(anyObject(),anyObject())).thenReturn("0");
        result=controller.addBizUser(npBizUser, request);
        Assert.assertEquals(result.get("code"),Constants.ECodeSuccess);
        
        when(bizUserService.addBizUser(anyObject(),anyObject())).thenReturn(Constants.ECodeException);
        result=controller.addBizUser(npBizUser, request);
        Assert.assertEquals(result.get("code"),Constants.ECodeException);
	}
//
	@Test
	public void testupdateBizUser() throws Exception {

		NPBizUser npBizUser = new NPBizUser();
		String requestBody = JSONObject.toJSONString(npBizUser);
		JSONObject result=controller.updateBizUser(npBizUser, request);
		Assert.assertEquals(result.get("code"),"E1000001");

		npBizUser.setId(1L);
	    NPAdminUser adminUser = new NPAdminUser();
	    npBizUser.setResetPassword(true);
	    requestBody = JSONObject.toJSONString(npBizUser);
	    Mockito.doNothing().when(bizUserService).resetPassword(npBizUser, adminUser);
	    JSONObject result1=controller.updateBizUser(npBizUser, request);
        Assert.assertEquals(result1.get("code"),"0"); 
		 
		npBizUser.setId(1L);
		requestBody = JSONObject.toJSONString(npBizUser);
		npBizUser.setResetPassword(false);
	
		when(bizUserService.getById(npBizUser.getId())).thenReturn(null);
		result1=controller.updateBizUser(npBizUser, request);
	    Assert.assertEquals(result1.get("code"),Constants.ECodeBadParam); 
	    
	    List<Long> roleIdList = new ArrayList<Long>();
        roleIdList.add(1L);
        npBizUser.setProvinceId(1L);
        npBizUser.setCityId(1L);
        npBizUser.setAreaId(1L);
        npBizUser.setRoleIdList(roleIdList);
        npBizUser.setUserName("dd");
        npBizUser.setContactWay("phone");
        
        CenterPubArea area=new CenterPubArea();
        when(AreaUtil.getByIdAndPid(anyObject(),anyObject())).thenReturn(area);
        when(bizUserService.getById(npBizUser.getId())).thenReturn(npBizUser);
        when(bizUserService.updateBizUser(anyObject(),anyObject())).thenReturn("0");
        when(bizRoleService.countByIdList(anyList())).thenReturn(1);
        result1=controller.updateBizUser(npBizUser, request);
        Assert.assertEquals(result1.get("code"),"0"); 
        
        when(bizUserService.updateBizUser(anyObject(),anyObject())).thenReturn(Constants.ECodeException);
        result1=controller.updateBizUser(npBizUser, request);
        Assert.assertEquals(result1.get("code"),Constants.ECodeException); 
	}

	@Test
	public void testdeleteBizUser2() throws Exception {
	    JSONObject result=controller.deleteBizUser(1L);
	    Assert.assertEquals(result.get("code"),"0");
		Mockito.doThrow(Exception.class).when(bizUserService).deleteById(11L);
		result=controller.deleteBizUser(11L);
	    Assert.assertEquals(result.get("code"),Constants.ECodeException);
	}
}
