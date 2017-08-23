/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-21 19:25
 * 创建作者: haoxu
 * 文件名称: myNest.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <ul class="my-list">
        <li class="my-rows" v-for="model in lists">
            <span>{{model.permissionName}}</span>
            <span @click="addToService(model)" v-if="selectable">+</span>
            <span @click="removeService(model)" v-else>×</span>
            <my-nest :selectable ="selectable" :lists="model.childPermission"></my-nest>
        </li>
    </ul>
</template>

<script type="text/javascript">
    export default{
        name: "myNest",
        props: {
            lists: Array,
            selectable:Boolean,
        },
        data(){
            return{

            }
        },
        methods: {
            addToService:function(model){
                if(model.isSelected){
                    alert("已选择");
                }else{
                    this.selectedLists.push(model);
                    model.isSelected = true;
                }
            },
            removeService:function(model){
                for(var value of this.lists){
                    if(value.serviceCode == model.serviceCode){
                        value.isSelected = false;
                    }
                }
                this.selectedLists.remove(model);
            },

        }
    }
</script>
