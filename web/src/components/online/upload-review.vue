/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-04 16:32
 * 创建作者: haoxu
 * 文件名称: upload-review.vue
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
                    <span class="form-text" v-text="serviceName"></span>
                </div>
                <div class="input-group clearfix">
                    <label>服务版本号：</label>
                    <span class="form-text" v-text="info.version"></span>
                </div>
                <div class="input-group clearfix">
                    <label>服务域名/IP：</label>
                    <span class="form-text" v-text="info.serviceHost"></span>
                </div>
                <div class="input-group clearfix">
                    <label>服务端口：</label>
                    <span class="form-text" v-if="info.servicePort" v-text="info.servicePort"></span>
                    <span class="form-text" v-else>暂无</span>
                </div>
                <div class="input-group clearfix">
                    <label>安全接口：</label>
                    <span class="form-text" v-text="info.checkSafe"></span>
                </div>
                <div class="input-group clearfix">
                    <label>权限校验接口：</label>
                    <span class="form-text" v-text="info.checkAuth"></span>
                </div>
                <div class="input-group clearfix">
                    <label>角色权限接口：</label>
                    <span class="form-text" v-text="info.rolePermissionApi"></span>
                </div>
                <div class="input-group clearfix">
                    <label>是否为菜单：</label>
                    <span class="form-text">{{ info.isMenu==null?"":info.isMenu?"是":"否" }}</span>
                </div>
                <div class="input-group clearfix">
                    <label>角色菜单接口：</label>
                    <span class="form-text"  v-if="info.roleMenuApi" v-text="info.roleMenuApi"></span>
                    <span class="form-text" v-else>暂无</span>
                </div>
                <div class="input-group clearfix">
                    <label>菜单树接口：</label>
                    <span class="form-text" v-if="info.menuTreeApi" v-text="info.menuTreeApi"></span>
                    <span class="form-text" v-else>暂无</span>
                </div>
            </div>
        </div>
        <div class="interface-list" style="margin:0 auto;width:720px;">
            <table style="margin:0 auto;">
                <tr>
                    <th v-for="(col,index) in columns" :style="{ width:trWidth(index) }">
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
                        <input type="checkbox" class="check" :attr="item.id"  @click="selectCheck($event)" v-else />
                    </td>
                    <td :style="{ width:trWidth(5) }">
                        <input type="checkbox" class="register" :attr="item.id" @click="selectRegister($event)" v-if="item.status==1" checked="checked"/>
                        <input type="checkbox" class="register" :attr="item.id" @click="selectRegister($event)" v-else/>
                    </td>
                </tr>
                <tr class="noInfo" v-if="!dataList || dataList.length == 0">没有找到相关数据</tr>
            </table>
        </div>

        <div class="normal-buttons">
            <a-button type="primary" icon="checkmark-round" :loading="loading" @click="nextTip">
                <span>审核通过</span>
            </a-button>
            <a-button type="info" icon="chevron-left"  @click="back">
                <span>上一步</span>
            </a-button>
        </div>
    </div>
</template>

<script type="text/javascript">
    import axios from 'axios';
    import HeaderCanvas from './header-canvas.vue';
    import router from '../../router';
    export default{
        data(){
            return {
                title:["配置服务","测试服务","审核服务","上线服务"],
                selectNum: 3,
                columns: ['序号', '接口名称', '接入方法', '服务名称', '校验接口','注册接口'],
                scales: ['11','16','15','16','21','17'],
                dataList: [],
                apiUrl:'/admin/servicepublishlog',
                info:{},
                total:{},
                loading:false,
                serviceName:'',
                width:720
            }
        },
        mounted:function(){
            this.showDetail(this.$route.params.logid);
        },
        methods: {
            trWidth:function(index){
                var scale = parseFloat(this.scales[index]),
                        indexWidth = this.width * scale/100;
                return indexWidth +'px';
            },
            /**
             * 功能：下一步
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-13 19:12
             */
            nextTip:function(){
                var vm = this;
                let params = {"id":parseInt(this.$route.params.logid),"publishStatus": 4};
                vm.loading = true;
                axios.put(this.apiUrl, params,{
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }).then(function (resp) {
                    vm.loading = false;
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.$Notice.success({
                            title: "成功"
                        });
                        setTimeout(function () {
                            router.push({path: '/online/success/'+vm.$route.params.logid});
                        }, 1000);
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
            /*cancel:function(){
                var publishStatus = 3;
                router.push({path: '/online/test/'+this.$route.params.logid+'/'+serviceId+'/ '+ publishStatus});
            },*/
            back:function(){
                var publishStatus = 3;
                router.push({path: '/online/test/'+this.$route.params.logid+'/'+ publishStatus});
            },
            showDetail:function(id){
                var vm = this;
                let url = this.apiUrl + "/"+id;
                axios.get(url).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.total = resp.data;
                        vm.info = resp.data.npService;
                        vm.serviceName =  resp.data.serviceName;
                        vm.showInterfaceList(resp.data.serviceId);
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
            /**
             * 功能：接口配置列表
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-13 15:21
             */
            showInterfaceList:function(sid){
                var vm = this;
                vm.showLoading = true;
                var url = "/admin/service/"+sid+"/tmpinterface";
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
            }
        },
        components: {
            HeaderCanvas
        }
    }
</script>
<style scoped lang="scss">
    /*@import "../../style/upload.scss";*/
    /*@import "../../style/list.scss";*/

</style>
