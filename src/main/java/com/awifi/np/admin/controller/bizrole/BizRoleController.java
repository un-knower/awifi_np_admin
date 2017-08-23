package com.awifi.np.admin.controller.bizrole;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.*;
import com.awifi.np.admin.service.impl.BizRoleServiceImpl;
import com.awifi.np.admin.service.impl.BizSuitRoleServiceImpl;
import com.awifi.np.admin.service.impl.BizUserRoleServiceImpl;
import com.awifi.np.admin.service.impl.SuitServiceImpl;
import com.awifi.np.admin.utils.SessionUtil;
import com.awifi.np.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/13
 * 创建作者：卢朱娜
 * 文件名称：BizRoleController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RestController
@RequestMapping("/admin/bizrole")
public class BizRoleController extends BaseController{

    @Autowired
    private BizRoleServiceImpl bizRoleService;
    @Autowired
    private BizSuitRoleServiceImpl bizSuitRoleService;
    @Autowired
    private SuitServiceImpl suitService;
    @Autowired
    private BizUserRoleServiceImpl bizUserRoleService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public JSONObject listBizRole(NPPage page,
                                  @RequestParam(value = "keyword", required = false) String keyword){


        try {

            //获取所有
            if(page.getPageSize()<0){
                List<NPBizRole> list = bizRoleService.listAll();
                return returnSuccess(list);
            }

            //获取符合条件的角色id
            List<Long> idList = bizRoleService.listPageBizRoleId(page,keyword);

            if(idList.size()==0){
                HashMap<String, Object> retMap = new HashMap<String, Object>();
                retMap.put("list", null);
                retMap.put("totalPage", 0);
                retMap.put("totalRecord", 0);
                retMap.put("page", page.getPage());
                retMap.put("pageSize", page.getPageSize());
                retMap.put("keyword", keyword);
                return returnSuccess(retMap);
            }

            //获取详情
            List<NPBizRole> bizRoleList = bizRoleService.listByIds(idList);
            //获取套码
            List<NPBizSuitRole> bizSuitRoleList = bizSuitRoleService.listByRoleIds(idList);

            HashMap<Long, ArrayList> map = new HashMap<>();
            for(Long roleId : idList){
                map.put(roleId,new ArrayList<String>());
            }
            for(NPBizSuitRole suitRole : bizSuitRoleList){
                Long roleId = suitRole.getRoleId();
                map.get(roleId).add(suitRole.getSuitCode());
            }
            for(NPBizRole bizRole : bizRoleList){
                    bizRole.setSuitCodeList(map.get(bizRole.getId()));
            }

            HashMap<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("list", bizRoleList);
            retMap.put("totalPage", page.getTotalPage());
            retMap.put("totalRecord", page.getTotalRecord());
            retMap.put("page", page.getPage());
            retMap.put("pageSize", page.getPageSize());
            retMap.put("keyword", keyword);
            return returnSuccess(retMap);
        }catch (Exception e){
            e.printStackTrace();
            return returnError(ECodeException);
        }

    }

    @RequestMapping(value = "/{bizrid}", method = RequestMethod.GET)
    public JSONObject getBizRole(@PathVariable("bizrid") Long bizrid){
        try {
            NPBizRole bizRole = bizRoleService.getById(bizrid);
            if (bizRole == null) {
                return returnError(Constants.ECodeBadParam, "bizrid");
            }
            List<String> suitCodList = bizSuitRoleService.listSuiteCodeByRoleId(bizrid);
            bizRole.setSuitCodeList(suitCodList);
            return returnSuccess(bizRole);
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

    }

    private JSONObject checkParams(NPBizRole npBizRole) throws Exception {
        List<String> suitCodeList = npBizRole.getSuitCodeList();

        if(StringUtil.isEmpty(npBizRole.getRoleName())){
            return returnError(ECodeParam, "roleName");
        }
        if(suitCodeList == null){
            return returnError(ECodeParam, "suitCodeList");
        }
        if(suitCodeList.size()==0){
            return returnError(ECodeParam, "suitCodeList");
        }
        if(suitCodeList.size() != suitService.countBySuitCodeList(suitCodeList)){
            return returnError(ECodeBadParam, "suitCodeList");
        }

        return null;

    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public JSONObject addBizRole(@RequestBody NPBizRole npBizRole,HttpServletRequest request){
        NPAdminUser adminUser = SessionUtil.getSessionUser(request);


        try {

            JSONObject checkResult = checkParams(npBizRole);
            if(checkResult != null){
                return checkResult;
            }

            String code = bizRoleService.addBizRole(npBizRole, adminUser);
            if(code.equals(Constants.ECodeSuccess)) {
                return returnSuccess();
            }else{
                return returnError(code);
            }
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }
    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
    public JSONObject updateBizRole(@RequestBody NPBizRole newNPBizRole){
        List<String> suitCodeList = newNPBizRole.getSuitCodeList();

        if(newNPBizRole.getId()==null){
            return returnError(Constants.ECodeParam, "id");
        }


        try {

            NPBizRole npBizRole = bizRoleService.getById(newNPBizRole.getId());
            if(npBizRole==null){
                return returnError(ECodeBadParam, "id");
            }

            JSONObject checkResult = checkParams(newNPBizRole);
            if(checkResult != null){
                return checkResult;
            }

            String code = bizRoleService.updateBizRole(newNPBizRole);
            if(code.equals(Constants.ECodeSuccess)) {
                return returnSuccess();
            }else{
                return returnError(code);
            }
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

    }


    @RequestMapping(value = "{bizrid}", method = RequestMethod.DELETE)
    public JSONObject deleteBizRole(@PathVariable("bizrid") Long bizrid) {

        try{

            if(bizUserRoleService.countByRoleId(bizrid)>0){
                return returnError("E1018002");
            }

            bizRoleService.deleteById(bizrid);
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }
    }

}
