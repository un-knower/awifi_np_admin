/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-04 16:33
 * 创建作者: haoxu
 * 文件名称: upload-success.vue
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

<template>
    <div class="container">
        <div class="config">
            <div class="canvas">
                <header-canvas :title="title" :num="selectNum"></header-canvas>
            </div>
            <div class="form-group" style="margin-left:-80px;">
                <div class="input-group clearfix" style="text-align:center;">
                    <div class="form-group delete-popup" style="text-align:center;">
                        <img src="../../image/icon/correct.png" width="35" height="35">
                        恭喜您，服务上线成功
                    </div>
                </div>
                <div class="normal-buttons" style="margin-left:160px;">
                    <a-button type="primary" icon="checkmark-round"  @click="confirm">
                        <span>确认上线</span>
                    </a-button>
                    <a-button type="info" icon="chevron-left"  @click="back">
                        <span>取消上线</span>
                    </a-button>
                </div>
            </div>
        </div>

    </div>
</template>

<script type="text/javascript">
    import axios from 'axios';
    import router from '../../router';
    import HeaderCanvas from './header-canvas.vue';
    export default{
        data(){
            return {
                title:["配置服务","测试服务","审核服务","上线服务"],
                selectNum: 4,
                apiUrl:'/admin/servicepublishlog',

            }
        },
        methods: {
            /**
             * 功能：下一步
             * 修改记录： xx
             * @author 郝旭
             * @date 2017-02-13 19:12
             */
            nextTip:function(publishStatus){
                var vm = this;
                let params = {"id":parseInt(this.$route.params.logid),"publishStatus":publishStatus};
                axios.put(this.apiUrl, params,{
                    headers: {
                        'Content-Type': 'application/json',
                    }
                }).then(function (resp) {
                    var resp = resp.data;
                    if(resp.code =="0"){
                        vm.$Notice.success({
                            title: "成功"
                        });
                        setTimeout(function () {
                            router.push({ path: '/online/list'});
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
            back:function(){
                this.nextTip(6);
            },
            confirm:function(){
                this.nextTip(5);
            }
        },
        components: {
            HeaderCanvas
        }
    }
</script>
<style scoped lang="scss">
    /*@import "../../style/upload.scss";*/
    .buttons{
        width:90%;
        margin-left:0px;
    }
</style>

