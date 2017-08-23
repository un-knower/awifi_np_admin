package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPAdminRole;
import com.awifi.np.admin.entity.NPAdminRoleCriteria;
import com.awifi.np.admin.entity.NPPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NPAdminRoleMapper {
    long countByExample(NPAdminRoleCriteria example);

    int deleteByExample(NPAdminRoleCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(NPAdminRole record);

    int insertSelective(NPAdminRole record);

    List<NPAdminRole> selectByExample(NPAdminRoleCriteria example);

    NPAdminRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NPAdminRole record, @Param("example") NPAdminRoleCriteria example);

    int updateByExample(@Param("record") NPAdminRole record, @Param("example") NPAdminRoleCriteria example);

    int updateByPrimaryKeySelective(NPAdminRole record);

    int updateByPrimaryKey(NPAdminRole record);

	NPAdminRole selectByRoleId(Integer roleid);

	List<NPAdminRole> listPageRoles(NPPage page);

	int deleteByIds(String[] idArr) throws Exception;

    NPAdminRole selectMenusByRoleId(Integer roleId);
}