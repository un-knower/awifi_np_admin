package com.awifi.np.admin.service.impl;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPAdminPermissionMapper;
import com.awifi.np.admin.dao.NPAdminRoleMapper;
import com.awifi.np.admin.dao.NPAdminRolePermissionMapper;
import com.awifi.np.admin.entity.*;
import com.awifi.np.admin.service.IAdminPermissionService;
import com.awifi.np.admin.utils.SerializableUtilOld;
import com.awifi.np.admin.utils.redis.JedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月10日 下午3:15:29
 * 创建作者：沈叶峰
 * 文件名称：AdminPermissionServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service("adminPermissionServiceImpl")
public class AdminPermissionServiceImpl implements IAdminPermissionService {
     
	 @Resource
     private NPAdminRolePermissionMapper adminRolePermissionMapper;
     @Resource
     private NPAdminPermissionMapper adminPermissionMapper;
    
     @Resource
     private NPAdminRoleMapper adminRoleMapper;
	 
     private static boolean HAS_DELETE=false;//标记redis该节点是否已删除
     private static boolean HAS_ADD=false;//标记redis该节点是否已添加
	/**
	 * 获取所有权限
	 * @author 王冬冬  
	 * @date 2017年1月19日 上午10:07:59
	 */
	public List<NPAdminPermission> getPermissionListAll() throws Exception {
		
		byte[] data=JedisUtil.get(Constants.PERMISSONS_KEY);
		 List<NPAdminPermission> permissions=null;
		if(data!=null){
		    permissions= SerializableUtilOld.unserialize(data);
		}else{
			/*读取数据库*/
//			NPAdminPermissionCriteria example = new NPAdminPermissionCriteria();
			permissions=adminPermissionMapper.selectByExample(null);
			/*保存节点到redis*/
			Map<Integer, NPAdminPermission> map=buildMap(permissions);//保存完整节点
			JedisUtil.set(Constants.PERMISSONS_NODES,SerializableUtilOld.serialize(map));//将map存到redis
			/*保存树到redis*/
			buildTree(permissions);//保存树
			Collections.sort(permissions, new Comparator<NPAdminPermission>() {
				@Override
				public int compare(NPAdminPermission o1, NPAdminPermission o2) {
					return o1.getListOrder()-o2.getListOrder();
				}
			});
			JedisUtil.set(Constants.PERMISSONS_KEY,SerializableUtilOld.serialize(permissions));
		}
		
		return permissions;
	}

	/**
	 * 获取单个角色的所有权限代码
	 * @author 沈叶峰  
	 * @date 2017年1月10日 下午4:14:39
	 */
	public List<NPAdminRolePermission> getPermissionIdListByRoleId(Integer roleId)
			throws Exception {
		NPAdminRolePermissionCriteria example = new NPAdminRolePermissionCriteria();
		example.createCriteria().andRoleIdEqualTo(roleId);
		return adminRolePermissionMapper.selectByExample(example);
	}

	/**
	 * 新增权限
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:57:27
	 */
	public int addPermisson(NPAdminPermission permission) throws Exception {
		/*插入数据库*/
		adminPermissionMapper.insertSelective(permission);
		/*新增节点到redis*/
		addNodeToMap(permission);//保存节点
		
		/*新增树节点，更新树到redis*/
		byte[] data=JedisUtil.get(Constants.PERMISSONS_KEY);
		List<NPAdminPermission> permissions=null;
		if(data!=null){
		    permissions= SerializableUtilOld.unserialize(data);
		    if(permission.getParentId()==0){//一级权限直接添加
		    	permissions.add(permission);
		    }else{
		       HAS_ADD=false;//新增前重置为false
			   addTreeNode(permissions,permission);
		    }
			JedisUtil.set(Constants.PERMISSONS_KEY,SerializableUtilOld.serialize(permissions));
		}
		return 1;
	}

	/**
	 * 更新权限
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:57:38
	 */
	public int updatePermisson(NPAdminPermission permission) throws Exception {
		
		/*更新数据库*/
//		NPAdminPermissionCriteria criteria=new NPAdminPermissionCriteria();
//		criteria.createCriteria().andIdEqualTo(permission.getId());
//		adminPermissionMapper.updateByExample(permission,criteria);
		adminPermissionMapper.updateByPrimaryKeySelective(permission);
		/*重新保存节点到redis*/
		Map<Integer,NPAdminPermission> map=updateNodeToMap(permission);
		List<NPAdminPermission> permissions=mapToList(map);
		JedisUtil.del(Constants.PERMISSONS_KEY);
		buildTree(permissions);
		Collections.sort(permissions, new Comparator<NPAdminPermission>() {
			@Override
			public int compare(NPAdminPermission o1, NPAdminPermission o2) {
				return o1.getListOrder()-o2.getListOrder();
			}
		});
		JedisUtil.set(Constants.PERMISSONS_KEY,SerializableUtilOld.serialize(permissions));
		return 1;
	}
	
