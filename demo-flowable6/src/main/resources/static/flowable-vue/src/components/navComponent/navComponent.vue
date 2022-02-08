<!--
 * @Author: your name
 * @Date: 2021-12-01 10:41:52
 * @LastEditTime: 2022-01-12 11:30:27
 * @LastEditors: your name
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \ceva-workflow\src\components\navComponent\navComponent.vue
-->
<template>
	<div class="leftMenu" v-if="navMenu.length > 0">
		<el-menu router :default-active="this.$route.path" class="el-menu-vertical-demo" active-text-color="#FF9900" @select="handleSelect">
			<el-menu-item v-for="(item, index) in navMenu" :key="index" :index="item.component">{{ item.meta.title }}</el-menu-item>
		</el-menu>
	</div>
</template>

<script>
export default {
	components: {},
	props: {
		leftMenuData: Array,
	},
	watch: {
		leftMenuData: function(val) {
			let t = val;

			this.navMenu = [];

			this.$nextTick(() => {
				this.navMenu = t;
			});

			// console.log(val);
			// console.log(this.$route.path);
			// this.defaultUrl = this.$route.path;
			// if (val.length > 0) {
			// 	if (val[0].component == this.$route.path) {
			// 		this.$router.push({
			// 			path: val[0].component,
			// 		});
			// 	}
			// } else {
			// 	this.$router.push({
			// 		path: this.$route.path,
			// 	});
			// }
		},
	},
	computed: {
		activeMenu() {
			const route = this.$route;
			const { meta, path } = route;
			return path;
		},
	},

	data() {
		return {
			// activeMenu: "",
			isCollapse: true,
			navMenu: [],
			defaultUrl: "",
		};
	},

	methods: {
		handleSelect(key) {
			// this.activeMenu = key;
		},
	},
	created() {},
	mounted() {},
};
</script>

<style lang="scss" scoped>
.leftMenu {
	height: calc(100vh - 60px);
	// background: #f9f9f9;
}
</style>
