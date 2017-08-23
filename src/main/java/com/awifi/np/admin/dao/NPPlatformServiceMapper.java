package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPPlatformService;
import com.awifi.np.admin.entity.NPPlatformServiceCriteria;
import com.awifi.np.admin.entity.NPPlatformServiceKey;
import java.util.List;

import com.awifi.np.admin.entity.NPService;
import org.apache.ibatis.annotations.Param;

public interface NPPlatformServiceMapper {
    int countByExample(NPPlatformServiceCriteria example);

    int deleteByExample(NPPlatformServiceCriteria example);

    int deleteByPrimaryKey(NPPlatformServiceKey key);

    int insert(NPPlatformService record);

    int insertSelective(NPPlatformService record);

    List<NPPlatformService> selectByExample(NPPlatformServiceCriteria example);

    NPPlatformService selectByPrimaryKey(NPPlatformServiceKey key);

    int updateByExampleSelective(@Param("record") NPPlatformService record, @Param("example") NPPlatformServiceCriteria example);

    int updateByExample(@Param("record") NPPlatformService record, @Param("example") NPPlatformServiceCriteria example);

    int updateByPrimaryKeySelective(NPPlatformService record);

    int updateByPrimaryKey(NPPlatformService record);

    List<String> selectBindedService(String appId);

    List<String> selectOwnedService(String appId);

    void insertBulk(List<NPPlatformService> bindList);

    List<NPPlatformService> selectBindedServiceInfo(String appId);
}