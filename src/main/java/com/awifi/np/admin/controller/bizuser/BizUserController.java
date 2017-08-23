package com.awifi.np.admin.controller.bizuser;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.dbc.utils.AreaUtil;
import com.awifi.np.admin.entity.*;
import com.awifi.np.admin.service.impl.BizRoleServiceImpl;
import com.awifi.np.admin.service.impl.BizUserRoleServiceImpl;
import com.awifi.np.admin.service.impl.BizUserServiceImpl;
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
 * 创建日期：2017/2/17
 * 创建作者：卢朱娜
 * 文件名称：BizUserController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RestController
@RequestMapping("/admin/bizuser")
public class BizUserController extends BaseController {

    @Autowired
    private BizRoleServiceImpl bizRoleService;
    @Autowired
    private BizUserServiceImpl bizUserService;
    @Autowired
    private BizUserRoleServiceImpl bizUserRoleService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public JSONObject listBizUser(NPPage page,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "provinceId", required = false) Long provinceId,
                                  @RequestParam(value = "cityId", required = false) Long cityId,
                                  @RequestParam(value = "areaId", required = false) Long areaId,
                                  @RequestParam(value = "rid", required = false) Long rid){
        try {
            //获取符合条件的用户id
            List<Long> idList = bizUserService.listPageBizUserId(page, keyword, provinceId, cityId, areaId, rid);

            if(idList.size()==0){
                HashMap<String, Object> retMap = new HashMap<String, Object>();
                retMap.put("list", null);
                retMap.put("totalPage", 0);
                retMap.put("totalRecord", 0);
                retMap.put("page", page.getPage());
                retMap.put("pageSize", page.getPageSize());
                retMap.put("keyword", keyword);
                retMap.put("provinceId", provinceId);
                retMap.put("cityId", cityId);
                retMap.put("areaId", areaId);
                retMap.put("rid", rid);
                return returnSuccess(retMap);
            }
            //获取详情
            List<NPBizUser> bizUserList = bizUserService.listByIds(idList);
            //获取角色
            List<NPBizUserRole> bizUserRoleList = bizUserRoleService.listByUserIds(idList);

            HashMap<Long, ArrayList> map = new HashMap<>();
            for(Long userId : idList){
                map.put(userId, new ArrayList<String>());
            }
            for(NPBizUserRole userRole : bizUserRoleList){
                Long userId = userRole.getUserId();
                map.get(userId).add(userRole.getRoleName());
            }
            for(NPBizUser bizUser : bizUserList){
                bizUser.setRoleNameList(map.get(bizUser.getId()));
                String longname = AreaUtil.getLongName(bizUser.getProvinceId(),bizUser.getCityId(),bizUser.getAreaId());
                bizUser.setAreaLongName(longname);
            }

            HashMap<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("list", bizUserList);
            retMap.put("totalPage", page.getTotalPage());
            retMap.put("totalRecord", page.getTotalRecord());
            retMap.put("page", page.getPage());
            retMap.put("pageSize", page.getPageSize());
            retMap.put("keyword", keyword);
            retMap.put("provinceId", provinceId);
            retMap.put("cityId", cityId);
            retMap.put("areaId", areaId);
            retMap.put("rid", rid);
            return returnSuccess(retMap);
        }catch (Exception e){
            e.printStackTrace();
            return returnError(ECodeException);
        }


    }


    @RequestMapping(value = "/{uid}", method = RequestMethod.GET)
    public JSONObject getBizUser(@PathVariable("uid") Long uid){

        try {
            NPBizUser bizUser = bizUserService.getById(uid);
            if (bizUser == null) {
                return returnError(Constants.ECodeBadParam, "bizrid");
            }
            String longname = AreaUtil.getLongName(bizUser.getProvinceId(),bizUser.getCityId(),bizUser.getAreaId());
            bizUser.setAreaLongName(longname);

            List<NPBizRole> roleList = bizUserRoleService.listRoleByUserId(uid);
            bizUser.setRoleList(roleList);
            return returnSuccess(bizUser);
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

    }


    private JSONObject checkParams(NPBizUser npBizUser) throws Exception {
        List<Long> roleIdList = npBizUser.getRoleIdList();
        Long provinceId = npBizUser.getProvinceId();
        Long cityId = npBizUser.getCityId();
        Long areaId = npBizUser.getAreaId();

        if(StringUtil.isEmpty(npBizUser.getUserName())){
            return returnError(Constants.ECodeParam, "userName");
        }
        if(StringUtil.isEmpty(npBizUser.getContactWay())){
            return returnError(Constants.ECodeParam, "contactWay");
        }
        if(npBizUser.getProvinceId()==null){
            return returnError(Constants.ECodeParam, "provinceId");
        }
        if(npBizUser.getCityId()==null){
            return returnError(Constants.ECodeParam, "cityId");
        }
        if(npBizUser.getAreaId()==null){
            return returnError(Constants.ECodeParam, "areaId");
        }
        if(roleIdList==null || roleIdList.size()==0){
            return returnError(Constants.ECodeParam, "roleIdList");
        }

        if(AreaUtil.getByIdAndPid(provinceId,1L)==null){
            return returnError(Constants.ECodeBadParam, "provinceId");
        }
        if(AreaUtil.getByIdAndPid(cityId,provinceId)==null){
            return returnError(Constants.ECodeBadParam, "cityId");
        }
        if(AreaUtil.getByIdAndPid(areaId,cityId)==null){
            return returnError(Constants.ECodeBadParam, "areaId");
        }

        if(bizRoleService.countByIdList(roleIdList)!=roleIdList.size()){
            return returnError(Constants.ECodeBadParam, "roleIdList");
        }

        return null;

    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public JSONObject addBizUser(@RequestBody  NPBizUser npBizUser, HttpServletRequest request) {

        NPAdminUser adminUser = SessionUtil.getSessionUser(request);

        try {
            JSONObject checkResult = checkParams(npBizUser);
            if(checkResult != null){
                return checkResult;
            }

//            if(bizUserService.countByUserName(npBizUser.getUserName())>0){
//                return returnError("E1019001");
//            }

            String code = bizUserService.addBizUser(npBizUser,adminUser);
            if(code.equals(Constants.ECodeSuccess)) {
                return returnSuccess();
            }else{
                return returnError(code);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
    public JSONObject updateBizUser(@RequestBody  NPBizUser npBizUser, HttpServletRequest request){
        NPAdminUser adminUser = SessionUtil.getSessionUser(request);


        if(npBizUser.getId()==null){
            return returnError(Constants.ECodeParam, "id");
        }

        //重置密码
        if(npBizUser.isResetPassword()){
            try {
                bizUserService.resetPassword(npBizUser, adminUser);
                return returnSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                return returnError(Constants.ECodeException);
            }
        }


        try {

            NPBizUser bizUserOld = bizUserService.getById(npBizUser.getId());
            if(bizUserOld==null){
                return returnError(Constants.ECodeBadParam, "id");
            }

            JSONObject checkResult = checkParams(npBizUser);
            if(checkResult != null){
                return checkResult;
            }

            npBizUser.setPassword(bizUserOld.getPassword());
            String code = bizUserService.updateBizUser(npBizUser,adminUser);
            if(code.equals(Constants.ECodeSuccess)) {
                return returnSuccess();
            }else{
                return returnError(code);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }
    }


    @RequestMapping(value = "{uid}", method = RequestMethod.DELETE)
    public JSONObject deleteBizUser(@PathVariable("uid") Long uid) {

        try{

            bizUserService.deleteById(uid);

            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }
    }


}
