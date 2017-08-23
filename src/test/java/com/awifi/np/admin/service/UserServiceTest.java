package com.awifi.np.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.admin.dao.NPAdminRoleMapper;
import com.awifi.np.admin.dao.NPAdminUserMapper;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPAdminUserCriteria;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.exception.BaseException;
import com.awifi.np.admin.service.impl.AdminUserServiceImpl;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年2月3日 下午3:53:45
* 创建作者：王冬冬
* 文件名称：UserService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
@RunWith(PowerMockRunner.class)
public class UserServiceTest {

	  @Mock
	  private NPAdminUserMapper userMapper;

	  @Mock
	  private NPAdminRoleMapper roleMapper;
	  
	  @InjectMocks
	  private AdminUserServiceImpl adminUserService;
	  
	  @Before
	  public void setUp() {
//	        MockitoAnnotations.initMocks(this);
	  }
	  
	  @Test
	  public void testList() throws Exception {
//	        ArrayList<NPService> serviceArrayList = TestDATA.getServiceList();
	        ArrayList<NPAdminUser> userList=new ArrayList<>();
	        userList.add(new NPAdminUser());
	        NPPage page=new NPPage();
	        page.setPage(1);
	        PowerMockito.when(userMapper.listPageUsers(page)).thenReturn(userList);
	        List<NPAdminUser> resultList =  adminUserService.listUsers(page, null, null);
	        Assert.assertEquals(resultList.size(), userList.size());
	        Mockito.verify(userMapper).listPageUsers(page);
	  }
	  
	  @Test
	  public void testList1() throws Exception {
//	        ArrayList<NPService> serviceArrayList = TestDATA.getServiceList();
	        ArrayList<NPAdminUser> userList=new ArrayList<>();
	        userList.add(new NPAdminUser());
	        NPPage page=new NPPage();
	        page.setPage(1);
	        Map map=new HashMap<>();
	        map.put("keyword","%admin%");
	        PowerMockito.when(userMapper.listPageUsers(page)).thenReturn(userList);
	        List<NPAdminUser> resultList =  adminUserService.listUsers(page, "admin", null);
	        Assert.assertEquals(resultList.size(), userList.size());
	        Mockito.verify(userMapper).listPageUsers(page);
	  }
	  
	  
	  @Test
	  public void testAdd() throws Exception {
		    NPAdminUser user= new NPAdminUser();
		    user.setLoginPwd("admin");
		    user.setLoginAccount("admin");
	        PowerMockito.when(userMapper.insertSelective(user)).thenReturn(1);
	        adminUserService.addUser(user);
	        Mockito.verify(userMapper).insertSelective(user);
	  }
	  
	  @Test
	  public void testdelete() throws Exception {
		  String [] ids=new String[]{"1,2"};
		  PowerMockito.when(userMapper.deleteByIds(ids)).thenReturn(1);
	      adminUserService.deteleByIds(ids);
	      Mockito.verify(userMapper).deleteByIds(ids);
	  }
	  
	  @Test
	  public void testupdate() throws Exception {
		  NPAdminUser user= new NPAdminUser();
		  user.setId(11);
		  PowerMockito.when(userMapper.updateByPrimaryKeySelective(user)).thenReturn(1);
	      adminUserService.updateUser(user);
	      Mockito.verify(userMapper).updateByPrimaryKeySelective(user);
	  }
	  
	  @Test
	  public void getUserById() throws Exception{
		  Integer id=1;
		  NPAdminUser user= new NPAdminUser();
		  user.setId(1);
		  PowerMockito.when(userMapper.selectByPrimaryKey(id)).thenReturn(user);
		  adminUserService.getUserById(id);
		  Mockito.verify(userMapper).selectByPrimaryKey(id);
	  }
	  
	  @Test
	  public void login(){
		  
		  String loginAccount="admin";
		  String loginpwd="admin";
		  List<NPAdminUser> list=new ArrayList<NPAdminUser>();
		  NPAdminUser user= new NPAdminUser();
		  user.setId(1);
		  user.setLoginAccount(loginAccount);
		  user.setLoginPwd(loginpwd);
		  list.add(user);
		  NPAdminUserCriteria criteria=new NPAdminUserCriteria();
		  criteria.createCriteria().andLoginAccountEqualTo(loginAccount).andLoginPwdEqualTo(loginpwd);
		  
		  PowerMockito.when(userMapper.selectByExample(criteria)).thenReturn(list);
		  try {
			adminUserService.login(loginAccount, loginpwd);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
}
