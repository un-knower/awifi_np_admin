/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-24 09:47
 * 创建作者: haoxu
 * 文件名称: interface-auth.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div>
        <div class="content">
            <div class="role-tree" ref="tree">
                <role-tree :lists="lists" :name="name" :children="children"  @data="gettingSelectedLists"></role-tree>
            </div>

            <div class="normal-buttons">
                <a-button type="primary" icon="checkmark-round" @click="saveFun">
                    <span>提交</span>
                </a-button>
                <a-button type="info" icon="chevron-left"  @click="cancel">
                    <span>取消</span>
                </a-button>
            </div>
        </div>
    </div>
</template>
<script type="text/javascript">
    import axios from 'axios';
    import router from '../../router';
    import roleTree from '../util/roleTree.vue';
    export default{
        name: 'role',
        data(){
            return{
                serviceUrl:'/admin/service/',
                roleId:this.$route.params.id,
                serviceLists:[],
                lists:[],
                name:"interfaceName",
                children:"childPermission",
                loading:false
            }
        },
        created: function () {
            this.showService();
        },
        mounted:function(){
            this.setHeight();
        },
        methods: {
            //设置显示框高度
            setHeight:function(){
                var height = window.outerHeight - 300;
                var tree = this.$refs.tree;
                tree.style.maxHeight = height+"px";
            },
            //数组排序
            compare:function(property){
                return function(a,b){
                    var value1 = a[property];
                    var value2 = b[property];
                    return value1 - value2;
                }
            },
            //显示所有服务
            showService: function () {
                var vm = this;
                axios.get(this.serviceUrl,{
                    params:{
                        isOnline:true
                    }
                }).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.serviceLists = resp.data;
                        var i=0;
                        for(var serviceItem of vm.serviceLists){
                         //   serviceItem.nocheck = true;
                            serviceItem.open = true;
                            serviceItem.interfaceName = serviceItem.serviceName;
                            serviceItem.childPermission =[];
                            serviceItem.order =i++;
                            vm.showInterfaceBySidRid(serviceItem, vm.roleId);
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
            //所有接口和已有接口
            showInterfaceBySidRid:function(serviceItem,rid){
                var vm = this;
                var Url = "/admin/bizrole/"+rid+"/service/"+serviceItem.id+"/interface";
                axios.get(Url).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        serviceItem.childPermission =  vm.initTransfer(resp.data, serviceItem.id );
                    }else{
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });
                    }
                    vm.lists.push(serviceItem);
                    vm.lists.sort(vm.compare('order'));
                }).catch(function (error) {
                    vm.lists.push(serviceItem);
                    vm.lists.sort(vm.compare('order'));
                });
            },
            initTransfer:function(lists ,serviceId){
                for(var myValue of lists){
                    if(myValue.flag){
                        myValue.checked = myValue.flag;
                    }
                    myValue.sid = serviceId;
                }
                return lists;
            },
            gettingSelectedLists:function(data){
                if(data.length == 0){
                    this.$Notice.error({
                        desc: "请选择授权接口"
                    });
                    return false;
                }
                var tarr =[];
                for(var value of data){
                    if(tarr.length == 0){
                        tarr.push(value.sid);
                    }else{
                        var index =0;
                        for(var item of tarr){
                            if(value.sid == item){
                                break;
                            }else{
                                index++;
                            }
                        }
                        if(index == tarr.length){
                            tarr.push(value.sid);
                        }
                    }
                }
                for(var sid of tarr){
                    this.saveInterfaceCode(data, sid);
                }
            },
            saveInterfaceCode:function(data,sid){
                var arr=[];
                for(var item of data){
                    if(item.sid == sid){
                        arr.push(item.interfaceCode);
                    }
                }
                this.BindBizInterfaceMenu(sid ,arr);
            },
            saveFun:function(){
                this.$emit('parentMsg', 'parentMsg');
            },
            //提交接口
            BindBizInterfaceMenu:function(serviceId,interfaceCodeList){
                var vm = this;
                var Url = "/admin/bizrole/"+this.roleId+"/service/"+serviceId+"/interface";
                if(interfaceCodeList.length > 0){
                    axios.put(Url, interfaceCodeList , {
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    }).then(function (resp) {
                        var resp = resp.data;
                        if(resp.code =="0"){
                            vm.$Notice.success({
                                title: "保存成功"
                            });
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
            cancel:function(){
                router.push({path: '/information/role'});
            }
        },
        components:{
            roleTree
        }
    }
</script>
<style scoped lang="scss">
    /*@import "../../style/relevant.scss";*/
    /*@import "../../style/auth.scss";*/
</style>
