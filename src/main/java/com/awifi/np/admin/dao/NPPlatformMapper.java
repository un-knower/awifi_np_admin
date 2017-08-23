package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPPlatformCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NPPlatformMapper {
    int countByExample(NPPlatformCriteria example);

    int deleteByExample(NPPlatformCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(NPPlatform record);

    int insertSelective(NPPlatform record);

    List<NPPlatform> selectByExample(NPPlatformCriteria example);

    NPPlatform selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NPPlatform record, @Param("example") NPPlatformCriteria example);

    int updateByExample(@Param("record") NPPlatform record, @Param("example") NPPlatformCriteria example);

    int updateByPrimaryKeySelective(NPPlatform record);

    int updateByPrimaryKey(NPPlatform record);

    public List<NPPlatform> listPagePlatform(NPPage page);
}