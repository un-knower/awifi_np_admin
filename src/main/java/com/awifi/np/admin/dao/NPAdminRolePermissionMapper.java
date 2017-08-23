package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPAdminRolePermission;
import com.awifi.np.admin.entity.NPAdminRolePermissionCriteria;
import com.awifi.np.admin.entity.NPAdminRolePermissionKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NPAdminRolePermissionMapper {
    long countByExample(NPAdminRolePermissionCriteria example);

    int deleteByExample(NPAdminRolePermissionCriteria example);

    int deleteByPrimaryKey(NPAdminRolePermissionKey key);

    int insert(NPAdminRolePermission record);

    int insertSelective(NPAdminRolePermission record);

    List<NPAdminRolePermission> selectByExample(NPAdminRolePermissionCriteria example);

    NPAdminRolePermission selectByPrimaryKey(NPAdminRolePermissionKey key);

    int updateByExampleSelective(@Param("record") NPAdminRolePermission record, @Param("example") NPAdminRolePermissionCriteria example);

    int updateByExample(@Param("record") NPAdminRolePermission record, @Param("example") NPAdminRolePermissionCriteria example);

    int updateByPrimaryKeySelective(NPAdminRolePermission record);

    int updateByPrimaryKey(NPAdminRolePermission record);

	void deleteByBatchPermissionIds(String[] permissionIds);

	void deleteByBatchRoleIds(String[] roleIds);

	int deleteByPermissionIds(Integer id);
}