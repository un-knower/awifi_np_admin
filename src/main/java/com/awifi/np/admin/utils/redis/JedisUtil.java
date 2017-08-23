package com.awifi.np.admin.utils.redis;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class JedisUtil {

	public static Jedis createJedis() {
		Jedis jedis = new Jedis("127.0.0.1");
		return jedis;
	}

	public static Jedis createJedis(String host, int port) {
		Jedis jedis = new Jedis(host, port);
		return jedis;
	}

	public static Jedis createJedis(String host, int port, String passwrod) {
		Jedis jedis = new Jedis(host, port);
		if (!StringUtils.isNotBlank(passwrod))
			jedis.auth(passwrod);

		return jedis;
	}


	public static String getValue(String key){
		Jedis jedis = JedisPoolUtils.getJedis();
		String value = null;
		try {
			value = jedis.get(key);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}
		return value;
	}


	public static void set(String key, String value){
		Jedis jedis = JedisPoolUtils.getJedis();
		try {
			jedis.set(key, value);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}

	}


	public static void setex(String key, int second, String value){
		Jedis jedis = JedisPoolUtils.getJedis();
		try {
			jedis.setex(key, second, value);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}

	}

	public static void del(String key){
		Jedis jedis = JedisPoolUtils.getJedis();
		try {
			jedis.del(key);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}
	}
	public static void del(byte[] key) {
		Jedis jedis = JedisPoolUtils.getJedis();
		try {
			jedis.del(key);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}
		
	}

	public static String hget(String key, String field){
		Jedis jedis = JedisPoolUtils.getJedis();
		String value = null;
		try {
			value = jedis.hget(key, field);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}
		return value;
	}


	public static void hset(String key, String field, String value){
		Jedis jedis = JedisPoolUtils.getJedis();
		try {
			jedis.hset(key, field, value);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}
	}


	public static void hdel(String key, String field){
		Jedis jedis = JedisPoolUtils.getJedis();
		try {
			jedis.hdel(key, field);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}
	}

	public static Map<String, String> hgetAll(String key){
		Jedis jedis = JedisPoolUtils.getJedis();
		Map<String, String> map = null;
		try {
			map = jedis.hgetAll(key);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}
		return map;
	}

//	public static <T> void set(String key, List<T> list){
//		Jedis jedis = JedisPoolUtils.getJedis();
//		try {
//			jedis.set(key.getBytes(), SerializableUtil.serialize(list));
//		}catch (Exception e){
//			e.printStackTrace();
//		}finally {
//			JedisPoolUtils.returnRes(jedis);
//		}
//
//	}
	
	public static void set(String key,byte[] bytes){
		Jedis jedis = JedisPoolUtils.getJedis();
		try {
			jedis.set(key.getBytes(),bytes);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}

	}
	
	public static <T> byte[] get(String key){
		Jedis jedis = JedisPoolUtils.getJedis();
		try {
			byte[] bytes =jedis.get(key.getBytes());
			return bytes==null?null:bytes;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}
	}



	public static void set(String key, String value,int seconds){
		Jedis jedis = JedisPoolUtils.getJedis();
		try {
			jedis.set(key, value);
			jedis.expire(key, seconds);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}

	}

	public static Set<String> keys(String keyreg){
		Jedis jedis = JedisPoolUtils.getJedis();
		try {
			return jedis.keys(keyreg);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}
		return null;
	}


	//需要释放资源
	public static Set<String> deleteBulk( String keyreg){
		Jedis jedis = JedisPoolUtils.getJedis();
		try {
			Set<String> keySet = jedis.keys(keyreg);
			if (keySet != null || keySet.size() > 0) {
				for (String key : keySet) {
					jedis.del(key);
				}
			}
			return keySet;
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			JedisPoolUtils.returnRes(jedis);
		}
		return null;
	}

//	public static <T> List<T> get(String key){
//		Jedis jedis = JedisPoolUtils.getJedis();
//		try {
//			byte[] bytes =jedis.get(key.getBytes());
//			return bytes==null?null:SerializableUtil.unserialize(bytes);
//		}catch (Exception e){
//			e.printStackTrace();
//			return null;
//		}finally {
//			JedisPoolUtils.returnRes(jedis);
//		}
//
//	}
public static void main(String[] args) {
//		del("permissons");
//		del("permisson_nodes");
//		del("permisson_1001");
//		del("menus_1001");
	//[116, 101, 109, 112, 108, 97, 116, 101, 95, 83, 95, 85, 83, 82, 45, 115, 117, 112, 101, 114, 97, 100, 109, 105, 110, 95, 118, 49, 45, 114, 111, 108, 101, 95, 97, 100, 100]
//	del("template_S_USR-null-role_add".getBytes());
//
//    Set<String> keys=keys("interface_S_MER_*");
//    keys.forEach((i)->{
//        del(i);
//    });
//	del("template_S_USR-superadmin_v1-test");
//	del("template_S_USR-superadmin_v1-whiteuser_add");
//	del("template_S_PUB-superadmin_v1-login");
//	del("template_null-superadmin_v1-list");
//	del("template_S_PROJ-superadmin_v1-list");
//	del("template_S_MER-superadmin_v1-list");
//	del("template_S_PUB-superadmin_v1-index");
//	del("template_S_MER-superadmin_v1-add");
//	del("template_S_PROJ-superadmin_v1-view");
//	del("template_S_PROJ-superadmin_v1-edit");
//	del("template_S_PROJ-superadmin_v1-add");
//	del("template_S_MER-superadmin_v1-view");
//	del("template_S_MER-superadmin_v1-edit");
//	del("template_S_MER-superadmin_v1-addsub");
//	
//	
//	del("template_S_MERDEV-superadmin_v1-device_list");
//	del("template_S_MERDEV-superadmin_v1-device_edit");
//	del("template_S_MERDEV-superadmin_v1-device_view");
//	del("template_S_MERDEV-superadmin_v1-entity_list");
//	del("template_S_MERDEV-superadmin_v1-hotarea_list");
//	del("template_S_MERDEV-superadmin_v1-device_allot");
//	
//	del("template_S_USR-superadmin_v1-staticuser_list");
//	del("template_S_USR-superadmin_v1-staticuser_add");
//	del("template_S_USR-superadmin_v1-staticuser_edit");
//	del("template_S_USR-superadmin_v1-blackuser_list");
//	del("template_S_USR-superadmin_v1-blackuser_add");
//	
//	
//	del("template_S_PUB-superadmin_v1-user_info");
//	del("template_S_PUB-superadmin_v1-user_password");
//	del("template_S_PUB-superadmin_v1-sysconfig_list");
//	
//	del("template_S_USR-superadmin_v1-role_list");
//	del("template_S_USR-superadmin_v1-role_add");
//	del("template_S_USR-superadmin_v1-role_view");
//	
//	del("template_S_USR-superadmin_v1-role_edit");
//	del("template_S_USR-superadmin_v1-whiteuser_list");
//	del("template_S_USR-superadmin_v1-whiteuser_add");
//	
//	del("template_S_PUB-superadmin_v1-list");
//	
//    del("interface_S_USR_/usrsrv/whiteusers:POST");
//   
//   
//   
//   del("template_S_MER-superadmin_v1-ll");
//   del("template_S_MER-superadmin_v1-msg_add");
//   del("template_S_MER-superadmin_v1-msg_edit");
//   del("template_S_MER-superadmin_v1-msg_list");
//   del("template_S_MER-superadmin_v1-switch_list");
//   del("template_S_MERDEV-superadmin_v1-list");
//   del("template_S_MERDEV-superadmin_v1-unbind_list");
//   del("template_S_PORTAL-superadmin_v1-portal_component_list");
//   del("template_S_PORTAL-superadmin_v1-portal_default_site_list");
//   
//   
//   del("interface_S_DEV_/devsrv/combo/manage:POST");
//   del("interface_S_DEV_/devsrv/combo/manage:DELETE");
//   del("interface_S_DEV_/devsrv/combo/manage:GET");
//   del("interface_S_DEV_/devsrv/combo/{comboId}:DELETE");
//   del("interface_S_DEV_/devsrv/combo:GET");
//   del("interface_S_DEV_/devsrv/combo:POST");
//   del("interface_S_DEV_/devsrv/device/{deviceid}:GET");
//   del("interface_S_DEV_/devsrv/devices/fatap/getlist:GET");
//   del("interface_S_DEV_/devsrv/devices/ssid/batch:POST");
//   del("template_S_USR-superadmin_v1-staticuser_list");
//   del("interface_S_DEV_/devsrv/devices/switch/escape:PUT");
//   del("interface_S_DEV_/devsrv/devices/switch/lan:PUT");
//   del("interface_S_DEV_/devsrv/devices/switch/ssid/awifi:PUT");
//   del("interface_S_DEV_/devsrv/devices/switch/ssid/chinanet:PUT");
//   del("interface_S_DEV_/devsrv/devices/ssid/{ssid}:PUT");
//   del("interface_S_DEV_/devsrv/devices/timeout:PUT");
//   del("interface_S_DEV_/devsrv/file/{filename:.*}:GET");
//   del("interface_S_DEV_/devsrv/hotareas/relation:POST");
//   del("interface_S_DEV_/devsrv/unbind:PUT");
//   
//   del("interface_S_MERDEV_/merdevsrv/devices:GET");
//   del("interface_S_MERDEV_/merdevsrv/entityinfos/xls:GET");
//   del("interface_S_MERDEV_/merdevsrv/entityinfos:GET");
//   del("interface_S_MERDEV_/merdevsrv/file/{filename:.*}:GET");
//   del("interface_S_MERDEV_/merdevsrv/hotareas/xls:GET");
//   del("interface_S_MERDEV_/merdevsrv/hotareas:GET");
//   del("interface_S_MERDEV_/merdevsrv/menus:GET");
//   del("interface_S_MERDEV_/merdevsrv/merchant/{id}/switch/status:GET");
//   del("interface_S_MERDEV_/merdevsrv/merchant/{merchantid}/devinfo:GET");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/antirobber:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/awifi:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/chinanet:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/escape:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/lan:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/timeout:PUT");
//   del("interface_S_MERDEV_/merdevsrv/owner:POST");
//   del("interface_S_MERDEV_/merdevsrv/owner:PUT");
//   
//   
//   
//   del("interface_S_MER_/mersrv/file/{filename:.*}:GET");
//   del("interface_S_MER_/mersrv/menus:GET");
//   del("interface_S_MER_/mersrv/merchant/{id}/resetpassword:GET");
//   del("interface_S_MER_/mersrv/merchant/{id}:GET");
//   del("interface_S_MER_/mersrv/merchant/{id}:PUT");
//   del("interface_S_MER_/mersrv/merchant:POST");
//   del("interface_S_MER_/mersrv/merchants:GET");
//   del("interface_S_MER_/mersrv/merchants:POST");
//   del("interface_S_MER_/mersrv/org/{orgid}/roles:GET");
//   del("interface_S_MER_/mersrv/smsconfig/{id}:DELETE");
//   del("interface_S_MER_/mersrv/smsconfig/{id}:PUT");
//   del("interface_S_MER_/mersrv/smsconfig/{id}:GET");
//   del("interface_S_MER_/mersrv/smsconfig:POST");
//   del("interface_S_MER_/mersrv/smsconfigs:GET");
//   del("interface_S_MER_/mersrv/submerchant:POST");
//  
//   
//   del("interface_S_PROJ_/projsrv/menus:GET");
//   del("interface_S_PROJ_/projsrv/project/{id}:DELETE");
//   del("interface_S_PROJ_/projsrv/project/{id}:GET");
//   del("interface_S_PROJ_/projsrv/project:POST");
//   del("interface_S_PROJ_/projsrv/projects/projectIds:GET");
//   del("interface_S_PROJ_/projsrv/projects/xls:GET");
//   del("interface_S_PROJ_/projsrv/projects:GET");
//   
//   del("interface_S_PUB_/pubsrv/1st/menus:GET");
//   del("interface_S_PUB_/pubsrv/authcode:GET");
//   del("interface_S_PUB_/pubsrv/industrys:GET");
//   del("interface_S_PUB_/pubsrv/menus:GET");
//   del("interface_S_PUB_/pubsrv/provinces:GET");
//   
//   del("interface_S_TIMEBUY_/timebuysrv/merchant/news/list:GET");
//   del("interface_S_TIMEBUY_/timebuysrv/merchant/news:POST");
//   
//   
//   del("interface_S_USR_/usrsrv/blackuser/{id}:DELETE");
//   del("interface_S_USR_/usrsrv/blackuser:POST");
//   del("interface_S_USR_/usrsrv/blackusers:GET");
//   del("interface_S_USR_/usrsrv/file/{filename:.*}:GET");
//   del("interface_S_USR_/usrsrv/menus:GET");
//   del("interface_S_USR_/usrsrv/role/{roleId}:GET");
//   del("interface_S_USR_/usrsrv/staticuser/{id}:DELETE");
//   del("interface_S_USR_/usrsrv/staticuser:POST");
//   del("interface_S_USR_/usrsrv/staticusers/merchant/{merchantid}:DELETE");
//   del("interface_S_USR_/usrsrv/staticusers:DELETE");
//   del("interface_S_USR_/usrsrv/staticusers:GET");
//   del("interface_S_USR_/usrsrv/staticusers:POST");
//   del("interface_S_USR_/usrsrv/user/{id}:GET");
//   del("interface_S_USR_/usrsrv/users:GET");
//   del("interface_S_USR_/usrsrv/whiteuser/file/{filename:.*}:GET");
//   del("interface_S_USR_/usrsrv/whiteuser:POST");
//   del("interface_S_USR_/usrsrv/whiteusers/{id}:DELETE");
//   del("interface_S_USR_/usrsrv/whiteusers:DELETE");
//   del("interface_S_USR_/usrsrv/whiteusers:GET");
//   del("interface_S_USR_/usrsrv/whiteusers:PUT");
//   
//   
//   del("interface_S_MERDEV_/merdevsrv/devices:GET");
//   del("interface_S_MERDEV_/merdevsrv/entityinfos/xls:GET");
//   del("interface_S_MERDEV_/merdevsrv/entityinfos:GET");
//   del("interface_S_MERDEV_/merdevsrv/file/{filename:.*}:GET");
//   del("interface_S_MERDEV_/merdevsrv/hotareas/xls:GET");
//   del("interface_S_MERDEV_/merdevsrv/hotareas:GET");
//   del("interface_S_MERDEV_/merdevsrv/menus:GET");
//   del("interface_S_MERDEV_/merdevsrv/merchant/{id}/switch/status:GET");
//   del("interface_S_MERDEV_/merdevsrv/merchant/{merchantid}/devinfo:GET");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/antirobber:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/awifi:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/chinanet:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/escape:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/lan:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/timeout:PUT");
//   del("interface_S_MERDEV_/merdevsrv/owner:POST");
//   del("interface_S_MERDEV_/merdevsrv/owner:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/escape:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/lan:PUT");
//   del("interface_S_MERDEV_/merdevsrv/merchants/switch/timeout:PUT");
//   del("interface_S_MERDEV_/merdevsrv/owner:POST");
//   del("interface_S_MERDEV_/merdevsrv/owner:PUT");
}


}
