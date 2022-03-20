<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="分类名称" prop="categoryName">
        <el-input v-model="queryParams.name" placeholder="请输入分类名称" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="分类编码" prop="code">
        <el-input v-model="queryParams.key" placeholder="请输入分类编码" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" style="float: right"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="categoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="分类名称" align="center" prop="typeText"/>
      <el-table-column label="分类编码" align="center" prop="identyType"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类名称" prop="typeText">
          <el-input v-model="form.typeText" placeholder="请输入分类名称"/>
        </el-form-item>
        <el-form-item label="分类编码" prop="identyType">
          <el-input v-model="form.identyType" placeholder="请输入分类编码"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listCategory, getCategory, delCategory, addCategory, updateCategory} from "@/api/flowable/category.js";

export default {
  name: "Category",
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 【请填写功能名称】表格数据
      categoryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        key: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        typeText: [{required: true, message: "分类名称不能为空", trigger: "blur"}],
        identyType: [{required: true, message: "分类编码不能为空", trigger: "blur"}],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listCategory(this.queryParams).then((response) => {
        console.log(response);
        this.categoryList = response.data.data.list;
        this.total = response.data.data.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        categoryId: undefined,
        categoryName: undefined,
        code: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        remark: undefined,
        delFlag: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.resetForm("queryForm");
      this.queryParams.name = undefined;
      this.queryParams.key = undefined;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.identyType);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加流程分类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const categoryId = row.identyType || this.ids;
      getCategory(categoryId).then((response) => {
        console.log(response.data);
        this.loading = false;
        this.form = response.data.data;
        this.open = true;
        this.title = "修改流程分类";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.categoryId != null) {
            updateCategory(this.form)
                .then((response) => {
                  this.$message.success("修改成功");
                  this.open = false;
                  this.getList();
                })
                .finally(() => {
                  this.buttonLoading = false;
                });
          } else {
            addCategory(this.form)
                .then((response) => {
                  this.$message.success("新增成功");
                  this.open = false;
                  this.getList();
                })
                .finally(() => {
                  this.buttonLoading = false;
                });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      console.log(row);
      console.log(this.ids);
      const categoryIds = row.identyType || this.ids;
      this.$confirm("是否确认删除分类编码为:【" + categoryIds + "】的数据？")
          .then(() => {
            this.loading = true;
            return delCategory(categoryIds);
          })
          .then((res) => {
            this.loading = false;
            this.getList();
            if (res.data.code == 200) {
              this.$message.success("删除成功");
            } else {
              this.$message.error(res.data.msg);
            }
          })
          .finally(() => {
            this.loading = false;
          });
    },
    /** 导出按钮操作 */
    // handleExport() {
    //   this.download("system/category/export", {
    //     ...this.queryParams
    //   }, `category_${new Date().getTime()}.xlsx`);
    // }
  },
};
</script>
