/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-04 15:39
 * 创建作者: haoxu
 * 文件名称: upload-log.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div class="container">
        <div class="search-bar">
            <ul>
                <li>
                    <a-input placeholder="请输入服务名称或服务编码" v-model="serviceId" class="input-width"></a-input>
                </li>
                <li>
                    <a-select v-model="status"  placeholder="状态" clearable>
                        <a-option v-for="option in options" :value="option.value">
                            {{ option.text }}
                        </a-option>
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
                <td :style="{ width:trWidth(0) }">{{index+1}}</td>
                <td :style="{ width:trWidth(1) }">{{item.serviceName}}</td>
                <td :style="{ width:trWidth(2) }">{{item.publishVersion}}</td>
                <td :style="{ width:trWidth(3) }">{{item.serviceCode}}</td>
                <td :style="{ width:trWidth(4) }" v-if="item.publishStatus == 1">待配置</td>
                <td :style="{ width:trWidth(4) }" v-if="item.publishStatus == 2">待测试</td>
                <td :style="{ width:trWidth(4) }" v-if="item.publishStatus ==3">待审核</td>
                <td :style="{ width:trWidth(4) }" v-if="item.publishStatus == 4">待上线</td>
                <td :style="{ width:trWidth(4) }" v-if="item.publishStatus == 5">已上线</td>
                <td :style="{ width:trWidth(4) }" v-if="item.publishStatus == 6">上线回滚</td>

                <td :style="{ width:trWidth(5) }" v-if ="item.configDate">{{item.configDate | getDate}}</td>
                <td :style="{ width:trWidth(5) }" v-else>暂无</td>
                <td :style="{ width:trWidth(6) }"  v-if ="item.testDate">{{item.testDate | getDate}}</td>
                <td :style="{ width:trWidth(6) }" v-else>暂无</td>
                <td :style="{ width:trWidth(7) }"  v-if ="item.checkDate">{{item.checkDate | getDate}}</td>
                <td :style="{ width:trWidth(7) }" v-else>暂无</td>
                <td :style="{ width:trWidth(8) }"  v-if ="item.publishDate">{{item.publishDate| getDate}}</td>
                <td :style="{ width:trWidth(8) }" v-else>暂无</td>
                <td :style="{ width:trWidth(9) }" class="ivu-table-cell">
                    <span @click="config(item.serviceId,item.serviceName,item.publishStatus)" v-if="item.publishStatus == 1">
                         <a href="javascript:void(0);" title="配置服务" class="ivu-btn ivu-btn-settings ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-settings"></i>
                         </a>
                    </span>
                    <span @click="test(item.id,item.serviceName)" v-if="item.publishStatus == 2">
                         <a href="javascript:void(0);" title="测试服务" class="ivu-btn ivu-btn-gear-b ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-gear-b"></i>
                         </a>
                    </span>
                      <span  @click="review(item.id,item.serviceName)" v-if="item.publishStatus == 3">
                         <a href="javascript:void(0);" title="审核服务" class="ivu-btn ivu-btn-ios-book-outline ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-ios-book-outline"></i>
                         </a>
                    </span>
                      <span @click="success(item.id)" v-if="item.publishStatus == 4">
                         <a href="javascript:void(0);" title="上线服务" class="ivu-btn ivu-btn-ios-redo ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-ios-redo"></i>
                         </a>
                    </span>
                    <!--<span @click="config(item.serviceId,item.serviceName,item.publishStatus)" v-if="item.publishStatus == 5 || item.publishStatus == 6">-->
                         <!--<a href="javascript:void(0);" title="升级" class="ivu-btn ivu-btn-android-arrow-up ivu-btn-circle ivu-btn-small ivu-btn-icon-only">-->
                             <!--<i class="ivu-icon ivu-icon-android-arrow-up"></i>-->
                         <!--</a>-->
                    <!--</span>-->
                </td>
            </tr>
            <tr class="noInfo" v-if="!dataList || dataList.length == 0">没有找到相关数据</tr>
            </tbody>
        </table>
            </div>

        <loading :showLoading="showLoading"></loading>
        <boot-page  v-if="showPager"  ref:page :async="true" :data="dataList" :url="apiUrl" :page-len="pageLen"
                   :param="paramList"  v-on:child-msg="handleIt"></boot-page>
    </div>
