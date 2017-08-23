package com.awifi.np.admin.constants;

public class Constants {
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(notLogin)|(logout)).*";	//不对匹配该值的访问路径拦截（正则）
	public static final String ADMIN_SESSION_KEY = "AdminUser";
	public static final String SYSTEM_DEFAULT_ENCODING = "UTF-8";
	public static final String LOGIN_URL = "/login";
	public static final Integer VERFITYCODE_HIGHT =26;
	public static final Integer VERFITYCODE_WIDTH = 60;
	public static final String VERFITYCODE_SEED = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String VERFITYCODE_FONT = "Arial";
	public static final int CODE_COUNT = 4;
	public static final int FONT_SIZE = 18;
	public static final String DEFAULT_PASSWORD = "Awifi@123";

	public static String ECodeSuccess = "0"; //传入参数：{%s}为空
	public static String ECodeParam = "E1000001"; //传入参数：{%s}为空
	public static String ECodeException = "E1000004";//系统异常
	public static String ECodeFailed = "E1000005";//操作失败
	public static String ECodeBadParam = "E1000008";//传入参数：{%s}非法
	public static String ECodeToken = "E1000007";//token检验失败
	public static String ECodeRequestBody = "E1000009"; //解析请求体异常
	
	public static final String PERMISSONS_KEY="np_admin_permissons";//所有权限
    public static final String PERMISSON_KEY="np_admin_permisson_";//后面跟roldId，该角色的权限树
    public static final String MENU_KEY="np_admin_menus_";//后面跟roldId，该角色的菜单树
	public static final String PERMISSONS_NODES = "np_admin_permisson_nodes";//树的节点
	public static final String INTERFACE = "np_admin_interface_";//
	public static final String SERVICE = "np_admin_service_";//
	public static final String TEMPLATE = "np_admin_template_";//
	public static final int SECONDS_OF_DAY = 86400;

	public static final int TOKEN_EXPIRE_TIME = 1800;
	public static byte INTERFACE_NOT_REGEISTER = 0;
	public static byte INTERFACE_REGEISTERED = 1;


	//上线状态
	public static byte PS_TO_BE_CONFIG = (byte)1;//待配置
	public static byte PS_TO_BE_TEST = (byte)2;//待测试
	public static byte PS_TO_BE_CHECK = (byte)3;//待审核
	public static byte PS_TO_BE_PUBLISH = (byte)4;//待上线
	public static byte PS_PUBLISHED = (byte)5;//已上线
	public static byte PS_CANCEL = (byte)6;//上线回滚


	public static int SECURITY_CODE_LEN = 32;

	public static byte SERVICR_NOT_ON_LINE = (byte)0;
	public static byte SERVICR_ON_LINE = (byte)1;
	public static byte SERVICR_PUBLISH_ING = (byte)2;
	public static byte SERVICR_UPDATE_ING = (byte)3;


	public static String  REDIS_DBC_AT = "np_admin_dbc_access_token";
	public static String  REDIS_AREA_PRE = "np_admin_area_";
	public static String  REDIS_SUB_AREA_PRE = "np_admin_sub_area_";
//	public static String  REDIS_CALLAPI = "np_admin_callapi_";



	public static void main(String []args){
		
		String rand = String.valueOf(Constants.VERFITYCODE_SEED.charAt(0));
		
	}
}
