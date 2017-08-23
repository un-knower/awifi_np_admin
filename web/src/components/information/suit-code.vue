/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-06 19:46
 * 创建作者: haoxu
 * 文件名称: suit-code.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div class="container">
        <div class="search-bar">
            <ul>
                <li>
                    <a-input placeholder="请输入套码编码或套码名称" v-model="suitcode" class="input-width"></a-input>
                </li>
                <li>
                    <a-button type="primary" @click="search" icon="search">查询</a-button>
                </li>
                <li>
                    <a-button type="warning" @click="createSuit(-1)" icon="plus">创建套码</a-button>
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
                <td :style="{ width:trWidth(2) }">{{item.suitName}}</td>
                <td :style="{ width:trWidth(3) }">{{item.suitCode}}</td>
                <td :style="{ width:trWidth(4) }">{{item.createDate |getDate }}</td>
                <td :style="{ width:trWidth(5) }" class="ivu-table-cell">
                    <span @click="detail(item.id)">
                         <a href="javascript:void(0);" title="查看" class="ivu-btn ivu-btn-eye ivu-btn-circle ivu-btn-small ivu-btn-icon-only">
                             <i class="ivu-icon ivu-icon-eye"></i>
                         </a>
                    </span>
                    <span  @click="createSuit(item.id)">
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
                        <label for="suitCode">套码编码：</label>
                        <input type="text" id="suitCode" v-model="suitCode">
                    </div>
                    <div class="input-group clearfix">
                        <label for="suitName">套码名称：</label>
                        <input type="text" id="suitName" v-model="suitName">
                    </div>
                    <div class="input-group clearfix">
                        <label for="remarks" class="textarea">备注:</label>
                        <textarea cols="6" id="remarks" v-model="remark"></textarea>
                    </div>

                    <div class="dialog-buttons">
                        <a-button type="primary" icon="checkmark-round" :loading="loading" @click="saveSuit">
                            <span>提交</span>
                        </a-button>
                        <a-button type="info" icon="chevron-left"  @click="cancelSuit">
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
                        <label>套码编码：</label>
                        <span class="form-text" v-text="info.suitCode"></span>
                    </div>
                    <div class="input-group clearfix">
                        <label>套码名称：</label>
                        <span class="form-text" v-text="info.suitName"></span>
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
                    您确定删除该套码吗？
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
    import date from "../../api/date";

    export default {
        name: 'suitList',
        data(){
            return {
                columns: ['序号', '套码ID','套码名称', '套码编码', '创建时间', '操作'],
                scales: ['12','16','16','16','18','22'],
                dataList: [],
                loading:false,
                apiUrl: '/admin/suits',
                addUrl:'/admin/suit',
                suitcode:'',
                showLoading: false,
                createShow: false,
                editShow:false,
                detailShow:false,
                deleteShow:false,
                deleteId:0,
                titles:["创建套码","编辑套码","套码详情","删除套码"],
                pageLen: 5, // 可显示的分页数
                paramList:{},
                info:{},
                remark:"",
                suitCode:'',
                suitName:'',
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
            getDate: function (tm) {
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
            /**
             * 功能：创建前清空一下
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-03-01 09:40
             */
            clearEdit:function(){
                this.suitCode = '';
                this.suitName ='';
                this.remark = '';
            },
            /**
             * 功能：suit列表
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-08 15:03
             */

            search: function () {
                this.paramList = { "keyword": this.suitcode };
            },
            showSuitList:function(keyword){
                var vm = this;
                vm.showLoading = true;
                var key = keyword?keyword:'';
                axios.get(this.apiUrl,{
                    params: {
                        pageno : 1,
                        pagesize:20,
                        keyword:key
                    }
                }).then(function (resp) {
                    vm.showLoading = false;
                    var resp = resp.data;
                    if (resp.code== 0) {
                        vm.dataList = resp.data.suitList;
                        vm.pageTotal =resp.data.totalPage;
                        vm.len =  resp.data.pageSize;
                        vm.pageLen =resp.data.page;
                        vm.paramList = { keyword: vm.suitcode };
                    }else{
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });
                    }
                }).catch(function (error) {
                    vm.showLoading = false;
                    vm.$Notice.error({
                        desc: error
                    });
                });
            },
            /**
             * 功能：创建套码、编辑套码
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-07 15:21
             */
            createSuit:function(id) {
                this.clearEdit();
                if(id!= -1){
                    this.addOrEdit =id;
                    var vm = this;
                    let url = this.addUrl +"/"+ id;
                    axios.get(url).then(function (resp) {
                        var resp = resp.data;
                        if(resp.code =="0"){
                            vm.suitCode = resp.data.suitCode;
                            vm.suitName = resp.data.suitName;
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
            saveSuit:function(){
                var vm = this;
                if(this.suitCode ==""){
                    this.$Notice.error({
                        desc: "套码不允许为空"
                    });
                    return false;
                }
                let params = { "suitCode":this.suitCode,"suitName":this.suitName,"remark":this.remark};
                this.loading =true;
                if(this.addOrEdit  ==0){
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
                            desc: error
                        });
                    });
                }else{
                    params.id = this.addOrEdit;
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
                            desc: error
                        });
                    });
                }
            },
            cancelSuit:function(){
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
             * 功能：删除套码
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-15 18:53
             */
            del:function(item){
                this.deleteShow = true;
                this.deleteId = item;
            },
            confirmDel:function(){
                let url = this.addUrl+'/'+this.deleteId.id;
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

            handleIt: function (res) {
                this.dataList = res.suitList;
            },
            showDetail: function (item) {
                router.push({path: '/detail/page', query: {Id: item.id}});
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