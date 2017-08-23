package com.awifi.np.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPServiceMapper;
import com.awifi.np.admin.dao.NPTemplateMapper;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.entity.NPServiceCriteria;
import com.awifi.np.admin.entity.NPTemplate;
import com.awifi.np.admin.entity.NPTemplateCriteria;
import com.awifi.np.admin.exception.BaseException;
import com.awifi.np.admin.service.ITemplateService;
import com.awifi.np.admin.utils.HttpClientUtil;
import com.awifi.np.admin.utils.MD5;
import com.awifi.np.admin.utils.redis.JedisUtil;


/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月24日 上午11:04:22
* 创建作者：王冬冬
* 文件名称：TemplateServiceImpl.java
* 版本：  v1.0
* 功能：模板service
* 修改记录：
*/
@Service
public class TemplateServiceImpl implements ITemplateService{

	@Autowired
	private NPTemplateMapper templateMapper;
	
	@Autowired
	private NPServiceMapper serviceMapper;
	
	@Autowired
	private ServiceServiceImpl serviceService;
	
	/**
	 * 根据参数查询模板
	 * @param servicecode 服务编码
	 * @param suitcode 套码编码
	 * @param templatecode 模板编码
	 * @author 王冬冬  
	 * @date 2017年5月25日 下午3:48:36
	 */
	public NPTemplate selectByParam(String servicecode,String suitcode, String templatecode) {
		
		NPTemplateCriteria criteria=new NPTemplateCriteria();
		criteria.createCriteria().andServiceCodeEqualTo(servicecode).andSuitCodeEqualTo(suitcode).andTemplateCodeEqualTo(templatecode);
		List<NPTemplate> list= templateMapper.selectByExample(criteria);
		if(list==null||list.isEmpty()){
			return null;
		}
		return list.get(0);
		
	}

	/**模板列表
	 * @param page
	 * @author 王冬冬  
	 * @date 2017年5月25日 下午3:42:29
	 */
	public List<NPTemplate> listPageTemplates(NPPage page) throws Exception {
		List<NPTemplate> templates=templateMapper.listPageTemplates(page);
		Set<String> servicecodesSet=new HashSet<String>();
		if(templates==null||templates.isEmpty()){
		    return null;
		}
		templates.forEach((template)->{
		    servicecodesSet.add(template.getServiceCode().trim());
		});
		List<String> servicecodes=new ArrayList<String>();
		servicecodes.addAll(servicecodesSet);
	    Map<String,String> serviceMap=serviceService.getServiceMap(servicecodes);
		for(NPTemplate template:templates){
		    template.setServiceName(serviceMap.get(template.getServiceCode().trim()));
		}
		return templates;
	}
	/**
	 * 模板新增
	 * @param template 模板
	 * @param npService 服务
	 * @author 王冬冬  
	 * @date 2017年5月25日 下午3:42:13
	 */
	@Override
	public int addTemplate(NPTemplate template, NPService npService) throws Exception {
		template.setCreateDate(new Date());
		template.setUpdateDate(new Date());
		String url=getUrl(npService,null);
		String responseBody=HttpClientUtil.post(url, null, JSONObject.toJSONString(template));
		System.out.println(responseBody);
		JSONObject authJson=JSONObject.parseObject(responseBody);
		if(responseBody!=null&&!"0".equals(authJson.get("code"))){
			throw new Exception("添加失败");
		}
		templateMapper.insert(template);
    	JedisUtil.setex(new StringBuilder().append(Constants.TEMPLATE).append(template.getServiceCode()).append("-").append(template.getSuitCode()).append("-").append(template.getTemplateCode()).toString(),Constants.TOKEN_EXPIRE_TIME,JSONObject.toJSONString(template));
		return 1;
	}

	/**
	 * 模板更新
	 * @author 王冬冬  
	 * @date 2017年4月11日 上午10:25:29
	 */
	public int updateTemplate(NPTemplate template,NPService npService) throws Exception {	
		template.setUpdateDate(new Date());
		String url=getUrl(npService,null);
		String responseBody=HttpClientUtil.put(url, null, JSONObject.toJSONString(template));
		JSONObject authJson=JSONObject.parseObject(responseBody);
		if(responseBody!=null&&!"0".equals(authJson.get("code"))){
			throw new Exception("添加失败");
		}
		templateMapper.updateByPrimaryKeySelective(template);
		return 1;
	}

