package com.awifi.np.admin.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPPage;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月23日 下午2:48:20
* 创建作者：王冬冬
* 文件名称：IInterfaceService.java
* 版本：  v1.0
* 功能：接口
* 修改记录：
*/
public interface IInterfaceService {

	NPInterface getInterfaceByParam(String servicecode, String interfacecode) throws Exception;
	List<NPInterface> listPageInterface(NPPage page, String keyword, String serviceName) throws Exception;
	NPInterface getByKey(Integer iid) throws Exception;
	String insert(NPInterface npInterface, NPAdminUser adminUser) throws Exception;
	String update(NPInterface npInterface, NPAdminUser adminUser) throws Exception;
	void delete(Integer iid) throws Exception;
	void deleteByServiceCode(String serviceCode) throws Exception;
	void insertFromTmp(String serviceCode) throws Exception;
	List<NPInterface> listRegistedByServiceCode(String serviceCode) throws Exception;
	public List<NPInterface> listRegistedByServiceCode(String serviceCode, List<String> okCodeList);
}
