<template>
    <div id="app" class="clearfix">
        <div id="left">
            <left-menu :path="path"></left-menu>
        </div>
        <div class="main-layout" id="right">
            <top-header :headerRoute="headerRoute"></top-header>
            <router-view class="bodycontentright"></router-view>
        </div>
    </div>
</template>

<script type="text/javascript">
    import Header from "./components/header.vue";
    import Menu from "./components/menu.vue"
    import router from './router';
    export default {
        name: 'app',
        data () {
            return {
                headerRoute:'平台列表',
                path:'/platform/list'
            }
        },
        created: function () {
            this.getCookie();
            router.beforeEach((to, from, next) => {
                this.headerRoute = to.name;
                this.path = to.path;
                next();
                document.cookie="path="+ to.path;
            });
        },
        methods:{
            getCookie() {
                let aCookie = document.cookie.split(";");
                let info = {"name":this.headerRoute,"path":this.path };
                for (var value of aCookie) {
                    var aCrumb = value.split("=");
                    if (aCrumb[0].toString().trim() == 'path'){
                        info.path = aCrumb[1];
                    }
                }
                this.path = info.path;
                for(let value of router.options.routes){
                    if(value.path == this.path){
                        this.headerRoute = value.name;
                    }
                }
            }
        },
        components: {
            TopHeader: Header,
            LeftMenu: Menu,
        }
    }
</script>