	/**
	 * 根据id获取权限
	 * @author 王冬冬  
	 * @date 2017年1月20日 上午11:22:44
	 */
	public NPAdminPermission getPermissonById(Integer id) throws Exception {
		
		byte[] bytes=JedisUtil.get(Constants.PERMISSONS_NODES);
		NPAdminPermission permisson=null;
		if(bytes!=null){//redis有数据
			Map<Integer,NPAdminPermission> map=(Map<Integer,NPAdminPermission>)SerializableUtilOld.unserialize(bytes);
			permisson=map.get(id);
		}else{
			permisson=adminPermissionMapper.selectByPrimaryKey(id);
		}
		return permisson;
	}
	/**
	 * 分页显示数据
	 * @author 王冬冬  
	 * @date 2017年1月20日 下午2:43:51
	 */
	public List<NPAdminPermission> getPermissionList(NPPage page) {
		return adminPermissionMapper.listPagePermission(page);
	}

	/**
	 * 删除权限
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:57:49
	 */
	public int deletePermisson(String[] ids) throws Exception {
		
		if(ids.length==0||ids==null){
			return 0;
		}
		
		Map<Integer,NPAdminPermission> nodeMap=getNodeFromMap();
		
		List<NPAdminPermission> pList=mapToListByIds(nodeMap,ids);
		if(pList!=null&&!pList.isEmpty()){
			/*删除数据库数据*/
			deleteRolePermission(pList);//删除rolePermission表数据
			deletePermisson(pList);//删除permission表数据
	
			/*删除节点到redis*/
			deleteNodeToMap(ids);
			
			/*删除树节点，更新树到redis*/
			byte[] data=JedisUtil.get(Constants.PERMISSONS_KEY);
			List<NPAdminPermission> permissions=null;
			if(data!=null){
				permissions= SerializableUtilOld.unserialize(data);
				for(NPAdminPermission p:pList){
				 deleteTreeNode(permissions,p);
				}
				JedisUtil.set(Constants.PERMISSONS_KEY,SerializableUtilOld.serialize(permissions));
			}
		}
		return 1;
	}
	
	/**
	 * 根据角色id获取权限树
	 * @author 王冬冬  
	 * @date 2017年1月19日 下午3:13:35
	 */
	public List<NPAdminPermission> getPermissionListByRoleId(Integer roleId) throws Exception {
		
		byte[] data=JedisUtil.get(Constants.PERMISSON_KEY+roleId);
		List<NPAdminPermission> permissions=null;
		if(data!=null){
		    permissions=SerializableUtilOld.unserialize(data);
		}else{//redis 中不存在
			/*读取数据库*/
			NPAdminRole role=adminRoleMapper.selectByRoleId(roleId);
			if(role!=null){
				permissions=role.getPermissions();
				/*保存节点到redis*/
				buildTree(permissions);
				Collections.sort(permissions, new Comparator<NPAdminPermission>() {
					@Override
					public int compare(NPAdminPermission o1, NPAdminPermission o2) {
						return o1.getListOrder()-o2.getListOrder();
					}
				});
			    JedisUtil.set(Constants.PERMISSON_KEY+roleId,SerializableUtilOld.serialize(permissions));
			}
		
		}
		return permissions;
	}
	
	/**
	 * 根据角色id获取菜单树
	 * @author 王冬冬  
	 * @throws Exception 
	 * @date 2017年1月19日 下午3:13:35
	 */
	public List<NPAdminPermission> getMenusListByRoleId(Integer roleId) throws Exception {

		byte[] data=JedisUtil.get(Constants.MENU_KEY+roleId);
		List<NPAdminPermission> menus=null;
		if(data!=null){
			menus=SerializableUtilOld.unserialize(data);
		}else{//redis 中不存在
			/*读取数据库*/
			NPAdminRole role=adminRoleMapper.selectMenusByRoleId(roleId);
			if(role!=null){
				menus=role.getPermissions();
				/*保存节点到redis*/
				buildTree(menus);
				Collections.sort(menus, new Comparator<NPAdminPermission>() {
					@Override
					public int compare(NPAdminPermission o1, NPAdminPermission o2) {
						return o1.getListOrder()-o2.getListOrder();
					}
				});
				JedisUtil.set(Constants.MENU_KEY+roleId,SerializableUtilOld.serialize(menus));
			}

		}
		return menus;
	}
	
	
//	/**
//	 * 获取非菜单的权限列表
//	 * @param roleId
//	 * @return
//	 * @throws Exception
//	 * @author 王冬冬
//	 * @date 2017年1月20日 下午5:04:39
//	 */
//	private List<NPAdminPermission> getNotMenusListByRoleId(Integer roleId) throws Exception {
//		List<NPAdminPermission> permissions=adminRoleMapper.selectByRoleId(roleId).getPermissions();
//		for(NPAdminPermission p:permissions){
//			if(p.getIsMenu().byteValue()==1){//如果是菜单，删除
//				permissions.remove(p);
//			}
//		}
//		return permissions;
//	}

