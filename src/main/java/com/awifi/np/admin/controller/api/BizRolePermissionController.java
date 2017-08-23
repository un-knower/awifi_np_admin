/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年4月12日 下午6:17:49
* 创建作者：王冬冬
* 文件名称：BizRolePermissionController.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.admin.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.BizRoleInterfaceServiceImpl;
import com.awifi.np.admin.service.impl.BizRoleServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.utils.StringUtil;

@RestController
@RequestMapping("/externalapi")
public class BizRolePermissionController extends BaseController{
	@Autowired
    private BizRoleServiceImpl bizRoleService;
    @Autowired
    private ServiceServiceImpl serviceService;
    @Autowired
    private BizRoleInterfaceServiceImpl bizRoleInterfaceService;
    
    @RequestMapping(value = "/query/interface", method = RequestMethod.GET)
    public JSONObject queryPermissionsByRole(String access_token,String servicecode,Long roleId){
        NPBizRole npBizRole = null;
        List<NPService> list=null;
        try {
            npBizRole = bizRoleService.getById(roleId);
            if (npBizRole == null) {
                return returnError(Constants.ECodeBadParam, "rid");
            }

            list=serviceService.getByRoleId(roleId);
            if(list==null||list.isEmpty()){
            	return returnError("E1040000");
            }
            for(NPService service:list){
	            if (service == null) {
	                return returnError(Constants.ECodeBadParam, "sid");
	            }
	            if (StringUtil.isEmpty(service.getRolePermissionApi())) {
	                return returnError("E1020001");
	            }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }
        try{
            return bizRoleInterfaceService.getRoleInterfaceRelation(roleId, list);
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("E1020002", servicecode+" "+e.getClass().getName());
        }

    }
}
