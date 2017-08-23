package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPBizRoleCriteria;
import java.util.List;

import com.awifi.np.admin.entity.NPPage;
import org.apache.ibatis.annotations.Param;

public interface NPBizRoleMapper {
    int countByExample(NPBizRoleCriteria example);

    int deleteByExample(NPBizRoleCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(NPBizRole record);

    int insertSelective(NPBizRole record);

    List<NPBizRole> selectByExample(NPBizRoleCriteria example);

    NPBizRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NPBizRole record, @Param("example") NPBizRoleCriteria example);

    int updateByExample(@Param("record") NPBizRole record, @Param("example") NPBizRoleCriteria example);

    int updateByPrimaryKeySelective(NPBizRole record);

    int updateByPrimaryKey(NPBizRole record);

    List<Long> listPageBizRoleId(NPPage page);
}