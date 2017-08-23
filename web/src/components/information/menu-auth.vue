/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-24 09:47
 * 创建作者: haoxu
 * 文件名称: menu-auth.vue
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
                name:"menuName",
                children:"subMenus"
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
                        isOnline:true,
                        isMenu:true
                    }
                }).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.serviceLists = resp.data;
                        var i=0;
                        for(var serviceItem of vm.serviceLists){
                          //  serviceItem.nocheck = true;
                            serviceItem.open = true;
                            serviceItem.menuName = serviceItem.serviceName;
                            serviceItem.subMenus =[];
                            serviceItem.order =i++;
                            vm.showMenuBySid(serviceItem, vm.roleId);
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
            //所有菜单
            showMenuBySid:function(serviceItem,rid){
                console.log("333",serviceItem);
                var vm = this;
                var Url = "/admin/service/"+serviceItem.id+"/menu";
                axios.get(Url).then(function (resp) {
                    var respfirst = resp.data;
                    if(respfirst.code =="0"){
                        if(respfirst.data){
                            var lists = respfirst.data;
                            var Url2 = "/admin/bizrole/"+rid+"/service/"+ serviceItem.id+"/menu";
                            axios.get(Url2).then(function (resp) {
                                var response = resp.data;
                                if(response.code =="0"){
                                    var selectLists = response.data;
                                    if(selectLists){
                                        var respData =  vm.isChecked(selectLists,lists);
                                        serviceItem.subMenus = vm.initTransfer(respData,serviceItem.id)
                                    }
                                }else{
                                    vm.$Notice.error({
                                        title: resp.code,
                                        desc: resp.msg
                                    });
                                }
                                vm.lists.push(serviceItem);
                                vm.lists.sort(vm.compare('order'));
                            }).catch(function (error) {
                                vm.$Notice.error({
                                    desc: error
                                });
                                vm.lists.push(serviceItem);
                                vm.lists.sort(vm.compare('order'));
                            });
                        }
                    }else{
                        vm.lists.push(serviceItem);
                        vm.lists.sort(vm.compare('order'));
                        vm.$Notice.error({
                            title: resp.data.code,
                            desc: resp.data.msg
                        });
                    }
                }).catch(function (error) {
                    vm.$Notice.error({
                        desc: error
                    });
                });
            },
            initTransfer:function(lists ,serviceId){
                for(var myValue of lists){
                    myValue.sid = serviceId;
                }
                return lists;
            },
            //已有菜单
            showMenuBySidRid:function(lists, serviceId, rid){

                var vm = this;
                var Url = "/admin/bizrole/"+rid+"/service/"+serviceId+"/menu";
                axios.get(Url).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        var selectLists = resp.data;
                        if(selectLists){
                           var respData =  vm.isChecked(selectLists,lists);
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
            //选中的数组
            isChecked: function (selectedLists ,lists) {
                for (let selectItem of selectedLists) {
                    this.isList(lists, selectItem.id);
                    if (selectItem.subMenus && selectItem.subMenus.length > 0) {
                        this.isChecked(selectItem.subMenus);
                    }
                }
                return lists;
            },
            //遍历全部的数组
            isList:function(lists, id){
                for (let item of lists) {
                    if (item.id === id) {
                        item.checked = true;
                        break;
                    }
                    if (item.subMenus && item.subMenus.length > 0) {
                        this.isList(item.subMenus, id);
                    }
                }
            },


            gettingSelectedLists:function(data){
                if(data.length == 0){
                    this.$Notice.error({
                        desc: "请选择授权菜单"
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
                    this.saveMenuId(data, sid);
                }
            },

            saveMenuId:function(data,sid){
                var arr=[];
                for(var item of data){
                    if(item.sid == sid){
                        arr.push(item.id);
                    }
                }
                this.BindBizRoleMenu(sid ,arr);
            },

            saveFun:function(){
                this.$emit('parentMsg', 'parentMsg');
            },
            //提交接口
            BindBizRoleMenu:function(serviceId,menuIdList){
                var vm = this;
                var Url = "/admin/bizrole/"+this.roleId+"/service/"+serviceId+"/menu";
                if(menuIdList.length>0){
                    axios.put(Url, menuIdList, {
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