</template>

<script type="text/javascript">
    import ajax from '../../api/API.js';
    import axios from 'axios';
    import router from '../../router';
    import Loading from '../util/loading.vue';
    import BootPage from '../util/pager.vue';
    import date from "../../api/date";

    export default {
        name: 'ServiceList',
        data(){
            return {
                columns: ['序号', '服务名称', '版本号', '服务编码', '当前状态', '配置时间','测试时间','审核时间','上线时间', '操作'],
                scales: ['4','8','5','8','7','15','15','15','15','8'],
                dataList: [],
                showPager:true,
                apiUrl: '/admin/servicepublishlog',
                showLoading: false,
                pageLen: 5, // 可显示的分页数
                paramList:{},
                serviceId:'',
                status:'',
                width:0,
                options:[ { text: '全部状态',value:''},
                    { text: '待配置', value: 1 },
                    { text: '待测试', value: 2 },
                    { text: '待审核', value: 3 },
                    { text: '待上线', value: 4 },
                    { text: '已上线', value: 5 },
                    { text: '上线回滚', value: 6 }
                   ]
            }
        },
        created: function () {
            if(this.$route.query.sid){
                this.showSidLogList(this.$route.query.sid);
                this.showPager = false;
            }
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
        filters: {
            getDate:function(tm){
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
             * 功能：log列表
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-08 15:03
             */
            search: function () {
                this.paramList = { "keyword": this.serviceId,"publishStatus":this.status };
            },
           /* showLogList:function(keyword){
                var vm = this;
                vm.showLoading = true;
                var key = keyword?keyword:'';
                axios.get(this.apiUrl,{
                    params: {
                        page : 1,
                        pageSize:20,
                        keyword:key,
                        publishStatus:vm.status
                    }
                }).then(function (resp) {
                    vm.showLoading = false;
                    var resp = resp.data;
                    if (resp.code== 0) {
                        vm.dataList = resp.data.list;
                        vm.pageTotal =resp.data.totalPage;
                        vm.len =  resp.data.pageSize;
                        vm.pageLen =resp.data.page;
                        vm.paramList = { keyword: vm.serviceId,publishStatus:vm.status };
                    }else{
                        console.log("err=>", error);
                    }
                }).catch(function (error) {
                    vm.showLoading = false;
                    console.log("err=>", error);
                });
            },*/
            showSidLogList:function(sid){
                var vm = this;
                vm.showLoading = true;
                axios.get(this.apiUrl,{
                    params: {
                       sid: sid
                    }
                }).then(function (resp) {
                    vm.showLoading = false;
                    var resp = resp.data;
                    if (resp.code== 0) {
                        vm.dataList = resp.data.list;
                    }else{
                    }
                }).catch(function (error) {
                    vm.showLoading = false;
                    vm.$Notice.error({
                        desc: error
                    });
                });
            },
            /**
             * 功能：点击按钮跳转
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-15 15:24
             */
            config:function(id,name,publishStatus){
                router.push({path: '/online/config/'+id+'/'+name+'/'+publishStatus});
            },
            test:function(id,name){
                router.push({path: '/online/test/'+id +'/'+ name});
            },
            review:function(id,name){
                router.push({path: '/online/review/'+id});
            },
            success:function(id,name){
                router.push({path: '/online/success/'+id });
            },

            handleIt: function (res) {
                this.dataList = res.list;
            }
        },
        components: {
            Loading,
            BootPage
        },
    }

</script>
<style scoped lang="scss">
    /*@import "../../style/list.scss";*/

</style>