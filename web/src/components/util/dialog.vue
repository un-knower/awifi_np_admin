/**
* 版权所有: 爱WiFi无线运营中心
* 创建日期: 2017-01-13 14:43
* 创建作者: haoxu
* 文件名称: Dialog.vue
* 版本: v1.0
* 功能: 弹框插件
* 修改记录: xx
*/

<template>
	<div class="dialogs">
		<div class="dialog" :class="{ 'dialog-active': isShow, 'interfaceDig':interface }" ref="dialog">
			<div class="dialog-content">
				<div class="close">
					<span class="iconfont" @click="close">×</span>
				</div>
				<header class="dialog-header" slot="header">
					<span class="dialog-title">{{title}}</span>
				</header>
				<slot name="body"></slot>
				<slot name="footer"></slot>
			</div>
		</div>
		<div class="dialog-overlay"></div>
	</div>
</template>

<script type="text/javascript">
	export default{
		name: 'NpDialog',
		props: {
			//是否显示
			showDialog: {
				type: Boolean,
			},
			title: {
				type: String,
				default: ''
			},
			interface:{
				type: Boolean,
				default: false
			}
		},
		data: function () {
			return {
				isShow:	this.showDialog,
				height:0
			}
		},
		methods: {
			close: function () {
				this.isShow = false;
				this.$emit('close',this.isShow);
			}
		},
		watch:{
			showDialog(val){
				this.isShow=val;
				var dialog = this.$refs.dialog,
				dialogHeight = dialog.clientHeight,
				top = parseInt((this.height- dialogHeight)/2);
				dialog.style.top = top+"px";
			}
		},
		mounted: function () {
			this.height = window.outerHeight-200;
		},
		components: {}
	}

</script>
