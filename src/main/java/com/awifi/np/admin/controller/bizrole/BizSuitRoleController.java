package com.awifi.np.admin.controller.bizrole;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPBizSuitRole;
import com.awifi.np.admin.service.IBizSuitRoleService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 下午2:19:22
 * 创建作者：沈叶峰
 * 文件名称：BizRoleController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RequestMapping("/admin")
@Controller
public class BizSuitRoleController extends BaseController {

	@Autowired
	private IBizSuitRoleService bizSuitRoleService;
	
	/**
	 * 角色与套码绑定
	 * @param roleId
	 * @param suitIdList
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月18日 下午4:00:52
	 */
	@RequestMapping(value="/bizrole/{id}/suits",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject bizRoleSuits(@PathVariable("id") Long roleId, 
			@RequestBody List<NPBizSuitRole> suitIdList){
	      try {
	    	   return  bizSuitRoleService.saveBizRoleSuits(roleId, suitIdList);
	      }catch (Exception e) {
	            e.printStackTrace();
	            return returnError(ECodeException);
	      }
	}
}
