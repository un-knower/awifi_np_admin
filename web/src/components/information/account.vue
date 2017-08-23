/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-06 19:46
 * 创建作者: haoxu
 * 文件名称: account.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div class="container">
        <div class="search-bar">
            <ul>
                <li>
                    <a-input placeholder="请输入账号" v-model="keyword" class="input-width"></a-input>
                </li>
                <li>
                    <a-select v-model="rid"  placeholder="请选择角色" clearable>
                        <a-option v-for="option in ridOptions" :value="option.value">
                            {{ option.text }}
                        </a-option>
                    </a-select>
                </li>
                <li>
                    <a-select v-model="provinceId"  placeholder="省" clearable  @on-change="showCityLists">
                        <a-option v-for="option in proviceOptions" :value="option.value">
                            {{ option.text }}
                        </a-option>
                    </a-select>
                </li>
                <li>
                    <a-select v-model="cityId"  placeholder="市" clearable  @on-change="showCountyLists">
                        <a-option v-for="option in cityOptions" :value="option.value">
                            {{ option.text }}
                        </a-option>
                    </a-select>
                </li>
                <li>
                    <a-select v-model="areaId"  placeholder="区" clearable >
                        <a-option v-for="option in areaOptions" :value="option.value">
                            {{ option.text }}
                        </a-option>
                    </a-select>
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
                    <td :style="{ width:trWidth(1) }">{{item.userName}}</td>
                    <td :style="{ width:trWidth(2) }">
                        <span v-for="(list,index) in item.roleNameList">
                            {{list}}
                            <span v-if="index != item.roleNameList.length-1">,</span>
                        </span>
                    </td>
                    <td :style="{ width:trWidth(3) }">{{item.areaLongName}}</td>
                    <td :style="{ width:trWidth(4) }">{{item.contactWay}}</td>
                    <td :style="{ width:trWidth(5) }">{{item.createDate | getDate}}</td>
                    <td :style="{ width:trWidth(6) }" class="ivu-table-cell">
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
                      <span  @click="del(item)">
                         <a href="javascript:void(0);" title="删除" class="ivu-btn ivu-btn-ghost ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-trash-a"></i>
                         </a>
                    </span>
                    <span  @click="resetPwd(item.id)">
                         <a href="javascript:void(0);" title="重置密码" class="ivu-btn ivu-btn-refresh ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-refresh"></i>
                         </a>
                    </span>
                    </td>
                </tr>
                <tr class="noInfo" v-if="!dataList || dataList.length == 0">没有找到相关数据</tr>
                </tbody>
            </table>
         </div>
        <np-dialog :show-dialog="detailShow" :title = "titles[0]" v-on:close ="closeDetailDig">
            <div class="dialog-body" slot="body">
                <div class="form-group">
                    <div class="input-group clearfix">
                        <label>账号：</label>
                        <span class="form-text" v-text="info.userName"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>角色：</label>
                        <span class="form-text" v-if="info.roleList" v-text="roleNameList"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>地区：</label>
                        <span class="form-text" v-text="info.areaLongName"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>联系方式：</label>
                        <span class="form-text" v-text="info.contactWay"></span>
                    </div>
                    <div class="input-group clearfix password">
                        <label>备注:</label>
                        <span class="form-text" v-text="info.remark"></span>
                    </div>
                </div>
            </div>
        </np-dialog>
        <np-dialog :show-dialog="deleteShow" :title = "titles[1]" v-on:close ="closeDeleteDig">
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
        <np-dialog :show-dialog="resetShow" :title = "titles[2]" v-on:close ="closeResetDig">
            <div class="dialog-body" slot="body">
                <div class="form-group delete-popup">
                    <img src="../../image/icon/delete.png">
                    您确定重置密码么？
                </div>

                <div class="normal-buttons">
                    <a-button type="primary" icon="checkmark-round" @click="confirmReset">
                        <span>确定</span>
                    </a-button>
                    <a-button type="info" icon="chevron-left"  @click="cancelReset">
                        <span>取消</span>
                    </a-button>
                </div>
            </div>
        </np-dialog>

        <loading :showLoading="showLoading"></loading>
        <boot-page ref:page :async="true" :data="dataList" :url="apiUrl" :page-len="pageLen"
                   :param="paramList"  v-on:child-msg="handleIt"></boot-page>
    </div>
</template>

