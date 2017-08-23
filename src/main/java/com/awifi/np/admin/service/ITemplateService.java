package com.awifi.np.admin.service;

import java.util.List;

import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.entity.NPTemplate;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月24日 上午11:03:58
* 创建作者：王冬冬
* 文件名称：ItemplateService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
public interface ITemplateService {

	NPTemplate selectByParam(String servicecode, String suitcode, String templatecode);
	
	/**
	 * 获取模板列表
	 * @param page
	 * @return
	 * @throws Exception
	 * @author 沈叶峰 
	 * @date 2017年2月6日 上午10:33:59
	 */
	public List<NPTemplate> listPageTemplates(NPPage page)  throws Exception;
	
	/**
	 * 添加模板
	 * @param template
	 * @return
	 * @throws Exception
	 * @author 沈叶峰 
	 * @param npService 
	 * @date 2017年2月6日 上午10:43:01
	 */
	public int addTemplate(NPTemplate template, NPService npService)  throws Exception; 
	
	
	/**
	 * 更新一套模板
	 * @param template
	 * @return
	 * @throws Exception
	 * @author 沈叶峰 
	 * @date 2017年2月6日 上午10:43:57
	 */
	public int updateTemplate(NPTemplate template,NPService npService)  throws Exception; 

	
	/**
	 * 获取单个模板
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 沈叶峰 
	 * @date 2017年2月16日 上午10:25:25
	 */
	public NPTemplate getTemplate(Integer templateId)  throws Exception;
	
	/**
	 * 删除模板
	 * @param templateId
	 * @throws Exception
	 * @author 沈叶峰 
	 * @date 2017年2月16日 上午10:27:01
	 */
    public void  deleteTemplate(Integer templateId)  throws Exception;
	  
}
