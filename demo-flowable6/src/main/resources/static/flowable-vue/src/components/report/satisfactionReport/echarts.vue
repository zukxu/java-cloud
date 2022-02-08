<template>
	<div class="echartsDiv" v-if="echartsData.length > 0">
		<el-row :gutter="20">
			<el-col :span="12"><div class="charts_bar_horizontal" id="satisFactionEcharts"></div></el-col>
			<el-col :span="12"><div class="charts_bar_horizontal" id="satisFactionEcharts2"></div></el-col>
		</el-row>
	</div>
</template>
<script>
let echarts = require("echarts");
var tooltip = {
	trigger: "axis",
	axisPointer: {
		type: "cross",
		crossStyle: {
			color: "#999",
		},
	},
};
var grid = {
	top: "5%",
	left: "10%",
};

export default {
	props: {
		echartsData: "",
	},
	watch: {
		echartsData: {
			//深度监听，可监听到对象、数组的变化
			handler(newV, oldV) {
				// do something, 可使用this
				if (newV != oldV) {
					// console.log(newV);
					this.handleSelectionChange(newV);
				}
			},
			deep: true,
		},
	},
	data() {
		return {
			echartsSatisfactionData: {
				xData: ["第一期", "第二期", "第三期", "第四期"],
				yData: {
					dx: [],
					yd: [],
					lt: [],
				},
			},
		};
	},

	methods: {
		drawBarHorizontal() {
			let barHorizontal = echarts.init(document.getElementById("satisFactionEcharts"));

			var series = this.getSeries("data");

			barHorizontal.setOption(
				{
					tooltip: tooltip,
					grid: grid,

					xAxis: [
						{
							type: "category",
							data: this.echartsSatisfactionData.xData,
							axisPointer: {
								type: "shadow",
							},

							axisTick: {
								show: false,
							},
						},
					],
					yAxis: [
						{
							scale: true,
							type: "value",
							name: "表现值",

							axisTick: {
								show: false,
							},
						},
					],

					series: series,
				},
				true
			);
		},
		drawBarHorizontal2() {
			let barHorizontal = echarts.init(document.getElementById("satisFactionEcharts2"));

			var series = this.getSeries("data2");

			barHorizontal.setOption(
				{
					tooltip: tooltip,
					grid: grid,

					xAxis: [
						{
							type: "category",
							data: this.echartsSatisfactionData.xData,
							axisPointer: {
								type: "shadow",
							},

							axisTick: {
								show: false,
							},
						},
					],
					yAxis: [
						{
							scale: true,
							type: "value",
							name: "排名",

							axisTick: {
								show: false,
							},
						},
					],

					series: series,
				},
				true
			);
		},

		/*
		 *  随机生成 #FFFFFF 样式的颜色
		 *  @return  color  返回颜色字符串
		 */
		randomColor() {
			var color = "#";
			for (var j = 0; j < 6; j++) {
				//六次来随机生成0-16内的数值
				//toString(16)把数字转换成16进制的字符串。
				color += parseInt(Math.random() * 16).toString(16);
			}
			return color;
		},
		/*
		 *  封装  series 数据
		 *  @param   data  data字段名的字符串
		 *  @return  series  返回封装号的series数据
		 */
		getSeries(data) {
			var series = []; //定义一个数组变量用于存放 echarts series配置

			for (var i = 0; i < this.echartsSatisfactionData.yData.dx.length; i++) {
				var color = this.randomColor();
				series.push({
					name: this.echartsSatisfactionData.yData.dx[i].title,
					type: "line",
					smooth: true,
					data: this.echartsSatisfactionData.yData.dx[i][data],
					itemStyle: {
						normal: {
							color: color,
						},
					},
				});
			}

			for (var i = 0; i < this.echartsSatisfactionData.yData.yd.length; i++) {
				var color = this.randomColor();
				series.push({
					name: this.echartsSatisfactionData.yData.yd[i].title,
					type: "line",
					smooth: true,
					data: this.echartsSatisfactionData.yData.yd[i][data],
					itemStyle: {
						normal: {
							color: color,
						},
					},
				});
			}

			for (var i = 0; i < this.echartsSatisfactionData.yData.lt.length; i++) {
				var color = this.randomColor();
				series.push({
					name: this.echartsSatisfactionData.yData.lt[i].title,
					type: "line",
					smooth: true,
					data: this.echartsSatisfactionData.yData.lt[i][data],
					itemStyle: {
						normal: {
							color: color,
						},
					},
				});
			}
			for (var i = 0; i < this.echartsSatisfactionData.yData.ohter.length; i++) {
				var color = this.randomColor();
				series.push({
					name: this.echartsSatisfactionData.yData.ohter[i].title,
					type: "line",
					smooth: true,
					data: this.echartsSatisfactionData.yData.ohter[i][data],
					itemStyle: {
						normal: {
							color: color,
						},
					},
				});
			}
			return series;
		},

		handleSelectionChange(val) {
			this.echartsSatisfactionData = {
				xData: ["第一期", "第二期", "第三期", "第四期"],
				yData: {
					dx: [],
					yd: [],
					lt: [],
					ohter: [],
				},
			};
			if (val.length > 0) {
				//处理 Echatrs 表格数据
				val.map((item, i) => {
					/*
					 *  根据数据创建y轴数据条数
					 *    title  指标名
					 *    data    左边 echarts 数据
					 *    data2   右边 echarts 指标数据
					 */

					//电信
					this.echartsSatisfactionData.yData.dx.push({
						title: "电信 - " + item.kpi_name,
						data: [],
						data2: [],
					});
					//向 data 中添加数据，
					//满意度
					if (item.dx_satisfaction1) {
						this.echartsSatisfactionData.yData.dx[i].data.push(item.dx_satisfaction1);
					}
					if (item.dx_satisfaction2) {
						this.echartsSatisfactionData.yData.dx[i].data.push(item.dx_satisfaction2);
					}
					if (item.dx_satisfaction3) {
						this.echartsSatisfactionData.yData.dx[i].data.push(item.dx_satisfaction3);
					}
					if (item.dx_satisfaction4) {
						this.echartsSatisfactionData.yData.dx[i].data.push(item.dx_satisfaction4);
					}
					//排名
					if (item.dx_rank1) {
						this.echartsSatisfactionData.yData.dx[i].data2.push(item.dx_rank1);
					}
					if (item.dx_rank2) {
						this.echartsSatisfactionData.yData.dx[i].data2.push(item.dx_rank2);
					}
					if (item.dx_rank3) {
						this.echartsSatisfactionData.yData.dx[i].data2.push(item.dx_rank3);
					}
					if (item.dx_rank4) {
						this.echartsSatisfactionData.yData.dx[i].data2.push(item.dx_rank4);
					}

					//移动
					this.echartsSatisfactionData.yData.yd.push({
						title: "移动 - " + item.kpi_name,
						data: [],
						data2: [],
					});
					//向 data 中添加数据，
					//满意度
					if (item.yd_satisfaction1) {
						this.echartsSatisfactionData.yData.yd[i].data.push(item.yd_satisfaction1);
					}
					if (item.yd_satisfaction2) {
						this.echartsSatisfactionData.yData.yd[i].data.push(item.yd_satisfaction2);
					}
					if (item.yd_satisfaction3) {
						this.echartsSatisfactionData.yData.yd[i].data.push(item.yd_satisfaction3);
					}
					if (item.yd_satisfaction4) {
						this.echartsSatisfactionData.yData.yd[i].data.push(item.yd_satisfaction4);
					}
					//排名
					if (item.yd_rank1) {
						this.echartsSatisfactionData.yData.yd[i].data2.push(item.yd_rank1);
					}
					if (item.yd_rank2) {
						this.echartsSatisfactionData.yData.yd[i].data2.push(item.yd_rank2);
					}
					if (item.yd_rank3) {
						this.echartsSatisfactionData.yData.yd[i].data2.push(item.yd_rank3);
					}
					if (item.yd_rank4) {
						this.echartsSatisfactionData.yData.yd[i].data2.push(item.yd_rank4);
					}

					//联通
					this.echartsSatisfactionData.yData.lt.push({
						title: "联通 - " + item.kpi_name,
						data: [],
						data2: [],
					});
					//向 data 中添加数据，
					//满意度
					if (item.lt_satisfaction1) {
						this.echartsSatisfactionData.yData.lt[i].data.push(item.lt_satisfaction1);
					}
					if (item.lt_satisfaction2) {
						this.echartsSatisfactionData.yData.lt[i].data.push(item.lt_satisfaction2);
					}
					if (item.lt_satisfaction3) {
						this.echartsSatisfactionData.yData.lt[i].data.push(item.lt_satisfaction3);
					}
					if (item.lt_satisfaction4) {
						this.echartsSatisfactionData.yData.lt[i].data.push(item.lt_satisfaction4);
					}
					//排名
					if (item.lt_rank1) {
						this.echartsSatisfactionData.yData.lt[i].data2.push(item.lt_rank1);
					}
					if (item.lt_rank2) {
						this.echartsSatisfactionData.yData.lt[i].data2.push(item.lt_rank2);
					}
					if (item.lt_rank3) {
						this.echartsSatisfactionData.yData.lt[i].data2.push(item.lt_rank3);
					}
					if (item.lt_rank4) {
						this.echartsSatisfactionData.yData.lt[i].data2.push(item.lt_rank4);
					}

					//其他
					this.echartsSatisfactionData.yData.ohter.push({
						title: item.kpi_name,
						data: [],
						data2: [],
					});
					//向 data 中添加数据，
					//满意度
					if (item.satisfaction1) {
						this.echartsSatisfactionData.yData.ohter[i].data.push(item.satisfaction1);
					}
					if (item.satisfaction2) {
						this.echartsSatisfactionData.yData.ohter[i].data.push(item.satisfaction2);
					}
					if (item.satisfaction3) {
						this.echartsSatisfactionData.yData.ohter[i].data.push(item.satisfaction3);
					}
					if (item.satisfaction4) {
						this.echartsSatisfactionData.yData.ohter[i].data.push(item.satisfaction4);
					}
					//排名
					if (item.rank1) {
						this.echartsSatisfactionData.yData.ohter[i].data2.push(item.rank1);
					}
					if (item.rank2) {
						this.echartsSatisfactionData.yData.ohter[i].data2.push(item.rank2);
					}
					if (item.rank3) {
						this.echartsSatisfactionData.yData.ohter[i].data2.push(item.rank3);
					}
					if (item.rank4) {
						this.echartsSatisfactionData.yData.ohter[i].data2.push(item.rank4);
					}
				});
				// console.log(this.echartsData);
				this.$nextTick(function() {
					// DOM 更新了
					this.drawBarHorizontal(); //加载echarts
					this.drawBarHorizontal2();
				});
			}
		},
	},
	mounted() {},
};
</script>

<style lang="scss" scoped>
.main {
	.echartsDiv {
		margin-top: 30px;
		.charts_bar_horizontal {
			// width: 900px;
			width: 100%;
			height: 600px;
		}
	}
}
</style>
