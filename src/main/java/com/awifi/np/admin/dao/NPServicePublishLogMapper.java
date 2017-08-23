package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPServicePublishLog;
import com.awifi.np.admin.entity.NPServicePublishLogCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NPServicePublishLogMapper {
    int countByExample(NPServicePublishLogCriteria example);

    int deleteByExample(NPServicePublishLogCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(NPServicePublishLog record);

    int insertSelective(NPServicePublishLog record);

    List<NPServicePublishLog> selectByExampleWithBLOBs(NPServicePublishLogCriteria example);

    List<NPServicePublishLog> selectByExample(NPServicePublishLogCriteria example);

    NPServicePublishLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NPServicePublishLog record, @Param("example") NPServicePublishLogCriteria example);

    int updateByExampleWithBLOBs(@Param("record") NPServicePublishLog record, @Param("example") NPServicePublishLogCriteria example);

    int updateByExample(@Param("record") NPServicePublishLog record, @Param("example") NPServicePublishLogCriteria example);

    int updateByPrimaryKeySelective(NPServicePublishLog record);

    int updateByPrimaryKeyWithBLOBs(NPServicePublishLog record);

    int updateByPrimaryKey(NPServicePublishLog record);

    List<NPServicePublishLog> listPagePublishLog(NPPage page);

    NPServicePublishLog getJoinInfo(Integer id);
}