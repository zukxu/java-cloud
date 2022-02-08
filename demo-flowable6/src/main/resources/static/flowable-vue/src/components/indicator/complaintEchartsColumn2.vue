<template>
	<div class="echartsDiv" id="div123">
		<div class="data" style="margin-left:20px;margin-bottom:20px" v-for="(item, i) in viewData" :key="i">
			<span class="title">{{ item.type }}</span>

			<p class="name">
				<span class="value">{{ viewData[i].data[viewData[i].data.length - 1] }} </span>
				<span class="num">环比 {{ monHb[i].hb }}</span>
			</p>
			<div class="charts_bar_horizontal" :id="'echartsIdd' + i"></div>
			<div class="line"></div>

			<el-table
				:data="tableData[i]"
				style="width: 100%"
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
				<el-table-column prop="data" align="center" label="指标值"> </el-table-column>
				<el-table-column prop="hb" align="center" label="环比">
					<!-- <template slot-scope="scope">
						<span v-if="scope.row.hb >= '0'" style="color:#67c23a">{{ scope.row.sequential }}</span>
						<span v-if="scope.row.hb < '0'" style="color:#f52631">{{ scope.row.sequential }}</span>
					</template> -->
				</el-table-column>
			</el-table>
		</div>
	</div>
</template>

<script>
let echarts = require("echarts");
export default {
	//import引入的组件需要注入到对象中才能使用
	components: {},

	props: {
		MarketingActivitiesDimensionData: "",
	},

	watch: {
		MarketingActivitiesDimensionData: {
			//深度监听，可监听到对象、数组的变化
			handler(newV, oldV) {
				// do something, 可使用this
				if (newV != oldV) {
					this.dataProcessing(newV);
				}
			},
			deep: true,
		},
	},
	data() {
		//这里存放数据
		return {
			viewData: [],
			blcg: {},
			tqjy: {},
			wyl: {},
			tableData: [],
			monHb: [],
		};
	},

	methods: {
		drawBarHorizontal() {
			for (let i = 0; i < this.viewData.length; i++) {
				let echartsId = "echartsIdd" + i;
				let barHorizontal = echarts.init(document.getElementById(echartsId));

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
							data: this.viewData[i].date,
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
							data: this.viewData[i].data,
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
			}
		},

		dataProcessing(data) {
			this.viewData = [];
			this.blcg = {};
			this.tqjy = {};
			this.wyl = {};
			this.tableData = [];
			this.monHb = [];

			let date = [];
			let blcgData = [];
			let tqjyData = [];
			let wylData = [];

			data.mon[0].mon.map((item) => {
				date.push(item.STAT_MON);
				blcgData.push(item.blcg);
				tqjyData.push(item.tqjy);
				wylData.push(item.wyl);
			});
			this.viewData.push({
				type: "办理成功率",
				date: date,
				data: blcgData,
			});

			this.viewData.push({
				type: "提前解约率",
				date: date,
				data: tqjyData,
			});
			this.viewData.push({
				type: "营销活动万投率",
				date: date,
				data: wylData,
			});

			let tableData1 = [];
			let tableData2 = [];
			let tableData3 = [];
			for (let i = 0; i < data.city[0].city.length; i++) {
				for (let j = 0; j < data.city[0].city_hb.length; j++) {
					if (data.city[0].city[i].cityName == data.city[0].city_hb[j].cityName) {
						tableData1.push({
							//办理成功率
							cityName: data.city[0].city[i].cityName,
							data: data.city[0].city[i].blcg,
							hb: data.city[0].city_hb[j].blcg,
						});
						tableData2.push({
							//提前解约率
							cityName: data.city[0].city[i].cityName,
							data: data.city[0].city[i].tqjy,
							hb: data.city[0].city_hb[j].tqjy,
						});
						tableData3.push({
							//营销活动万投率
							cityName: data.city[0].city[i].cityName,
							data: data.city[0].city[i].wyl,
							hb: data.city[0].city_hb[j].wyl,
						});
					}
				}
			}

			this.tableData = [tableData1, tableData2, tableData3];

			data.mon_hb[0];

			this.monHb.push({
				time: data.mon_hb[0].STAT_MON,
				hb: data.mon_hb[0].blcg,
			});
			this.monHb.push({
				time: data.mon_hb[0].STAT_MON,
				hb: data.mon_hb[0].tqjy,
			});
			this.monHb.push({
				time: data.mon_hb[0].STAT_MON,
				hb: data.mon_hb[0].wyl,
			});

			this.$nextTick(function() {
				// DOM 更新了
				this.drawBarHorizontal();
			});
		},
	},

	mounted() {},
	created() {},
};
</script>
<style lang="scss" scoped>
.echartsDiv {
	display: flex;
	flex-direction: row;
	justify-content: flex-start;
	flex-wrap: wrap;
	.data {
		width: 23%;
		.charts_bar_horizontal {
			width: 100%;
			height: 200px;
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
		.line {
			border-bottom: 1px solid #ff9900;
		}
		.title {
			color: #7f7f7f;
			border-bottom: 2px solid #ff9900;
		}
	}
}
</style>
