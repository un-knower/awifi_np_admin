package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPSuit;
import com.awifi.np.admin.entity.NPSuitCriteria;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface NPSuitMapper {
    long countByExample(NPSuitCriteria example);

    int deleteByExample(NPSuitCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(NPSuit record);

    int insertSelective(NPSuit record);

    List<NPSuit> selectByExample(NPSuitCriteria example);

    NPSuit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NPSuit record, @Param("example") NPSuitCriteria example);

    int updateByExample(@Param("record") NPSuit record, @Param("example") NPSuitCriteria example);

    int updateByPrimaryKeySelective(NPSuit record);

    int updateByPrimaryKey(NPSuit record);
    
    List<NPSuit> listPageSuits(NPPage page);

    List<NPSuit> listAllSuits();
}