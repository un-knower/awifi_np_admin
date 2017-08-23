/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-15 11:30
 * 创建作者: haoxu
 * 文件名称: modify-pwd.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div class="container">
        <div class="config">
            <div class="form-group content clearfix">
                <div class="input-group clearfix">
                    <label for="old-pwd">旧密码：</label>
                    <input type="password" id="old-pwd" v-model="oldPwd">
                </div>
                <div class="input-group clearfix">
                    <label for="new-pwd">新密码：</label>
                    <input type="password" id="new-pwd" v-model="newPwd">
                </div>
                <div class="input-group clearfix">
                    <label for="re-pwd"> 重复新密码：</label>
                    <input type="password" id="re-pwd" v-model="rePwd">
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
    import md5 from 'md5';
    import router from '../router';
    export default{
        data(){
            return{
                oldPwd:'',
                newPwd:'',
                rePwd:'',
                apiUrl: '/admin/user/password',
                loading:false
            }
        },
          methods: {
            save:function(){
                if(this.oldPwd == ""){
                    this.$Notice.error({
                        desc: "请输入旧密码"
                    });
                    return false;
                }
                if(this.newPwd == ""){
                    this.$Notice.error({
                        desc: "请输入新密码"
                    });
                    return false;
                }
                if(this.rePwd == ""){
                    this.$Notice.error({
                        desc: "请再次确认密码"
                    });

                    return false;
                }
                if(this.rePwd != this.newPwd){
                    this.$Notice.error({
                        desc: "两次密码输入不一致"
                    });
                    return false;
                }
                var vm = this;
                var id = this.$route.query.id;
               let params = {"id":id,"loginPwd":md5(this.rePwd)};
                this.loading = true;
                axios.put(this.apiUrl, params,{
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }).then(function (resp) {
                    vm.loading = false;
                    var resp = resp.data;
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
                        desc: error
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
