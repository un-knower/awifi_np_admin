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
    <div @data="settingSelectedLists">
        <div class="left-section" v-show="showRightSection">
            <div class="title">可选权限</div>
            <!--第一层-->
            <ul class="my-list">
                <li class="my-rows" v-for="model in lists">
                    <span>{{model.permissionName}}</span>
                    <i class="icon" @click="addToServiceFirst(model)">+</i>
                    <!--第二层-->
                    <ul class="my-list" v-if="model.childPermission">
                        <li class="my-rows" v-for="(secondModel,index) in model.childPermission">
                            <span>{{secondModel.permissionName}}</span>
                            <i class="icon" @click="addToServiceSecond(secondModel,model,index)">+</i>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="right-section">
            <div class="title">已有权限</div>
            <!--第一层-->
            <ul class="my-list">
                <li class="my-rows" v-for="model in selectedLists" v-if="selectedLists&&selectedLists.length">
                    <span>{{model.permissionName}}</span>
                    <i class="icon" @click="removeServiceFirst(model)"  v-show="showRightSection">×</i>
                    <!--第二层-->
                    <ul class="my-list" v-if="model.childPermission">
                        <li class="my-rows" v-for="(secondModel,index) in model.childPermission" >
                            <span>{{secondModel.permissionName}}</span>
                            <i class="icon" @click="removeServiceSecond(secondModel,model,index)"  v-show="showRightSection">×</i>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</template>
<script type="text/javascript">
    export default{
        name: 'myTransfer',
        props: {
            lists: Array,
            selectedLists: Array,
            showRightSection:Boolean
        },
        data(){
            return{
              /*  lists: [],
                selectedLists: []*/
            }
        },
        created: function () {
            console.log(this.lists);
            this.initTransfer();
            var vm = this;
            this.$parent.$on('parentMsg', function(res) {
                console.log('子组件响应父组件$emit的parentMsg事件：' + res);
                vm.settingSelectedLists();
            });
        },
        methods: {
            settingSelectedLists:function(){
                this.$emit('data', this.selectedLists);
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
                                    var child = this.copyArr(value.childPermission);
                                    var array = new Array;
                                    array.push(child[index]);
                                    values.childPermission =this.copyArr(array);
                                }
                            }
                        }else{
                            console.log("3333333333");
                            for(var values of this.selectedLists){
                                if(values.id == model.id) {
                                    values.childPermission.push(secondModel);
                                }
                            }
                        }
                    }
                }
            },
            removeServiceSecond:function(secondModel,model,index){
                if(model.childPermission.length >1){
                    for(var values of this.selectedLists){
                        if(values.id == model.id) {
                            values.childPermission.remove(secondModel);
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
                    if(myValue.childPermission && myValue.childPermission.length >0 ){
                        for(var myChild of myValue.childPermission){
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
