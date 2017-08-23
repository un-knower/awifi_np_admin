/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-01-22 09:49
 * 创建作者: haoxu
 * 文件名称: nestification.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
   <div class="list">
        <li class="rows" @data ="handleData">
            <span class="select" :class="{ active:model.isActive }" @click="selFun" v-show="showSelection"></span>
            <span class="names"  @click="toggle">
                {{model.title}}
                <span v-if="isFolder">[{{open ? '-' : '+'}}]</span>
            </span>
            <ul v-show="open" v-if="isFolder">
                <nestification class="item"  v-for="model in model.childTreeNode" :model="model"  @data="childDataHandler" :showSelection = "showSelection"></nestification>
            </ul>
        </li>
    </div>
</template>

<script type="text/javascript">
    export default{
        name: "nestification",
        props: {
            model: Object,
            showSelection: Boolean,
            openFolder: Boolean
        },
        data(){
            return {
                selectOpen: true,
                dataArray: [],
                mySelectChild: [],
                open: this.openFolder
            }
        },
        created: function () {

        },
        watch: {},
        computed: {
            isFolder() {
                return this.model.childTreeNode && this.model.childTreeNode.length
            }
        },
        methods: {
            toggle() {
                if (this.isFolder) {
                    this.open = !this.open
                }
            },
            selFun(){
                this.model.isActive = !this.model.isActive;
                if (this.isFolder) {
                    //有子目录
                    for (var value of this.model.childTreeNode) {
                        value.isActive = this.model.isActive;
                         //this.selFun();
                    }
                } else {

                    //没有子目录
                    this.handleData();
                }
            },
            //父组件的事件
            childDataHandler(){
             //   console.log("通过绑定在子组件上的事件得到来自子组件的消息：");
                var i=0;
                for (var value of this.model.childTreeNode) {
                    if (value.isActive == true) {
                        this.model.isActive = true;
                    }else{
                        i++;
                    }
                    if(this.model.childTreeNode.length == i){
                        this.model.isActive = false;
                    }
                }
              /*  var mymodel = this.model;
                this.mymodel.childTreeNode = this.mySelectChild;
                let msg = this.dataArray; */
                this.$emit('data');
            },
            //子组件事件
            handleData(){
               /* if (this.isFolder) {
                    var mymodel = this.model;
                    this.mymodel.childTreeNode = this.mySelectChild;
                } else {
                }
                this.dataArray.push(this.mymodel);
                let msg = this.dataArray;
                console.log(msg);  */
                this.$emit('data');
            }
        }
    }
</script>
