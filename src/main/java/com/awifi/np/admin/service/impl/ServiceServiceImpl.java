package com.awifi.np.admin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPServiceMapper;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.entity.NPServiceCriteria;
import com.awifi.np.admin.service.IServiceService;
import com.awifi.np.admin.utils.StringUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/16
 * 创建作者：卢朱娜
 * 文件名称：ServiceServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service
public class ServiceServiceImpl implements IServiceService {

    @Autowired
    private NPServiceMapper serviceMapper;
    @Autowired
    private PlatformServiceServiceImpl platformServiceService;


    public List<NPService> getByServiceCode(String serviceCode) throws Exception {
        NPServiceCriteria example = new NPServiceCriteria();
        example.createCriteria().andServiceCodeEqualTo(serviceCode);
        return serviceMapper.selectByExample(example);
    }

    public NPService getById(int sid) throws Exception{
        return serviceMapper.selectByPrimaryKey(sid);
    }



    public void insert(NPService service) throws Exception {
        Date now = new Date();
        NPServiceCriteria example = new NPServiceCriteria();
        example.createCriteria().andServiceCodeEqualTo(service.getServiceCode());
        List<NPService> serviceList = serviceMapper.selectByExample(example);
        if(serviceList.size()==0){
            service.setCreateDate(now);
            serviceMapper.insertSelective(service);
        }
    }


    public List<NPService> listPageService(NPPage page, String keyword) throws Exception {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("keyword", StringUtil.isNotEmpty(keyword)?"%"+keyword+"%":null);
        page.setParams(params);
        return serviceMapper.listPageService(page);
    }

    @Override
    public List<NPService> listAll() throws Exception {
        return serviceMapper.selectByExample(null);
    }

    public List<NPService> listPageLevelOneMenu(NPPage page, String keyword, Boolean isMenu) throws Exception {
        HashMap<String,Object> params = new HashMap<>();
        params.put("keyword", StringUtil.isNotEmpty(keyword)?"%"+keyword+"%":null);
        params.put("isMenu", isMenu!=null ? isMenu : null);
        page.setParams(params);
        return serviceMapper.listPageLevelOneMenu(page);
    }


    public NPService getJoinInfo(int sid) {

        return serviceMapper.getJoinInfo(sid);
    }

    public void insert(NPService npService, NPPlatform npPlatform, NPAdminUser adminUser) {
        npService.setPublishStatus(Constants.SERVICR_NOT_ON_LINE);
        npService.setCreateDate(new Date());
        if(adminUser!=null){
            npService.setCreateUserId(adminUser.getId());
            npService.setCreateUsername(adminUser.getLoginAccount());
        }
        serviceMapper.insertSelective(npService);
        platformServiceService.insert(npPlatform.getAppId(), npService.getServiceCode(), true);
    }

    public String updateAndBindPlatform(NPService npService, NPPlatform npPlatform) {
        NPServiceCriteria example = new NPServiceCriteria();
        example.createCriteria().andServiceCodeEqualTo(npService.getServiceCode())
                .andIdNotEqualTo(npService.getId());
        List<NPService> serviceList = serviceMapper.selectByExample(example);
        if(serviceList.size()>0){
            return "E1012001";
        }

        serviceMapper.updateByPrimaryKeySelective(npService);
        platformServiceService.insert(npPlatform.getAppId(), npService.getServiceCode(), true);

        return Constants.ECodeSuccess;
    }


    public void updateById(NPService npService) {
        serviceMapper.updateByPrimaryKeySelective(npService);
    }


    public void updateStatus(Integer sid, byte status) {
        NPService npService = new NPService();
        npService.setId(sid);
        npService.setPublishStatus(status);
        serviceMapper.updateByPrimaryKeySelective(npService);
    }

    @Override
    public List<NPService> listOnlineService(Boolean isMenu) throws Exception {
            return serviceMapper.selectALL(isMenu);
    }

	@Override
	public List<NPService> getByServiceCodes(List<String> serviceCodes) throws Exception {
		
		return serviceMapper.getByServiceCodes(serviceCodes);
	}

	/**
	 * 返回key为servicecode,value为servicename的map
	 * @param serviceCodes
	 * @return
	 * @throws Exception
	 * @author 王冬冬  
	 * @date 2017年5月25日 上午11:04:39
	 */
	public Map<String,String> getServiceMap(List<String> serviceCodes) throws Exception {
	    Map<String,String> map=new HashMap<String,String>();
	    List<NPService> services=serviceMapper.getServieNamesByServiceCodes(serviceCodes);
	    if(services==null||services.isEmpty()){
	        return null;
	    }
	    for(NPService service:services){
	        map.put(service.getServiceCode(),service.getServiceName());
	    }
	    return map;
	}
	@Override
	public List<NPService> getByRoleId(Long rid) throws Exception {
		return serviceMapper.getByRoleId(rid);
	}

	

	


//    @Override
//    public List<NPService> list() throws Exception {
//        return npServiceMapper.selectByExample(null);
//    }



}
