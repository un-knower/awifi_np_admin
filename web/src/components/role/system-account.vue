/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-06 19:45
 * 创建作者: haoxu
 * 文件名称: system-account.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div class="container">

        <div class="search-bar">
            <ul>
                <li>
                    <a-input placeholder="请输入账号/角色名称" v-model="keywords" class="input-width"></a-input>
                </li>
                <li>
                    <a-button type="primary" @click="search" icon="search">查询</a-button>
                </li>
                <li>
                    <a-button type="warning" @click="createAccount(0)" icon="plus">创建账号</a-button>
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
            <tbody  ref="serviceTable">
            <tr v-for="(item,index) in dataList">
                <td :style="{ width:trWidth(0) }">{{index+1}}</td>
                <td :style="{ width:trWidth(1) }">{{item.loginAccount}}</td>
                <td :style="{ width:trWidth(2) }" v-if="item.role">{{item.role.roleName}}</td>
                <td :style="{ width:trWidth(2) }" v-else></td>
                <td :style="{ width:trWidth(3) }">{{item.createDate | getDate}}</td>
                <td :style="{ width:trWidth(4) }">{{item.phone}}</td>
                <td :style="{ width:trWidth(5) }" class="ivu-table-cell">
                    <span @click="detail(item.id)">
                         <a href="javascript:void(0);" title="查看" class="ivu-btn ivu-btn-eye ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-eye"></i>
                         </a>
                    </span>
                    <span  @click="createAccount(item.id)">
                         <a href="javascript:void(0);" title="编辑" class="ivu-btn ivu-btn-edit ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-edit"></i>
                         </a>
                    </span>
                      <span  @click="del(item.id)">
                         <a href="javascript:void(0);" title="删除" class="ivu-btn ivu-btn-ghost ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-trash-a"></i>
                         </a>
                    </span>
                </td>
            </tr>
            <tr class="noInfo" v-if="!dataList || dataList.length == 0">没有找到相关数据</tr>
            </tbody>
        </table>
            </div>
        <np-dialog :show-dialog="createShow" :title = "titles[0]" v-on:close ="closeDig">
            <div class="dialog-body" slot="body">
                <div class="form-group">
                    <div class="input-group clearfix" v-if="editId == 0">
                        <label for="account">账号:</label>
                        <input type="text" id="account" v-model="loginAccount">
                    </div>
                    <div class="input-group clearfix" v-else>
                        <label for="account">账号:</label>
                        <span class="form-text" v-text="info.loginAccount"></span>
                    </div>
                    <div class="input-group clearfix" v-if="editId == 0">
                        <label for="role">角色:</label>
                        <select id="role" v-model="roleId">
                            <option v-for="option in options" :value="option.value">
                                {{ option.text }}
                            </option>
                        </select>
                    </div>
                    <div class="input-group clearfix" v-else>
                        <label>角色：</label>
                        <span class="form-text" v-if="info.role" v-text="info.role.roleName"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label for="phone">联系方式：</label>
                        <input type="text" id="phone" v-model="phone">
                    </div>
                    <div class="input-group clearfix">
                        <label for="remarks" class="textarea">备注:</label>
                        <textarea cols="6" id="remarks" v-model="remark"></textarea>
                    </div>

                    <div class="dialog-buttons">
                        <a-button type="primary" icon="checkmark-round" :loading="loading" @click="saveAccount">
                            <span>提交</span>
                        </a-button>
                        <a-button type="info" icon="chevron-left"  @click="cancelAccount">
                            <span>取消</span>
                        </a-button>
                    </div>
                </div>
            </div>
        </np-dialog>

        <np-dialog :show-dialog="detailShow" :title = "titles[2]" v-on:close ="closeDetailDig">
            <div class="dialog-body" slot="body">
                <div class="form-group">
                    <div class="input-group clearfix">
                        <label>账号：</label>
                        <span class="form-text" v-text="info.loginAccount"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>角色：</label>
                        <span class="form-text" v-if="info.role" v-text="info.role.roleName"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>联系方式：</label>
                        <span class="form-text" v-text="info.phone"></span>
                    </div>
                    <div class="input-group clearfix password">
                        <label>备注:</label>
                        <span class="form-text" v-text="info.remark"></span>
                    </div>
                </div>
            </div>
        </np-dialog>
        <np-dialog :show-dialog="deleteShow" :title = "titles[3]" v-on:close ="closeDeleteDig">
            <div class="dialog-body" slot="body">
                <div class="form-group delete-popup">
                    <img src="../../image/icon/delete.png">
                    您确定删除该账号吗？
                </div>
                <div class="normal-buttons">
                    <a-button type="primary" icon="checkmark-round" @click="confirmDel">
                        <span>确定</span>
                    </a-button>
                    <a-button type="info" icon="chevron-left"  @click="cancelDel">
                        <span>取消</span>
                    </a-button>
                </div>
            </div>
        </np-dialog>

        <loading :showLoading="showLoading"></loading>
        <boot-page ref:page :async="true" :data="dataList" :url="apiUrl" :page-len="pageLen"
                   :param="paramList"  v-on:child-msg="handleIt" ref="pager"></boot-page>
    </div>
</template>

