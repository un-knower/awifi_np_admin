/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-06 19:44
 * 创建作者: haoxu
 * 文件名称: system-role.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */
<template>
    <div class="container">

        <div class="search-bar">
            <ul>
                <li>
                    <a-input placeholder="请输入角色ID/角色名称" v-model="keywords" class="input-width"></a-input>
                </li>
                <li>
                    <a-button type="primary" @click="search" icon="search">查询</a-button>
                </li>
                <li>
                    <a-button type="warning" @click="createRole(0)" icon="plus">创建角色</a-button>
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
                <td :style="{ width:trWidth(1) }">{{item.id}}</td>
                <td :style="{ width:trWidth(2) }">{{item.roleName}}</td>
                <td :style="{ width:trWidth(3) }">{{item.createDate|getDate}}</td>

                <td :style="{ width:trWidth(4) }" class="ivu-table-cell">
                    <span @click="detail(item.id)">
                         <a href="javascript:void(0);" title="查看" class="ivu-btn ivu-btn-eye ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-eye"></i>
                         </a>
                    </span>
                    <span  @click="createRole(item.id)">
                         <a href="javascript:void(0);" title="编辑" class="ivu-btn ivu-btn-edit ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-edit"></i>
                         </a>
                    </span>
                      <span  @click="del(item)">
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
        <np-dialog :show-dialog="detailShow" :title = "titles[0]" v-on:close ="closeDetailDig">
            <div class="dialog-body" slot="body">
                <div class="form-group">
                    <div class="input-group clearfix">
                        <label>角色名称：</label>
                        <span class="form-text" v-text="info.roleName"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label class="textarea">角色权限：</label>
                        <span class="form-text role-style" v-if="selectedLists">
                            <my-tree :lists="selectedLists" :selectedLists="selectedLists"  :async="false" class="tree"></my-tree>
                        </span>
                          <span class="form-text role-top" v-else>
                           暂无
                          </span>
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
                    您确定删除该角色吗？
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
    import router from '../../router';
    import axios from 'axios';
    import Loading from '../util/loading.vue';
    import BootPage from '../util/pager.vue';
    import NpDialog from "../util/dialog.vue";
    import date from "../../api/date";
    import myTree from '../util/tree.vue';
    export default {
        name: 'ServiceList',
        data(){
            return {
                columns: ['序号', '角色ID', '角色名称', '创建时间', '操作'],
                scales: ['18','18','20','22','22'],
                dataList: [],
                apiUrl: '/admin/roles/',
                detailUrl:'/admin/role/',
                showLoading: false,
                editShow:false,
                detailShow:false,
                deleteShow:false,
                deleteId:'',
                titles:["角色详情","删除角色"],
                pageLen: 5, // 可显示的分页数
                paramList:{},
                info:{},
                keywords:'',
                roleKey:'',
                lists:[],
                selectedLists:[],
                width:0
            }
        },
        filters: {
            getDate: function (tm) {
                var tt = new Date(tm).format("yyyy-MM-dd hh:mm:ss");
                return tt;
            }
        },
        events: {
            // 分页组件传回的表格数据
            'data' (data) {
                this.dataList = data
            },

        },
        mounted:function(){
            this.reverseWidth();
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
             * 功能：role列表
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-08 15:03
             */
            search: function () {
                this.paramList = { "keyword": this.keywords};
            },

            /**
             * 功能：创建、编辑角色
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-21 17:08
             */
            createRole:function(id){
                router.push({path: '/system/role/create/' + id});
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
                let url = this.detailUrl+id;
                axios.get(url).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.info = resp.data;
                        vm.selectedLists = resp.data.permissions;
                        for(var serviceItem of vm.selectedLists){
                            serviceItem.open = true;
                            if(serviceItem.childPermission && serviceItem.childPermission.length>0){
                                for(var child of serviceItem.childPermission){
                                    child.open = true;
                                }
                            }
                        }
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
            del:function(item){
                this.deleteShow = true;
                this.deleteId = item;
            },
            confirmDel:function(){
                var vm = this;
                let url = this.detailUrl;
                axios.delete(url,{
                    params: {
                        ids : vm.deleteId.id
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
            NpDialog,
            myTree
        },
    }

</script>
<style scoped lang="scss">
    /*@import "../../style/list.scss";*/
    .container{
        .role-style{
            border: 1px solid #f0f0f0;
            height: auto;
            max-height: 400px;
            overflow: auto;
            margin-top: 15px;
            .tree{
                padding: 10px 15px 0 10px;
            }
        }
    }
</style>