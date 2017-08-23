package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPBizSuitRole;
import com.awifi.np.admin.entity.NPBizSuitRoleCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NPBizSuitRoleMapper {
    long countByExample(NPBizSuitRoleCriteria example);

    int deleteByExample(NPBizSuitRoleCriteria example);

    int insert(NPBizSuitRole record);

    int insertSelective(NPBizSuitRole record);

    List<NPBizSuitRole> selectByExample(NPBizSuitRoleCriteria example);

    int updateByExampleSelective(@Param("record") NPBizSuitRole record, @Param("example") NPBizSuitRoleCriteria example);

    int updateByExample(@Param("record") NPBizSuitRole record, @Param("example") NPBizSuitRoleCriteria example);

    List<String> listSuiteCodeByRoleId(Long roleId);
    void insertBulk(List<NPBizSuitRole> npBizSuitRoleList);

}