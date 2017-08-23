/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-01-19 08:57
 * 创建作者: haoxu
 * 文件名称: menu-list.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div class="container">
        <div class="search-bar">
            <ul>
                <li>
                    <a-input placeholder="请输入服务名称/菜单名称" v-model="menuKeywords" class="input-width"></a-input>
                </li>
                <li>
                    <a-select v-model="MenuSelect"  placeholder="是否为菜单" clearable>
                        <a-option :value="2">全部</a-option>
                        <a-option  :value="1">是</a-option>
                        <a-option  :value="0">否</a-option>
                    </a-select>
                </li>
                <li>
                    <a-button type="primary" @click="search" icon="search">查询</a-button>
                </li>
            </ul>
        </div>
        <div class="tables">
        <table>
            <thead>
            <tr>
                <th v-for="(col,index) in columns" :style="{ width:trWidth(index) }">
                    {{ col }}
                </th>
            </tr>
            </thead>
            <tbody ref="serviceTable">
            <tr v-for="(item,index) in dataList">
                <td :style="{ width:trWidth(0)}">{{index+1}}</td>
                <td :style="{ width:trWidth(1)}">{{item.serviceName}}</td>
                <td :style="{ width:trWidth(2)}">{{item.menuName}}</td>
                <td :style="{ width:trWidth(3)}">{{item.menuUrl}}</td>
                <td :style="{ width:trWidth(4)}" v-if="!item.isMenu && item.isMenu != 0">暂无</td>
                <td :style="{ width:trWidth(4)}" v-else>{{item.isMenu ? '是':'否'}}</td>
                <td :style="{ width:trWidth(5)}">{{item.hasSubmenu ? '是':'否'}}</td>
                <td :style="{ width:trWidth(6)}">{{item.targetId}}</td>
                <td :style="{ width:trWidth(7) }" class="ivu-table-cell">
                    <span @click="detail(item.id)">
                         <a href="javascript:void(0);" title="查看" class="ivu-btn ivu-btn-eye ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-eye"></i>
                         </a>
                    </span>
                    <span  @click="editMenu(item.id)">
                         <a href="javascript:void(0);" title="编辑" class="ivu-btn ivu-btn-edit ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-edit"></i>
                         </a>
                    </span>
                </td>
            </tr>
            <tr class="noInfo" v-if="!dataList || dataList.length == 0">没有找到相关数据</tr>
            </tbody>
        </table>
            </div>
        <np-dialog :show-dialog="editShow" :title = "titles[0]" v-on:close ="closeDig">
            <div class="dialog-body" slot="body">
                <div class="form-group">
                    <div class="input-group clearfix">
                        <label>服务名称：</label>
                        <span class="form-text" v-text="serviceName"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label for="code">菜单名称：</label>
                        <input type="text" id="code" v-model="menuName">
                    </div>
                    <div class="input-group clearfix">
                        <label for="code">菜单URL：</label>
                        <input type="text" id="code2" v-model="menuUrl">
                    </div>
                    <div class="input-group clearfix">
                        <label for="platform">是否为菜单:</label>
                        <select id="platform" v-model="isMenus">
                            <option value="">请选择</option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </div>
                    <div class="input-group clearfix">
                        <label for="platform2">显示下级菜单:</label>
                        <select id="platform2" v-model="hasSubmenu">
                            <option value="">请选择</option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </div>
                    <div class="input-group clearfix">
                        <label for="menuTreeApi">菜单树接口：</label>
                        <input type="text" id="menuTreeApi" v-model="menuTreeApi">
                    </div>
                    <div class="input-group clearfix">
                        <label for="roleMenuApi">角色菜单接口：</label>
                        <input type="text" id="roleMenuApi" v-model="roleMenuApi">
                    </div>
                    <div class="input-group clearfix">
                        <label for="code">显示区域：</label>
                        <input type="text" id="" v-model="targetId">
                    </div>
                    <div class="input-group clearfix">
                        <label for="remarks" class="textarea">备注:</label>
                        <textarea cols="6" id="remarks" v-model="remark"></textarea>
                    </div>
                    <div class="dialog-buttons">
                        <a-button type="primary" icon="checkmark-round" :loading="loading" @click="saveMenu">
                            <span>提交</span>
                        </a-button>
                        <a-button type="info" icon="chevron-left"  @click="cancelMenu">
                            <span>取消</span>
                        </a-button>
                    </div>
                </div>
            </div>
        </np-dialog>
        <np-dialog :show-dialog="detailShow" :title = "titles[1]" v-on:close ="closeDetailDig">
            <div class="dialog-body" slot="body">
                <div class="form-group">
                    <div class="input-group clearfix">
                        <label>服务名称：</label>
                        <span class="form-text" v-text="info.serviceName"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>菜单URL：</label>
                        <span class="form-text" v-text="info.menuUrl"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>菜单名称:</label>
                        <span class="form-text" v-text="info.menuName"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>显示下级菜单:</label>
                        <span class="form-text">{{info.isMenu ? '是':'否'}}</span>
                    </div>
                    <div class="input-group clearfix">
                        <label>显示下级菜单:</label>
                        <span class="form-text">{{info.hasSubmenu ? '是':'否'}}</span>
                    </div>
                    <div class="input-group clearfix">
                        <label>菜单树接口:</label>
                        <span class="form-text" v-text="info.menuTreeApi"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>角色菜单接口:</label>
                        <span class="form-text" v-text="info.roleMenuApi"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>显示区域:</label>
                        <span class="form-text" v-text="info.targetId"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>备注:</label>
                        <span class="form-text" v-text="info.remark"></span>
                    </div>
                </div>
            </div>
        </np-dialog>
        <loading :showLoading="showLoading"></loading>
        <boot-page ref:page :async="true" :data="dataList" :url="apiUrl" :page-len="pageLen"
                   :param="paramList"  v-on:child-msg="handleIt" ref="pager"></boot-page>
    </div>
