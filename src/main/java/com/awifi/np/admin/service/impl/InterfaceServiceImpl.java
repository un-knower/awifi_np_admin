package com.awifi.np.admin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.utils.StringUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awifi.np.admin.dao.NPInterfaceMapper;
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPInterfaceCriteria;
import com.awifi.np.admin.service.IInterfaceService;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月23日 下午3:12:35
* 创建作者：王冬冬
* 文件名称：InterfaceServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
@Service
public class InterfaceServiceImpl implements IInterfaceService{

	@Autowired
	private NPInterfaceMapper interfaceMapper;
	
	/**
	 * 根据serviceCode和interfaceCode查询接口是否注册
	 * @author 王冬冬  
	 * @date 2017年1月23日 下午3:19:25
	 */
	public NPInterface getInterfaceByParam(String servicecode, String interfacecode) {
//		NPInterfaceCriteria criteria=new NPInterfaceCriteria();
//		criteria.createCriteria().andServiceCodeEqualTo(servicecode).andInterfaceCodeEqualTo(interfacecode);
		return interfaceMapper.getInterfaceByParam(servicecode,interfacecode);
	}


	public List<NPInterface> listPageInterface(NPPage page, String keyword, String serviceName) {

		HashMap<String,Object> params = new HashMap<String, Object>();
		params.put("keyword", StringUtil.isNotEmpty(keyword)? "%"+keyword+"%" : null);
		params.put("serviceName", StringUtil.isNotEmpty(serviceName)? "%"+serviceName+"%" : null);
		page.setParams(params);

		return interfaceMapper.listPageInterface(page);
	}

	public NPInterface getByKey(Integer iid) throws Exception {
		return interfaceMapper.getByKey(iid);
	}

	public String insert(NPInterface npInterface, NPAdminUser adminUser) throws Exception {

		NPInterfaceCriteria npInterfaceCriteria = new NPInterfaceCriteria();
		npInterfaceCriteria.createCriteria()
				.andServiceCodeEqualTo(npInterface.getServiceCode())
				.andInterfaceCodeEqualTo(npInterface.getInterfaceCode());
		List<NPInterface> tmpList = interfaceMapper.selectByExample(npInterfaceCriteria);
		if(tmpList.size()>0){
			return "E1013001";
		}
		if(adminUser!=null) {
			npInterface.setCreateUserId(adminUser.getId());
			npInterface.setUpdateUserId(adminUser.getId());
		}
		npInterface.setCreateDate(new Date());
		npInterface.setUpdateDate(new Date());
		npInterface.setStatus((byte)0);

		interfaceMapper.insert(npInterface);

		return Constants.ECodeSuccess;
	}


	public String update(NPInterface npInterface, NPAdminUser adminUser) throws Exception {
		NPInterfaceCriteria example = new NPInterfaceCriteria();
		example.createCriteria()
				.andInterfaceCodeEqualTo(npInterface.getInterfaceCode())
				.andServiceCodeEqualTo(npInterface.getServiceCode())
				.andIdNotEqualTo(npInterface.getId());
		List<NPInterface> tmpList = interfaceMapper.selectByExample(example);
		if(tmpList.size()>0){
			return "E1013001";
		}
		if(adminUser!=null) {
			npInterface.setUpdateUserId(adminUser.getId());
		}
		npInterface.setUpdateDate(new Date());
		interfaceMapper.updateByPrimaryKeySelective(npInterface);

		return Constants.ECodeSuccess;
	}


	public void delete(Integer iid) throws Exception {
	    NPInterface npInterface = getByKey(iid);
		interfaceMapper.deleteByPrimaryKey(iid);
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npInterface.getServiceCode()).append("_").append(npInterface.getInterfaceCode()).toString());
	}


	public void deleteByServiceCode(String serviceCode) throws Exception {
		NPInterfaceCriteria example = new NPInterfaceCriteria();
		example.createCriteria().andServiceCodeEqualTo(serviceCode);
		interfaceMapper.deleteByExample(example);
	}

	@Override
	public void insertFromTmp(String serviceCode) throws Exception {
		interfaceMapper.insertFromTmp(serviceCode);
	}

	@Override
	public List<NPInterface> listRegistedByServiceCode(String serviceCode) throws Exception {
		NPInterfaceCriteria example = new NPInterfaceCriteria();
		example.createCriteria().andServiceCodeEqualTo(serviceCode).andStatusEqualTo(Constants.INTERFACE_REGEISTERED);
		example.setOrderByClause("interface_code");
		return interfaceMapper.selectByExample(example);
	}

	@Override
	public List<NPInterface> listRegistedByServiceCode(String serviceCode, List<String> okCodeList) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("serviceCode", serviceCode);
		map.put("codeList", okCodeList);
		return interfaceMapper.listRegistedByServiceCode(map);
	}

}
