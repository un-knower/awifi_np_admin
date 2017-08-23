/**
* 版权所有: 爱WiFi无线运营中心
* 创建日期: 2017-01-19 09:01
* 创建作者: haoxu
* 文件名称: list.vue
* 版本: v1.0
* 功能: xx
* 修改记录: xx
*/

<template>
    <div class="container">
        <div class="search-bar">
            <ul>
                <li>
                    <a-input placeholder="请输入平台代码或平台名称" v-model="platformKey" class="input-width"></a-input>
                </li>
                <li>
                    <a-button type="primary" @click="search" icon="search">查询</a-button>
                </li>
                <li>
                    <a-button type="warning" @click="createPlatform(-1)" icon="plus">创建平台</a-button>
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
                    <td :style="{ width:trWidth(1) }">{{item.id}}</td>
                    <td :style="{ width:trWidth(2) }">{{item.platformName}}</td>
                    <td :style="{ width:trWidth(3) }"><a :href="item.platformUrl" class="entry" target="_Blank">平台入口</a></td>
                    <td :style="{ width:trWidth(4) }">{{item.appId}}</td>
                    <td :style="{ width:trWidth(5) }" class="ivu-table-cell">
                          <span  @click="relevant(item.id,item.platformName)">
                         <a href="javascript:void(0);" title="关联服务" class="ivu-btn ivu-btn-ios-infinite ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-ios-infinite"></i>
                         </a>
                      </span>
                     <span @click="detail(item.id)">
                         <a href="javascript:void(0);" title="查看" class="ivu-btn ivu-btn-eye ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-eye"></i>
                         </a>
                    </span>
                    <span  @click="createPlatform(item.id)">
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
       <np-dialog :show-dialog="createShow" :title = "titles[0]" v-on:close ="closeDig">
            <div class="dialog-body" slot="body">
                <div class="form-group">
                    <div class="input-group clearfix">
                        <label for="platformName">平台名称：</label>
                        <input type="text" id="platformName" v-model="platformName">
                    </div>
                    <div class="input-group clearfix">
                        <label for="entry">平台入口：</label>
                        <input type="text" id="entry" v-model="platformUrl">
                    </div>
                    <div class="input-group clearfix">
                        <label for="code">平台编码：</label>
                        <input type="text" id="code" v-model="appId">
                    </div>
                    <div class="input-group clearfix password">
                        <label for="password">平台密钥:</label>
                        <input type="text" id="password" v-model="appKey" readonly="readonly" >
                        <span class="create" @click="createAppKey">生成</span>
                    </div>
                    <div class="input-group clearfix">
                        <label for="remarks" class="textarea">备注:</label>
                        <textarea cols="6" id="remarks" v-model="remark"></textarea>
                    </div>
                    <div class="dialog-buttons">
                        <a-button type="primary" icon="checkmark-round" :loading="loading" @click="savePlatform">
                            <span>提交</span>
                        </a-button>
                        <a-button type="info" icon="chevron-left"  @click="cancelPlatform">
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
                    <label>平台名称：</label>
                    <span class="form-text" v-text="info.platformName"></span>
                </div>
                <div class="input-group clearfix">
                    <label>平台入口：</label>
                    <span class="form-text" v-text="info.platformUrl"></span>
                </div>
                <div class="input-group clearfix">
                    <label>平台编码：</label>
                    <span class="form-text" v-text="info.appId"></span>
                </div>
                <div class="input-group clearfix password">
                    <label>平台密钥:</label>
                    <span class="form-text" v-text="info.appKey"></span>
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
    import router from '../../router';
    import Loading from '../util/loading.vue';
    import BootPage from '../util/pager.vue';
    import NpDialog from "../util/dialog.vue";
    import Vue from 'vue';
    import AwifiUI from 'awifiui';
    Vue.use(AwifiUI);

    export default {
        name: 'ServiceList',
        data(){
            return {
                columns: ['序号', '平台ID', '平台名称', '平台入口', '平台代码', '操作'],
                scales: ['11', '13', '16', '18', '18', '24'],
                dataList: [],
                loading: false,
                apiUrl: '/admin/platform',
                createKeyUrl:'/admin/tool/security_code',
                showLoading: false,
                createShow: false,
                detailShow: false,
                titles: ["创建平台", "编辑平台", "平台详情"],
                pageLen: 5, // 可显示的分页数
                paramList:{},
                info:{},
                platformKey:'',
                platformName: '',
                platformUrl: '',
                appId: '',
                appKey: '',
                remark: '',
                addOrEdit:0,
                width:0
            }
        },

    events: {
        // 分页组件传回的表格数据
        'data'(data){
            this.dataList = data
        },
    },
        created:function(){
            window.addEventListener('resize', this.reverseWidth);
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
         * 功能：创建前清空一下
         * 修改记录： xx
         * @author 郝旭
         * @date 2017-03-01 09:40
         */
        clearEdit:function(){
            this.platformName = '';
            this.platformUrl = '';
            this.appId = '';
            this.appKey = '';
            this.remark = '';
        },
        relevant:function(id,name){
            router.push({ path: '/platform/relevant/'+id+'/'+ name});
        },
        search: function () {
            this.paramList = { "keyword": this.platformKey };
        },

        reqComplete(){
            this.search();
            this.$refs.pager.resetPager();
            this.$refs.pager.getPages();
        },
        /**
         * 功能：创建平台/编辑平台
         * 修改记录： xx
         * @author 郝旭
         * @date 2017-02-07 15:21
         */
        createPlatform:function(id) {
            this.clearEdit();
            if(id!= -1){
                this.addOrEdit =id;
                var vm = this;
                let url = this.apiUrl +"/"+ id;
                axios.get(url).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.platformName = resp.data.platformName;
                        vm.platformUrl = resp.data.platformUrl;
                        vm.appId = resp.data.appId;
                        vm.appKey =  resp.data.appKey;
                        vm.remark =  resp.data.remark;
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
            }else{
                this.addOrEdit = 0;
            }
            this.createShow = true;
        },
        createAppKey:function(){
            var vm = this;
            axios.get(this.createKeyUrl).then(function (resp) {
                vm.appKey = resp.data.data;
            }).catch(function (error) {
                vm.$Notice.error({
                    desc: error
                });
            });
        },
        savePlatform:function(){
            var vm = this;
            if(this.appId ==""){
                this.$Notice.warning({
                    title: '平台代码不允许为空',
                });
                return false;
            }
            if(this.appKey ==""){
                this.$Notice.error({
                    desc: "请输入平台密钥"
                });

                return false;
            }
            if(this.platformUrl ==""){
                this.$Notice.error({
                    desc: "请输入平台入口"
                });

                return false;
            }
            let params = { "appId": this.appId,"appKey": this.appKey,"platformUrl": this.platformUrl,
                "platformName":this.platformName ,"remark":this.remark};
            this.loading =true;
            if(this.addOrEdit  ==0){
                axios.post(this.apiUrl, params,{
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
                        desc: error
                    });
                });
            }else{
                params.id = this.addOrEdit;
                axios.put(this.apiUrl, params,{
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
                        desc: error
                    });
                });
            }
        },
        cancelPlatform:function(){
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
