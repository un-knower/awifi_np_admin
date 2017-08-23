package com.awifi.np.admin.service.impl;

import java.util.Date;
import java.util.List;

import com.awifi.np.admin.entity.NPSuitCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awifi.np.admin.dao.NPSuitMapper;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPSuit;
import com.awifi.np.admin.service.ISuitService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 上午10:08:36
 * 创建作者：沈叶峰
 * 文件名称：SuitService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service
public class SuitServiceImpl implements ISuitService {
	@Autowired
	private NPSuitMapper suitMapper;
	
	public List<NPSuit> listPageSuits(NPPage page) throws Exception {
		return suitMapper.listPageSuits(page);
	}

	public int addSuit(NPSuit suit) throws Exception {
		suit.setCreateDate(new Date());
		return suitMapper.insert(suit);
	}

	public void updateSuit(NPSuit suit) throws Exception {
		suitMapper.updateByPrimaryKeySelective(suit);
	}

	public void deleteSuit(Integer suitId) throws Exception {
	     suitMapper.deleteByPrimaryKey(suitId);
	}

	 /**
	  * 获取单个套码详情
	  * @author 沈叶峰  
	  * @date 2017年1月24日 下午12:18:31
	  */
	public NPSuit getSuit(Integer suitId) throws Exception {
		return suitMapper.selectByPrimaryKey(suitId);
	}

	@Override
	public long countBySuitCode(String suitCode) throws Exception {
		NPSuitCriteria example = new NPSuitCriteria();
		example.createCriteria().andSuitCodeEqualTo(suitCode);
		return suitMapper.countByExample(example);

	}

	public long countBySuitCode4update(NPSuit npSuit) throws Exception {
		NPSuitCriteria example = new NPSuitCriteria();
		example.createCriteria().andSuitCodeEqualTo(npSuit.getSuitCode()).andIdNotEqualTo(npSuit.getId());
		return suitMapper.countByExample(example);

	}


	public long countBySuitCodeList(List<String> suitCodeList) throws Exception {
		NPSuitCriteria example = new NPSuitCriteria();
		example.createCriteria().andSuitCodeIn(suitCodeList);
		return suitMapper.countByExample(example);
	}

	@Override
	public List<NPSuit> listAllSuits() throws Exception {
		// TODO Auto-generated method stub
		return suitMapper.listAllSuits();
	}


}
