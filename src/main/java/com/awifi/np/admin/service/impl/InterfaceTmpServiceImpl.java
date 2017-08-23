package com.awifi.np.admin.service.impl;

import com.awifi.np.admin.dao.NPInterfaceTmpMapper;
import com.awifi.np.admin.dto.InterfaceDTO;
import com.awifi.np.admin.entity.NPInterfaceTmp;
import com.awifi.np.admin.entity.NPInterfaceTmpCriteria;
import com.awifi.np.admin.service.IInterfaceTmpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/16
 * 创建作者：卢朱娜
 * 文件名称：InterfaceTmpServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service
public class InterfaceTmpServiceImpl implements IInterfaceTmpService {

    @Resource
    NPInterfaceTmpMapper npInterfaceTmpMapper;

    /**
     * 根据serviceCode获取接口列表
     * @param serviceCode
     * @return
     * @throws Exception
     */
    public List<NPInterfaceTmp> listByServiceCode(String serviceCode) throws Exception {
        NPInterfaceTmpCriteria example = new NPInterfaceTmpCriteria();
        example.createCriteria().andServiceCodeEqualTo(serviceCode);
        return npInterfaceTmpMapper.selectByExample(example);
    }

    /**
     * 获取服务下接口的最大创建时间
     * @param serviceCode
     * @return
     * @throws Exception
     */
    public Date getMaxDate(String serviceCode) throws Exception {
        NPInterfaceTmpCriteria example = new NPInterfaceTmpCriteria();
        example.createCriteria().andServiceCodeEqualTo(serviceCode);
        return npInterfaceTmpMapper.selectMaxCreateDate(serviceCode);
    }

    public void deleteAndInsert(String serviceCode, HashMap<String, InterfaceDTO> interfaceMap) {
        ArrayList<NPInterfaceTmp> npInterfaceTmpList = new ArrayList<NPInterfaceTmp>();

        Date now = new Date();
        Iterator<Map.Entry<String, InterfaceDTO>> entries = interfaceMap.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<String, InterfaceDTO> entry = entries.next();
            String key = entry.getKey();
            InterfaceDTO interfaceDTO = entry.getValue();

            NPInterfaceTmp tmpInterface = new NPInterfaceTmp();
            tmpInterface.setServiceCode(serviceCode);
            tmpInterface.setCreateDate(now);
            tmpInterface.setUpdateDate(now);
            tmpInterface.setInterfaceCode(key);
            tmpInterface.setInterfaceUrl(interfaceDTO.getPath());
            tmpInterface.setInterfaceMethod(interfaceDTO.getType());
            tmpInterface.setInterfaceName(interfaceDTO.getName());
            tmpInterface.setIfcheck(interfaceDTO.getIfcheck()==null?true:interfaceDTO.getIfcheck());
            tmpInterface.setRemark(interfaceDTO.getRemark());
            tmpInterface.setStatus((byte)0);

            npInterfaceTmpList.add(tmpInterface);
        }


        NPInterfaceTmpCriteria example = new NPInterfaceTmpCriteria();
        example.createCriteria().andServiceCodeEqualTo(serviceCode);
        npInterfaceTmpMapper.deleteByExample(example);
        npInterfaceTmpMapper.insertBulk(npInterfaceTmpList);

    }

    public void updateByInterfaceCode(NPInterfaceTmp npInterfaceTmp) {
        NPInterfaceTmpCriteria example = new NPInterfaceTmpCriteria();
        example.createCriteria().andServiceCodeEqualTo(npInterfaceTmp.getServiceCode())
        .andInterfaceCodeEqualTo(npInterfaceTmp.getInterfaceCode());
        npInterfaceTmpMapper.updateByExampleSelective(npInterfaceTmp, example);

    }

    @Override
    public void updateBulk(String serviceCode, List<Integer> notCheckList, List<Integer> notRegisterList) throws Exception {
        NPInterfaceTmp npInterfaceTmp = new NPInterfaceTmp();

        if(notCheckList.size()>0) {
            NPInterfaceTmpCriteria exampleNotCheck = new NPInterfaceTmpCriteria();
            exampleNotCheck.createCriteria().andServiceCodeEqualTo(serviceCode).andIdIn(notCheckList);
            npInterfaceTmp.setIfcheck(false);
            npInterfaceTmpMapper.updateByExampleSelective(npInterfaceTmp, exampleNotCheck);
        }

        NPInterfaceTmpCriteria exampleCheck = new NPInterfaceTmpCriteria();
        if(notCheckList.size()>0){
            exampleCheck.createCriteria().andServiceCodeEqualTo(serviceCode).andIdNotIn(notCheckList);
        }else{
            exampleCheck.createCriteria().andServiceCodeEqualTo(serviceCode);
        }
        npInterfaceTmp.setIfcheck(true);
        npInterfaceTmpMapper.updateByExampleSelective(npInterfaceTmp, exampleCheck);



        if(notRegisterList.size()>0) {
            NPInterfaceTmpCriteria exampleNotRegister = new NPInterfaceTmpCriteria();
            exampleNotRegister.createCriteria().andServiceCodeEqualTo(serviceCode).andIdIn(notRegisterList);
            npInterfaceTmp.setIfcheck(null);
            npInterfaceTmp.setStatus((byte) 0);
            npInterfaceTmpMapper.updateByExampleSelective(npInterfaceTmp, exampleNotRegister);
        }

        NPInterfaceTmpCriteria exampleRegister = new NPInterfaceTmpCriteria();
        if(notRegisterList.size()>0) {
            exampleRegister.createCriteria().andServiceCodeEqualTo(serviceCode).andIdNotIn(notRegisterList);
        }else{
            exampleRegister.createCriteria().andServiceCodeEqualTo(serviceCode);
        }
        npInterfaceTmp.setIfcheck(null);
        npInterfaceTmp.setStatus((byte)1);
        npInterfaceTmpMapper.updateByExampleSelective(npInterfaceTmp, exampleRegister);

    }


    public void deleteByServiceCode(String serviceCode) throws Exception {
        NPInterfaceTmpCriteria example = new NPInterfaceTmpCriteria();
        example.createCriteria().andServiceCodeEqualTo(serviceCode);
        npInterfaceTmpMapper.deleteByExample(example);
    }


}
