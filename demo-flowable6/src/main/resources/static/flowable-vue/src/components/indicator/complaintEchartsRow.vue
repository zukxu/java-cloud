<template>
	<div class="echartsDiv">
		<span class="title">{{ componentsData.type }}</span>
		<el-row :gutter="20" style="margin-top:10px">
			<el-col :span="12">
				<p class="name">
					<label class="value"> {{ componentsData.currMonthSequentialList[0].value }}</label>
					<label class="num"> 环比 {{ componentsData.currMonthSequentialList[0].sequential }} </label>
					<!-- <label class="num" v-if="Number(componentsData.currMonthSequentialList[0].sequential.replace('%', '')) >= 0">
						环比 <span style="color:#67c23a">{{ componentsData.currMonthSequentialList[0].sequential }}</span>
					</label>
					<label class="num" v-if="Number(componentsData.currMonthSequentialList[0].sequential.replace('%', '')) < 0">
						环比 <span style="color:#f52631">{{ componentsData.currMonthSequentialList[0].sequential }}</span>
					</label> -->
				</p>
				<div class="charts_bar_horizontal" id="echartsId"></div>
			</el-col>
			<el-col :span="12">
				<el-table
					:data="componentsData.sequentialList"
					style="width: 100%;"
					:row-style="{ height: '30px' }"
					:cell-style="{ 'text-align': 'center', color: '#4D4D4D', height: '30px', 'font-size': '14px', padding: '0' }"
					:header-cell-style="{
						'text-align': 'center',
						color: '#262626',
						height: '32px',
						'font-size': '14px',
					}"
				>
					<el-table-column prop="cityName" align="center" label="区域"> </el-table-column>
					<el-table-column prop="value" align="center" label="指标值"> </el-table-column>
					<el-table-column prop="sequential" align="center" label="环比">
						<template slot-scope="scope">
							<span v-if="scope.row.sequential >= '0'" style="color:#67c23a">{{ scope.row.sequential }}</span>
							<span v-if="scope.row.sequential < '0'" style="color:#f52631">{{ scope.row.sequential }}</span>
						</template>
					</el-table-column>
				</el-table>
			</el-col>
		</el-row>
	</div>
</template>

<script>
let echarts = require("echarts");

export default {
	//import引入的组件需要注入到对象中才能使用
	components: {},
	props: {
		componentsData: "",
	},
	watch: {
		componentsData: {
			//深度监听，可监听到对象、数组的变化
			handler(newV, oldV) {
				// do something, 可使用this
				if (newV != oldV) {
					// this.dataProcessing(newV);
					this.drawBarHorizontal();
				}
			},
			deep: true,
		},
	},
	data() {
		//这里存放数据
		return {
			date: [],
			data: [],
		};
	},

	methods: {
		drawBarHorizontal() {
			let barHorizontal = echarts.init(document.getElementById("echartsId"));

			barHorizontal.setOption({
				tooltip: {
					trigger: "axis",
					axisPointer: {
						type: "cross",
						crossStyle: {
							color: "#999",
						},
					},
				},
				grid: {
					top: "5%",
					left: "5%",
				},

				xAxis: [
					{
						type: "category",
						data: this.componentsData.monthData.date,
						axisPointer: {
							type: "shadow",
						},
						axisLine: {
							show: false,
						},
						axisTick: {
							show: false,
						},
					},
				],
				yAxis: [
					{
						type: "value",
						// name: "总人数",
						axisLabel: {
							show: false,
						},
						axisLine: {
							show: false,
						},
						axisTick: {
							show: false,
						},
						splitLine: {
							show: false,
						},
					},
				],
				series: [
					{
						// name: "降水量",
						type: "bar",
						data: this.componentsData.monthData.data,
						barWidth: 15,
						itemStyle: {
							normal: {
								color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
									{ offset: 0, color: "#88c9f9" }, //柱图渐变色
									{ offset: 0.5, color: "#55b2f7" }, //柱图渐变色
									{ offset: 1, color: "#20a0ff" }, //柱图渐变色
								]),
							},
						},
					},
				],
			});
		},
	},

	mounted() {
		this.drawBarHorizontal();
	},
	created() {},
};
</script>
<style lang="scss" scoped>
.echartsDiv {
	.charts_bar_horizontal {
		// width: 900px;
		width: 40vw;
		height: 240px;
	}

	.name {
		text-align: right;

		font-size: 20px;
		font-weight: bold;
	}
	.value {
		color: #ed784e;
	}
	.num {
		color: #7f7f7f;
		margin-left: 30px;
	}
	.title {
		color: #7f7f7f;
		border-bottom: 2px solid #ff9900;
	}
}
</style>
