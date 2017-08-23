/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-03-07 14:27
 * 创建作者: haoxu
 * 文件名称: roleTree.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */


<template>
    <div  @data="settingSelectedLists">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
</template>
<style scoped lang="scss">
    /*@import "../../style/metroStyle.css";*/
</style>
<script type="text/javascript">
    import $ from '../../api/jquery-vendor.js';
    import 'ztree';

    export default{
        name: 'roleTree',
        props: {
            lists: {
                type:Array,
                default:[]
            },
            children:{
                type:String
            },
            name:{
                type:String
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
                        children: this.children,
                        name: this.name
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
            setCheck:function(){
                var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                        py = "p",
                        sy = "s",
                        pn = "p",
                        sn = "s",
                        type = { "Y": sy, "N": sn };
                zTree.setting.check.chkboxType = type;
            },
            nodeFliter:function(node){
                return (node.checked);
            },
            settingSelectedLists:function(){
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                var nodes = zTree.getNodesByFilter(this.nodeFliter); // 查找节点集合
                this.$emit('data', nodes);
            },
        },
        watch: {
            // 如果 question 发生改变，这个函数就会运行
            lists: function (val,newVal) {
                $.fn.zTree.init($("#treeDemo"), this.setting, val);
                    this.setCheck();
                }
        },
        components:{
        }
    }
</script>