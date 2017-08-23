/**
* 版权所有: 爱WiFi无线运营中心
* 创建日期: 2017-01-18 14:37
* 创建作者: haoxu
* 文件名称: menu.vue
* 版本: v1.0
* 功能: xx
* 修改记录: xx
*/

<template>
    <div class="nav on" >
        <div class="title-left-text">爱WiFi管理系统</div>
        <div class="nav-wrap">
           <!-- <div class="nav-item icon-index branch">
                <router-link to="/" class="nav-title" title="平台管理"  :class="[1==current?'active':'']"  @click="menuClick(1, $event)">
                    <i class="icon"></i>
                    <span style="background-image: none">平台管理</span>
                </router-link>
            </div>
            <div class="nav-item icon-service branch">
                <a href="javascript:void(0);" class="nav-title" title="服务管理"  :class="[2==current?'active':'']" @click="menuClick(2, $event)">
                    <i class="icon"></i><span>服务管理</span>
                    <i class="ios-arrow-up" v-if="2==current "></i>
                    <i class="ios-arrow-down" v-if="2!=current "></i>
                </a>
                <ul class="nav-sub" v-show="2==current">
                    <li><router-link to="/service/list" ><span>服务列表</span></router-link></li>
                    <li><router-link to="/service/interface"><span>接口列表</span></router-link></li>
                    <li><router-link to="/service/menu" ><span>服务菜单列表</span></router-link></li>
                </ul>
            </div>
            <div class="nav-item icon-online branch">
                <a href="javascript:void(0);" class="nav-title" title="平台管理"  :class="[3==current?'active':'']"  @click="menuClick(3, $event)">
                    <i class="icon"></i><span>上线管理</span>
                    <i class="ios-arrow-up" v-if="3==current "></i>
                    <i class="ios-arrow-down" v-if="3!=current "></i>
                </a>
                <ul class="nav-sub" v-show="3==current">
                    <li><router-link to="/online/list" ><span>上线列表</span></router-link></li>
                    <li><router-link to="/online/log"><span>上线日志</span></router-link></li>
                </ul>
            </div>
            <div class="nav-item icon-system branch">
                <a href="javascript:void(0);" class="nav-title" title="平台管理"  :class="[4==current?'active':'']" @click="menuClick(4, $event)">
                    <i class="icon"></i><span>系统角色管理</span>
                    <i class="ios-arrow-up" v-if="4==current "></i>
                    <i class="ios-arrow-down" v-if="4!=current "></i>
                </a>
                <ul class="nav-sub"v-show="4==current">
                    <li><a href="#/system/role" ><span>系统角色</span></a></li>
                    <li><a href="#/system/account"><span>系统账号</span></a></li>
                </ul>
            </div>
            <div class="nav-item icon-grid branch">
                <a href="javascript:void(0);" class="nav-title" title="平台管理"  :class="[5==current?'active':'']"  @click="menuClick(5, $event)">
                    <i class="icon"></i><span>业务信息管理</span>
                    <i class="ios-arrow-up" v-if="5==current "></i>
                    <i class="ios-arrow-down" v-if="5!=current "></i>
                </a>
                <ul class="nav-sub" v-show="5==current">
                    <li><a href="#/information/suit" ><span>套码管理</span></a></li>
                    <li><a href="#/information/template"><span>模板管理</span></a></li>
                    <li><a href="#/information/role" ><span>角色管理</span></a></li>
                    <li><a href="#/information/account"><span>账号管理</span></a></li>
                </ul>
            </div> -->

          <div class="nav-item branch" v-for="(menu, key) in menuLists" :class="['icon-'+menu.menuIcon]">
                <a href="javascript:void(0);" class="nav-title" title="menu.permissionName"
                   v-if="isFolder(menu)"
                   :class="[menu==current?'active':'']"
                   @click="menuClick(menu, $event)" >
                    <i class="icon"></i><span>{{menu.permissionName}}</span>
                    <i class="ios-arrow-up" v-if="menu==current && isFolder(menu)"></i>
                    <i class="ios-arrow-down" v-if="menu!=current && isFolder(menu)"></i>
                </a>
               <a :href="menu.remark" class="nav-title" title="menu.permissionName" v-else
                            :class="[menu==current?'router-link-active':'']" @click="menuClick(menu, $event)">
                    <i class="icon"></i><span>{{menu.permissionName}}</span>
                </a>
                <ul class="nav-sub"  v-if="isFolder(menu)"  v-show="menu==current|| currentUrl == menu">
                    <li v-for="(sub, index) in menu.childPermission" :class="sub==subCurrent?'active':''">
                        <router-link :to="sub.remark|subStr" @click="subMenuClick(sub, $event)">
                            <span>{{sub.permissionName}}</span>
                        </router-link>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script type="text/javascript">
import ajax from '../api/API.js';
    export default{
        name: "Menu",
        props: {
            path:''
        },
        data(){
            return{
                menuUrl:'/admin/index/menus/',
                isShowSub:true,
                //所有菜单
                menuLists:[],
                // 当前一级菜单

                current: null,
                // 当前二级菜单
                subCurrent: null,
                currentUrl:'platform'
            }
        },
        filters: {
            subStr: function (value) {
                return value.substring(1);
            },
            menuFliter: function (value) {
                var arr = value.remark.split("/");
                return arr[1];
            }
        },
     /**
      * 功能：获取菜单列表
      * 修改记录： xx
      * @author 郝旭
      * @date 2017-01-18 16:46
      */
        created:function(){
              let param ="";
              let vm = this;
              this.getCurrentUrl();
              ajax.get(this.menuUrl).then(function (resp) {
                      var resp = resp.data;
                        if(resp.code=="0"){
                            vm.menuLists = resp.data;
                            for(var item of vm.menuLists){
                                vm.openSubMenu(vm.currentUrl,item);
                            }
                        }else if(resp.code == "E1000002"){
                            window.location.href="./login.html";
                        }
                    }).catch(function (error) {
                          vm.$Notice.error({
                              desc:error
                          });
                         setTimeout(function () {
                             window.location.href="./login.html";
                          }, 1000);
                    });
        },
        methods:{
            //获取当前url
            getCurrentUrl:function(){
                var arr = this.path.split("/");
                this.currentUrl = arr[1];
            },
            openSubMenu:function(currentUrl,menuItem){
                if(currentUrl == "platform"){
                    currentUrl="index";
                }
                if(currentUrl == "information"){
                    currentUrl="grid";
                }
                if(menuItem.menuIcon == currentUrl){
                    this.current = menuItem;
                }
            },
            isFolder:function(item) {
                return item.childPermission&& item.childPermission.length
            },
            menuClick: function (current, e) {

                this.current = current;
            },
            subMenuClick: function (current, e) {
                this.subCurrent = current;
            },

        }
    }
</script>
