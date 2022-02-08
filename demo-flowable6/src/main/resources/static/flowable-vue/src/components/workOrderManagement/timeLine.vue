<template>
	<div class="page">
		<h3>工单流程</h3>
		<el-timeline>
			<el-timeline-item v-for="(activity, index) in activities" :key="index" :size="activity.size" :timestamp="activity.HandlerTime">
				{{ activity.Hander }}
				<br />
				{{ activity.workFlowStatus }}
			</el-timeline-item>
		</el-timeline>
	</div>
</template>

<script>
export default {
	components: {},
	props: {
		identifier: "", //工单号
	},
	watch: {},
	data() {
		return {
			activities: [],
		};
	},
	computed: {},
	methods: {
		QueryCSSTimeLine() {
			let t = {
				identifier: this.identifier,
			};
			this.$store.dispatch("workOrderManageApi/QueryCSSTimeLine", t).then((res) => {
				if (res.data.code == 200) {
					this.activities = res.data.data;
				} else {
				}
			});
		},
	},
	created() {},
	mounted() {
		this.QueryCSSTimeLine();
	},
};
</script>

<style lang="scss" scoped>
.page {
}
</style>
