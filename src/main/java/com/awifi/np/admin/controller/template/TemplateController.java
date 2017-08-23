package com.awifi.np.admin.controller.template;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.entity.NPTemplate;
import com.awifi.np.admin.exception.BaseException;
import com.awifi.np.admin.service.IServiceService;
import com.awifi.np.admin.service.ITemplateService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月6日 上午10:24:16
 * 创建作者：沈叶峰
 * 文件名称：TemplateController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RequestMapping("/admin")
@Controller
public class TemplateController extends BaseController {
	@Autowired
	private ITemplateService templateService;
	
	@Autowired
	private IServiceService serviceService;
	
	/**
	 * 分页列出模板
	 * @param page
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年2月6日 上午10:53:57
	 */
	@RequestMapping(value="/templates",method=RequestMethod.GET)
	@ResponseBody
	public  JSONObject  listTemplates(NPPage page, @RequestParam(value = "keyword", required = false) String keyword){
		   try {
			     HashMap<String,Object> params = new HashMap<String, Object>();
	              params.put("keyword", keyword);
	              page.setParams(params);
	        	  JSONObject result = new JSONObject();
				  List<NPTemplate> npTemplateList = templateService.listPageTemplates(page);
				  result.put("templateList", npTemplateList);
				  result.put("page", page.getPage());
				  result.put("pageSize", page.getPageSize());
				  result.put("totalRecord", page.getTotalRecord());
				return returnSuccess(result);
	        }catch(BaseException e){
	            return returnError(ECodeException,e.getMessage());
	        }
		    catch (Exception e) {
	            e.printStackTrace();
	            return returnError(ECodeException,e.getMessage());
	        }
	}
	
	/**
	 * 添加一个模板
	 * @param template
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年2月6日 上午10:55:58
	 */
	@RequestMapping(value="/template",method=RequestMethod.POST)
	@ResponseBody
    public  JSONObject  addTemplate(@RequestBody NPTemplate template, HttpServletRequest request){
        try {
          	NPAdminUser adminUser = getAdminUser(request);
        	if(adminUser != null){
        		template.setCreateUserId(adminUser.getId());
        		template.setUpdateUserId(adminUser.getId());
        	}
        	if(template.getServiceCode().isEmpty()){
        	    return returnError("E0000003");
        	}
        	//同步到业务系统
		    List<NPService> services= serviceService.getByServiceCode(template.getServiceCode());
		    if(services==null||services.isEmpty()||services.get(0)==null){
		    	return returnError(ECodeException);
		    }
		    
//		    HttpClientUtil.post("", template);
		    Integer templateId = templateService.addTemplate(template,services.get(0));
		    
			return returnSuccess(templateId);
        }catch(BaseException e){
            return returnError(ECodeException,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException,e.getMessage());
        }
    }
	
	/**
	 * 获取套码
	 * @param templateId
	 * @return
	 * @author 王冬冬  
	 * @date 2017年5月25日 下午3:40:24
	 */
	@RequestMapping(value="/template/{id}",method=RequestMethod.GET)
	@ResponseBody
	  public  JSONObject  getSuit(@PathVariable("id") Integer templateId){
        try {
        	if(templateId == null || templateId < 0){
        		return returnError("E1000001","id");
        	}
		    NPTemplate template = templateService.getTemplate(templateId);
			return returnSuccess(template);
        }catch(BaseException e){
            return returnError(ECodeException,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException,e.getMessage());
        }
    }
	
	/**
	 * 修改一套模板
	 * @param template
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年2月6日 上午11:00:28
	 */
	@RequestMapping(value="/template",method=RequestMethod.PUT)
	@ResponseBody
    public  JSONObject  updateTemplate(@RequestBody NPTemplate template){
        try {
        	//同步到业务系统
		    List<NPService> services= serviceService.getByServiceCode(template.getServiceCode());
		    if(services==null||services.isEmpty()||services.get(0)==null){
		    	
		    	return returnError(ECodeException);
		    }
        	templateService.updateTemplate(template,services.get(0));
        	//同步修改业务系统
//		    HttpClientUtil.post("", template);
			return returnSuccess();
        }catch(BaseException e){
            return returnError(ECodeException,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException,e.getMessage());
        }
    }
	
	/**
	 * 删除模板
	 * @param id
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年2月16日 上午10:28:54
	 */
	@RequestMapping(value="/template/{id}",method=RequestMethod.DELETE)
	@ResponseBody
    public  JSONObject  deleteTemplate(@PathVariable("id") Integer id){
        try {
          	if(id == null ||  id < 0){
        		return returnError("E1000001","id");
        	}
          	templateService.deleteTemplate(id);
        	//同步删除业务系统
            
			return returnSuccess();
        }catch(BaseException e){
            return returnError(ECodeException,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException,e.getMessage());
        }
    }
//	public static void main(String[] args) throws Exception {
//		NPTemplate template=new NPTemplate();
////	    NPTemplate template=new NPTemplate();
//	   	template.setSuitCode("superadmin_v1");
//		template.setTemplateCode("xxxx");
//		template.setServiceCode("S_PUB");
//		System.currentTimeMillis();
//		HashMap<String,Object> map=new HashMap<String,Object>();
//		String timestamp=String.valueOf(System.currentTimeMillis());
//		map.put("timestamp", timestamp);
//		map.put("token", MD5.md5("S_PROJ"+"de65864c22a2e75f79217416c41a5604"+timestamp));
//   	    map.put("serviceCode", template.getServiceCode());
//   	    map.put("suitCode", template.getSuitCode());
//   	    map.put("templateCode", template.getTemplateCode());
//        HttpClientUtil.get("http://192.168.41.77:80/projsrv/am/template",map);
//	}
}
