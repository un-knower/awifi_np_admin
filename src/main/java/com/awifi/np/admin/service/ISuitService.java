package com.awifi.np.admin.service;

import java.util.List;

import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.entity.NPSuit;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 上午10:02:40
 * 创建作者：沈叶峰
 * 文件名称：ISuitService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface ISuitService {

	  /**
	   * 列出套码
	   * @param page
	   * @return
	   * @throws Exception
	   * @author 沈叶峰 
	   * @date 2017年1月18日 上午10:07:20
	   */
	  public List<NPSuit> listPageSuits(NPPage page)  throws Exception;
	  /**
	   * 添加套码
	   * @param suit
	   * @return
	   * @throws Exception
	   * @author 沈叶峰 
	   * @date 2017年1月18日 上午10:07:32
	   */
	  public int  addSuit(NPSuit suit)  throws Exception;
	  /**
	   * 更新套码
	   * @param suit
	   * @throws Exception
	   * @author 沈叶峰 
	   * @date 2017年1月18日 上午10:07:41
	   */
	  public void  updateSuit(NPSuit suit)  throws Exception;
	  /**
	   * 删除套码
	   * @param suitId
	   * @throws Exception
	   * @author 沈叶峰 
	   * @date 2017年1月18日 上午10:07:48
	   */
	  public void  deleteSuit(Integer suitId)  throws Exception;
	  
	  /**
	   * 获取单个套码详情
	   * @param suitId
	   * @return
	   * @throws Exception
	   * @author 沈叶峰 
	   * @date 2017年1月24日 下午12:17:13
	   */
	  public NPSuit getSuit(Integer suitId)  throws Exception;


	/**
	 * 根据套码获取详情
	 * @param suitCode
	 * @return
	 * @throws Exception
	 */
	long countBySuitCode(String suitCode) throws Exception;

	/**
	 * 检查除了本身外，套码是否被使用
	 * @param npSuit
	 * @return
	 * @throws Exception
	 */
	long countBySuitCode4update(NPSuit npSuit) throws Exception;


	long countBySuitCodeList(List<String> suitCodeList) throws Exception;

	  /**
	   * 列出所有套码
	   * @return
	   * @throws Exception
	   * @author 沈叶峰 
	   * @date 2017年2月20日 上午11:00:48
	   */
	  public List<NPSuit> listAllSuits()  throws Exception;
		
}