	/**
	 * 递归删除permission表数据
	 * @param pList
	 * @author 王冬冬  
	 * @date 2017年1月19日 下午3:44:25
	 */
	private void deletePermisson(List<NPAdminPermission> pList) {	
		if(pList.isEmpty()){
			return;
		}
		for(NPAdminPermission p:pList){
			adminPermissionMapper.deleteByPrimaryKey(p.getId());
			deletePermisson(p.getChildPermission());
		}
	}

	/**
	 * 递归删除RolePermission表数据
	 * @param pList
	 * @author 王冬冬  
	 * @date 2017年1月19日 下午3:41:20
	 */
	private void deleteRolePermission(List<NPAdminPermission> pList) {
		if(pList.isEmpty()){
			return;
		}
		for(NPAdminPermission p:pList){
//			NPAdminRolePermissionCriteria example = new NPAdminRolePermissionCriteria();
//			example.createCriteria().andPermissionIdEqualTo(p.getId());
			adminRolePermissionMapper.deleteByPermissionIds(p.getId());
			deletePermisson(p.getChildPermission());//递归删除表数据
		}
		
	}

	/**
	 * 构建权限树
	 * @param permissions
	 * @author 王冬冬  
	 * @date 2017年1月18日 下午2:24:18
	 */
	private void buildTree(List<NPAdminPermission> permissions) {
		 //buid权限树
		 Map<Integer,NPAdminPermission> map=new HashMap<Integer, NPAdminPermission>();
	     for(NPAdminPermission np:permissions){
	    	 map.put(np.getId(), np);
	     }  
	     permissions.clear();//清空
	     for(Map.Entry<Integer, NPAdminPermission> maps:map.entrySet()){
	    	 NPAdminPermission temp=maps.getValue();
	        	while(temp.getParentId()!=0){
	        		NPAdminPermission fa=map.get(temp.getParentId());
	        		if(fa==null){
	        			break;
	        		}
	        		fa.addSub(temp);
	        		temp=fa;
	        	}

	        }
		for(Map.Entry<Integer, NPAdminPermission> maps:map.entrySet()){
			NPAdminPermission temp=maps.getValue();
			if(temp.getParentId()==0){
				permissions.add(temp);
			}
		}
	}
	/**
	 * 递归添加节点
	 * @param permissions
	 * @param permission
	 * @author 王冬冬  
	 * @date 2017年1月18日 下午3:57:24
	 */
	private void addTreeNode(List<NPAdminPermission> permissions,NPAdminPermission permission) {
		if(permissions.size()==0){
			return;
		}
		for(NPAdminPermission np:permissions){
			if(np.getId().intValue()==permission.getParentId().intValue()){
				np.addSub(permission);
				HAS_ADD=true;
				return;
			}
			addTreeNode(np.getChildPermission(),permission);//递归添加节点
			if(HAS_ADD){
				break;
			}
		}
	}
	/**
	 * 删除该节点以及他的子节点
	 * @param permissions
	 * @param permission
	 * @throws Exception
	 * @author 王冬冬  
	 * @date 2017年1月18日 下午4:48:17
	 */
	private void deleteTreeNode(List<NPAdminPermission> permissions,NPAdminPermission permission) throws Exception{
		if(permissions.isEmpty()){
			return;
		}
		for(NPAdminPermission p:permissions){
			if(p.getId().intValue()==permission.getParentId().intValue()){
				p.removeSub(permission);
				HAS_DELETE=true;
				break;
			}
			deleteTreeNode(p.getChildPermission(),permission);//递归查找删除
			if(HAS_DELETE){
				break;
			}
		}
	}
	
