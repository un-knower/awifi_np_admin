package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPAdminUserCriteria;
import com.awifi.np.admin.entity.NPPage;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NPAdminUserMapper {
    long countByExample(NPAdminUserCriteria example);

    int deleteByExample(NPAdminUserCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(NPAdminUser record);

    int insertSelective(NPAdminUser record);

    List<NPAdminUser> selectByExample(NPAdminUserCriteria example);

    NPAdminUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NPAdminUser record, @Param("example") NPAdminUserCriteria example);

    int updateByExample(@Param("record") NPAdminUser record, @Param("example") NPAdminUserCriteria example);

    int updateByPrimaryKeySelective(NPAdminUser record);

    int updateByPrimaryKey(NPAdminUser record);

	List<NPAdminUser> listPageUsers(NPPage page);

	int deleteByIds(String[] idArr);
}