/**
* 版权所有: 爱WiFi无线运营中心
* 创建日期: 2017-02-21 15:15
* 创建作者: haoxu
* 文件名称: createRole.vue
* 版本: v1.0
* 功能: xx
* 修改记录: xx
*/

<template>
    <div>
        <div class="content">
            <div class="form-group form-wrap" style="margin-left:20%;">
                <div class="input-group clearfix">
                    <label for="roleName">角色名称:</label>
                    <input type="text" id="roleName" v-model="roleName" placeholder="请输入角色名称">
                </div>
                <div class="input-group clearfix">
                    <label class="my-rel">套码名称：</label>

                <div class="left-section" :style="'height:'+maxHeight">
                    <div class="title">可选套码</div>
                    <ul class="my-list" >
                        <li class="my-rows"
                            :class="{rowDisabled:model.isSelected}"
                            v-for="model in lists"
                            @click="addToService(model)">
                            <span class="name">{{model.suitCode}}</span>
                            <i class="icon" v-show="!model.isSelected">+</i>
                        </li>
                    </ul>
                </div>
                <div class="right-section" :style="'height:'+maxHeight">
                    <div class="title">已有套码</div>
                    <ul class="my-list">
                        <li class="my-rows"
                            v-for="model in selectedLists"
                            @click="removeService(model)"
                            v-dragging="{ item: model, list: selectedLists, group: 'selected' }" :key="model.id">
                            <span class="name">{{model.suitCode}}</span>
                            <i class="icon" >×</i>
                        </li>
                    </ul>
                </div>

                </div>
            </div>

            <div class="normal-buttons">
                <a-button type="primary" icon="checkmark-round" :loading="loading" @click="saveRole">
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
    import array from '../../api/array';
    import Vue from 'vue';
    import VueDND from 'awe-dnd';
    Vue.use(VueDND);
    export default{
        name: 'myTransfer',
        data(){
            return {
                maxHeight: '500px',
                lists: [],
                selectedLists: [],
                apiUrl: "/admin/bizrole",
                allsuitUrl: '/admin/allsuits',
                roleName:'',
                editId: this.$route.params.id,
                loading:false
            }
        },
        created: function () {
            this.showSuits();
        },
        methods: {

            /**
             * 功能：显示所有套码
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-23 09:49
             */
            showSuits: function () {
                var vm = this;
                axios.get(this.allsuitUrl).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        vm.lists = resp.data;
                        for (var myValue of vm.lists) {
                            myValue.isSelected = false;
                        }
                        if (vm.$route.params.id != 0) {
                            vm.showBinded();
                        }
                    } else {
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
            /**
             * 功能：显示已有角色
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-23 09:49
             */
            showBinded: function () {
                let url = this.apiUrl + "/" + this.$route.params.id ;
                var vm = this;
                axios.get(url).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        vm.roleName = resp.data.roleName;
                        for(var value of resp.data.suitCodeList){
                            for(var list of  vm.lists){
                                if(list.suitCode == value){
                                    list.isSelected=true;
                                    vm.selectedLists.push(list);
                                }
                            }
                        }
                    } else {
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
            addToService: function (model) {
                if (model.isSelected) {
                    // this.$Notice.error({
                    //     desc: "已选择"
                    // });
                    return
                } else {
                    this.selectedLists.push(model);
                    model.isSelected = true;
                }
            },
            removeService: function (model) {
                for (var value of this.lists) {
                    if (value.id == model.id) {
                        value.isSelected = false;
                    }
                }
                this.selectedLists.remove(model);
            },

            saveRole: function () {
                var vm = this;
                if (this.roleName == "") {
                    this.$Notice.error({
                        desc: "请输入角色名称"
                    });
                    return false;
                }
                if(this.selectedLists.length==0){
                    this.$Notice.error({
                        desc: "请选择套码"
                    });
                    return false;
                }
                var suitList = [];
                for (var value of this.selectedLists) {
                    suitList.push(value.suitCode);
                }

                let params = {
                    "roleName": this.roleName,
                    "suitCodeList": suitList
                };
                vm.loading = true;
                if(this.editId == 0){
                    axios.post(this.apiUrl, params, {
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    }).then(function (resp) {
                        vm.loading = false;
                        var resp = resp.data;
                        if (resp.code == "0") {
                            vm.$Notice.success({
                                title: "成功"
                            });
                            setTimeout(function () {
                                router.push({path: '/information/role'});
                            }, 1000);
                        } else {
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

                }else{
                    params.id = parseInt(this.editId);
                    axios.put(this.apiUrl, params, {
                        headers: {
                            'Content-Type': 'application/json',
                        }
                    }).then(function (resp) {
                        vm.loading = false;
                        var resp = resp.data;
                        if (resp.code == "0") {
                            vm.$Notice.success({
                                title: "成功"
                            });
                            setTimeout(function () {
                                router.push({path: '/information/role'});
                            }, 1000);
                        } else {
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
                }
            },
            cancel:function () {
                router.push({path: '/information/role'});
            },
        },
    }
</script>
<style scoped lang="scss">
    /*@import "../../style/relevant.scss";*/

</style>