/**
* 版权所有: 爱WiFi无线运营中心
* 创建日期: 2017-02-21 10:14
* 创建作者: haoxu
* 文件名称: myTransfer.vue
* 版本: v1.0
* 功能: xx
* 修改记录: xx
*/

<template>
    <div  v-show="isShow">
        <label class="my-rel">{{serviceName}}</label>
        <div class="left-section">
            <div class="title">可选权限</div>
            <!--第一层-->
            <ul class="my-list">
                <li class="my-rows" v-for="model in lists">
                    <span>{{model.menuName}}</span>
                    <i class="icon" @click="addToServiceFirst(model)">+</i>
                    <!--第二层-->
                    <ul class="my-list" v-if="model.subMenus">
                        <li class="my-rows" v-for="(secondModel,index) in model.subMenus">
                            <span>{{secondModel.menuName}}</span>
                            <i class="icon" @click="addToServiceSecond(secondModel,model,index)">+</i>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="right-section">
            <div class="title">已选权限</div>
            <!--第一层-->
            <ul class="my-list">
                <li class="my-rows" v-for="model in selectedLists" v-if="selectedLists&&selectedLists.length">
                    <span>{{model.menuName}}</span>
                    <i class="icon" @click="removeServiceFirst(model)" >×</i>
                    <!--第二层-->
                    <ul class="my-list" v-if="model.subMenus">
                        <li class="my-rows" v-for="(secondModel,index) in model.subMenus">
                            <span>{{secondModel.menuName}}</span>
                            <i class="icon" @click="removeServiceSecond(secondModel,model,index)">×</i>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</template>
<script type="text/javascript">
    import axios from 'axios';

    export default{
        name: 'roleTransfer',
        props: {
            roleId: String,
            serviceId: Number,
            serviceName:String
        },
        data(){
            return{
                 lists: [],
                 selectedLists: [],
                 isShow:true
            }
        },
        created: function () {
            this.showMenuBySid(this.serviceId);
            this.showMenuBySidRid(this.serviceId,this.roleId);
            var vm = this;
            this.$parent.$on('parentMsg', function(res) {
                vm.BindBizRoleMenu();
            });
        },
        methods: {
            //所有菜单
            showMenuBySid:function(sid){
                var vm = this;
                var Url = "/admin/service/"+sid+"/menu";
                axios.get(Url).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.lists = resp.data;
                        if(resp.data.length == 0){
                            vm.isShow = false;
                        }
                    }else{
                        vm.isShow = false;
                        console.log(resp.msg);
                    }
                }).catch(function (error) {
                    console.log(error);
                });
            },
            //已有菜单
            showMenuBySidRid:function(sid,rid){
                var vm = this;
                var Url = "/admin/bizrole/"+rid+"/service/"+sid+"/menu";

                axios.get(Url).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.selectedLists = resp.data;

                        //初始化lists和selectedlists，目前不起作用
                        vm.initTransfer();
                    }else{
                        console.log(resp.msg);
                    }
                }).catch(function (error) {
                    console.log(error);
                });
            },
            //提交接口
            BindBizRoleMenu:function(){
                var Url = "/admin/bizrole/"+this.roleId+"/service/"+this.serviceId+"/menu";
                if(this.selectedLists.length>0){
                    var menuIdList = [];
                    for(var value of this.selectedLists){
                        menuIdList.push(value.id);
                    }
                    axios.put(Url, menuIdList, {
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    }).then(function (resp) {
                        var resp = resp.data;
                        if(resp.code =="0"){
                            alert("保存成功");
                        }else{
                            console.log(resp.msg);
                        }
                    }).catch(function (error) {
                        console.log(error);
                    });
                }
            },
            /*一层操作*/
            addToServiceFirst:function(model){
                if(model.isSelected){
                    alert("已选择");
                }else{
                    console.log(this.selectedLists);
                    this.selectedLists.push(model);
                    model.isSelected = true;
                }
            },
            removeServiceFirst:function(model){
                for(var value of this.lists){
                    if(value.id == model.id){
                        value.isSelected = false;
                    }
                }
                this.selectedLists.remove(model);
            },
            /*二层操作*/
            addToServiceSecond:function(secondModel,model,index){
                console.log("2222222222");
                for(var value of this.lists){
                    if(value.id == model.id){
                        console.log(value.isSelected);
                        if(!value.isSelected){
                            console.log("11111111111");
                            var arrays =[];
                            arrays.push(value);
                            value.isSelected =true;
                            this.selectedLists =this.copyArr(arrays);
                            for(var values of this.selectedLists){
                                if(values.id == value.id) {
                                    var child = this.copyArr(value.subMenus);
                                    var array = new Array;
                                    array.push(child[index]);
                                    values.subMenus =this.copyArr(array);
                                }
                            }
                        }else{
                            console.log("3333333333");
                            for(var values of this.selectedLists){
                                if(values.id == model.id) {
                                    values.subMenus.push(secondModel);
                                }
                            }
                        }
                    }
                }
            },
            removeServiceSecond:function(secondModel,model,index){
                if(model.subMenus.length >1){
                    for(var values of this.selectedLists){
                        if(values.id == model.id) {
                            values.subMenus.remove(secondModel);
                        }
                    }
                }else{
                    for(var values of this.lists){
                        if(values.id == model.id){
                            values.isSelected = false;
                        }
                    }
                    this.selectedLists.remove(model);
                }
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
            initTransfer:function(){
                for(var myValue of this.lists){
                    myValue.isSelected = false;
                    if(myValue.subMenus && myValue.subMenus.length >0 ){
                        for(var myChild of myValue.subMenus){
                            myChild.isSelected = false;
                        }
                    }
                }
                for(var select of this.selectedLists){
                    for(var value of this.lists){
                        if(select.id == value.id){
                            value.isSelected = true;
                        }
                    }
                }
                console.log("232323");
                console.log(this.lists);
            }
        },
        components:{
        }
    }
</script>
<style scoped lang="scss">
    /*@import "../../style/relevant.scss";*/
</style>
