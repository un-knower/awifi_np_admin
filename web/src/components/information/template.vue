/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-06 19:46
 * 创建作者: haoxu
 * 文件名称: template.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div class="container">
        <div class="search-bar">
            <ul>
                <li>
                    <a-select v-model="serviceCode1"  placeholder="所属服务" clearable>
                        <a-option v-for="option in options" v-bind:value="option.value">
                            {{ option.text }}
                        </a-option>
                    </a-select>
                </li>
                <li>
                    <a-input placeholder="请输入模板名称或模板编号" v-model="keyword" class="input-width"></a-input>
                </li>
                <li>
                    <a-button type="primary" @click="search" icon="search">查询</a-button>
                </li>
                <li>
                    <a-button type="warning" @click="createTemplate(-1)" icon="plus">创建模板</a-button>
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
                <td :style="{ width:trWidth(1) }">{{item.templateCode}}</td>
                <td :style="{ width:trWidth(2) }">{{item.templateName}}</td>
                <td :style="{ width:trWidth(3) }">{{item.suitCode}}</td>
                <td :style="{ width:trWidth(4) }">{{item.serviceName}}</td>
                <td :style="{ width:trWidth(5) }">{{item.serviceCode}}</td>
                <td :style="{ width:trWidth(6) }">{{item.createDate | getDate}}</td>

                <td :style="{ width:trWidth(7) }" class="ivu-table-cell">
                    <span @click="detail(item.id)">
                         <a href="javascript:void(0);" title="查看" class="ivu-btn ivu-btn-eye ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-eye"></i>
                         </a>
                    </span>
                    <span  @click="createTemplate(item.id)">
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
        <np-dialog :show-dialog="createShow" :title = "titles[0]" v-on:close ="closeDig">
            <div class="dialog-body" slot="body">
                <div class="form-group">
                    <div class="input-group clearfix">
                        <label for="templateName">模板名称：</label>
                        <input type="text" id="templateName" v-model="templateName">
                    </div>
                    <div class="input-group clearfix">
                        <label for="code">模板编码：</label>
                        <input type="text" id="code" v-model="templateCode">
                    </div>
                    <div class="input-group clearfix">
                        <label for="suitCode">套码编码:</label>
                        <select cols="6" id="suitCode" v-model="suitCode">
                            <option v-for="option in suitOptions" v-bind:value="option.value">
                                {{ option.text }}
                            </option>
                        </select>
                    </div>
                    <div class="input-group clearfix">
                        <label for="serviceId">所属服务:</label>
                        <select cols="6" id="serviceId" v-model="serviceCode">
                            <option v-for="option in options" v-bind:value="option.value">
                                {{ option.text }}
                            </option>
                        </select>
                    </div>
                    <div class="input-group clearfix">
                        <label for="src">模板源代码：</label>
                        <textarea id="src" v-model="src"></textarea>
                    </div>
                    <div class="input-group clearfix">
                        <label for="content">模板编译代码：</label>
                        <textarea id="content" v-model="content"></textarea>
                    </div>
                    <div class="input-group clearfix">
                        <label for="remark" class="textarea">备注:</label>
                        <textarea id="remark" v-model="remark"></textarea>
                    </div>

                    <div class="dialog-buttons" style="margin-left:35%;">
                        <a-button type="primary" icon="checkmark-round" :loading="loading" @click="saveTemplate">
                            <span>提交</span>
                        </a-button>
                        <a-button type="info" icon="chevron-left"  @click="cancelTemplate">
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
                        <label>模板名称：</label>
                        <span class="form-text" v-text="info.templateName"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>模板编码：</label>
                        <span class="form-text" v-text="info.templateCode"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>套码编码：</label>
                        <span class="form-text" v-text="info.suitCode"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>所属服务：</label>
                        <span class="form-text" v-text="info.serviceCode"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>模板源代码：</label>
                        <span class="form-text" v-text="info.src"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>模板编译代码：</label>
                        <span class="form-text" v-if="info.content" v-text="info.content"></span>
                        <span class="form-text" v-else>暂无</span>
                    </div>
                    <div class="input-group clearfix">
                        <label>备注:</label>
                        <span class="form-text" v-if="info.remark" v-text="info.remark"></span>
                        <span class="form-text" v-else>暂无</span>
                    </div>
                </div>
            </div>
        </np-dialog>
        <np-dialog :show-dialog="deleteShow" :title = "titles[3]" v-on:close ="closeDeleteDig">
            <div class="dialog-body" slot="body">
                <div class="form-group delete-popup">
                    <img src="../../image/icon/delete.png">
                    您确定删除该模板吗？
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
    import Loading from '../util/loading.vue';
    import BootPage from '../util/pager.vue';
    import NpDialog from "../util/dialog.vue";
    import date from "../../api/date";

    export default {
        name: 'templateList',
        data(){
            return {
                columns: ['序号', '模板编号', '模板名称', '套码','服务名称' ,'服务编码','创建时间', '操作'],
                scales: ['6', '14', '14', '16','12','10', '14', '14'],
                dataList: [],
                loading:false,
                apiUrl: '/admin/templates',
                addUrl: '/admin/template',
                allUrl:'/admin/service',
                suitUrl:'/admin/allsuits',
                showLoading: false,
                createShow: false,
                editShow: false,
                detailShow: false,
                deleteShow: false,
                deleteId: 0,
                titles: ["创建模板", "编辑模板", "模板详情","删除模板"],
                pageLen: 5, // 可显示的分页数
                keyword:'',
                options:[],
                suitOptions:[],
                paramList: {},
                info: {},
                remark: '',
                templateName: '',
                templateCode: '',
                suitCode: '',
                serviceCode: '',
                src: '',
                content: '',
                addOrEdit: 0,
                width:0,
                serviceCode1:''
            }
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
        created:function(){
            this.showSuitLists();
            this.showServiceLists();
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
            /**
             * 功能：创建前清空一下
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-03-01 09:40
             */
            clearEdit:function(){
                this.templateName = '';
                this.templateCode = '';
                this.suitCode = '';
                this.serviceCode = '';
                this.src = '';
                this.content = '';
                this.remark = '';
            },
            /**
             * 功能：template列表
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-08 15:03
             */
            search: function () {
                this.paramList = {keyword: this.keyword, serviceCode: this.serviceCode1 };
            },

            /**
             * 功能：创建模板、编辑模板
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-07 15:21
             */
            createTemplate: function (id) {
                this.clearEdit();
                if (id != -1) {
                    this.addOrEdit = id;
                    var vm = this;
                    let url = this.addUrl + "/" + id;
                    axios.get(url).then(function (resp) {
                        var resp = resp.data;
                        if (resp.code == "0") {
                            vm.templateName = resp.data.templateName;
                            vm.templateCode = resp.data.templateCode;
                            vm.suitCode = resp.data.suitCode;
                            vm.serviceCode = resp.data.serviceCode;
                            vm.src = resp.data.src;
                            vm.content = resp.data.content;
                            vm.remark = resp.data.remark;

                        } else {
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
                } else {
                    this.addOrEdit = 0;
                }
                this.createShow = true;
            },
            showServiceLists:function(){
                var vm = this;
                axios.get(this.allUrl,{
                    params: {
                        pageSize : -1
                    }
                }).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        for(var value of resp.data){
                            var service ={text:value.serviceName , value: value.serviceCode };
                            vm.options.push(service);
                        }
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
            showSuitLists:function(){
                var vm = this;
                axios.get(this.suitUrl).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        for(var value of resp.data){
                            var suit ={text:value.suitCode , value: value.suitCode };
                            vm.suitOptions.push(suit);
                        }
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
            saveTemplate: function () {
                var vm = this;
                if (this.templateName == "") {
                    this.$Notice.error({
                        desc: "模板名称不允许为空"
                    });
                    return false;
                }
                if (this.templateCode == "") {
                    this.$Notice.error({
                        desc: "模板编号不允许为空"
                    });
                    return false;
                }
                if (this.suitCode == "") {
                    this.$Notice.error({
                        desc: "套码不允许为空"
                    });
                    return false;
                }
                if (this.serviceCode == "") {
                    this.$Notice.error({
                        desc: "所属服务不允许为空"
                    });

                    return false;
                }
                if (this.src == "") {
                    this.$Notice.error({
                        desc: "模板源码不允许为空"
                    });
                    return false;
                }

                let params = {
                    "templateName": this.templateName,
                    "templateCode": this.templateCode,
                    "suitCode": this.suitCode,
                    "serviceCode": this.serviceCode,
                    "src": this.src,
                    "content": this.content,
                    "remark": this.remark
                };
                this.loading =true;
                if (this.addOrEdit == 0) {
                    axios.post(this.addUrl, params, {
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    }).then(function (resp) {
                        vm.loading =false;
                        var resp = resp.data;
                        if (resp.code == "0") {
                            vm.$Notice.success({
                                title: "成功"
                            });
                            vm.createShow = false;
                            vm.reqComplete();
                        } else {
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
                } else {
                    params.id = this.addOrEdit;
                    axios.put(this.addUrl, params, {
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    }).then(function (resp) {
                        vm.loading =false;
                        var resp = resp.data;
                        if (resp.code == "0") {
                            vm.$Notice.success({
                                title: "成功"
                            });
                            vm.createShow = false;
                            vm.reqComplete();
                        } else {
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
            cancelTemplate: function () {
                this.createShow = false;
            },
            closeDig: function (res) {
                this.createShow = res;
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
                let url = this.addUrl + "/" + id;
                axios.get(url).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        vm.info = resp.data;
                    } else {
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
            closeDetailDig: function (res) {
                this.detailShow = res;
            },
            /**
             * 功能：删除套码
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-15 18:53
             */
            del: function (item) {
                this.deleteShow = true;
                this.deleteId = item;

            },
            confirmDel: function () {
                let url = this.addUrl + '/' + this.deleteId.id;
                var vm = this;
                axios.delete(url).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        vm.$Notice.success({
                            title: "成功"
                        });
                        vm.deleteShow = false;
                        vm.reqComplete();
                    } else {
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
            cancelDel: function () {
                this.deleteShow = false;
            },
            closeDeleteDig: function (res) {
                this.deleteShow = res;
            },

            handleIt: function (res) {
                this.dataList = res.templateList;
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
    .form-group {
        .input-group{
            label{
                width:35%;
            }
            span,input,textarea,select{
                width:60%;
            }
        }
    }
</style>