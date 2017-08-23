<template>
	<div class="loading-overlay" :class="{'show-loading': showLoading}">
		<div class="sk-three-bounce">
			<div class="sk-child sk-bounce1"></div>
			<div class="sk-child sk-bounce2"></div>
			<div class="sk-child sk-bounce3"></div>
		</div>
	</div>
</template>

<script type="text/javascript">
	export default{
		name: 'Loading',
		data(){
			return {}
		},
		props: {
			showLoading: {
				showLoading: Boolean,
				default: false
			}
		},
		components: {}
	}
</script>