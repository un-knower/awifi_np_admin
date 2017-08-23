/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-15 11:30
 * 创建作者: haoxu
 * 文件名称: modify-account.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div class="container">
        <div class="config">
            <div class="form-group content clearfix">
                <div class="input-group clearfix">
                    <label>账号：</label>
                    <div class="form-text" v-text="dataInfo.loginAccount"></div>
                </div>
                <div class="input-group clearfix">
                    <label>角色：</label>
                    <div class="form-text" v-text ="roleName"></div>
                </div>
                <div class="input-group clearfix">
                    <label for="phone">联系方式：</label>
                    <input type="text" id="phone" v-model="phone">
                </div>
                <div class="input-group clearfix">
                    <label for="remarks" class="textarea">备注:</label>
                    <textarea id="remarks" v-model="remark"></textarea>
                </div>
            </div>

            <div class="normal-buttons">
                <a-button type="primary" icon="checkmark-round" :loading="loading" @click="save">
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
    import router from '../router';
    export default{
        data(){
            return{
                phone:'',
                remark:'',
                apiUrl: '/admin/user',
                userUrl:'/admin/index/currentUser',
                dataInfo:{},
                roleName:"",
                loading:false
            }
        },
        created: function () {
           this.showUserInfo();
        },
        methods: {
            showUserInfo:function(){
                var vm = this;
                axios.get(this.userUrl).then(function (resp) {
                    var resp = resp.data;
                    if (resp.code== 0) {
                        vm.dataInfo = resp.data;
                        vm.roleName = resp.data.role.roleName;
                        vm.phone = resp.data.phone;
                        vm.remark = resp.data.remark;
                    }else{
                        console.log("err=>", error);
                    }
                }).catch(function (error) {
                    console.log("err=>", error);
                });
            },
            save:function(){
                var vm = this;
                if(this.phone == ""){
                    this.$Notice.error({
                        desc: "联系方式不能为空"
                    });

                    return false;
                }
                var id = this.$route.query.id;
               let params = {"id":id,"phone":this.phone,"remark":this.remark};
                this.loading =true;
                axios.put(this.apiUrl, params,{
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }).then(function (resp) {
                    var resp = resp.data;
                    vm.loading =false;
                    if(resp.code=="0"){
                        vm.$Notice.success({
                            title: "成功"
                        });
                        setTimeout(function () {
                            router.push({path: '/'});
                        }, 1000);
                    }else{
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

            },
            cancel:function(){
                router.push({path: '/'});
            }
        }
    }
</script>
<style scoped lang="scss">
    /*@import "../style/modify.scss";*/
</style>

