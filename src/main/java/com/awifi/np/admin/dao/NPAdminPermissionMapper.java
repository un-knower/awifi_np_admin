package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPAdminPermission;
import com.awifi.np.admin.entity.NPAdminPermissionCriteria;
import com.awifi.np.admin.entity.NPPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NPAdminPermissionMapper {
    long countByExample(NPAdminPermissionCriteria example);

    int deleteByExample(NPAdminPermissionCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(NPAdminPermission record);

    int insertSelective(NPAdminPermission record);

    List<NPAdminPermission> selectByExample(NPAdminPermissionCriteria example);

    NPAdminPermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NPAdminPermission record, @Param("example") NPAdminPermissionCriteria example);

    int updateByExample(@Param("record") NPAdminPermission record, @Param("example") NPAdminPermissionCriteria example);

    int updateByPrimaryKeySelective(NPAdminPermission record);

    int updateByPrimaryKey(NPAdminPermission record);

	int deleteByBatch(String[] permissionIds);

	List<NPAdminPermission> listPagePermission(NPPage page);

}