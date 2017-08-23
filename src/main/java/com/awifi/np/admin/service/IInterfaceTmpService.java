package com.awifi.np.admin.service;

import com.awifi.np.admin.dto.InterfaceDTO;
import com.awifi.np.admin.entity.NPInterfaceTmp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/16
 * 创建作者：卢朱娜
 * 文件名称：IInterfaceTmpService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IInterfaceTmpService {
    List<NPInterfaceTmp> listByServiceCode(String serviceCode) throws Exception;
    Date getMaxDate(String serviceCode) throws Exception;
    void deleteAndInsert(String serviceCode, HashMap<String, InterfaceDTO> interfaceMap) throws Exception;
    void updateByInterfaceCode(NPInterfaceTmp npInterfaceTmp);
    void updateBulk(String serviceCode, List<Integer> notCheckList, List<Integer> notRegisterList) throws Exception;
    void deleteByServiceCode(String serviceCode) throws Exception;
}