<script type="text/javascript">
    import axios from 'axios';
    import date from "../../api/date";
    import Loading from '../util/loading.vue';
    import BootPage from '../util/pager.vue';
    import NpDialog from "../util/dialog.vue";
    import router from '../../router';

    export default {
        name: 'ServiceList',
        data(){
            return {
                columns: ['序号', '账号', '角色名称', '地区', '联系方式', '创建时间', '操作'],
                scales: ['8', '12', '18', '15', '15', '16', '16'],
                dataList: [],
                apiUrl: '/admin/bizuser',
                allRolesUrl: '/admin/bizrole',
                showLoading: false,
                createShow: false,
                detailShow: false,
                deleteShow: false,
                resetShow:false,
                deleteId: 0,
                resetId: 0,
                titles: [ "账号详情", "删除账号","重置密码"],
                pageLen: 5, // 可显示的分页数
                keyword: '',
                rid: '',
                provinceId: '',
                cityId: '',
                areaId: '',
                paramList: {},
                info: {},
                roleNameList:'',
                ridOptions: [],
                proviceOptions: [],
                cityOptions: [],
                areaOptions: [],
                width:0
            }
        },
        created: function () {
            this.showAllRolesLists();
            this.showAreaLists(1);
            window.addEventListener('resize', this.reverseWidth);
        },
        mounted:function(){
            this.reverseWidth();
        },
        events: {
            // 分页组件传回的表格数据
            'data' (data) {
                this.dataList = data
            },
        },
        filters: {
            getDate: function (tm) {
                var tt = new Date(tm).format("yyyy-MM-dd hh:mm:ss");
                return tt;
            }
        },
        methods: {
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
             * 功能：显示所有角色
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-21 15:02
             */
            showAllRolesLists: function () {
                var vm = this;
                axios.get(this.allRolesUrl, {
                    params: {
                        page: -1
                    }
                }).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        for (var value of resp.data.list) {
                            var role = {text: value.roleName, value: value.id};
                            vm.ridOptions.push(role);
                        }
                    } else {
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });
                    }
                }).catch(function (error) {
                    vm.$Notice.error({
                        title: error,
                    });

                });
            },
            /**获取省、市、区
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-21 15:07
             */
            showAreaLists: function (id) {
                var vm = this;
                var areaUrl = '/api/area/' + id + '/sub';
                axios.get(areaUrl).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        for (var value of resp.data) {
                            var provice = {text: value.areaName, value: value.id};
                            vm.proviceOptions.push(provice);
                        }
                    } else {
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
            showCityLists: function () {
                var vm = this;
                var areaUrl = '/api/area/' + this.provinceId + '/sub';
                vm.cityOptions=[];
                axios.get(areaUrl).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        for (var value of resp.data) {
                            var city = {text: value.areaName, value: value.id};
                            vm.cityOptions.push(city);
                        }
                    } else {
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });
                    }
                }).catch(function (error) {
                    vm.$Notice.error({
                        title: error,
                    });
                });
            },
            showCountyLists: function () {
                var vm = this;
                vm.areaOptions = [];
                var areaUrl = '/api/area/' + this.cityId + '/sub';
                axios.get(areaUrl).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        for (var value of resp.data) {
                            var area = {text: value.areaName, value: value.id};
                            vm.areaOptions.push(area);
                        }
                    } else {
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });
                    }
                }).catch(function (error) {
                    vm.$Notice.error({
                        title: error,
                    });
                });
            },


            search: function () {
                this.showUserList(this.keyword);
            },
            showUserList: function (keyword) {
                var vm = this;
                vm.showLoading = true;
                var key = keyword ? keyword : '';
                axios.get(this.apiUrl, {
                    params: {
                        page: 1,
                        pageSize: 20,
                        keyword: key,
                        rid: vm.rid,
                        provinceId: vm.provinceId,
                        cityId: vm.cityId,
                        areaId: vm.areaId
                    }
                }).then(function (resp) {
                    vm.showLoading = false;
                    var resp = resp.data;
                    if (resp.code == 0) {
                        vm.dataList = resp.data.list;
                        vm.pageTotal = resp.data.totalPage;
                        vm.len = resp.data.pageSize;
                        vm.pageLen = resp.data.page;
                        vm.paramList = {
                            keyword: vm.keyword,
                            rid: vm.rid,
                            provinceId: vm.provinceId,
                            cityId: vm.cityId,
                            areaId: vm.areaId
                        };
                    } else {
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });
                    }
                }).catch(function (error) {
                    vm.showLoading = false;
                    vm.$Notice.error({
                        title: error,
                    });
                });
            },
            /**
             * 功能：创建账号/编辑账号
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-07 15:21
             */
            createAccount: function (id) {
                router.push({path: '/information/createAccount/' + id});
            },

            /**
             * 功能：详情页面
             * 修改记录：详情页面
             * @author 郝旭
             * @date 2017-02-07 15:35
             */
            detail: function (id) {
                var vm = this;
                this.detailShow = true;
                let url = this.apiUrl + "/" + id;
                axios.get(url).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        vm.info = resp.data;
                        for(var value of vm.info.roleList){
                            if(vm.roleNameList){
                                vm.roleNameList = vm.roleNameList +',' +value.roleName;
                            }else{
                                vm.roleNameList = value.roleName;
                            }
                         }
                    } else {
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });
                    }
                }).catch(function (error) {
                    vm.$Notice.error({
                        title: error,
                    });
                });
            },
            closeDetailDig: function (res) {
                this.detailShow = res;
            },
            /**
             * 功能：删除账号
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-14 11:03
             */
            del: function (item) {
                this.deleteShow = true;
                this.deleteId = item;
            },
            confirmDel: function () {
                var vm = this;
                let url = this.apiUrl+'/'+ this.deleteId.id;
                axios.delete(url).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        vm.$Notice.success({
                            title: "成功"
                        });
                        vm.deleteShow = false;
                        //vm.dataList.pop(vm.deleteId);
                        //删除回调
                        vm.search(vm.keyword);

                    } else {
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });

                    }
                }).catch(function (error) {
                    vm.$Notice.error({
                        title: error,
                    });
                });
            },
            cancelDel: function () {
                this.deleteShow = false;
            },
            closeDeleteDig: function (res) {
                this.deleteShow = res;
            },
            /**
             * 功能：重置密码
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-23 16:08
             */
            resetPwd:function(id){
                this.resetShow = true;
                this.resetId = id;
            },
            confirmReset:function(){
                let params = {
                    "id":this.resetId,
                    "resetPassword": true
                };
                var vm = this;
                axios.put(this.apiUrl, params, {
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        vm.$Notice.success({
                            title: "成功"
                        });
                        vm.resetShow = false;
                    } else {
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
            cancelReset:function(){
                this.resetShow = false;
            },

            closeResetDig: function (res) {
                this.resetShow = res;
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

