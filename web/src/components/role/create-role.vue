/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-21 17:05
 * 创建作者: haoxu
 * 文件名称: create-role.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */
<template>
    <div>
        <div class="content form-wrap" style="margin-left:30%;">
            <div class="form-group">
                <div class="input-group clearfix relevant">
                    <label for="roleName">角色名称：</label>
                    <input type="text" class="form-text" id="roleName" v-model="roleName" placeholder="角色名称">
                </div>
                <div class="input-group clearfix relevant">
                    <label></label>
                    <span class="form-text">1-50位字符，汉字或字母或数或“-”或“_”或“()”或者“（）”</span>
                </div>
            <div class="input-group clearfix">
                <label class="my-rel">角色权限：</label>
               <!-- <span class="form-text">
                     <my-transfer :lists="lists" :selectedLists="selectedLists" :showRightSection="true" @data="gettingSelectedLists"></my-transfer>
                    </span> -->
                <div class="form-text role-tree" style="height:auto;">
                     <my-tree :lists="lists" :selectedLists="selectedLists"  :async="false" @data="gettingSelectedLists"></my-tree>
                </div>
            </div>
                <div class="input-group clearfix">
                    <label for="remark" class="textarea">备注:</label>
                    <textarea cols="6" id="remark" v-model="remark"></textarea>
                </div>
        </div>
        </div>

        <div class="normal-buttons">
            <a-button type="primary" icon="checkmark-round" :loading="loading" @click="saveFun">
                <span>保存</span>
            </a-button>
            <a-button type="info" icon="chevron-left"  @click="cancel">
                <span>取消</span>
            </a-button>
        </div>
    </div>
</template>
<script type="text/javascript">
    import axios from 'axios';
    import router from '../../router';
    import array from '../../api/array';
    import myTree from '../util/tree.vue';
    export default{
        name: 'role',
        data(){
            return{
                lists:[],
                selectedLists:[],
                allUrl:"/admin/allpermissions",
                apiUrl:'/admin/role/',
                roleName:'',
                remark:'',
                myLists:[],
                loading:false
            }
        },
        created: function () {
          /*  if(this.$route.params.id !=0){
                this.showBinded();
            }*/
            this.showService();
        },
        methods: {
            showService: function () {
                var vm = this;
                axios.get(this.allUrl).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.lists = resp.data;
                        if(vm.$route.params.id !=0){
                            vm.showBinded();
                        }
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
            showBinded:function(){
                var vm = this;
                var url = this.apiUrl+this.$route.params.id;
                axios.get(url).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.selectedLists =resp.data.permissions;
                        vm.roleName = resp.data.roleName;
                        vm.remark = resp.data.remark;
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
            },

            gettingSelectedLists:function(data){
                this.myLists =[];
                var lists =this.copyArr(data);
                for(var item of lists){
                    this.myLists.push(this.listsMap(item));
                }
                for(var value of this.myLists){
                    this.deleteAttr(value);
                }
            },
            listsMap:function(item){
                    var arrayInfo =[];
                    if(item.childPermission && item.childPermission.length > 0 ){
                        for(var value of item.childPermission){
                            if(!value.isChecked){
                                arrayInfo.push(value);
                            }else{
                                this.listsMap(value);
                            }
                        }
                        for(var arr of  arrayInfo){
                            for(var value of item.childPermission){
                                if(arr == value){
                                    item.childPermission.remove(value);
                                }
                            }
                        }
                        for(var value of item.childPermission){
                            this.deleteAttr(value);
                        }
                    }else{
                    }
                 return item;
            },
            deleteAttr:function(value){
                delete value.check_Child_State;
                delete value.check_Focus;
                delete value.checkedOld;
                delete value.chkDisabled;
                delete value.editNameFlag;
                delete value.halfCheck;
                delete value.isAjaxing;
                delete value.isChecked;
                delete value.isFirstNode;
                delete value.isHover;
                delete value.isLastNode;
                delete value.isParent;
                delete value.tId;
                delete value.zAsync;
                delete value.parentTId;
                delete value.level;
                delete value.nocheck;
                delete value.open;
            },
            saveFun:function(){
                this.$emit('parentMsg', 'parentMsg');
                var vm = this;
                if(this.roleName==""){
                    this.$Notice.error({
                        desc: "请输入角色名称"
                    });

                    return false;
                }
                let params = { "roleName": this.roleName,"remark": this.remark,"permissions": this.myLists};
                vm.loading = true;
                if(this.$route.params.id !=0){
                    params.id = this.$route.params.id;
                    axios.put(this.apiUrl, params,{
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    }).then(function (resp) {
                        vm.loading = false;
                        var resp = resp.data;
                        if(resp.code=="0"){
                            vm.$Notice.success({
                                title: "成功"
                            });
                            setTimeout(function () {
                                router.push({path: '/system/role'});
                            }, 1000);

                        }else if(resp.code == "E1000002"){
                            window.location.href = "./login.html";
                        }
                    }).catch(function (error) {
                        vm.$Notice.error({
                            desc: error
                        });
                    });

                }else{
                    axios.post(this.apiUrl, params,{
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    }).then(function (resp) {
                        var resp = resp.data;
                        if(resp.code=="0"){
                            vm.$Notice.success({
                                title: "成功"
                            });
                            setTimeout(function () {
                                router.push({path: '/system/role'});
                            }, 1000);

                        }else if(resp.code == "E1000002"){
                            window.location.href = "./login.html";
                        }
                    }).catch(function (error) {
                        vm.$Notice.error({
                            desc:error
                        });
                    });
                }
            },
            cancel:function(){
                router.push({path: '/system/role'});
            },
            copyArr:function(arr){
                return arr.map((e)=>{
                    if(typeof e === 'object'){
                        return Object.assign({},e)
                    }else{
                        return e
                    }
                })
            },
        },
        components:{
            myTree
        }
    }
</script>
<style scoped lang="scss">
    /*@import "../../style/relevant.scss";*/
</style>