	/**
	 * 保存节点
	 * @param permissions
	 * @author 王冬冬  
	 * @return 
	 * @date 2017年1月19日 下午3:19:03
	 */
	private Map<Integer, NPAdminPermission> buildMap(List<NPAdminPermission> permissions) {
		Map<Integer,NPAdminPermission> map=new HashMap<Integer, NPAdminPermission>();
	     for(NPAdminPermission np:permissions){
	    	 map.put(np.getId(), np);
	     }  
	     
	     return map;
	}
	/**
	 * 新增节点到map
	 * @param permission
	 * @author 王冬冬  
	 * @date 2017年1月19日 下午2:25:19
	 */
	private void addNodeToMap(NPAdminPermission permission) throws Exception {
		
		byte[] bytes=JedisUtil.get(Constants.PERMISSONS_NODES);
		Map<Integer,NPAdminPermission> map=null;
		if(bytes!=null){
		   map=SerializableUtilOld.unserializeMap(bytes);
		}else{
			map=new HashMap<Integer,NPAdminPermission>();
		}
		map.put(permission.getId(), permission);
		JedisUtil.set(Constants.PERMISSONS_NODES,SerializableUtilOld.serialize(map));
	}
	/**
	 * 更新node到map
	 * @param permission
	 * @author 王冬冬  
	 * @date 2017年1月19日 下午4:30:03
	 */
	private Map<Integer,NPAdminPermission> updateNodeToMap(NPAdminPermission permission) throws Exception {
		
		byte[] bytes=JedisUtil.get(Constants.PERMISSONS_NODES);
		Map<Integer,NPAdminPermission> map=null;
		if(bytes!=null){
		   map=SerializableUtilOld.unserializeMap(bytes);
		}else{
			map=getNodeFromMap();
		}
		map.remove(permission.getId());
		map.put(permission.getId(), permission);
		JedisUtil.set(Constants.PERMISSONS_NODES,SerializableUtilOld.serialize(map));
		return map;
	}
	/**
	 * 删除map中的节点
	 * @param ids
	 * @author 王冬冬  
	 * @date 2017年1月19日 下午2:51:10
	 */
	private void deleteNodeToMap(String[] ids) throws Exception {
		byte[] bytes=JedisUtil.get(Constants.PERMISSONS_NODES);
		Map<Integer,NPAdminPermission> map=null;
		if(bytes!=null){
		   map=SerializableUtilOld.unserializeMap(bytes);
		}else{
			map=new HashMap<Integer,NPAdminPermission>();
		}
		for(String id:ids){
			if(map.keySet().contains(Integer.parseInt(id))){
				map.remove(Integer.parseInt(id));
			}
		}
		JedisUtil.set(Constants.PERMISSONS_NODES,SerializableUtilOld.serialize(map));
	}
	
	/**
	 * 获取map中的节点
	 * @param
	 * @author 王冬冬  
	 * @date 2017年1月19日 下午2:51:10
	 */
	private Map<Integer,NPAdminPermission> getNodeFromMap() throws Exception {
		byte[] bytes=JedisUtil.get(Constants.PERMISSONS_NODES);
		Map<Integer,NPAdminPermission> map=null;
		if(bytes!=null){
		   map=(Map<Integer,NPAdminPermission>)SerializableUtilOld.unserialize(bytes);
		   JedisUtil.set(Constants.PERMISSONS_NODES,SerializableUtilOld.serialize(map));
		}else{
//			NPAdminPermissionCriteria example = new NPAdminPermissionCriteria();
			List<NPAdminPermission>	permissions=adminPermissionMapper.selectByExample(null);
			map=buildMap(permissions);
		    JedisUtil.set(Constants.PERMISSONS_NODES,SerializableUtilOld.serialize(map));
		}
		return map;
	}
	
	/**
	 * 将list转map
	 * @param map
	 * @return
	 * @author 王冬冬
	 * @date 2017年1月20日 上午9:00:21
	 */
	private List<NPAdminPermission> mapToList(Map<Integer, NPAdminPermission> map) {
		List<NPAdminPermission> list = new ArrayList<NPAdminPermission>();
		for (Map.Entry<Integer, NPAdminPermission> m : map.entrySet()) {
			list.add(m.getValue());
		}
		return list;
	}

	/**
	 * 根据id查询List转成map
	 * @param nodeMap
	 * @param ids
	 * @return
	 * @author 王冬冬
	 * @date 2017年1月19日 下午3:32:19
	 */
	private List<NPAdminPermission> mapToListByIds(Map<Integer, NPAdminPermission> nodeMap, String[] ids) {
		if(nodeMap.isEmpty()){
			return null;
		}
		List<NPAdminPermission> permissions = null;
		if(ids.length!=0){
			permissions=new ArrayList<NPAdminPermission>();
			for (String id : ids) {
				permissions.add(nodeMap.get(Integer.parseInt(id)));
			}
		}
		return permissions;
	}
	
}
