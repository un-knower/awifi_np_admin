/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-04 16:28
 * 创建作者: haoxu
 * 文件名称: upload-config.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div class="container">
        <div class="config">
        <div class="canvas">
            <header-canvas :title="title" :num="selectNum"></header-canvas>
        </div>
        <div class="form-group">
            <div class="input-group clearfix">
                <label>服务名称：</label>
                <div class="form-text">{{serviceName}}</div>
            </div>
            <div class="input-group clearfix">
                <label for="version">服务版本号：</label>
                <input type="text" id="version" v-model="version">
            </div>
            <div class="input-group clearfix">
                <label for="serviceHost">服务域名/IP：</label>
                <input type="text" id="serviceHost" v-model="serviceHost">
            </div>
            <div class="input-group clearfix">
                <label for="servicePort">服务端口：</label>
                <input type="text" id="servicePort" v-model="servicePort">
            </div>
            <div class="input-group clearfix">
                <label for="checkSafe">安全接口：</label>
                <input type="text" id="checkSafe" v-model="checkSafe" placeholder="安全校验接口URL">
            </div>
            <div class="input-group clearfix">
                <label for="checkAuth">权限校验接口：</label>
                <input type="text" id="checkAuth" v-model="checkAuth" placeholder="权限校验接口URL">
            </div>
            <div class="input-group clearfix">
                <label for="rolePermissionApi">角色权限接口：</label>
                <input type="text" id="rolePermissionApi" v-model="rolePermissionApi" placeholder="角色权限接口URL">
            </div>
            <div class="input-group clearfix">
                <label for="isMenu">是否为菜单:</label>
                <select id="isMenu" v-model="isMenu">
                    <option value="">请选择</option>
                    <option value="1">是</option>
                    <option value="0">否</option>
                </select>
            </div>
            <div class="input-group clearfix">
                <label for="roleMenuApi">角色菜单接口：</label>
                <input type="text" id="roleMenuApi" v-model="roleMenuApi" placeholder="菜单接口URL">
            </div>
            <div class="input-group clearfix">
                <label for="menuTreeApi">菜单树接口：</label>
                <input type="text" id="menuTreeApi" v-model="menuTreeApi" placeholder="菜单树接口URL">
            </div>
            <div class="squaredTwo" >
                <input type="checkbox" id="mySwitch"/>
                <label for="mySwitch" @click="interfaceConfig">接口配置</label>
            </div>
            <div class="normal-buttons">
                <a-button type="primary" icon="checkmark-round" :loading="loading" @click="nextTip">
                    <span>下一步</span>
                </a-button>
                <a-button type="info" icon="chevron-left"  @click="backToList">
                    <span>取消</span>
                </a-button>
            </div>
        </div>
        </div>

        <np-dialog :show-dialog="interfaceShow" :title = "titles" v-on:close ="closeDetailDig" :interface="interfaceDig">
            <div class="dialog-body" slot="body">
                <table style="margin:0 auto;">
                    <tr>
                        <th v-for="(col,index) in columns" :style="{ width:trWidth(index)}">
                            <div class="squaredTwo" v-if="index == 4||index == 5">
                                <input type="checkbox" :id="'squaredTwo'+index" @click="selectAll(index,$event)"/>
                                <label>{{ col }}</label>
                            </div>
                            <div v-else>{{ col }}</div>
                        </th>
                    </tr>
                    <tr v-for="(item,index) in dataList">
                        <td :style="{ width:trWidth(0) }">{{index+1}}</td>
                        <td :style="{ width:trWidth(1) }">{{item.interfaceName}}</td>
                        <td :style="{ width:trWidth(2) }">{{item.interfaceMethod}}</td>
                        <td :style="{ width:trWidth(3) }">{{item.serviceCode}}</td>
                        <td :style="{ width:trWidth(4) }">
                            <input type="checkbox" class="check" :attr="item.id"  @click="selectCheck($event)" v-if="item.ifcheck" checked="checked"/>
                            <input type="checkbox" class="check" :attr="item.id"  @click="selectCheck($event)" v-else/>
                        </td>
                        <td :style="{ width:trWidth(5) }">
                            <input type="checkbox" class="register" :attr="item.id" @click="selectRegister($event)" v-if="item.status==1" checked="checked"/>
                            <input type="checkbox" class="register" :attr="item.id" @click="selectRegister($event)" v-else/>
                        </td>
                    </tr>
                    <tr class="noInfo" v-if="!dataList || dataList.length == 0">没有找到相关数据</tr>
                    <div class="dialog-buttons" style="margin-left:38%;">
                        <a-button type="primary" icon="checkmark-round" :loading="diaLoading" @click="saveInterface">
                            <span>提交</span>
                        </a-button>
                        <a-button type="info" icon="chevron-left"  @click="cancel">
                            <span>取消</span>
                        </a-button>
                    </div>
                </table>

            </div>
        </np-dialog>
    </div>
</template>

