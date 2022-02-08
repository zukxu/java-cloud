<template>
	<div class="echartsDiv">
		<!-- <div class="search">
			<span>选择时间</span>
			<el-date-picker
				v-model="search.date"
				@change="getData"
				type="date"
				placeholder="选择日期"
				style="width:350px;margin-left:5px"
				size="mini"
				format="yyyy-MM-dd"
				value-format="yyyyMMdd"
				default-value
			>
			</el-date-picker>
		</div> -->
		<div class="statisticalData">
			<p>
				<label class="data">{{ lastMonTarget[0].targetNum }}{{ lastMonTarget[0].unit }}</label
				><label
					>环比:<span>{{ lastMonTarget[0].targetHb }}%</span></label
				>
			</p>
		</div>

		<div class="title">
			趋势图
		</div>
		<div class="charts" id="echartsMon"></div>
		<div v-if="this.echarts[1].xData.length > 0">
			<div class="title">
				地市情况
			</div>
			<div class="charts" id="echartsCity"></div>
		</div>
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
					console.log(newV);
					// this.dataProcessing(newV);
					// this.drawBarHorizontal();
				}
			},
			deep: true,
		},
	},
	data() {
		//这里存放数据
		return {
			search: {
				date: "",
			},
			echarts: [
				{
					xData: [],
					yData: [],
				},
				{
					xData: [],
					yData1: [],
					yData2: [],
				},
			],

			lastMonTarget: [{ targetNum: "" }],
		};
	},

	methods: {
		getData() {
			let t = this.componentsData;
			if (t.areaId) {
				this.$store.dispatch("indicator/cityHb", t).then((res) => {
					if (res.data.code == 200) {
						res.data.data.map((item) => {
							this.echarts[1].xData.push(item.cityName);
							this.echarts[1].yData1.push(item.targetHb);
							this.echarts[1].yData2.push(item.targetNum);
						});
						this.$nextTick(function() {
							// DOM 更新了
							this.drawBarHorizontal2();
						});
					}
				});
			}
			this.$store.dispatch("indicator/lastMonTarget", t).then((res) => {
				if (res.data.code == 200) {
					this.lastMonTarget = res.data.data;
				}
			});

			this.$store.dispatch("indicator/twelveMonTarget", t).then((res) => {
				if (res.data.code == 200) {
					res.data.data.map((item) => {
						this.echarts[0].xData.push(item.STAT_MON);
						this.echarts[0].yData.push(item.targetNum);
					});
					this.$nextTick(function() {
						// DOM 更新了
						this.drawBarHorizontal1();
					});
				}
			});

			// this.drawBarHorizontal1();
			// this.drawBarHorizontal2();
		},

		drawBarHorizontal1() {
			let barHorizontal = echarts.init(document.getElementById("echartsMon"));
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
					top: "15%",
					left: "5%",
				},

				xAxis: [
					{
						type: "category",
						data: this.echarts[0].xData,
						axisPointer: {
							type: "shadow",
						},
						axisLine: {
							// show: false,
						},
						axisTick: {
							show: false,
						},
					},
				],
				yAxis: [
					{
						type: "value",
						name: "指标值",
						axisLabel: {
							// show: false,
						},
						axisLine: {
							// show: false,
						},
						axisTick: {
							show: false,
						},
						splitLine: {
							// show: false,
						},
					},
				],
				series: [
					{
						name: "指标值",
						type: "line",
						smooth: true,
						data: this.echarts[0].yData,

						itemStyle: {
							normal: {
								color: "#67C23A",
							},
						},
					},
				],
			});
		},
		drawBarHorizontal2() {
			let barHorizontal = echarts.init(document.getElementById("echartsCity"));
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
					top: "15%",
					left: "5%",
				},

				legend: {
					data: ["环比", "指标值"],
				},

				xAxis: [
					{
						type: "category",
						data: this.echarts[1].xData,
						axisPointer: {
							type: "shadow",
						},
						axisLine: {
							// show: false,
						},
						axisTick: {
							show: false,
						},
						axisLabel: {
							formatter: function(params) {
								var newParamsName = ""; // 最终拼接成的字符串
								var paramsNameNumber = params.length; // 实际标签的个数
								var provideNumber = 4; // 每行能显示的字的个数
								var rowNumber = Math.ceil(paramsNameNumber / provideNumber); // 换行的话，需要显示几行，向上取整
								/**
								 * 判断标签的个数是否大于规定的个数， 如果大于，则进行换行处理 如果不大于，即等于或小于，就返回原标签
								 */
								// 条件等同于rowNumber>1
								if (paramsNameNumber > provideNumber) {
									/** 循环每一行,p表示行 */
									for (var p = 0; p < rowNumber; p++) {
										var tempStr = ""; // 表示每一次截取的字符串
										var start = p * provideNumber; // 开始截取的位置
										var end = start + provideNumber; // 结束截取的位置
										// 此处特殊处理最后一行的索引值
										if (p == rowNumber - 1) {
											// 最后一次不换行
											tempStr = params.substring(start, paramsNameNumber);
										} else {
											// 每一次拼接字符串并换行
											tempStr = params.substring(start, end) + "\n";
										}
										newParamsName += tempStr; // 最终拼成的字符串
									}
								} else {
									// 将旧标签的值赋给新标签
									newParamsName = params;
								}
								//将最终的字符串返回

								return newParamsName;
							},
						},
					},
				],
				yAxis: [
					{
						type: "value",
						name: "环比",
						axisTick: {
							show: false,
						},

						axisLabel: {
							formatter: "{value} %",
						},
					},
					{
						type: "value",
						name: "指标值",
						axisTick: {
							show: false,
						},
					},
				],
				series: [
					{
						name: "环比",
						type: "line",
						smooth: true,
						data: this.echarts[1].yData1,
						axisLabel: {
							formatter: "{value} %",
						},
						itemStyle: {
							normal: {
								color: "#67C23A",
							},
						},
					},
					{
						name: "指标值",
						type: "bar",
						data: this.echarts[1].yData2,
						barWidth: 15,
						yAxisIndex: 1,
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
		this.getData();
	},
	created() {},
};
</script>
<style lang="scss" scoped>
.echartsDiv {
	.line {
		border-bottom: 1px solid #ff9900;
		margin-bottom: 10px;
	}
	.charts {
		// width: 900px;
		width: 100%;
		height: 270px;
	}
	.title {
		font-size: 18px;
		font-weight: bold;
		border-left: 3px solid #ff9900;
		text-indent: 1em;
	}
	.statisticalData {
		.data {
			font-size: 24px;
			font-weight: bold;
			margin-right: 20px;
		}
	}
}
</style>
