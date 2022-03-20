<template>
  <div id="instanceList">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
        >新增流程
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="myProcessList" border>
      <el-table-column label="流程编号" align="center" prop="processInstanceId" :show-overflow-tooltip="true"/>
      <el-table-column label="流程名称" align="center" prop="processDefinitionName" :show-overflow-tooltip="true"/>
      <el-table-column label="流程类别" align="center" prop="category" width="100px"/>
      <el-table-column label="流程版本" align="center" width="80px">
        <template slot-scope="scope">
          <el-tag size="medium">v{{ scope.row.processDefinitionVersion }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="提交时间" align="center" prop="startTime" width="180"/>
      <el-table-column label="流程状态" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.endTime == null" size="mini">进行中</el-tag>
          <el-tag type="success" v-if="scope.row.endTime != null" size="mini">已完成</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="耗时" align="center" prop="duration" width="180"/>
      <el-table-column label="当前节点" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.taskName">{{ scope.row.taskName }}</el-tag>
          <label v-else>无</label>
        </template>
      </el-table-column>
      <el-table-column label="办理人" align="center">
        <template slot-scope="scope">
          <label v-if="scope.row.taskAssignee">{{ scope.row.taskAssignee }}
            <el-tag type="info" size="mini" v-if="scope.row.deptName">{{ scope.row.deptName }}</el-tag>
          </label>
          <label v-else-if="scope.row.candidate">{{ scope.row.candidate }}</label>
          <label v-else>无</label>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <span class="el-dropdown-link">
              更多操作<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-tickets" @click.native="handleFlowRecord(scope.row)">
                详情
              </el-dropdown-item>
              <el-dropdown-item icon="el-icon-circle-close" @click.native="handleStop(scope.row)"
                                v-if="scope.row.endTime == null">
                终止流程
              </el-dropdown-item>
              <el-dropdown-item icon="el-icon-delete" @click.native="handleDelete(scope.row)"
                                v-if="scope.row.endTime != null">
                删除
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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

    <!-- 发起流程 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body>
      <el-table v-loading="processLoading" fit :data="definitionList" border>
        <el-table-column label="流程名称" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button type="text" @click="handleReadImage(scope.row)">
              <span>{{ scope.row.name }}</span>
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="流程分类" align="center" prop="category"/>
        <el-table-column label="流程版本" align="center">
          <template slot-scope="scope">
            <el-tag size="medium">v{{ scope.row.version }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="300" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="text"
                icon="el-icon-edit-outline"
                @click="handleStartProcess(scope.row)"
            >发起流程
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
          v-show="processTotal>0"
          :total="processTotal"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="listDefinition"
      />
    </el-dialog>
    <!-- 流程图 -->
    <el-dialog :title="readImage.title" :visible.sync="readImage.open" width="70%" append-to-body>
      <flow :xml-data="readImage.xml"/>
    </el-dialog>
  </div>
</template>

<script>
import {deleteInstance, listProcess, startAndExecute, stopProcess} from "@/api/flowable/instance"
import {listDefinition, readXml} from "@/api/flowable/definition"
import Flow from "@/views/flowable/record/flow";

export default {
  name: "instanceList",
  data() {
    return {
      // 遮罩层
      loading: true,
      processLoading: true,
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
      processTotal: 0,
      // 我发起的流程列表数据
      myProcessList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      src: "",
      definitionList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        category: null,
        key: null,
        tenantId: null,
        deployTime: null,
        derivedFrom: null,
        derivedFromRoot: null,
        parentDeploymentId: null,
        engineVersion: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      readImage: {
        open: false,
        title: "",
        xml: ""
      },
    };
  },
  components: {
    Flow
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询流程定义列表 */
    getList() {
      this.loading = true;
      listProcess(this.queryParams).then((res) => {
        this.loading = false;
        this.myProcessList = res.data.list;
        this.total = res.data.total;
      }).catch(() => {
        this.loading = false;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true;
      this.title = "发起流程";
      this.listDefinition();
    },
    listDefinition() {
      listDefinition(this.queryParams).then((res) => {
        this.processLoading = false;
        this.definitionList = res.data.list;
        this.processTotal = res.data.total;
      });
    },
    /** 流程图查看 */
    handleReadImage(row) {
      this.readImage.title = "流程图";
      this.readImage.open = true;
      readXml(row.deploymentId).then((res) => {
        this.readImage.xml = res.data
      });
    },
    /**  发起流程申请 */
    handleStartProcess(row) {
      const pda = {
        definitionKey: row.key,
        tenantId: "",
        workId: "",
        variables: {}
      }
      startAndExecute(pda).then((res) => {
        this.open = false;
        this.$message.success(res.msg)
        this.getList()
      })
    },
    /** 详情 */
    handleFlowRecord(row) {
      this.$router.push({
        path: "/flowable/record",
        query: {
          procInsId: row.processInstanceId,
          taskId: row.taskId,
          finished: true
        }
      });
    },
    /**  取消流程申请 */
    handleStop(row) {
      stopProcess(row.processInstanceId).then((res) => {
        this.$message.success(res.msg);
        this.getList();
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const that = this;
      this.$confirm("是否确认删除流程实例", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        deleteInstance(row.processInstanceId).then((res) => {
          if (res.code !== 200) {
            that.$message.error("删除失败");
          } else {
            that.$message.success("删除成功");
          }
          that.getList();
        });
      });
    },
  }
}
</script>

<style scoped lang="scss">
#instanceList {

}
</style>
