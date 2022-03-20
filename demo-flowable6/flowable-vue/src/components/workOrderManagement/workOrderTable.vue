<template>
	<div class="page">
		<div class="search">
			<el-radio-group v-model="search.timeRadio" size="mini" @change="changeTimeRaio">
				<el-radio-button label="day">今天</el-radio-button>
				<el-radio-button label="yest">昨天</el-radio-button>
				<el-radio-button label="week">本周</el-radio-button>
				<el-radio-button label="month">本月</el-radio-button>
			</el-radio-group>

			<span>工单日期</span>
			<el-date-picker
				v-model="search.time"
				value-format="yyyyMMdd"
				style="width:200px"
				size="mini"
				type="daterange"
				range-separator="-"
				start-placeholder="开始日期"
				end-placeholder="结束日期"
				@change="changeTime"
			>
			</el-date-picker>

			<span>标题</span>
			<el-input v-model="search.input" size="mini" placeholder="标题搜索" style="width:180px;">
				<el-button slot="append" icon="el-icon-search" size="mini" @click="QueryCSS"></el-button>
			</el-input>

			<span>工单类型</span>
			<el-select v-model="search.IdentySubtype" placeholder="请选择" size="mini" @change="QueryCSS">
				<el-option v-for="item in search.identyTypeOptions" :key="item.identyType" :label="item.typeText" :value="item.identyType"> </el-option>
			</el-select>

			<span>工单状态</span>
			<el-select v-model="search.workFlowStatus" placeholder="请选择" size="mini" style="width:100px" @change="QueryCSS">
				<el-option label="处理中" value="01"> </el-option>
				<el-option label="已退回" value="02"> </el-option>
				<el-option label="已撤回" value="03"> </el-option>
				<el-option label="已回复" value="04"> </el-option>
				<el-option label="已归档" value="05"> </el-option>
			</el-select>
		</div>
		<div class="tableBox">
			<el-table
				:data="tableData.data"
				border
				stripe
				:row-style="{ height: '40px' }"
				:cell-style="{ 'text-align': 'center', color: '#4D4D4D', height: '40px', 'font-size': '14px' }"
				:header-cell-style="{
					'text-align': 'center',
					color: '#262626',
					height: '32px',
					'font-size': '14px',
				}"
			>
				<el-table-column prop="Identifier" label="编号" :show-overflow-tooltip="true"> </el-table-column>
				<el-table-column prop="IdentyType" label="工单类型">
					<template slot-scope="scope">
						<span v-if="scope.row.IdentyType == '00'">预警单</span>
						<span v-if="scope.row.IdentyType == '01'">督办单 </span>
						<span v-if="scope.row.IdentyType == '02'">任务单 </span>
						<span v-if="scope.row.IdentyType == '03'">申请单 </span>
					</template>
				</el-table-column>

				<el-table-column prop="Title" label="标题"> </el-table-column>
				<el-table-column prop="combFrom" label="发起部门"> </el-table-column>
				<el-table-column prop="combFrom" label="发起部人"> </el-table-column>
				<el-table-column prop="combFrom" label="当前部门/处理人"> </el-table-column>
				<el-table-column prop="CreatTime" label="发起时间"> </el-table-column>
				<el-table-column prop="workFlowStatus" label="状态">
					<template slot-scope="scope">
						<span v-if="scope.row.workFlowStatus == '01'">处理中</span>
						<span v-if="scope.row.workFlowStatus == '02'">已退回 </span>
						<span v-if="scope.row.workFlowStatus == '03'">已撤回 </span>
						<span v-if="scope.row.workFlowStatus == '04'">已回复 </span>
						<span v-if="scope.row.workFlowStatus == '05'">已归档 </span>
						<span v-if="scope.row.workFlowStatus == '06'">催办中 </span>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="450px">
					<template slot-scope="scope">
						<span v-if="scope.row.IdentyType == '03'">
							<!-- 申请单 -->
							<el-button type="text" style="color:#007fff;" @click="toInfoPage(scope.row.Identifier, scope.row.IdentyType, scope.row.IdentySubtype)">详情</el-button>
							<!-- <el-button type="text" style="color:#007fff" @click="toInfoPage(scope.row.Identifier, scope.row.IdentyType, scope.row.IdentySubtype)">处理</el-button> -->
							<el-button type="text" style="color:#007fff">关闭</el-button>
							<!-- <el-button type="text" style="color:#007fff"  @click="toInfoPage(scope.row.Identifier, scope.row.IdentyType, scope.row.IdentySubtype)">督办</el-button> -->
							<el-button v-if="scope.row.workFlowStatus == '04'" type="text" style="color:#339933" @click="operation(scope.row.Identifier, '归档')">归档</el-button>
							<el-button type="text" style="color:#339933" @click="toInfoPage(scope.row.Identifier, scope.row.IdentyType, scope.row.IdentySubtype)">转派</el-button>
							<el-button v-if="scope.row.workFlowStatus == '01'" type="text" style="color:#339933" @click="operation(scope.row.Identifier, '催办')">催办</el-button>
							<el-button v-if="scope.row.workFlowStatus == '04'" type="text" style="color:#339933" @click="operation(scope.row.Identifier, '再处理')">再处理</el-button>
							<el-button v-if="scope.row.workFlowStatus == '01'" type="text" style="color:#f33" @click="operation(scope.row.Identifier, '撤单')">撤单</el-button>
						</span>
						<span v-if="scope.row.IdentyType != '03'">
							<el-button type="text" style="color:#007fff;" @click="toInfoPage(scope.row.Identifier, scope.row.IdentyType, scope.row.IdentySubtype)">详情</el-button>
							<!-- <el-button type="text" style="color:#007fff" @click="toInfoPage(scope.row.Identifier, scope.row.IdentyType, scope.row.IdentySubtype)">处理</el-button> -->
							<el-button type="text" style="color:#007fff">关闭</el-button>
							<!-- <el-button type="text" style="color:#007fff"  @click="toInfoPage(scope.row.Identifier, scope.row.IdentyType, scope.row.IdentySubtype)">督办</el-button> -->
							<el-button type="text" style="color:#339933" @click="toInfoPage(scope.row.Identifier, scope.row.IdentyType, scope.row.IdentySubtype)">转派</el-button>
						</span>
					</template>
				</el-table-column>
			</el-table>
			<div style="float:right">
				<el-pagination
					@size-change="handleSizeChange"
					@current-change="handleCurrentChange"
					:current-page="tableData.page"
					:page-sizes="[10, 20, 30, 50]"
					:page-size="tableData.limit"
					layout="total, sizes, prev, pager, next, jumper"
					:total="tableData.totalCount"
				>
				</el-pagination>
			</div>
			<el-dialog :title="form.title" :visible.sync="dialogTableVisible" width="45%" @close="dialogVisible = false">
				<el-form :model="form" ref="ruleForm" label-width="100px" class="demo-ruleForm">
					<el-form-item label="原因" prop="people">
						<el-input type="textarea" v-model="form.desc"></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="operationSubmit">{{ form.title }}</el-button>
					</el-form-item>
				</el-form>
			</el-dialog>
		</div>
	</div>
