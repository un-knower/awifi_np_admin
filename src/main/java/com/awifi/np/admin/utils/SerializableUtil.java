package com.awifi.np.admin.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.utils.redis.JedisUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/16
 * 创建作者：卢朱娜
 * 文件名称：SerializableUtil2.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class SerializableUtil {
    public static byte[] serialize(Object object) throws Exception {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
            //序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     * @throws Exception
     * @author 
     * @date 2017年2月22日 下午4:15:20
     */
    public static Object unserialize(byte[] bytes) throws Exception {
    	if(bytes==null){
    		return null;
    	}
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();

    }

    public static void main(String[] args) throws Exception{
//    	NPInterface intf=new NPInterface();
//    	JedisUtil.set(Constants.INTERFACE+"1"+"_"+"1",SerializableUtil.serialize(intf));
//    	byte[] b=JedisUtil.get(Constants.INTERFACE+"1"+"_"+"1");
//    	NPInterface npinterface=(NPInterface) SerializableUtil.unserialize(b);
    }
}
