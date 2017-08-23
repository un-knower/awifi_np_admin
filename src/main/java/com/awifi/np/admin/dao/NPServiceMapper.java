package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.entity.NPServiceCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NPServiceMapper {
    long countByExample(NPServiceCriteria example);

    int deleteByExample(NPServiceCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(NPService record);

    int insertSelective(NPService record);

    List<NPService> selectByExample(NPServiceCriteria example);

    NPService selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NPService record, @Param("example") NPServiceCriteria example);

    int updateByExample(@Param("record") NPService record, @Param("example") NPServiceCriteria example);

    int updateByPrimaryKeySelective(NPService record);

    int updateByPrimaryKey(NPService record);
    List<NPService> listPageService(NPPage page);

    NPService getJoinInfo(int sid);

	List<NPService> listPageLevelOneMenu(NPPage page);

	List<NPService> getByServiceCodes(List<String> serviceCodes);
	List<NPService> getServieNamesByServiceCodes(List<String> serviceCodes);
	List<NPService> getByRoleId(Long rid);

    List<NPService> selectALL(Boolean isMenu);
}