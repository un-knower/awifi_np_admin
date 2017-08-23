/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-06 19:46
 * 创建作者: haoxu
 * 文件名称: role.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */
<template>
    <div class="container">
        <div class="search-bar">
            <ul>
                <li>
                    <a-input placeholder="请输入角色名称/套码" v-model="keyword" class="input-width"></a-input>
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
                <td :style="{ width:trWidth(3) }">{{item.suitCodeList.join(',')}}</td>
                <td :style="{ width:trWidth(4) }">{{item.createDate | getDate}}</td>

                <td :style="{ width:trWidth(5) }" class="ivu-table-cell">
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
                    <span  @click="interface(item.id)">
                         <a href="javascript:void(0);" title="接口授权" class="ivu-btn ivu-btn-shuffle ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-shuffle"></i>
                         </a>
                    </span>
                       <span  @click="menu(item.id)">
                         <a href="javascript:void(0);" title="菜单授权" class="ivu-btn ivu-btn-map ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-map"></i>
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
                        <label>套码：</label>
                        <span class="form-text"><span v-for="value in info.suitCodeList">{{value}} &nbsp;&nbsp;</span></span>
                    </div>
                   <!-- <div class="input-group clearfix">
                        <label>服务权限：</label>
                        <span class="form-text" v-text="info.phone"></span>
                    </div>
                    <div class="input-group clearfix password">
                        <label>菜单权限:</label>
                        <span class="form-text" v-text="info.remark"></span>
                    </div>-->
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
                columns: ['序号', '角色ID', '角色名称', '套码','创建时间', '操作'],
                scales: ['10','14','14','14','18','30'],
                dataList: [],
                apiUrl: '/admin/bizrole',
                showLoading: false,
                detailShow:false,
                deleteShow:false,
                deleteId:0,
                titles:["角色详情","删除角色"],
                pageLen: 5, // 可显示的分页数
                paramList:{},
                info:{},
                keyword:'',
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
        filters: {
            getDate:function(tm){
                var tt = new Date(tm).format("yyyy-MM-dd hh:mm:ss");
                return tt;
            }
        },
        mounted:function(){
            this.reverseWidth();
            window.addEventListener('resize', this.reverseWidth);
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

            search: function () {
                this.paramList = { "keyword": this.keyword };
            },

            /**
             * 功能：创建角色/编辑角色
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-07 15:21
             */
            createRole: function (id) {
                router.push({path: '/information/createRole/' + id});
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
                let url = this.apiUrl + "/"+id;
                axios.get(url).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.info = resp.data;
                    }else{
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });
                    }
                }).catch(function (error) {
                    vm.$Notice.error({
                        desc: error
                    });
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
                let url = this.apiUrl+"/"+this.deleteId.id;
                var vm = this;
                axios.delete(url).then(function (resp) {
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
                    vm.$Notice.error({
                        desc: error
                    });
                });
            },
            cancelDel:function(){
                this.deleteShow = false;
            },
            closeDeleteDig:function(res){
                this.deleteShow = res;
            },
            /**
             * 功能：接口授权
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-24 09:49
             */
            interface:function(id){
                router.push({path: '/information/interface/auth/' + id});
            },
            /**
             * 功能：菜单授权
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-24 09:49
             */
            menu:function(id){
                router.push({path: '/information/menu/auth/' + id});
            },
            handleIt: function (res) {
                this.dataList = res.list;
            },
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

