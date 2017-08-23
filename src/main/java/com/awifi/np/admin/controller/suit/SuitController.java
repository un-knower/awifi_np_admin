package com.awifi.np.admin.controller.suit;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPSuit;
import com.awifi.np.admin.service.ISuitService;
import com.awifi.np.admin.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 上午9:54:49
 * 创建作者：沈叶峰
 * 文件名称：SuitController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RequestMapping("/admin")
@Controller
public class SuitController extends BaseController {
	@Autowired
	private ISuitService suitService;
	
	/**
	 * 套码列表
	 * @param page
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月18日 上午10:26:57
	 */
	@RequestMapping(value="/suits",method=RequestMethod.GET)
	@ResponseBody
    public  JSONObject  listSuits(NPPage page, @RequestParam(value = "keyword", required = false) String keyword){
        try {
	            HashMap<String,Object> params = new HashMap<String, Object>();
	            params.put("keyword", keyword);
	            page.setParams(params);
        	  JSONObject result = new JSONObject();
			  List<NPSuit> npSuitList = suitService.listPageSuits(page);
			  result.put("suitList", npSuitList);
			  result.put("page", page.getPage());
			  result.put("pageSize", page.getPageSize());
			  result.put("totalRecord", page.getTotalRecord());
			return returnSuccess(result);
        }catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException);
        }
    }
	
	/**
	 * 获取所有套码
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年2月20日 上午11:07:27
	 */
	@RequestMapping(value="/allsuits",method=RequestMethod.GET)
	@ResponseBody
    public  JSONObject  listAllSuits(){
        try {
        	  List<NPSuit> npSuitList = suitService.listAllSuits();
			return returnSuccess(npSuitList);
        }catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException);
        }
    }
	
	/**
	 * 获取单个套码详情
	 * @param suitId
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月24日 下午2:36:06
	 */
	@RequestMapping(value="/suit/{id}",method=RequestMethod.GET)
	@ResponseBody
    public  JSONObject  getSuit(@PathVariable("id") Integer suitId){
        try {
        	if(suitId == null || suitId < 0){
        		return returnError("E1000001","id");
        	}
		    NPSuit suit = suitService.getSuit(suitId);
			return returnSuccess(suit);
        }catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException);
        }
    }
	
	/**
	 * 添加套码
	 * @param suit
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月18日 上午10:27:11
	 */
	@RequestMapping(value="/suit",method=RequestMethod.POST)
	@ResponseBody
    public  JSONObject  addSuit(@RequestBody NPSuit suit, HttpServletRequest request){
        try {
        	NPAdminUser adminUser = getAdminUser(request);
        	if(adminUser != null){
            	suit.setCreateName(adminUser.getLoginAccount());
            	suit.setCreateUserId(adminUser.getId());
        	}
			if(suitService.countBySuitCode(suit.getSuitCode())>0){
        		return returnError("E1017002");
			}
		    Integer suitId = suitService.addSuit(suit);
			return returnSuccess(suitId);
        }catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException);
        }
    }
	
	/**
	 * 修改套码
	 * @param suit
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月18日 上午10:28:31
	 */
	@RequestMapping(value="/suit",method=RequestMethod.PUT)
	@ResponseBody
    public  JSONObject  updateSuit(@RequestBody NPSuit suit){
        try {
			if(suitService.countBySuitCode4update(suit)>0){
				return returnError("E1017002");
			}

		    suitService.updateSuit(suit);
			return returnSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException);
        }
    }
	
	/**
	 * 套码删除
	 * @param id
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月18日 上午10:31:04
	 */
	@RequestMapping(value="/suit/{id}",method=RequestMethod.DELETE)
	@ResponseBody
    public  JSONObject  deleteSuit(@PathVariable("id") Integer id){
        try {
          	if(id == null ||  id < 0){
        		return returnError("E1000001","id");
        	}
		    suitService.deleteSuit(id);
			return returnSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException);
        }
    }
}
