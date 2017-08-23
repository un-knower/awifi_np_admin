package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPBizUserRole;
import com.awifi.np.admin.entity.NPBizUserRoleCriteria;
import com.awifi.np.admin.entity.NPBizUserRoleKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NPBizUserRoleMapper {
    int countByExample(NPBizUserRoleCriteria example);

    int deleteByExample(NPBizUserRoleCriteria example);

    int deleteByPrimaryKey(NPBizUserRoleKey key);

    int insert(NPBizUserRole record);

    int insertSelective(NPBizUserRole record);

    List<NPBizUserRole> selectByExample(NPBizUserRoleCriteria example);

    NPBizUserRole selectByPrimaryKey(NPBizUserRoleKey key);

    int updateByExampleSelective(@Param("record") NPBizUserRole record, @Param("example") NPBizUserRoleCriteria example);

    int updateByExample(@Param("record") NPBizUserRole record, @Param("example") NPBizUserRoleCriteria example);

    int updateByPrimaryKeySelective(NPBizUserRole record);

    int updateByPrimaryKey(NPBizUserRole record);

    void insertBulk(List<NPBizUserRole> bizUserRoleList);

    List<NPBizUserRole> selectByUserIdList(List<Long> userIdList);

    List<NPBizRole> selectByUserId(Long uid);
}