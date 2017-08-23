/**
* 版权所有: 爱WiFi无线运营中心
* 创建日期: 2017-01-19 14:47
* 创建作者: haoxu
* 文件名称: relevant.vue
* 版本: v1.0
* 功能: xx
* 修改记录: xx
*/
<template>
    <div>
        <div class="content" style="margin-left:30%;">
            <div class="input-group clearfix relevant">
                <label>平台名称：</label>
                <span class="form-text">{{serviceName}}</span>
            </div>
            <div class="input-group clearfix">
                <label class="my-rel">关联服务：</label>
                <span class="form-text">
                <div class="left-section" :style="'height:'+maxHeight">
                    <div class="title">可选服务</div>
                    <ul class="my-list">
                        <li class="my-rows" v-for="model in lists"  @click="addToService(model)">
                            <span class="name">{{model.serviceName}}</span>
                            <span class="icon">+</span>
                        </li>
                    </ul>
                </div>
                <div class="right-section" :style="'height:'+maxHeight">
                    <div class="title">已选服务</div>
                    <ul class="my-list">
                        <li class="my-rows" v-for="model in selectedLists" @click="removeService(model)">
                            <span class="name">{{model.serviceName}}</span>
                            <span class="icon">×</span>
                        </li>
                    </ul>
                </div>
                    </span>
            </div>
        </div>
        <div class="normal-buttons">
            <a-button type="primary" icon="checkmark-round" :loading="loading" @click="saveFun">
                <span>提交</span>
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

    export default{
        name: 'myTransfer',
        data(){
            return {
                maxHeight: '500px',
                lists: [],
                selectedLists: [],
                serviceName: '',
                loading: false
            }
        },
        created: function () {
            this.showService();
            this.serviceName = this.$route.params.name;
        },
        methods: {
            showService: function () {
                var vm = this;
                let url = "/admin/platform/" + this.$route.params.id + "/service";
                axios.get(url).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        vm.lists = resp.data.serviceList;
                        vm.selectedLists = resp.data.bindedList;
                        for (var myValue of vm.lists) {
                            myValue.isSelected = false;
                        }
                        for (var select of vm.selectedLists) {
                            for (var value of vm.lists) {
                                if (select.serviceCode == value.serviceCode) {
                                    value.isSelected = true;
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
                    this.$Notice.error({
                        desc: "已选择"
                    });
                } else {
                    this.selectedLists.push(model);
                    model.isSelected = true;
                }
            },
            removeService: function (model) {
                for (var value of this.lists) {
                    if (value.serviceCode == model.serviceCode) {
                        value.isSelected = false;
                    }
                }
                this.selectedLists.remove(model);
            },
            saveFun: function () {
                var vm = this;
                var serviceCodeList = [];
                let myurl = "/admin/platform/" + this.$route.params.id + "/service";
                for (var value of this.selectedLists) {
                    serviceCodeList.push(value.serviceCode);
                }
                this.loading =true;
                axios.post(myurl, serviceCodeList, {
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }).then(function (resp) {
                    vm.loading =false;
                    var resp = resp.data;
                    if (resp.code == "0") {
                        vm.$Notice.success({
                            title: "成功"
                        });
                        setTimeout(function () {
                            router.push({path: '/platform/list'});
                        }, 1000);
                    } else if (resp.code == "E1000002") {
                        window.location.href = "./login.html";
                    }
                }).catch(function (error) {
                    vm.$Notice.error({
                        desc:error
                    });
                });

            },
            cancel: function () {
                router.push({path: '/platform/list'});
            }
        },
        mounted() {
            this.maxHeight = (window.outerHeight - 350) + 'px';
        }
    }
</script>
<style scoped lang="scss">
    /*@import "../../style/relevant.scss";*/
</style>
