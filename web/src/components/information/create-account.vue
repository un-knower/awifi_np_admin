/**
* 版权所有: 爱WiFi无线运营中心
* 创建日期: 2017-02-21 15:15
* 创建作者: haoxu
* 文件名称: createAccount.vue
* 版本: v1.0
* 功能: xx
* 修改记录: xx
*/

<template>
    <div>
        <div class="content form-wrap clearfix" style="margin-left:20%;">
            <div class="form-group">
                <div class="input-group clearfix">
                    <label for="userName">账号:</label>
                    <input type="text" id="userName" v-model="userName" placeholder="请输入账号">
                </div>
                <div class="input-group clearfix">
                    <label class="my-rel">角色：</label>
                <span class="form-text">
                <div class="left-section">
                    <div class="title">可选角色</div>
                    <ul class="my-list">
                        <li class="my-rows" v-for="model in lists"  @click="addToService(model)">
                            <span class="name">{{model.roleName}}</span>
                            <i class="icon" v-show="!model.isSelected">+</i>
                        </li>
                    </ul>
                </div>
                <div class="right-section">
                    <div class="title">已选角色</div>
                    <ul class="my-list">
                        <li class="my-rows" v-for="model in selectedLists" @click="removeService(model)">
                            <span class="name">{{model.roleName}}</span>
                            <i class="icon">×</i>
                        </li>
                    </ul>
                </div>
                    </span>
                </div>
                <div class="input-group clearfix">
                    <label>地区:</label>
                    <select class="select-control area-control"  v-model="provinceId" v-on:change="showCityLists(provinceId)">
                        <option value="">省</option>
                        <option v-for="option in proviceOptions" :value="option.value">
                            {{ option.text }}
                        </option>
                    </select>

                    <select class="select-control area-control"  v-model="cityId" v-on:change="showCountyLists(cityId)">
                        <option value="">市</option>
                        <option v-for="option in cityOptions" :value="option.value">
                            {{ option.text }}
                        </option>
                    </select>
                    <select class="select-control area-control"  v-model="areaId">
                        <option value="">区</option>
                        <option v-for="option in areaOptions" :value="option.value">
                            {{ option.text }}
                        </option>
                    </select>
                </div>
                <div class="input-group clearfix">
                    <label for="contactWay">联系方式:</label>
                    <input type="text" id="contactWay" v-model="contactWay" placeholder="请输入联系方式">
                </div>
                <div class="input-group clearfix">
                    <label for="remark" class="textarea">备注:</label>
                    <textarea cols="6" id="remark" v-model="remark"></textarea>
                </div>
            </div>
        </div>


        <div class="normal-buttons" style="margin-left:-15%;">
            <a-button type="primary" icon="checkmark-round" :loading="loading" @click="saveAccount">
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
    import array from '../../api/array';
    export default{
        name: 'myTransfer',
        data(){
            return {
                lists: [],
                selectedLists: [],
                apiUrl: "/admin/bizuser",
                allroleUrl: '/admin/bizrole',
                loading:false,
                userName: '',
                provinceId: '',
                cityId: '',
                areaId: '',
                city:'',
                area:'',
                contactWay: '',
                remark: '',
                proviceOptions: [],
                cityOptions: [],
                areaOptions: [],
                editId: this.$route.params.id
            }
        },
        created: function () {
            this.showRole();
            this.showAreaLists(1);
            if (this.$route.params.id != 0) {
                this.showBinded();
            }
        },
        methods: {
            /**
             * 功能：显示所有角色
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-23 09:49
             */

            showRole: function () {
                var vm = this;
                axios.get(this.allroleUrl, {
                    params: {
                        page: -1
                    }
                }).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        vm.lists = resp.data.list;
                        for (var myValue of vm.lists) {
                            myValue.isSelected = false;
                        }
                    } else {
                        vm.$Notice.error({
                            title: resp.msg,
                        });
                    }
                }).catch(function (error) {
                    vm.$Notice.error({
                        title: error,
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
                        vm.userName = resp.data.userName;
                        vm.provinceId = resp.data.provinceId;
                        vm.city = resp.data.cityId;
                        vm.area = resp.data.areaId;
                        vm.contactWay = resp.data.contactWay;
                        vm.remark = resp.data.remark;
                        vm.selectedLists = resp.data.roleList;
                    } else {
                        vm.$Notice.error({
                            title: resp.code,
                            desc: resp.msg
                        });
                    }
                }).catch(function (error) {
                    vm.$Notice.error({
                        title: error
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
            /**获取省、市、区
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-21 15:07
             */
            showAreaLists: function (id) {
                var vm = this;
                var areaUrl = '/api/area/' + id + '/sub';
                axios.get(areaUrl).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        for (var value of resp.data) {
                            var provice = {text: value.areaName, value: value.id};
                            vm.proviceOptions.push(provice);
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
            showCityLists: function (provinceId) {
                var vm = this;
                vm.cityOptions =[];
                var areaUrl = '/api/area/' + provinceId + '/sub';
                axios.get(areaUrl).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        for (var value of resp.data) {
                            var city = {text: value.areaName, value: value.id};
                            vm.cityOptions.push(city);
                        }
                        if(vm.city){
                            vm.cityId = vm.city;
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
            showCountyLists: function (cityId) {
                var vm = this;
                vm.areaOptions =[];
                var areaUrl = '/api/area/' + cityId + '/sub';
                axios.get(areaUrl).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code == "0") {
                        for (var value of resp.data) {
                            var area = {text: value.areaName, value: value.id};
                            vm.areaOptions.push(area);
                        }
                        if(vm.area){
                            vm.areaId = vm.area;
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
            saveAccount: function () {
                var vm = this;
                if (this.userName == "") {
                    this.$Notice.error({
                        desc: "请输入账号"
                    });
                    return false;
                }
                if (this.contactWay == "") {
                    this.$Notice.error({
                        desc: "请输入联系方式"
                    });
                    return false;
                }
                if(this.provinceId == "" || this.cityId  == "" ||this.areaId =="" ){
                    this.$Notice.error({
                        desc: "请选择地区"
                    });
                    return false;
                }
                if(this.roleIdList=="" ){
                    this.$Notice.error({
                        desc: "请选择角色"
                    });
                    return false;
                }
                var roleIdList = [];
                for (var value of this.selectedLists) {
                    roleIdList.push(value.id);
                }
                let params = {
                    "userName": this.userName,
                    "provinceId": this.provinceId,
                    "cityId": this.cityId,
                    "areaId": this.areaId,
                    "contactWay": this.contactWay,
                    "remark": this.remark,
                    "roleIdList": roleIdList
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
                                router.push({path: '/information/account'});
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
                                router.push({path: '/information/account'});
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
                router.push({path: '/information/account'});
            },
        },
        watch: {
            provinceId:function(val){
                this.showCityLists(val);
            },
            cityId:function(val){
                this.showCountyLists(val);
            }
        }
    }
</script>
<style scoped lang="scss">
    /*@import "../../style/relevant.scss";*/
</style>