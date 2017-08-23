/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-01-20 16:08
 * 创建作者: haoxu
 * 文件名称: transfer.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div>
       <div class="left-section">
           <div class="title">可选服务</div>
           <nestification v-for="model in lists" :model = "model" :showSelection ="true"  :openFolder ="true"></nestification>
       </div>
        <div class="right-section">
            <div class="title">已选服务</div>
            <nestification v-for="model in selectedLists" :model = "model" :showSelection ="false" :openFolder ="true"></nestification>
        </div>
        <div class="buttons">
            <button class="confirm" @click="saveFun">保存</button>
            <button class="cancel" @click="cancel">取消</button>
        </div>
    </div>
</template>


<script type="text/javascript">
    import Nestification from './nestification.vue';
    export default{
        name: 'Transfer',
        props: {
            list: Array,
            selectedList: Array
        },
        data(){
            return {
                //可选服务列表
                lists: [{
                    "id": "1",
                    "isActive": true,
                    "title": "商户服务",
                    "childTreeNode": [{
                        "title": "标题1", "isActive": true, "id": "11",
                        "childTreeNode": [{"title": "标题3333", "id": "111", "isActive": true},
                            {"title": "标题2555", "id": "112", "isActive": true}]
                    }, {"title": "标题2", "id": "12", "isActive": false}]
                },
                    {
                        "id": "2",
                        "isActive": false,
                        "title": "设备服务",
                        "childTreeNode": [{"title": "标题3", "id": "21", "isActive": false}, {
                            "title": "标题4",
                            "id": "22",
                            "isActive": false
                        }]
                    },
                    {
                        "id": "3",
                        "parent": "0",
                        "title": "商户设备服务",
                        "isActive": true,
                        "childTreeNode": [{"title": "标题1", "id": "31", "isActive": true,}, {
                            "title": "标题2",
                            "id": "32",
                            "isActive": true
                        }]
                    }
                ],
                //已选服务列表
                selectedLists: []
            }
        },
        methods: {
            childMap: function (mymodel) {
                var newLists = mymodel;
                if (mymodel.isActive == false) {
                    newLists = null;
                } else {
                    if (mymodel.childTreeNode && mymodel.childTreeNode.length != 0) {
                        newLists.childTreeNode = [];
                        for (var value of mymodel.childTreeNode) {
                            console.log(value);
                            console.log(this.childMap(value));
                            newLists.childTreeNode.push(this.childMap(value));
                        }
                    }
                }
                console.log(newLists);
                return newLists;
            },
            saveFun: function () {
                console.log(this.lists);
                var newLists = [];
                for (var value of this.lists) {
                     var child = this.childMap(value);
                     if(child){
                         newLists.push(child);
                     }
                }
                this.selectedLists = newLists;
                console.log(this.selectedLists);
              },
    cancel: function () {
        console.log("12222");
    }
    },
    components: {
        Nestification
    }
    }
</script>
