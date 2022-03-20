<template>
  <div id="finishedList">
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

    <el-table v-loading="loading" :data="finishedList" border @selection-change="handleSelectionChange">
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
              icon="el-icon-tickets"
              @click="handleFlowRecord(scope.row)"
          >流转记录
          </el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-tickets"
              @click="handleRevoke(scope.row)"
          >撤回
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
import {finishedList} from "@/api/flowable/taskList";
import {revoke} from "@/api/flowable/task";

export default {
  name: "finishedList",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 流程待办任务表格数据
      finishedList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        category: null
      },
    }
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true
      finishedList(this.queryParams).then((res) => {
        console.log(res)
        this.loading = false
        this.total = res.data.total
        this.finishedList = res.data.list
      })
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
    /**=============================================================*/
    /** 流程流转记录 */
    handleFlowRecord(row) {
      this.$router.push({
        path: '/flowable/record',
        query: {
          procInsId: row.processInstanceId,
          taskId: row.taskId,
          finished: true
        }
      })
    },
    /** 撤回任务 */
    handleRevoke(row) {
      const params = {
        instanceId: row.procInsId
      }
      revoke(params).then(res => {
        this.$message.success(res.msg)
        this.getList();
      });
    },

    /**=============================================================*/
  }
}
</script>

<style scoped lang="scss">
#finishedList {

}
</style>
