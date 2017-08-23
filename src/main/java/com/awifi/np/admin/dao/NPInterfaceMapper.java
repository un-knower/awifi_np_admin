package com.awifi.np.admin.dao;

import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPInterfaceCriteria;
import java.util.List;
import java.util.Map;

import com.awifi.np.admin.entity.NPPage;
import org.apache.ibatis.annotations.Param;

public interface NPInterfaceMapper {
    int countByExample(NPInterfaceCriteria example);

    int deleteByExample(NPInterfaceCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(NPInterface record);

    int insertSelective(NPInterface record);

    List<NPInterface> selectByExample(NPInterfaceCriteria example);

    NPInterface selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NPInterface record, @Param("example") NPInterfaceCriteria example);

    int updateByExample(@Param("record") NPInterface record, @Param("example") NPInterfaceCriteria example);

    int updateByPrimaryKeySelective(NPInterface record);

    int updateByPrimaryKey(NPInterface record);

    List<NPInterface> listPageInterface(NPPage page);

    NPInterface getByKey(Integer iid);

    void insertFromTmp(String serviceCode);

	NPInterface getInterfaceByParam(@Param("serviceCode") String servicecode, @Param("interfaceCode") String interfacecode);

	List<NPInterface> listRegistedByServiceCode(Map<String, Object> map);

}