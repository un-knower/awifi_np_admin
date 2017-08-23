/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-01-19 08:54
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
                    <a-input placeholder="请输入服务编码或服务名称" v-model="serviceKeyword" class="input-width"></a-input>
                </li>
                <li>
                    <a-button type="primary" @click="search" icon="search">查询</a-button>
                </li>
                <li>
                    <a-button type="warning" @click="createService(-1)" icon="plus">创建服务</a-button>
                </li>
            </ul>
        </div>
        <div class="tables">
        <table>
            <thead>
            <tr>
                <th v-for="(col,index) in columns" :style="{ width: trWidth(index) }">
                    {{ col }}
                </th>
            </tr>
            </thead>
            <tbody ref="serviceTable">
            <tr v-for="(item,index) in dataList">
                <td :style="{ width:trWidth(0)}">{{index+1}}</td>
                <td :style="{ width:trWidth(1) }">{{item.serviceName}}</td>
                <td :style="{ width:trWidth(2) }">{{item.serviceCode}}</td>
                <td :style="{ width:trWidth(3) }">{{item.serviceKey}}</td>
                <td :style="{ width:trWidth(4) }" v-if="item.publishStatus == 0">未上线</td>
                <td :style="{ width:trWidth(4) }" v-if="item.publishStatus == 1">已上线</td>
                <td :style="{ width:trWidth(4) }" v-if="item.publishStatus == 2">上线中</td>
                <td :style="{ width:trWidth(4) }" v-if="item.publishStatus == 3">升级中</td>
                <td :style="{ width:trWidth(5) }">{{item.createDate|getDate}}</td>
                <td :style="{ width:trWidth(6) }" class="ivu-table-cell">
                    <span @click="detail(item.id)">
                         <a href="javascript:void(0);" title="查看" class="ivu-btn ivu-btn-eye ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-eye"></i>
                         </a>
                    </span>
                    <span  @click="createService(item.id)">
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
                        <label for="serviceName">服务名称：</label>
                        <input type="text" id="serviceName" v-model="serviceName">
                    </div>
                    <div class="input-group clearfix">
                        <label for="code">服务编码：</label>
                        <span class="create" style="display:inline-block;width:5%;">S_</span>
                        <input type="text" id="code" v-model="serviceCode" style="display:inline-block;width:64%;">
                    </div>
                    <div class="input-group clearfix password">
                        <label for="password">服务密钥:</label>
                        <input type="text" id="password" v-model="serviceKey" readonly="readonly">
                        <span class="create" @click="createAppKey">生成</span>
                    </div>
                    <div class="input-group clearfix">
                        <label for="platform">所属平台:</label>
                        <select id="platform" v-model="pid">
                            <option v-for="(item,index) in platformOptions" :value="item.value">
                                {{item.text}}
                            </option>
                        </select>
                    </div>
                    <div class="dialog-buttons">
                        <a-button type="primary" icon="checkmark-round" :loading="loading" @click="saveService">
                            <span>提交</span>
                        </a-button>
                        <a-button type="info" icon="chevron-left"  @click="cancelService">
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
                        <label>上线状态:</label>
                        <span class="form-text" v-if="info.publishStatus == 0">未上线</span>
                        <span class="form-text" v-if="info.publishStatus == 1">已上线</span>
                        <span class="form-text" v-if="info.publishStatus == 2">上线中</span>
                        <span class="form-text" v-if="info.publishStatus == 3">升级中</span>

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
    import date from "../../api/date"
    export default {
        name: 'ServiceList',
        data(){
            return {
                columns: ['序号', '服务名称', '服务编码', '密钥', '上线状态','创建时间', '操作'],
                scales: ['5','15','10','30','10','15','15'],
                dataList: [],
                loading: false,
                platformOptions:[],  //平台列表
                apiUrl: '/admin/service',
                createKeyUrl:'/admin/tool/security_code',
                platformUrl: '/admin/platform',
                showLoading: false,
                createShow: false,
                detailShow:false,
                titles:["创建服务","编辑服务","服务详情"],
                pageLen: 5, // 可显示的分页数
                paramList:{},
                info:{},
                serviceKeyword:'',
                serviceName:'',
                serviceCode: '',
                serviceKey: '',
                pid: 0,
                addOrEdit:0,
                width:0
            }
        },
        created: function () {
            this.showPlatformLists();
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
//                return this.scales[index] + '%';
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
                this.serviceName = '';
                this.serviceCode = '';
                this.serviceKey = '';
                this.pid = '';
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

            /**
             * 功能：创建服务/编辑服务
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-07 15:21
             */
            createService:function(id) {
                this.clearEdit();
                if(id!= -1){
                    this.addOrEdit =id;
                    var vm = this;
                    let url = this.apiUrl +"/"+ id;
                    axios.get(url).then(function (resp) {
                        var resp = resp.data;
                        if(resp.code =="0"){
                            vm.serviceName = resp.data.serviceName;
                            vm.serviceCode = resp.data.serviceCode.substr(2);
                            vm.serviceKey = resp.data.serviceKey;
                            vm.pid =  resp.data.pid;
                        }else{
                            console.log(resp.msg);
                        }
                    }).catch(function (error) {
                        console.log(error);
                    });
                }else{
                    this.addOrEdit = 0;
                }
                this.createShow = true;
            },
            showPlatformLists:function(){
                var vm = this;
                axios.get(this.platformUrl,{
                    params: {
                        pageSize : -1
                    }
                }).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        for(var value of resp.data){
                            var service ={text:value.platformName , value: value.id };
                            vm.platformOptions.push(service);
                        }
                    }else{
                        console.log(resp.msg);
                    }
                }).catch(function (error) {
                    console.log(error);
                });

            },
            createAppKey:function(){
                var vm = this;
                axios.get(this.createKeyUrl).then(function (resp) {
                    console.log(resp);
                    vm.serviceKey = resp.data.data;
                }).catch(function (error) {
                    console.log(error);
                });
            },
            saveService:function(){
                var vm = this;
                if(this.serviceName ==""){
                    this.$Notice.error({
                        desc: "请输入服务名称"
                    });

                    return false;
                }
                if(this.serviceCode ==""){
                    this.$Notice.error({
                        desc: "请输入服务编码"
                    });

                    return false;
                }
                if(this.serviceKey ==""){
                    this.$Notice.error({
                        desc: "请生成服务密钥"
                    });

                    return false;
                }
                if(this.pid ==""){
                    this.$Notice.error({
                        desc: "请选择所属平台"
                    });

                    return false;
                }
                var serviceCode ="S_"+this.serviceCode;
                let params = { "serviceCode": serviceCode,"serviceName": this.serviceName,"serviceKey": this.serviceKey, "pid":this.pid };
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
                            desc:error
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
                            desc:error
                        });
                    });
                }
            },
            cancelService:function(){
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
