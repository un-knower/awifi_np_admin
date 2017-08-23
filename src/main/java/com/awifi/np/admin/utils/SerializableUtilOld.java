package com.awifi.np.admin.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.awifi.np.admin.entity.NPAdminPermission;
import com.awifi.np.admin.entity.NPService;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月18日 上午9:18:08
* 创建作者：王冬冬
* 文件名称：SerializableUtil.java
* 版本：  v1.0
* 功能：list序列化工具类
* 修改记录：
*/
public class SerializableUtilOld {
		
	/**
	 * 序列化list
	 * @param list
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月18日 上午11:24:19
	 */
	public static <T> byte[] serialize(List<T> list){
		
		ByteArrayOutputStream byteOutputStream=null;
		ObjectOutputStream outputStream=null;
		try {
			byteOutputStream=new ByteArrayOutputStream();
			outputStream =new ObjectOutputStream(byteOutputStream);
			for(T t:list){
				outputStream.writeObject(t);
			}
			return byteOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			try {
				byteOutputStream.close();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
 /**
  * 反序列化成list
  * @param bytes
  * @return
  * @author 王冬冬  
  * @date 2017年1月18日 上午11:24:36
  */
@SuppressWarnings("unchecked")
  public static <T> List<T> unserialize(byte[] bytes){	
		ByteArrayInputStream byteInputStream=null;
		ObjectInputStream inputStream=null;
		List<T> list=new ArrayList<T>();
		try {
			byteInputStream=new ByteArrayInputStream(bytes);
			inputStream =new ObjectInputStream(byteInputStream);
			while(byteInputStream.available()>0){
				T t=(T)inputStream.readObject();
				if(t==null){
					break;
				}
				list.add(t);
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			
			try {
				byteInputStream.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}


/**
 * 序列化Map
 * @param map
 * @return
 * @author 王冬冬  
 * @date 2017年1月18日 上午11:24:19
 */
public static <T,V> byte[] serialize(Map<T,V> map){
	
	ByteArrayOutputStream byteOutputStream=null;
	ObjectOutputStream outputStream=null;
	try {
		byteOutputStream=new ByteArrayOutputStream();
		outputStream =new ObjectOutputStream(byteOutputStream);
		outputStream.writeObject(map);
//		for(Map.Entry<T, V> m:map.entrySet()){
//			outputStream.writeObject(m);
//		}
		return byteOutputStream.toByteArray();
	} catch (IOException e) {
		e.printStackTrace();
	}finally{
		
		try {
			byteOutputStream.close();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	return null;
}

/**
* 反序列化成list
* @param bytes
* @return
* @author 王冬冬  
* @date 2017年1月18日 上午11:24:36
*/
@SuppressWarnings("unchecked")
public static <T,V> Map<T,V> unserializeMap(byte[] bytes){	
	ByteArrayInputStream byteInputStream=null;
	ObjectInputStream inputStream=null;
	Map<T,V> map=null;
	try {
		byteInputStream=new ByteArrayInputStream(bytes);
		inputStream =new ObjectInputStream(byteInputStream);
		while(byteInputStream.available()>0){
			map=(Map<T, V>)inputStream.readObject();
			if(map==null){
				break;
			}
		}
		return map;
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}finally{
		
		try {
			byteInputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	return null;
}
//	public static void main(String[] args) {
////	    List<NPAdminPermission> list=new ArrayList<NPAdminPermission>();
////	    NPAdminPermission p=new NPAdminPermission();
////	    p.setId(1);
////	    p.setParentId(0);
////	    
////	    
////	    NPAdminPermission p1=new NPAdminPermission();
////	    p1.setId(2);
////	    p1.setParentId(1);
////	    
////	    p.addSub(p1);
////
////	    list.add(p);
////	    
////		byte[] s=serialize(list);
////		
////		List<NPAdminPermission> arr=unserialize(s);
//		
////		Map<Integer,NPAdminPermission> map=new HashMap<Integer, NPAdminPermission>();
////	    NPAdminPermission p=new NPAdminPermission();
////	    p.setId(1);
////	    p.setParentId(0);
////	    
////	    NPAdminPermission p1=new NPAdminPermission();
////	    p1.setId(2);
////	    p1.setParentId(0);
////	    
////		map.put(p.getId(),p);
////		map.put(p1.getId(), p1);
////		
////		byte[] s=serialize(map);
////		Map<Integer,NPAdminPermission> map1=unserializeMap(s);
//		List o = new ArrayList();
//		if(o instanceof List){
//			List a=(List)o;
//		}else{
//		}
//	List<NPService> serviceList=new ArrayList<>();	
//	NPService service=new NPService();
//	service.setCreateUsername("hello");
//	serviceList.add(service);
//	byte[] s=serialize(serviceList);
//	
//	List<NPService> arr=unserialize(s);
//}

}
