/**
* 版权所有: 爱WiFi无线运营中心
* 创建日期: 2017-01-11 10:43
* 创建作者: haoxu
* 文件名称: BootPage.vue
* 版本: v1.0
* 功能: 分页组件
* 修改记录: xx
*/

<template>
    <div class="boot clearfix">
    <nav class="boot-nav">
        <ul class="pagination boot-page" @child-msg = "changeData()">
            <li>
                <a href="javascript:void(0)" aria-label="Previous" @click="onFirstClick()">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li>
                <a href="javascript:void(0)" aria-label="Next" @click="onPrevClick()">
                    <span aria-hidden="true">‹</span>
                </a>
            </li>
            <li v-for="(page, index) in pages" :class="activeNum === index ? 'active' : ''" :key="index">
                <a href="javascript:void(0)" v-text="page" @click="onPageClick(index)"></a>
            </li>
            <li>
                <a href="javascript:void(0)" aria-label="Next" @click="onNextClick()">
                    <span aria-hidden="true">›</span>
                </a>
            </li>
            <li>
                <a href="javascript:void(0)" aria-label="Next" @click="onLastClick()">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
        <div class="page-total">
            共 <span v-text="pageTotal"></span> 页
        </div>
    </nav>
    <select class="form-control boot-select" v-model="len">
        <option v-for="(arr,index) in lens" :value="arr" v-text="arr" :selected="index === 0 ? true : false"></option>
    </select>
    </div>
</template>

<script type="text/javascript">
import axios from 'axios';
import qs from 'qs';
export default {
    props: {
        // 是否请求服务器端数据
        async: {
            type: Boolean,
            default: false
        },
        // 显示个数数组
        lens: {
            type: Array,
            default: function () {
                return [20, 40, 60]
            }
        },

        // 表格数据（数组）
        data: {
            type: Array,
            default: function () {
                return []
            }
        },

        // AJAX地址
        url: {
            type: String,
            default: ''
        },

        // 显示页数
        pageLen: {
            type: Number,
            default: 5
        },
        // 参数内容
        param: {
            type: Object,
            default: function () {
                return {}
            }
        }
    },
    data () {
        return {
            activeNum: 0,
            // 页码
            pages:[],
            // 每页显示个数
            len: 20,
            // 总条数
            totalRecord: 1,
            //总页数
            pageTotal:1
        }
    },
    methods: {
        resetPager(){
            this.activeNum=0;
        },
        changeData(newData){
          this.$emit('child-msg',newData);
        },
        // 点击页码刷新数据
        onPageClick (index) {

            this.activeNum = index
        },

        // 上一页
        onPrevClick () {

            // 当前页是否为当前最小页码
            if (this.activeNum > 0) {
                this.activeNum = this.activeNum - 1
            } else {
                if (this.pages[0] !== 1) {
                    let newPages = [];

                    for (let i = 0; i < this.pages.length; i++) {
                        newPages[i] = this.pages[i] - 1
                    }

                    this.pages = newPages;
                    this.getData( )
                }
            }
        },

        // 下一页
        onNextClick () {

            // 当前页是否为当前最大页码
            if (this.activeNum < this.pages.length - 1) {
                this.activeNum = this.activeNum + 1
            } else {
                if (this.pages[this.pages.length - 1] < this.pageTotal) {
                    let newPages = [];

                    for (let i = 0; i < this.pages.length; i++) {
                        newPages[i] = this.pages[i] + 1
                    }

                    this.pages = newPages;

                    this.getData()
                }
            }
        },

        // 第一页
        onFirstClick () {
            if (this.pages[0] === 1) {
                this.activeNum = 0
            } else {
                let originPage = [];

                for (let i = 1; i <= this.pageLen; i++) {
                    originPage.push(i)
                }

                this.pages = originPage;
                this.activeNum === 0 ? this.getData() : this.activeNum = 0
            }
        },

        // 最后一页
        onLastClick () {
            if (this.pageTotal <= this.pageLen) {
                this.activeNum = this.pages.length - 1
            } else {
                let lastPage = [];

                for (let i = this.pageLen - 1; i >= 0; i--) {
                    lastPage.push(this.pageTotal - i)
                }

                this.pages = lastPage;
                this.activeNum === this.pages.length - 1 ? this.getData() : this.activeNum = this.pages.length - 1
            }
        },

        // 获取页码
        getPages () {
            this.pages = [];

            if (!this.async) {
                this.pageTotal = Math.ceil(this.data.length / this.len)
            }

            // 比较总页码和显示页数
            if (this.pageTotal <= this.pageLen) {
                for (let i = 1; i <= this.pageTotal; i++) {
                    this.pages.push(i)
                }
            } else {
                for (let i = 1; i <= this.pageLen; i++) {
                    this.pages.push(i)
                }
            }
        },
        resetPage(){
                this.activeNum=0;
        },
        // 页码变化获取数据
        getData () {
            if (!this.async) {
                let len = this.len,
                    pageNum = this.pages[this.activeNum] - 1,
                    newData = [];

                for (let i = pageNum * len; i < (pageNum * len + len); i++) {
                    this.data[i] !== undefined ? newData.push(this.data[i]) : ''
                }
               this.changeData(newData);
            } else {

                this.param.page = this.pages[this.activeNum];
                this.param.pageSize  = this.len;

                var vm = this;
                var url = vm.url+"?"+ qs.stringify(this.param);
                axios.get(url).then(function (response) {
                        var response = response.data;
                        vm.pageTotal = Math.floor((response.data.totalRecord-1)/vm.len)+1;
                    if (vm.pages.length !== vm.pageLen || vm.pageTotal < vm.pageLen) {
                        vm.getPages()
                    }
                     vm.changeData(response.data);
                 }).catch(function (error) {
                        console.log(error);
                 });
                 }
        }
    },
    created: function () {
        if (!this.async) {
            this.getPages()
        } 

        this.getData()
    },
    watch: {
        'param'(newVal,oldVal){
            this.getData();
         },
        // 监听显示数量
        'len' (newVal, oldVal) {
            if (!this.async) {
                this.getPages();

                if (this.activeNum + 1 > this.pages.length) {
                    this.activeNum = this.pages.length - 1
                }

                this.getData()
            } else {
                this.getPages();
                this.getData();
            }
        },

        // 监测当前页变化
        activeNum (newVal, oldVal) {
            this.getData()
        }
    }
}
</script>