	/**
	 * 根据模板id获取模板
	 * @param templateId 模板id
	 * @author 王冬冬  
	 * @date 2017年5月25日 下午3:43:07
	 */
	public NPTemplate getTemplate(Integer templateId) throws Exception {
		
		NPTemplate template=templateMapper.selectByPrimaryKey(templateId);
		NPService npService=queryService(template);
		
		HashMap<String,Object> map=concatParam(template.getTemplateCode(),template.getServiceCode(),template.getSuitCode());
		String url=getUrl(npService,map);
		String responseBody=HttpClientUtil.get(url, null);
		JSONObject authJson=JSONObject.parseObject(responseBody);
		if(responseBody!=null&&!"0".equals(authJson.get("code"))){
			throw new Exception("查询失败");
		}
		JSONObject tempTemplate=(JSONObject)authJson.get("data");
		template.setSrc((String)tempTemplate.get("src"));
		template.setContent((String)tempTemplate.get("content"));
		return template;
	}

	/**
	 * 获取模板对应的服务
	 * @param template
	 * @return
	 * @author 王冬冬  
	 * @date 2017年4月7日 上午11:00:22
	 */
	private NPService queryService(NPTemplate template) {
		NPServiceCriteria example = new NPServiceCriteria();
	    example.createCriteria().andServiceCodeEqualTo(template.getServiceCode());
		return serviceMapper.selectByExample(example).get(0);
	}

	/**
	 * 拼接参数
	 * @param templateCode
	 * @param servicecode
	 * @param suitcode
	 * @return
	 * @author 王冬冬  
	 * @date 2017年4月7日 上午11:00:59
	 */
	HashMap<String,Object>  concatParam(String templateCode,String servicecode,String suitcode){
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("templateCode",templateCode);
		map.put("serviceCode", servicecode);
		map.put("suitCode",suitcode);
		return map;
	}
	
	/**
	 * 删除模板
	 * @param templateId 模板id
	 * @author 王冬冬  
	 * @date 2017年5月25日 下午3:43:52
	 */
	public void deleteTemplate(Integer templateId) throws Exception {
		NPTemplate template=templateMapper.selectByPrimaryKey(templateId);
		NPService npService=queryService(template);
		
		HashMap<String,Object> map=concatParam(template.getTemplateCode(),template.getServiceCode(),template.getSuitCode());
		
		String url=getUrl(npService,map);
		String responseBody=HttpClientUtil.delete(url,null);
		JSONObject authJson=JSONObject.parseObject(responseBody);
		if(responseBody!=null&&!"0".equals(authJson.get("code"))){
			throw new Exception("删除失败");
		}
	    templateMapper.deleteByPrimaryKey(templateId);
    	JedisUtil.del(new StringBuilder().append(Constants.TEMPLATE).append(template.getServiceCode()).append("-").append(template.getSuitCode()).append("-").append(template.getTemplateCode()).toString());
	}
	
	/**
	 * 获取url
	 * @param npService
	 * @param map
	 * @return
	 * @throws BaseException
	 * @author 王冬冬  
	 * @date 2017年5月25日 下午3:27:25
	 */
	public String getUrl(NPService npService, HashMap<String, Object> map) throws BaseException{
		String host=npService.getServiceHost();
		String port=npService.getServicePort();
		String template_op=npService.getTemplateCrud();
		if(StringUtils.isEmpty(host)||StringUtils.isEmpty(template_op)){
			throw new BaseException("安全接口主机或调用地址未配置");
		}
		StringBuilder builder=new StringBuilder();
		if(!host.startsWith("http://")){
			builder.append("http://");
		}
		builder.append(host);
		if(port!=null){
			builder.append(":").append(port);
		}
	   
	   String timestamp=String.valueOf(System.currentTimeMillis());
	   String token=MD5.md5(npService.getServiceCode()+npService.getServiceKey()+timestamp);
	   builder.append(template_op).append("?").append("timestamp="+timestamp).append("&token="+token);
	   if(map==null||map.isEmpty()){
		   return builder.toString();
	   }
	   for(Entry<String, Object> ele:map.entrySet()){
		   builder.append("&"+ele.getKey()).append("="+ele.getValue());
	   }
	   return builder.toString();
	}
}