<script type="text/javascript">
    import Vue from 'vue';
    import AwifiUI from 'awifiui';
    Vue.use(AwifiUI);
    import axios from 'axios';
    import router from '../../router';
    import HeaderCanvas from './header-canvas.vue';
    import NpDialog from "../util/dialog.vue";
    import Loading from '../util/loading.vue';
    export default{
        data(){
            return {
                columns: ['序号', '接口名称', '接入方法', '服务名称', '校验接口','注册接口'],
                scales: ['12','17','17','18','18','18'],
                dataList: [],
                loading: false,
                diaLoading:false,
                interfaceUrl: '/admin/service/tmpinterface',
                title:["配置服务","测试服务","审核服务","上线服务"],
                selectNum: 1,
                ApiUrl: '/admin/servicepublishlog',
                serviceUrl:'/admin/service',
                interfaceShow:false,
                showLoading:false,
                titles:'接口配置',
                serviceHost:'',
                servicePort:'',
                version:'',
                isMenu:'',
                checkAuth:'',
                checkSafe:'',
                rolePermissionApi:'',
                menuTreeApi:'',
                roleMenuApi:'',
                notCheckList:[],
                notRegisterList:[],
                params: this.$route.params,
                interfaceDig:true,
                width:720,
                serviceId:0,
                serviceName:''
            }
        },
        created:function(){
            if(this.params.publishStatus == 0){
                this.serviceId = this.params.sid;
                this.showServiceInfo();
            }else{
                this.showConfigInfo();
            }
        },
        methods: {
            trWidth:function(index){
                var scale = parseFloat(this.scales[index]),
                        indexWidth = this.width * scale/100;
                return indexWidth +'px';
            },
            /**
             * 功能：发起上线，升级按钮，获取服务信息
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-03-02 15:18
             */
            showServiceInfo:function(){
                    var vm = this;
                    //根据sid获取显示信息
                    let url = this.serviceUrl +"/"+ this.serviceId;
                    axios.get(url).then(function (resp) {
                        var resp = resp.data;
                        if(resp.code =="0"){
                            vm.version = resp.data.version;
                            vm.serviceHost = resp.data.serviceHost;
                            vm.servicePort = resp.data.servicePort;
                            vm.checkSafe = resp.data.checkSafe;
                            vm.checkAuth = resp.data.checkAuth;
                            vm.rolePermissionApi = resp.data.rolePermissionApi;
                            vm.isMenu = resp.data.isMenu ? 1:0;
                            vm.roleMenuApi = resp.data.roleMenuApi;
                            vm.menuTreeApi = resp.data.menuTreeApi;
                            vm.serviceName = resp.data.serviceName;

                        }else{
                            vm.$Notice.error({
                                title: resp.code,
                                desc: resp.msg
                            });
                        }
                    }).catch(function (error) {
                        vm.$Notice.error({
                            title: error
                        });
                    });
            },
            /**
             * 编辑日志中的服务配置
             */
            showConfigInfo: function (){
                var vm = this;
                let url = this.ApiUrl +"/"+ this.params.logid;
                axios.get(url).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.version = resp.data.npService.version;
                        vm.serviceHost = resp.data.npService.serviceHost;
                        vm.servicePort = resp.data.npService.servicePort;
                        vm.checkSafe = resp.data.npService.checkSafe;
                        vm.checkAuth = resp.data.npService.checkAuth;
                        vm.rolePermissionApi = resp.data.npService.rolePermissionApi;
                        vm.isMenu = resp.data.npService.isMenu ? 1:0;
                        vm.roleMenuApi = resp.data.npService.roleMenuApi;
                        vm.menuTreeApi = resp.data.npService.menuTreeApi;
                        vm.serviceId = resp.data.serviceId;
                        vm.serviceName = resp.data.serviceName;
                    }else{
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });
                    }
                }).catch(function (error) {
                    vm.$Notice.error({
                        title: error
                    });
                });


            },
            /**
             * 功能：保存配置
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-13 14:40
             */

            nextTip:function(){
                var vm = this;
                if(!this.serviceHost){
                    this.$Notice.error({
                        desc: "请输入服务域名"
                    });

                    return false;
                }
                if(!this.serviceHost.startsWith("http")){
                    this.$Notice.error({
                        desc: "服务域名需要http开头"
                    });

                    return false;
                }
                if(this.servicePort!=null && !/^[0-9]*$/.test(this.servicePort)){
                    this.$Notice.error({
                        desc: "请输入正确的端口"
                    });

                    return false;
                }
                if(this.isMenu.toString().trim() ==""){
                    this.$Notice.error({
                        desc: "请选择是否为菜单"
                    });
                    return false;
                }
                if(!this.checkAuth){
                    this.$Notice.error({
                        desc: "请输入权限接口"
                    });

                    return false;
                }
                if(!this.checkSafe){
                    this.$Notice.error({
                        desc: "请输入安全接口"
                    });

                    return false;
                }
                if(this.isMenu  ==1 && !this.menuTreeApi ){
                    this.$Notice.error({
                        desc: "请输入菜单树接口"
                    });

                    return false;
                }
                if(this.isMenu== 1 && !this.roleMenuApi ){
                    this.$Notice.error({
                        desc: "请输入角色菜单授权接口"
                    });

                    return false;
                }
                var menus = (this.isMenu == 1) ? true:false;
                this.loading =true;
                let npService = { "id":this.serviceId,"serviceHost": this.serviceHost,"servicePort": this.servicePort,"version": this.version,
                    "checkAuth":this.checkAuth,"isMenu":menus,"checkSafe":this.checkSafe ,"rolePermissionApi":this.rolePermissionApi,
                    "roleMenuApi":this.roleMenuApi,"menuTreeApi":this.menuTreeApi};
               let params ={ "npService": npService };
                if(this.params.logid == 0){
                    axios.post(this.ApiUrl, params,{
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
                            setTimeout(function () {
                                //跳转到测试页面
                                router.push({path: '/online/test/'+resp.data.id +'/1'});  //logid+sid;
                            }, 1000);
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
                    params.id = this.params.logid;
                    params.publishStatus = 3;
                    axios.put(this.ApiUrl, params,{
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
                            setTimeout(function () {
                                //跳转到测试页面
                                router.push({path: '/online/test/'+vm.params.logid +'/1'});  //logid+sid;
                            }, 1000);
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
            /**
             * 功能：返回列表页
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-13 14:40
             */

            backToList:function(){
                router.push({path: '/online/list'});
            },
            /**
             * 功能：接口配置
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-13 14:39
             */
            interfaceConfig:function(){
                this.interfaceShow = true;
                this.showInterfaceList();
            },
            closeDetailDig:function (res) {
                this.interfaceShow = res;
            },
            /**
             * 功能：接口配置列表
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-13 15:21
             */
            showInterfaceList:function(){
                var vm = this;
                vm.showLoading = true;
                var url = "/admin/service/"+this.serviceId+"/tmpinterface";
                axios.get(url).then(function (resp) {
                    vm.showLoading = false;
                    var resp = resp.data;
                    if (resp.code== 0) {
                        vm.dataList = resp.data;
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
             * 功能：校验接口和安全接口的选择
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-28 10:38
             */

            selectAll:function(index,e){
                if(index == 4){
                    var checks = document.querySelectorAll(".check");
                    for(var value of checks){
                        if(e.target.checked){
                            value.checked = true;
                        }else{
                            value.checked = false;
                        }
                    }

                }else{
                    var registers = document.querySelectorAll(".register");
                    for(var value of registers){
                        if(e.target.checked){
                            value.checked = true;
                        }else{
                            value.checked = false;
                        }
                    }
                }
            },
            selectCheck:function(e){
                var checks = document.querySelectorAll(".check");
                var squaredTwo4 = document.getElementById("squaredTwo4");
                var i = 0;
                for(var value of checks){
                    if(value.checked){
                        i++;
                    }else{

                    }
                }
                if(e.target.checked){
                    if(i == checks.length){
                        squaredTwo4.checked = true;
                    }

                }else{
                    i--;
                    squaredTwo4.checked = false;
                }
            },
            selectRegister:function(e){
                var registers = document.querySelectorAll(".register");
                var squaredTwo5 = document.getElementById("squaredTwo5");
                var i = 0;
                for(var value of registers){
                    if(value.checked){
                        i++;
                    }else{
                    }
                }
                if(e.target.checked){
                    if(i == registers.length){
                        squaredTwo5.checked = true;
                    }
                }else{
                    i--;
                    squaredTwo5.checked = false;
                }
            },
            /**
             * 功能：保存、取消按钮事件
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-13 15:28
             */
            cancel:function(){
                this.interfaceShow = false;
            },
            saveInterface:function(){
                var vm = this;
                var checks = document.querySelectorAll(".check");
                var registers = document.querySelectorAll(".register");
                for(var value of registers){
                    if(!value.checked){
                        this.notRegisterList.push(parseInt(value.getAttribute("attr")));
                    }else{
                    }
                }
                for(var value of checks){
                    if(!value.checked){
                        this.notCheckList.push(parseInt(value.getAttribute("attr")));
                    }else{
                    }
                }
                var url = "/admin/service/"+this.serviceId+"/tmpinterface";
                let params = { "notCheckList": this.notCheckList ,"notRegisterList": this.notRegisterList };
                this.diaLoading = true;
                axios.put(url, params,{
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }).then(function (resp) {
                    vm.diaLoading = false;
                    var resp = resp.data;
                    if(resp.code=="0"){
                        vm.$Notice.success({
                            title: "成功"
                        });
                        vm.interfaceShow = false;

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
        components: {
            HeaderCanvas,
            NpDialog,
            Loading
        }
    }
</script>
<style scoped lang="scss">
    /*@import "../../style/upload.scss";*/
    /*@import "../../style/list.scss";*/

    .interfaceDig{
        width:800px;
    }
</style>

