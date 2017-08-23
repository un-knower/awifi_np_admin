package com.awifi.np.admin.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.awifi.np.admin.entity.NPAdminPermission;

public class SerializableUtilOldTest {

	@Test
	public void test(){
		
    List<NPAdminPermission> list=new ArrayList<NPAdminPermission>();
    NPAdminPermission p=new NPAdminPermission();
    p.setId(1);
    p.setParentId(0);
    
    
    NPAdminPermission p1=new NPAdminPermission();
    p1.setId(2);
    p1.setParentId(1);
    
    p.addSub(p1);

    list.add(p);
    
	byte[] s=SerializableUtilOld.serialize(list);
	Arrays.toString(s);
	
	List<NPAdminPermission> arr=SerializableUtilOld.unserialize(s);
	
    }
	
	@Test
	public void test1(){
		
    List<NPAdminPermission> list=null;
    try{
	 SerializableUtilOld.serialize(list);
      }catch(Exception e){
    	
    	
    }
//	
//	List<NPAdminPermission> arr=SerializableUtilOld.unserialize(s);
	
    }
	
	@Test
	public void test2(){
		
		List<Man> list=new ArrayList<Man>();
		Man man=new Man();
		list.add(man);
    try{
	 SerializableUtilOld.serialize(list);
      }catch(Exception e){	
    }
	
    }
	
	@Test
	public void test4(){
		byte[] s=new byte[]{1,2,2,2};
		
    try{
    	List<NPAdminPermission> arr=SerializableUtilOld.unserialize(s);
      }catch(Exception e){	
    }
	
    }
	
	
	@Test
	public void test3(){
		
		Map<Integer,NPAdminPermission> map=new HashMap<Integer, NPAdminPermission>();
	    NPAdminPermission p=new NPAdminPermission();
	    p.setId(1);
	    p.setParentId(0);
	    
	    NPAdminPermission p1=new NPAdminPermission();
	    p1.setId(2);
	    p1.setParentId(0);
	    
		map.put(p.getId(),p);
		map.put(p1.getId(), p1);
		
		byte[] s=SerializableUtilOld.serialize(map);
		Map<Integer,NPAdminPermission> map1=SerializableUtilOld.unserializeMap(s);
	}
	
	@Test
	public void test31(){
		
		Map<Integer,Man> map=new HashMap<Integer, Man>();
		Man p=new Man();
		try{
		byte[] s=SerializableUtilOld.serialize(map);
		}catch(Exception e){
			
		}
	}
	
	@Test
	public void test32(){
		
		byte[] s=new byte[]{1,2,2,2};
		
	    try{
	    	SerializableUtilOld.unserializeMap(s);
	      }catch(Exception e){	
	    }
	}
	class Man{
		
		
	}
}
