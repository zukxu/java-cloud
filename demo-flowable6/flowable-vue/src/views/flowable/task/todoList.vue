<template>
  <div id="todoList">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
        >删除
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="todoList" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="任务编号" align="center" prop="taskId" :show-overflow-tooltip="true"/>
      <el-table-column label="流程名称" align="center" prop="processDefinitionName"/>
      <el-table-column label="任务节点" align="center" prop="taskName"/>
      <el-table-column label="流程发起人" align="center">
        <template slot-scope="scope">
          <label>{{ scope.row.starter }}
            <el-tag type="info" size="mini" v-if="scope.row.deptName">{{ scope.row.deptName }}</el-tag>
          </label>
        </template>
      </el-table-column>
      <el-table-column label="接收时间" align="center" prop="createTime" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="text"
              icon="el-icon-edit-outline"
              @click="handleProcess(scope.row)"
          >处理
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
  </div>
</template>

<script>
import {todoList} from "@/api/flowable/taskList";

export default {
  name: "todoList",
  components: {},
  data() {
    return {
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
      // 流程待办任务表格数据
      todoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        category: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询流程定义列表 */
    getList() {
      this.loading = true;
      todoList(this.queryParams).then((res) => {
        this.todoList = res.data.list;
        this.total = res.data.total;
        this.loading = false;
      });
    },
    // 跳转到处理页面
    handleProcess(row) {
      this.$router.push({
        path: '/flowable/record',
        query: {
          procInsId: row.processInstanceId,
          taskId: row.taskId,
          finished: false
        }
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        category: null,
        key: null,
        tenantId: null,
        deployTime: null,
        derivedFrom: null,
        derivedFromRoot: null,
        parentDeploymentId: null,
        engineVersion: null
      };
      this.resetForm("form");
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.taskId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      const that = this
      this.$confirm('是否确认删除', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return that.$store.dispatch("flowable/taskDelete", ids);
      }).then(() => {
        this.getList();
        this.$message.success("删除成功");
      })
    },
  }
}
</script>

<style scoped lang="scss">
#todoList {

}
</style>
