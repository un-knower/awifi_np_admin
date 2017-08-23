package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPAdminLog;
import com.awifi.np.admin.entity.NPAdminLogCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NPAdminLogMapper {
    long countByExample(NPAdminLogCriteria example);

    int deleteByExample(NPAdminLogCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(NPAdminLog record);

    int insertSelective(NPAdminLog record);

    List<NPAdminLog> selectByExample(NPAdminLogCriteria example);

    NPAdminLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NPAdminLog record, @Param("example") NPAdminLogCriteria example);

    int updateByExample(@Param("record") NPAdminLog record, @Param("example") NPAdminLogCriteria example);

    int updateByPrimaryKeySelective(NPAdminLog record);

    int updateByPrimaryKey(NPAdminLog record);
}