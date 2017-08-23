/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-02-04 16:31
 * 创建作者: haoxu
 * 文件名称: upload-test.vue
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
            <div class="form-group clearfix" style="margin-left:-100px;">
                <div class="input-group" style="text-align:center;">
                    <div class="form-group delete-popup">
                        <img src="../../image/icon/correct.png" width="35" height="35">
                       服务测试通过，请进行审核服务
                    </div>
                </div>
                <div class=" normal-buttons" >
                    <a-button type="primary" icon="checkmark-round"  @click="nextTip">
                        <span>进入审核</span>
                    </a-button>
                    <a-button type="info" icon="chevron-left"  @click="back">
                        <span>上一步</span>
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
                selectNum: 2
            }
        },
        methods: {
            nextTip:function(){
                router.push({path: '/online/review/'+this.$route.params.logid });
            },
            back:function(){
                router.push({path: '/online/config/'+this.$route.params.logid +'/0/'+this.$route.params.publishStatus});
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