<script type="text/javascript">
    import ajax from '../../api/API.js';
    import axios from 'axios';
    import date from "../../api/date";
    import Loading from '../util/loading.vue';
    import BootPage from '../util/pager.vue';
    import NpDialog from "../util/dialog.vue";

    export default {
        name: 'ServiceList',
        data(){
            return {
                columns: ['序号', '账号', '角色名称', '创建时间','联系电话', '操作'],
                scales: ['12','16','16','18','18','20'],
                dataList: [],
                apiUrl: '/admin/users',
                addUrl: '/admin/user',
                allRoles:'/admin/allroles',
                options:[],
                showLoading: false,
                loading: false,
                createShow: false,
                detailShow:false,
                deleteShow:false,
                deleteId:0,
                titles:["创建账号","编辑账号","账号详情","删除账号"],

                pageLen: 5, // 可显示的分页数
                keywords:'',
                paramList:{},
                info:{},
                loginAccount: '',
                roleId: '',
                phone: '',
                remark: '',
                editId:0,
                width:0
            }
        },
        events: {
            // 分页组件传回的表格数据
            'data' (data) {
                this.dataList = data
            },
        },
        created:function(){
            this.showRolesLists();
        },
        mounted:function(){
            this.reverseWidth();
        },
        filters: {
            getDate:function(tm){
                var tt = new Date(tm).format("yyyy-MM-dd hh:mm:ss");
                return tt;
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
             * 功能：创建前清空一下
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-03-01 09:40
             */
            clearEdit:function(){
                this.loginAccount = '';
                this.roleId = '';
                this.phone = '';
                this.remark = '';
            },
            showRolesLists:function(){
                var vm = this;
                axios.get(this.allRoles).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        for(var value of resp.data.list){
                            var service ={text:value.roleName , value: value.id };
                            vm.options.push(service);
                        }
                    }else{
                        console.log(resp.msg);
                    }
                }).catch(function (error) {
                    console.log(error);
                });

            },

            search: function () {
                this.paramList = { "keyword": this.keywords };
            },

            /**
             * 功能：创建账号/编辑账号
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-07 15:21
             */
            createAccount:function(id) {
                this.clearEdit();
                this.editId = id;
                if(this.editId !=0){
                    var vm = this;
                    let url = this.addUrl +"/"+ id;
                    axios.get(url).then(function (resp) {
                        var resp = resp.data;
                        if(resp.code =="0"){
                            vm.info.loginAccount =  resp.data.loginAccount;
                            vm.info.role =  resp.data.role;
                            vm.phone = resp.data.phone;
                            vm.remark =  resp.data.remark;
                        }else{
                            console.log(resp.msg);
                        }
                    }).catch(function (error) {
                        console.log(error);
                    });
                }else{
                    this.editId = 0;
                }
                this.createShow = true;
            },
            saveAccount:function(){
                var vm = this;
                if(this.phone ==""){
                    this.$Notice.error({
                        desc: "请输入联系方式"
                    });

                    return false;
                }
                if( this.editId == 0){
                    if(this.loginAccount ==""){
                        this.$Notice.error({
                            desc: "请输入账号"
                        });

                        return false;
                    }
                    if(this.roleId ==""){
                        this.$Notice.error({
                            desc: "请选择角色"
                        });

                        return false;
                    }
                    let params = { "loginAccount": this.loginAccount,"roleId": parseInt(this.roleId),"phone": this.phone, "remark":this.remark};
                    this.loading =true;
                    axios.post(this.addUrl, params,{
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    }).then(function (resp) {
                        vm.loading =false;
                        var resp = resp.data;
                        if(resp.code=="0"){
                            vm.$Notice.success({
                                title: "成功"
                            });
                            vm.createShow = false;
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

                }else{
                    let params = { "id": this.editId,"phone": this.phone, "remark":this.remark};
                    axios.put(this.addUrl, params,{
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    }).then(function (resp) {
                        vm.loading =false;
                        var resp = resp.data;
                        if(resp.code=="0"){
                            vm.$Notice.success({
                                title: "成功"
                            });
                            vm.createShow = false;
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
                }
            },
            cancelAccount:function(){
                this.createShow = false;
            },
            closeDig:function (res) {
                this.createShow = res;
            },
            /**
             * 功能：详情页面
             * 修改记录：详情页面
             * @author 郝旭
             * @date 2017-02-07 15:35
             */
            detail:function (id) {
                var vm = this;
                this.detailShow = true;
                let url = this.addUrl + "/"+id;
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
            /**
             * 功能：删除账号
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-14 11:03
             */
            del:function(id){
                this.deleteShow = true;
                this.deleteId = id;
            },
            confirmDel:function(){
                let url = this.addUrl;
                var vm = this;
                axios.delete(url,{
                    params: {
                        ids : this.deleteId
                    }
                }).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.$Notice.success({
                            title: "成功"
                        });
                        vm.deleteShow = false;
                        vm.reqComplete();

                    }else{
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });
                    }
                }).catch(function (error) {
                    console.log(error);
                });
            },
            cancelDel:function(){
                this.deleteShow = false;
            },
            closeDeleteDig:function(res){
                this.deleteShow = res;
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
