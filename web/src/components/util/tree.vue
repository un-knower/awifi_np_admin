/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-03-03 17:28
 * 创建作者: haoxu
 * 文件名称: tree.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div  @data="settingSelectedLists">
        <div id="treeDemo" class="ztree"></div>
    </div>
</template>
<style scoped lang="scss">
    /*@import "../../style/metroStyle.css";*/
</style>
<script type="text/javascript">
    import $ from '../../api/jquery-vendor.js';
    import 'ztree';
    import router from '../../router';

    export default{
        name: 'myTree',
        props: {
            //是否显示
            url: {
                type: String,
                default:''
            },
            async: {
                type:Boolean,
                default:true
            },
            lists: {
                type:Array,
                default:[]
            },
            selectedLists:{
                type:Array,
                default:[]
            }
        },
        data(){
            return{
                setting:{}
            }
        },
        created(){
            this.setting = {
                check: {
                    enable: true
                },
                data: {
                    key: {
                        checked: "isChecked",
                        children: "childPermission",
                        name:"permissionName",
                        title:"",
                        url:""
                    },
                    simpleData:{
                        enable:false,
                        idKey: "id"
                    }
                }
            };
            var vm = this;
            this.$parent.$on('parentMsg', function(res) {
                vm.settingSelectedLists();
            });
        },
        methods:{
            //选中的数组
            isChecked: function (selectedLists) {
                for (let selectItem of selectedLists) {
                    this.isList(this.lists, selectItem.id);
                    if (selectItem.childPermission && selectItem.childPermission.length > 0) {
                        this.isChecked(selectItem.childPermission);
                    }
                }
            },
            //遍历全部的数组
            isList:function(lists, id){
                for (let item of lists) {
                    if (item.id === id) {
                        item.isChecked = true;
                        break;
                    }
                    if (item.childPermission && item.childPermission.length > 0) {
                        this.isList(item.childPermission, id);
                    }
                }
            },
            setCheck:function(){
                var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                        py = "p",
                        sy = "s",
                        pn = "p",
                        sn = "s",
                        type = { "Y":py + sy, "N":pn + sn};
                zTree.setting.check.chkboxType = type;
            },
            setAsync:function(){
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                zTree.setting.async.enable = true;
                zTree.setting.async.url = this.url;
                zTree.setting.async.contentType = "application/json";
                zTree.setting.async.autoParam = ["id", "name=n", "level=lv"];
                zTree.setting.async.otherParam = {"otherParam":"zTreeAsyncTest"};
                zTree.setting.async.dataFilter = this.myFilter;
            },
            myFilter:function(){
                if (!childNodes) return null;
                for (var i=0, l=childNodes.length; i<l; i++) {
                    childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
                }
                return childNodes;
            },
            nodeFliter:function(node){
                return (node.level == 0 && node.isChecked);
            },
            settingSelectedLists:function(){
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                var nodes = zTree.getNodesByFilter(this.nodeFliter); // 查找节点集合
                this.$emit('data', nodes);
            },
        },
        watch: {
            selectedLists:function(val){

                if(val){
                    this.isChecked(val);
                    if(this.async) {
                        $.fn.zTree.init($("#treeDemo"), this.setting);
                        this.setAsync();
                    }else{

                        $.fn.zTree.init($("#treeDemo"), this.setting,this.lists);
                    }
                }else{
                    $.fn.zTree.init($("#treeDemo"), this.setting,this.lists);
                }
                this.setCheck();
            },
            // 如果 question 发生改变，这个函数就会运行
            lists: function (val,newVal) {

                if(this.$route.params.id ==0){
                    if(this.async) {
                        $.fn.zTree.init($("#treeDemo"), this.setting);
                        this.setAsync();
                    }else{
                        $.fn.zTree.init($("#treeDemo"), this.setting,val);
                    }
                    this.setCheck();
                }
            }
        },
        components:{
        }
    }
</script>