</template>

<script>
export default {
	components: {},
	props: {
		tableType: "",
	},
	watch: {
		//监听tableType变化，重新查询
		// tableType: function(val) {
		// 	this.QueryCSS();
		// },
	},
	data() {
		return {
			identyType: "", //工单类型

			search: {
				timeRadio: "day",
				time: [],
				input: "",
				workFlowStatus: "",
				IdentySubtype: "",
				identyTypeOptions: [],
			},
			tableData: {
				data: [],
				totalCount: 0,
				page: 1,
				limit: 10,
			},
			form: {
				title: "",
				identifier: "",
				desc: "",
			},

			dialogTableVisible: false,
		};
	},
	computed: {},
	methods: {
		handleSizeChange(val) {
			this.tableData.limit = val;
			this.QueryCSS();
		},
		handleCurrentChange(val) {
			this.tableData.page = val;
			this.QueryCSS();
		},

		toInfoPage(identifier, identyType, IdentySubtype) {
			switch (identyType) {
				case "00":
					//预警单
					this.$router.push({ name: "workOrderInfo", params: { identifier: identifier, identyType: identyType, IdentySubtype: IdentySubtype } });
					break;
				case "01":
					//督办单
					this.$router.push({ name: "workOrderInfo", params: { identifier: identifier, identyType: identyType, IdentySubtype: IdentySubtype } });
					break;
				case "02":
					//任务单
					this.$router.push({ name: "workOrderInfo", params: { identifier: identifier, identyType: identyType, IdentySubtype: IdentySubtype } });
					break;
				case "03":
					//申请单
					this.$router.push({ name: "workOrderInfo", params: { identifier: identifier, identyType: identyType, IdentySubtype: IdentySubtype } });
					break;
			}
		},
		changeTimeRaio() {
			this.search.time = [];
			this.QueryCSS();
		},
		changeTime() {
			this.search.timeRadio = "";
			this.QueryCSS();
		},
		QueryCSS() {
			let t = {
				IdentyType: this.identyType, //工单类型 02任务单 01督办单 00预警单 03申请单,全部为空
				IdentySubtype: this.search.IdentySubtype, //工单子类
				Title: this.search.input, //工单标题
				StartTime: this.search.time[0] || "", //开始时间
				EndTime: this.search.time[1] || "", //结束时间
				DateType: this.search.timeRadio, //日期类型
				workFlowStatus: this.search.workFlowStatus, //工单状态
				page: this.tableData.page,
				limit: this.tableData.limit,
			};
			this.$store.dispatch("workOrderManageApi/QueryCSSList", t).then((res) => {
				if (res.data.code == 200) {
					this.tableData.data = res.data.data;
					this.tableData.totalCount = res.data.totalCount;
				}
			});
		},
		operation(identifier, title) {
			this.form.identifier = identifier;
			this.form.title = title;
			this.dialogTableVisible = true;
		},
		operationSubmit() {
			let t = {
				Identifier: this.form.identifier,
			};
			switch (this.form.title) {
				case "催办":
					t.UrgeReason = this.form.desc;
					this.$store.dispatch("workOrderManageApi/UrgeCSS", t).then((res) => {
						if (res.data.code == 200) {
							this.$message.success("催办成功");
							this.dialogTableVisible = false;
						} else {
							this.$message.success("催办失败");
						}
					});
					break;
				case "归档":
					t.FilingOpinion = this.form.desc;
					this.$store.dispatch("workOrderManageApi/StatementCSS", t).then((res) => {
						if (res.data.code == 200) {
							this.$message.success("归档成功");
							this.dialogTableVisible = false;
						} else {
							this.$message.success("归档失败");
						}
					});
					break;
				case "撤单":
					t.WithdrawReason = this.form.desc;
					this.$store.dispatch("workOrderManageApi/WithdrawCSS", t).then((res) => {
						if (res.data.code == 200) {
							this.$message.success("撤单成功");
							this.dialogTableVisible = false;
						} else {
							this.$message.success("撤单失败");
						}
					});
					break;
				case "再处理":
					t.ReprocessingOpinion = this.form.desc;
					this.$store.dispatch("workOrderManageApi/ReprocessCSS", t).then((res) => {
						if (res.data.code == 200) {
							this.$message.success("再处理成功");
							this.ReprocessingOpinionVisible = false;
						} else {
							this.$message.success("失败");
						}
					});
			}
		},

		//工单类型查询
		identyDetail(identyType, type) {
			let t = {
				identyType,
			};
			this.$store.dispatch("workOrderManageApi/identyDetail", t).then((res) => {
				if (res.data.code == 200) {
					this.search.identyTypeOptions = res.data.data;
				}
			});
		},
	},
	created() {},
	mounted() {
		this.identyType = this.tableType;
		this.QueryCSS();
		this.identyDetail(this.identyType);
	},
};
</script>

<style lang="scss" scoped>
.page {
	.search {
		// display: flex;
		// flex-direction: row;
		// justify-content: space-between;
		// // flex-wrap: wrap;
		// align-content: space-between;
		span {
			margin: 0px 5px 0px 10px;
		}
	}
	.tableBox {
		margin-top: 20px;
	}
}
</style>
