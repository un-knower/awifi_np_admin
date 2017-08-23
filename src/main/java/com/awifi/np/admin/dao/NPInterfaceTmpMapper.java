package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPInterfaceTmp;
import com.awifi.np.admin.entity.NPInterfaceTmpCriteria;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NPInterfaceTmpMapper {
    int countByExample(NPInterfaceTmpCriteria example);

    int deleteByExample(NPInterfaceTmpCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(NPInterfaceTmp record);

    int insertSelective(NPInterfaceTmp record);

    List<NPInterfaceTmp> selectByExample(NPInterfaceTmpCriteria example);

    NPInterfaceTmp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NPInterfaceTmp record, @Param("example") NPInterfaceTmpCriteria example);

    int updateByExample(@Param("record") NPInterfaceTmp record, @Param("example") NPInterfaceTmpCriteria example);

    int updateByPrimaryKeySelective(NPInterfaceTmp record);

    int updateByPrimaryKey(NPInterfaceTmp record);

    Date selectMaxCreateDate(String serviceCode);

    void insertBulk(List<NPInterfaceTmp> npInterfaceTmpList);
}