package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPBizUser;
import com.awifi.np.admin.entity.NPBizUserCriteria;
import java.util.List;

import com.awifi.np.admin.entity.NPPage;
import org.apache.ibatis.annotations.Param;

public interface NPBizUserMapper {
    int countByExample(NPBizUserCriteria example);

    int deleteByExample(NPBizUserCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(NPBizUser record);

    int insertSelective(NPBizUser record);

    List<NPBizUser> selectByExample(NPBizUserCriteria example);

    NPBizUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NPBizUser record, @Param("example") NPBizUserCriteria example);

    int updateByExample(@Param("record") NPBizUser record, @Param("example") NPBizUserCriteria example);

    int updateByPrimaryKeySelective(NPBizUser record);

    int updateByPrimaryKey(NPBizUser record);

    List<Long> listPageBizUserId(NPPage page);
}