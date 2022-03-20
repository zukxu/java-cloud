<template>
  <div id="index">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
        >新增
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="formList">
      <!--<el-table-column label="表单主键" align="center" prop="formId"/>-->
      <el-table-column label="表单名称" align="center" prop="formName"/>
      <el-table-column label="表单key" align="center" prop="formKey"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleDetail(scope.row)"
          >详情
          </el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
    />

    <!--表单配置详情-->
    <el-dialog :title="formTitle" :visible.sync="formConfOpen" width="60%" append-to-body>
      <v-form-render :form-json="formJson" :form-data="formData" :option-data="optionData" ref="vFormRef"/>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </el-dialog>
  </div>
</template>

<script>
import {listForm, delForm} from "@/api/flowable/form"
export default {
  name: "index",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 流程表单表格数据
      formList: [],
      // 弹出层标题
      title: "",
      formConf: {}, // 默认表单数据
      formConfOpen: false,
      formTitle: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        formContent: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      formJson: {},
      formData: {},
      optionData: {}

    };
  },
  created() {
    this.getList();
  },
  methods: {
    submitForm() {
      this.$refs.vFormRef.getFormData().then(formData => {
        // Form Validation OK
        alert( JSON.stringify(formData) )
      }).catch(error => {
        // Form Validation failed
        this.$message.error(error)
      })
    },
    /** 查询流程表单列表 */
    getList() {
      this.loading = true;
      listForm(this.queryParams).then(res => {
        this.formList = res.data.list;
        this.total = res.data.total;
        this.loading = false;
      });
      this.optionData={
        'gender': [
          {label: '男', value: '1'},
          {label: '女', value: '2'}
        ],
        'paymentType': [
          {label: '支付宝', value: 'alipay'},
          {label: '微信', value: 'wechat'}
        ]
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push({path: "/flowable/form/build", query: {formId: null}});
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$router.push({path: "/flowable/form/build", query: {formId: row.formId}});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const formIds = row.formId;
      this.$confirm("是否确认删除流程表单:【" + row.formKey + "】", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delForm(formIds);
      }).then((res) => {
        if (res.code !== 200) {
          this.$message.error(res.data.msg);
        } else {
          this.$message.success("删除成功");
        }
        this.getList();
      });
    },
    /** 表单详情信息 */
    handleDetail(row) {
      this.formConfOpen = true;
      this.formTitle = "表单详情";
      this.formJson = JSON.parse(row.formContent);
    },
  }
}
</script>

<style scoped lang="scss">
#index {

}
</style>
