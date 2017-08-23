package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPTemplate;
import com.awifi.np.admin.entity.NPTemplateCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NPTemplateMapper {
    long countByExample(NPTemplateCriteria example);

    int deleteByExample(NPTemplateCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(NPTemplate record);

    int insertSelective(NPTemplate record);

    List<NPTemplate> selectByExample(NPTemplateCriteria example);

    NPTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NPTemplate record, @Param("example") NPTemplateCriteria example);

    int updateByExample(@Param("record") NPTemplate record, @Param("example") NPTemplateCriteria example);

    int updateByPrimaryKeySelective(NPTemplate record);

    int updateByPrimaryKey(NPTemplate record);

	List<NPTemplate> listPageTemplates(NPPage page);
}