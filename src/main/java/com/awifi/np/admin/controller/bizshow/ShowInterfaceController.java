package com.awifi.np.admin.controller.bizshow;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPTemplate;
import com.awifi.np.admin.service.ITemplateService;
import com.awifi.np.admin.utils.redis.JedisUtil;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月24日 上午10:26:51
* 创建作者：王冬冬
* 文件名称：ShowInterfaceController.java
* 版本：  v1.0
* 功能：显示接口
* 修改记录：
*/
@Controller
@RequestMapping("/externalapi")
public class ShowInterfaceController extends BaseController{
	
	@Autowired
	private ITemplateService templateService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/interface/view")
	@ResponseBody
	public JSONObject show(HttpServletRequest request,HttpServletResponse response,String access_token,String servicecode,String suitcode,String templatecode){
		
		NPTemplate template=null;
		Map<String, Object> userInfo=null;
//		String b=null;
		if(StringUtils.isEmpty(servicecode)){
			return returnError("E0000003");
		}
		if(StringUtils.isEmpty(templatecode)){
			return returnError("E0000007");
		}
		
		if(!StringUtils.isEmpty(suitcode)){//参数直接传过过来
			template=getTemplate(servicecode,suitcode,templatecode);
			if(template==null){//为空表示模板未注册
				return returnError("E1008002");
			}else{
				return returnSuccess();
			}
		}
		
		//获取value
		String value=JedisUtil.getValue(access_token);
		if(value!=null){
			JedisUtil.setex(access_token,Constants.TOKEN_EXPIRE_TIME,value);//更新redis
			Map<String, Object> resultMap = JSON.parseObject(value, Map.class);//转成map
			if(resultMap==null){
				return returnError("E1009004");
			}
			String suitCode = (String)resultMap.get("suitCode");//获取suitCode数据
			if(suitCode!=null){
				template=getTemplate(servicecode,suitCode,templatecode);
			}else{
				userInfo = (Map<String,Object>)resultMap.get("userInfo");//获取userInfo数据
				if(userInfo!=null){
				  suitCode = (String)userInfo.get("suitCode");
				  if(suitCode!=null){
					  template=getTemplate(servicecode,suitCode,templatecode);
				  }else{
					  return returnError("E0000005");
				  }
				}else{
					 return returnError("E0000005");
				}
			}
		}else{
			return returnError("E0000001");
		}
		if(template==null){//为空表示模板未注册
	    	return returnError("E1008002");
	    }
		return returnSuccess(userInfo);
	}

	/**
	 * 查询模板信息
	 * @param servicecode
	 * @param suitcode
	 * @param templatecode
	 * @return
	 * @author 王冬冬  
	 * @date 2017年4月11日 上午11:23:11
	 */
	private NPTemplate getTemplate(String servicecode, String suitcode, String templatecode) {
		String b=null;
		String key=new StringBuilder().append(Constants.TEMPLATE).append(servicecode).append("-").append(suitcode).append("-").append(templatecode).toString();
		if((b=JedisUtil.getValue(key))!=null){
            logger.debug("method show() get template data from redis:"+"key("+key+")");
			return JSONObject.parseObject(b,NPTemplate.class);
		}else{
			NPTemplate template=templateService.selectByParam(servicecode,suitcode,templatecode);
			logger.debug("method show() get template data from database:"+JSONObject.toJSONString(template));
		    if(template!=null){
		    	JedisUtil.setex(key,Constants.TOKEN_EXPIRE_TIME,JSONObject.toJSONString(template));
		    }
		    return template;
		}
	}
}
