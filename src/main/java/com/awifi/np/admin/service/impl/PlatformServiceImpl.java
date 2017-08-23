package com.awifi.np.admin.service.impl;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPPlatformMapper;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPPlatformCriteria;
import com.awifi.np.admin.service.IPlatformService;
import com.awifi.np.admin.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/11
 * 创建作者：卢朱娜
 * 文件名称：PlatformServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service
public class PlatformServiceImpl implements IPlatformService {
    @Resource
    private NPPlatformMapper platformMapper;


    public List<NPPlatform> listAll() throws Exception {
//        NPPlatformCriteria example = new NPPlatformCriteria();
//        example.setOrderByClause("by create_date desc");
        return platformMapper.selectByExample(null);
    }

    public NPPlatform getById(Integer id) throws Exception {
        return platformMapper.selectByPrimaryKey(id);
    }

    public List<NPPlatform> selectByAppId(String appId) throws Exception {
        NPPlatformCriteria example = new NPPlatformCriteria();
        example.createCriteria().andAppIdEqualTo(appId);
        return platformMapper.selectByExample(example);

    }


    public String update(NPPlatform platform) throws Exception {
        NPPlatformCriteria example = new NPPlatformCriteria();
        example.createCriteria().andIdNotEqualTo(platform.getId())
                .andAppIdEqualTo(platform.getAppId());

        List<NPPlatform> list = platformMapper.selectByExample(example);
        if(list.size()>0){
            return "E1011001";
        }

        platformMapper.updateByPrimaryKeySelective(platform);
        return Constants.ECodeSuccess;
    }

    public void insert(NPPlatform npPlatform, NPAdminUser user) throws Exception {

        List<NPPlatform> list = selectByAppId(npPlatform.getAppId());
        if(list.size()==0){
            Date now = new Date();
            npPlatform.setCreateDate(now);
            npPlatform.setStatus((byte)0);
            if(user != null){
                npPlatform.setCreateUserId(user.getId());
                npPlatform.setCreateName(user.getLoginAccount());
            }
            platformMapper.insert(npPlatform);
        }
    }

    public List<NPPlatform> listPagePlatform(NPPage page, String keyword) throws Exception {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("keyword", StringUtil.isNotEmpty(keyword)?"%"+keyword+"%":null);
        page.setParams(params);

        return platformMapper.listPagePlatform(page);
    }


}
