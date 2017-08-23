/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-01-19 08:56
 * 创建作者: haoxu
 * 文件名称: interface-list.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div class="container">
        <div class="search-bar">
            <ul>
                <li>
                    <a-input placeholder="请输入接口名称/接口编码" v-model="keywords" class="input-width"></a-input>
                </li>
                <li>
                    <a-input placeholder="服务名称" v-model="serviceNamekeywords" class="input-width"></a-input>
                </li>
                <li>
                    <a-button type="primary" @click="search" icon="search">查询</a-button>
                </li>
                <li>
                    <a-button type="warning" @click="createInterface(-1)" icon="plus">注册接口</a-button>
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
                <td :style="{ width:trWidth(1)}" :title="item.interfaceName">{{item.interfaceName}}</td>
                <td :style="{ width:trWidth(2)}" :title="item.interfaceCode">{{item.interfaceCode}}</td>
                <td :style="{ width:trWidth(3)}" :title="item.serviceName">{{item.serviceName}}</td>
                <td :style="{ width:trWidth(4)}">{{item.ifcheck ? "是":"否"}}</td>
                <td :style="{ width:trWidth(5)}" v-if="item.status == 0">未注册</td>
                <td :style="{ width:trWidth(5)}" v-if="item.status == 1">已注册</td>
                <td :style="{ width:trWidth(6) }" class="ivu-table-cell">
                    <span  @click="createInterface(item.id)">
                         <a href="javascript:void(0);" title="编辑" class="ivu-btn ivu-btn-eye ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
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
                        <label for="interfaceName">接口名称：</label>
                        <input type="text" id="interfaceName" v-model="interfaceName">
                    </div>
                    <div class="input-group clearfix">
                        <label for="interfaceMethod">接口方法：</label>
                        <select id="interfaceMethod" v-model="interfaceMethod">
                          <option value=''>请选择方法</option>
                          <option v-for="option in funOptions" v-bind:value="option.value">
                             {{ option.text }}
                          </option>
                        </select>
                    </div>
                    <div class="input-group clearfix">
                        <label for="interfaceUrl">接口地址：</label>
                        <input type="text" id="interfaceUrl" v-model="interfaceUrl">
                    </div>
                    <div class="input-group clearfix">
                        <label for="ifcheck">校验权限:</label>
                        <select id="ifcheck" v-model="ifcheck">
                            <option value=''>请选择</option>
                            <option value='1'>是</option>
                            <option value='0'>否</option>
                        </select>
                    </div>
                    <div class="input-group clearfix">
                        <label for="status">注册状态:</label>
                        <select id="status" v-model="status">
                            <option value=''>请选择</option>
                            <option value='0'>待注册</option>
                            <option value='1'>已注册</option>
                        </select>
                    </div>
                    <div class="input-group clearfix">
                        <label for="serviceId">所属服务:</label>
                        <select id="serviceId" v-model="serviceId">
                            <option value=''>请选择服务</option>
                            <option v-for="option in options" v-bind:value="option.value">
                                {{ option.text }}
                            </option>
                        </select>
                    </div>
                    <div class="input-group clearfix">
                        <label for="remark" class="textarea">备注:</label>
                        <textarea cols="6" id="remark" v-model="remark"></textarea>
                    </div>

                    <div class="dialog-buttons">
                        <a-button type="primary" icon="checkmark-round" :loading="loading" @click="saveInterface">
                            <span>提交</span>
                        </a-button>
                        <a-button type="info" icon="chevron-left"  @click="cancelInterface">
                            <span>取消</span>
                        </a-button>
                    </div>
                </div>
            </div>
        </np-dialog>

        <np-dialog :show-dialog="deleteShow" :title = "titles[3]" v-on:close ="closeDeleteDig">
            <div class="dialog-body" slot="body">
                <div class="form-group delete-popup">
                    <img src="../../image/icon/delete.png">
                    您确定删除该接口吗？
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
    import Loading from '../util/loading.vue';
    import BootPage from '../util/pager.vue';
    import NpDialog from "../util/dialog.vue";

    export default {
        name: 'interfaceList',
        data(){
            return {
                columns: ['序号', '接口名称', '接口编码', '服务名称', '是否校验权限','状态', '操作'],
                scales: ['5','20','27','12','12','11','13'],
                dataList: [],
                loading: false,
                apiUrl: '/admin/interface',
                allUrl:'/admin/service',
                showLoading: false,
                createShow: false,
                editShow:false,
                detailShow:false,
                deleteShow:false,
                titles:["注册接口","编辑接口","删除接口"],
                options:[],
                funOptions:[
                    {text:"GET" , value:"GET" },
                    {text:"POST" , value:"POST"},
                    {text:"PUT" , value: "PUT" },
                    {text:"DELETE" , value: "DELETE" }
                    ],
                pageLen: 10, // 可显示的分页数
                paramList:{},
                info:{},
                keywords:'',
                serviceNamekeywords:'',
                serviceName:'',
                interfaceName:'',
                interfaceMethod:'',
                interfaceUrl:'',
                serviceId:'',
                remark:'',
                status:'',
                ifcheck:'',
                width:0
            }
        },
        created: function () {
            this.showServiceLists();
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
             * 功能：创建前清空一下
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-03-01 09:40
             */
            clearEdit:function(){
                this.interfaceName = '';
                this.interfaceMethod = '';
                this.interfaceUrl = '';
                this.serviceId = '';
                this.remark = '';
            },
            /**
             * 功能：menu列表
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-08 15:03
             */
            search: function () {
                this.paramList = { "keyword": this.keywords ,"serviceName":this.serviceNamekeywords };
            },
           /* showInterfaceList:function(){
                var vm = this;
                vm.showLoading = true;
                axios.get(this.apiUrl,{
                    params: {
                        page : 1,
                        pageSize:20
                    }
                }).then(function (resp) {
                    vm.showLoading = false;
                    var resp = resp.data;
                    if (resp.code== 0) {
                        vm.dataList = resp.data.list;
                        vm.pageTotal =resp.data.totalPage;
                        vm.len =  resp.data.pageSize;
                        vm.currentPage = 0;
                    }else{
                        console.log("err=>", error);
                    }
                }).catch(function (error) {
                    vm.showLoading = false;
                });
            },*/

            /**
             * 功能：注册接口/编辑接口
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-07 15:21
             */
            createInterface:function(id) {
                this.clearEdit();
                if(id!= -1){
                    this.addOrEdit =id;
                    var vm = this;
                    let url = this.apiUrl +"/"+ id;
                    axios.get(url).then(function (resp) {
                        var resp = resp.data;
                        if(resp.code =="0"){
                            vm.interfaceName = resp.data.interfaceName;
                            vm.interfaceMethod = resp.data.interfaceMethod;
                            vm.interfaceUrl = resp.data.interfaceUrl;
                            vm.serviceId =  resp.data.serviceId;
                            vm.remark = resp.data.remark;
                            vm.ifcheck = resp.data.ifcheck?1:0;
                            vm.status = resp.data.status;

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
                            var service ={text:value.serviceName , value: value.id };
                            vm.options.push(service);
                        }
                    }else{
                        console.log(resp.msg);
                    }
                }).catch(function (error) {
                    console.log(error);
                });

            },
            saveInterface:function(){
                var vm = this;
                if(this.interfaceName ==""){
                    this.$Notice.error({
                        desc: "请输入接口名称"
                    });

                    return false;
                }
                if(this.interfaceMethod ==""){
                    this.$Notice.error({
                        desc: "请输入接入方法"
                    });

                    return false;
                }
                if(this.ifCheck ==""){
                    this.$Notice.error({
                        desc: "请选择是否做权限校验"
                    });
                    return false;
                }
                if(this.serviceId ==""){
                    this.$Notice.error({
                        desc: "请选择所属服务"
                    });
                    return false;
                }
                var ifchecks = this.ifcheck==1 ? true: false;
                let params = { "interfaceName": this.interfaceName,"interfaceMethod": this.interfaceMethod,"interfaceUrl": this.interfaceUrl,
                    "ifcheck":ifchecks ,"status":this.status, "serviceId":this.serviceId ,"remark" :this.remark};
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
            cancelInterface:function(){
                this.createShow = false;
            },
            closeDig:function (res) {
                this.createShow = res;
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
                let url =  this.apiUrl +"/"+ this.deleteId.id;
                axios.delete(url).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){

                        vm.$Notice.success({
                            title: "成功"
                        });

                        vm.deleteShow = false;
                        //vm.dataList.pop(vm.deleteId);
                        //删除成功
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
    .container{
        .search-input{
            width:15%;
            margin-right:10px;
        }
    }

</style>
