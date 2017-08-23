/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-04 15:39
 * 创建作者: haoxu
 * 文件名称: upload-list.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div class="container">
        <div class="search-bar">
            <ul>
                <li>
                    <a-input placeholder="请输入服务编码或服务名称" v-model="serviceKeyword" class="input-width"></a-input>
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
                <td :style="{ width:trWidth(0) }">{{index+1}}</td>
                <td :style="{ width:trWidth(1) }">{{item.serviceName}}</td>
                <td :style="{ width:trWidth(2) }">{{item.serviceCode}}</td>
                <td :style="{ width:trWidth(3) }" v-if="item.publishStatus == 0">未上线</td>
                <td :style="{ width:trWidth(3) }" v-if="item.publishStatus == 1">已上线</td>
                <td :style="{ width:trWidth(3) }" v-if="item.publishStatus == 2">上线中</td>
                <td :style="{ width:trWidth(3) }" v-if="item.publishStatus == 3">升级中</td>
                <td :style="{ width:trWidth(4) }" class="ivu-table-cell">
                    <span @click="uploadLog(item.id)" v-if="item.publishStatus != 0">
                         <a href="javascript:void(0);" title="上线日志" class="ivu-btn ivu-btn-document ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-document"></i>
                         </a>
                    </span>
                    <span v-if="item.publishStatus == 0" @click="upload(item.id,item.serviceName)">
                         <a href="javascript:void(0);" title="发起上线" class="ivu-btn ivu-btn-ios-redo ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-ios-redo"></i>
                         </a>
                    </span>
                    <span v-if="item.publishStatus == 1" @click="upload(item.id,item.serviceName)">
                         <a href="javascript:void(0);" title="升级" class="ivu-btn ivu-btn-android-arrow-up ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-android-arrow-up"></i>
                         </a>
                    </span>
                </td>
            </tr>
            <tr class="noInfo" v-if="!dataList || dataList.length == 0">没有找到相关数据</tr>
            </tbody>
        </table>
            </div>

        <np-dialog :show-dialog="detailShow" :title = "titles[2]" v-on:close ="closeDetailDig">
            <div class="dialog-body" slot="body">
                <div class="form-group">
                    <div class="input-group clearfix">
                        <label>服务名称：</label>
                        <span class="form-text" v-text="info.serviceName"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>服务编码：</label>
                        <span class="form-text" v-text="info.serviceCode"></span>
                    </div>
                    <div class="input-group clearfix password">
                        <label>服务密钥:</label>
                        <span class="form-text" v-text="info.serviceKey"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>所属平台:</label>
                        <span class="form-text" v-text="info.platformName"></span>
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
    import date from "../../api/date";
    import router from '../../router'
    export default {
        name: 'ServiceList',
        data(){
            return {
                columns: ['序号', '服务名称', '服务编码', '上线状态', '操作'],
                scales: ['15','20','20','20','25'],
                dataList: [],
                apiUrl: '/admin/service',
                platformUrl: '/admin/platform',
                createKeyUrl:'/admin/tool/security_code',
                showLoading: false,
                detailShow:false,
                titles:["创建服务"],
                pageLen: 5, // 可显示的分页数
                paramList:{},
                info:{},
                platformOptions:[],
                serviceKeyword:'',
                serviceName:'',
                serviceCode: '',
                serviceKey: '',
                pid: 0,
                addOrEdit:0,
                width:0
            }
        },
        events: {
            // 分页组件传回的表格数据
            'data' (data) {
                this.dataList = data
            }
        },
        filters: {
            getDate:function(tm){
                var tt = new Date(tm).format("yyyy-MM-dd hh:mm:ss");
                return tt;
            }
        },
        mounted:function(){
            this.reverseWidth();
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
             * 功能：服务列表
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-08 15:03
             */
            search: function () {
                this.paramList = { "keyword": this.serviceKeyword };
            },
           /* showServiceList:function(keyword){
                var vm = this;
                vm.showLoading = true;
                var key = keyword?keyword:'';
                axios.get(this.apiUrl,{
                    params: {
                        page : 1,
                        pageSize:20,
                        keyword:key
                    }
                }).then(function (resp) {
                    vm.showLoading = false;
                    var resp = resp.data;
                    if (resp.code== 0) {
                        vm.dataList = resp.data.list;
                        vm.pageTotal =resp.data.totalPage;
                        vm.len =  resp.data.pageSize;
                        vm.pageLen =resp.data.page;
                        vm.paramList = { keyword: vm.serviceKeyword };
                    }else{
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });
                    }
                }).catch(function (error) {
                    vm.showLoading = false;
                    console.log("err=>", error);
                });
            },*/

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
            handleIt: function (res) {
                this.dataList = res.list;
            },
            /**
             * 功能：发起上线/升级
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-13 09:31
             */
            upload:function(id,name){
                //id为serviceId ，logId设为0，publicStatus为为升级
                router.push({path: '/online/config/0/'+id+'/0'});
            },
            /**
             * 功能：上线日志
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-13 09:57
             */
            uploadLog:function(sid){
                router.push({path: '/online/log',query:{ sid:sid }});
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
