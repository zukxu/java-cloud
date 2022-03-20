<template>
	<div class="page">
		<div class="search">
			<div>
				<span style="margin-right:5px">选择日期</span>
				<el-date-picker
					v-model="search.time"
					value-format="yyyy/MM/dd"
					format="yyyy/MM/dd"
					style="width:240px"
					size="mini"
					type="daterange"
					range-separator="-"
					start-placeholder="开始日期"
					end-placeholder="结束日期"
					@change="getTaskList"
				>
				</el-date-picker>
			</div>
			<el-input v-model="search.text" size="mini" placeholder="关键字搜索" style="width:200px;">
				<el-button slot="append" icon="el-icon-search" size="mini" @click="getTaskList"></el-button>
			</el-input>
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
				<el-table-column prop="id" label="编号" :show-overflow-tooltip="true"> </el-table-column>
				<el-table-column prop="taskName" label="任务名称"> </el-table-column>
				<el-table-column prop="researchWay" label="调研方式"> </el-table-column>
				<el-table-column prop="dept" label="发起部门"> </el-table-column>
				<el-table-column prop="createUser" label="发起人"> </el-table-column>
				<el-table-column prop="createTime" label="任务发起时间" :show-overflow-tooltip="true"> </el-table-column>
				<el-table-column prop="smsContent" label="短信内容"></el-table-column>
				<el-table-column prop="smsUrl" label="URL"></el-table-column>
				<el-table-column prop="status" label="状态">
					<template slot-scope="scope">
						<!-- 0未推送,1已推送,2已完成 -->
						<span>{{ scope.row.status == "0" ? "未推送" : scope.row.status == "1" ? "已推送" : "已完成" }}</span>
					</template>
				</el-table-column>
				<el-table-column prop="peopleNum" label="用户数"></el-table-column>
				<el-table-column prop="replyRate" label="回复率"></el-table-column>
				<el-table-column label="操作" width="230px">
					<template slot-scope="scope">
						<el-button type="text" style="color:#007fff" @click="getInfo(scope.row.id, '0')">详情</el-button>
						<el-button type="text" style="color:#007fff" @click="getInfo(scope.row.id, '1')">编辑</el-button>

						<el-popconfirm confirmButtonText="确定" @confirm="delTask(scope.row.id)" cancelButtonText="取消" icon="el-icon-info" iconColor="red" title="确定删除吗？">
							<el-button slot="reference" type="text" size="small" style="color:red;margin-left:10px">删除</el-button>
						</el-popconfirm>
						<!-- <el-button type="text" style="color:#007fff">下载</el-button>
						<el-button type="text" style="color:#f33">撤单</el-button> -->
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
		</div>
	</div>
</template>

<script>
export default {
	components: {},
	props: ["tableType"],
	watch: {
		$route(to, from) {
			console.log(to);
			console.log(from);
		},
		//监听tableType变化，重新查询
		tableType: function(val) {
			this.getTaskList();
		},
	},
	data() {
		return {
			search: {
				time: "",
				text: "",
				type: "",
				options: [
					{
						label: "1",
						value: "0",
					},
				],
			},
			tableData: {
				data: [],
				totalCount: 0,
				page: 1,
				limit: 10,
			},
		};
	},
	computed: {},
	methods: {
		handleSizeChange(val) {
			this.tableData.limit = val;
			this.getTaskList();
		},
		handleCurrentChange(val) {
			this.tableData.page = val;
			this.getTaskList();
		},

		getTaskList() {
			let t = {
				taskName: this.search.text,
				status: this.tableType == "first" ? "" : this.tableType,
				startTime: this.search.time[0],
				endTime: this.search.time[1],
				page: this.tableData.page,
				limit: this.tableData.limit,
			};
			this.$store.dispatch("research/getTaskList", t).then((res) => {
				if (res.data.code == 200) {
					this.tableData.data = res.data.data.list;
					this.tableData.totalCount = res.data.data.total;
				}
			});
		},
		// 详情
		getInfo(id, n) {
			let d = {
				id,
			};
			this.$store.dispatch("research/getInfo", id).then((res) => {
				this.$router.push({ name: "addTask", query: { edit: n } });
			});
		},
		// 删除
		delTask(id) {
			console.log(id);
			let d = {
				id,
			};
			this.$store.dispatch("research/delTask", d).then((res) => {
				if (res.data.code == 200) {
					this.$message.success(res.data.msg);
					this.getTaskList();
				}
			});
		},
	},
	created() {},
	mounted() {
		this.getTaskList();
	},
};
</script>

<style lang="scss" scoped>
.page {
	.search {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		// flex-wrap: wrap;
		align-content: space-between;
	}
	.tableBox {
		margin-top: 20px;
	}
}
</style>
