<template>
	<div class="page">
		<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
			<el-form-item label="工单编号" prop="id">
				<el-input v-model="ruleForm.id"></el-input>
			</el-form-item>
			<el-form-item label="标题" prop="title">
				<el-input v-model="ruleForm.title"></el-input>
			</el-form-item>
			<el-form-item label="内容描述" prop="desc">
				<el-input v-model="ruleForm.desc" type="textarea"></el-input>
			</el-form-item>
			<el-form-item label="处理部门" prop="partment">
				<el-select v-model="ruleForm.partment" placeholder="请选择处理部门">
					<el-option label="区域一" value="shanghai"></el-option>
					<el-option label="区域二" value="beijing"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="处理人" prop="people">
				<el-select v-model="ruleForm.people" placeholder="请选择处理人">
					<el-option label="区域一" value="shanghai"></el-option>
					<el-option label="区域二" value="beijing"></el-option>
				</el-select>
			</el-form-item>

			<el-form-item>
				<el-button @click="resetForm('ruleForm')">重置</el-button>
				<div style="float: right;">
					<el-button type="success" @click="submitForm('ruleForm')">提交</el-button>
				</div>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
export default {
	components: {},
	props: {
		workOrderId: "", //工单号
	},
	data() {
		return {
			ruleForm: {
				id: "",
				title: "",
				partment: "",
				people: "",
				desc: "",
			},
			rules: {},
		};
	},
	computed: {},
	methods: {},
	created() {},
};
</script>

<style lang="scss" scoped>
.page {
}
</style>