</template>

<script type="text/javascript">
    import axios from 'axios';
    import Loading from '../util/loading.vue';
    import BootPage from '../util/pager.vue';
    import NpDialog from "../util/dialog.vue";

    export default {
        name: 'menuList',
        data(){
            return {
                columns: ['序号', '服务名称', '菜单名称', '菜单URL', '是否为菜单','是否显示下级菜单','显示区域', '操作'],
                scales: ['8','14','14','14','14','12','12','12','18'],
                dataList: [],
                loading:false,
                apiUrl: '/admin/levelonemenu',
                showLoading: false,
                editShow:false,
                detailShow:false,
                titles:["编辑","详情"],
                pageLen: 5, // 可显示的分页数
                paramList:{},
                info:{},
                menuKeywords:'',
                serviceName:'',
                menuName:'',
                menuUrl: '',
                MenuSelect: 2,
                isMenus: '',
                hasSubmenu: '',
                menuTreeApi:"",
                roleMenuApi:"",
                targetId:"",
                remark:"",
                width:0
            }
        },
        created: function () {

            window.addEventListener('resize', this.reverseWidth);
        },
        mounted:function(){
            this.reverseWidth();
        },
        events: {
            // 分页组件传回的表格数据
            'data' (data) {
                this.dataList = data
            }
        },
        methods: {
            reqComplete(){
                this.search();
                this.$refs.pager.resetPager();
                this.$refs.pager.getPages();
            },
            trWidth:function(index){
                var scale = parseFloat(this.scales[index]),
                        indexWidth = this.width * scale/100;
                return indexWidth +'px';
            },
            reverseWidth:function(){
                var obj = this.$refs.serviceTable;
                this.width = obj.getBoundingClientRect().width;
            },
            /**
             * 功能：menu列表
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-08 15:03
             */
            search: function () {
                this.MenuSelect = (this.MenuSelect==2) ? "" : (this.MenuSelect ? 1 : 0);
                this.paramList = { "keyword": this.menuKeywords ,"isMenu": this.MenuSelect };
                //this.showMenuList(this.menuKeywords,this.MenuSelect);
            },

           /* showMenuList:function(keyword,isMenu){
                var vm = this;
                vm.showLoading = true;
                var key = keyword?keyword:'';
                axios.get(this.apiUrl,{
                    params: {
                        page : 1,
                        pageSize:20,
                        keyword:key,
                        isMenu:isMenu
                    }
                }).then(function (resp) {
                    vm.showLoading = false;
                    var resp = resp.data;
                    if (resp.code== 0) {
                        vm.dataList = resp.data.list;
                        vm.pageTotal =resp.data.totalPage;
                        vm.len =  resp.data.pageSize;
                        vm.pageLen =resp.data.page;
                        vm.paramList = { keyword: vm.menuKeywords,isMenu: vm.MenuSelect };
                    }else{
                        console.log("err=>", error);
                    }
                }).catch(function (error) {
                    vm.showLoading = false;
                    console.log("err=>", error);
                });
            },*/
            /**
             * 功能：编辑服务菜单
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-07 15:21
             */
            editMenu:function(id) {
                    this.addOrEdit =id;
                    var vm = this;
                    let url = this.apiUrl +"/"+ id;
                    axios.get(url).then(function (resp) {
                        var resp = resp.data;
                        if(resp.code =="0"){
                            vm.serviceName = resp.data.serviceName;
                            vm.menuName = resp.data.menuName;
                            vm.menuUrl = resp.data.menuUrl;
                            vm.hasSubmenu =  resp.data.hasSubmenu? 1:0;
                            vm.isMenus = resp.data.isMenu ? 1:0;
                            vm.targetId =  resp.data.targetId;
                            vm.remark = resp.data.remark;
                            vm.menuTreeApi = resp.data.menuTreeApi;
                            vm.roleMenuApi = resp.data.roleMenuApi;
                        }else{
                            console.log(resp.msg);
                        }
                    }).catch(function (error) {
                        console.log(error);
                    });
                   this.editShow = true;
            },
            saveMenu:function(){
                var vm = this;
                if(this.isMenus == 1 && this.menuName ==""){
                    this.$Notice.error({
                        desc: "请输入菜单名称"
                    });

                    return false;
                }
                if(this.isMenus == 1 && this.menuUrl ==""){
                    this.$Notice.error({
                        desc: "请输入菜单url"
                    });

                    return false;
                }
                if(this.isMenus == 1 && this.targetId  ==""){
                    this.$Notice.error({
                        desc: "显示区域不能为空"
                    });

                    return false;
                }
                if(this.hasSubmenu.toString().trim() == ''){
                    this.$Notice.error({
                        desc: "请选择是否显示下级菜单"
                    });

                    return false;
                }

                if(this.isMenus.toString().trim() == '' ){
                    this.$Notice.error({
                        desc: "请选择是否为菜单"
                    });

                    return false;
                }
                if(this.menuTreeApi =="" && this.isMenus == 1){
                    this.$Notice.error({
                        desc: "请输入获取菜单树的接口"
                    });

                    return false;
                }
                if(this.roleMenuApi ==""&& this.isMenus == 1){
                    this.$Notice.error({
                        desc: "请输入角色菜单授权接口"
                    });

                    return false;
                }
                var submenu = (this.hasSubmenu==1) ? true:false;
                var isMenu = (this.isMenus==1) ? true:false;
                vm.showLoading = true;
                let params = { "id":this.addOrEdit,"menuName": this.menuName,"menuUrl": this.menuUrl,"targetId": this.targetId,
                    "hasSubmenu":submenu,"isMenu":isMenu,"remark":this.remark,"roleMenuApi":this.roleMenuApi,"menuTreeApi":this.menuTreeApi};
                    axios.put(this.apiUrl, params,{
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    }).then(function (resp) {
                        vm.showLoading = false;
                        var resp = resp.data;
                        if(resp.code=="0"){
                            vm.$Notice.success({
                                title: "成功"
                            });
                            vm.editShow = false;
                            vm.reqComplete();

                        }else{
                            vm.$Notice.error({
                                title: resp.code,
                                desc: resp.msg
                            });
                        }
                    }).catch(function (error) {
                        vm.$Notice.error({
                            desc:error
                        });
                    });
            },
            cancelMenu:function(){
                this.editShow = false;
            },
            closeDig:function (res) {
                this.editShow = res;
            },
            /**
             * 功能：详情页面
             * 修改记录：xx
             * @author 郝旭
             * @date 2017-02-07 15:35
             */
            detail:function (id) {
                var vm = this;
                this.detailShow = true;
                let url = this.apiUrl + "/"+id;
                axios.get(url).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.info = resp.data;
                    }else{

                    }
                }).catch(function (error) {
                    console.log(error);
                });
            },
            closeDetailDig:function (res) {
                this.detailShow = res;
            },

            handleIt: function (res) {
                this.dataList = res.list;
            }
        },
        components: {
            Loading,
            BootPage,
            NpDialog
        },
    }

</script>
<style scoped lang="scss">
    /*@import "../../style/list.scss";*/

</style>
