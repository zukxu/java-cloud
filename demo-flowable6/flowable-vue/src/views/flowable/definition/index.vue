<template>
  <div id="index">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="el-icon-upload"
            size="mini"
            @click="handleImport"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleLoadXml"
        >新增
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" fit :data="definitionList" border>
      <!--<el-table-column type="selection" width="55" align="center" />-->
      <!--<el-table-column label="流程编号" align="center" prop="deploymentId" :show-overflow-tooltip="true"/>-->
      <el-table-column label="流程标识" align="center" prop="key" :show-overflow-tooltip="true"/>
      <el-table-column label="流程分类" align="center" prop="category"/>
      <el-table-column label="部署名称" align="center" prop="resourceName"/>
      <el-table-column label="流程名称" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <el-button type="text" @click="handleReadImage(scope.row)">
            <span>{{ scope.row.name }}</span>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="流程版本" align="center">
        <template slot-scope="scope">
          <el-tag size="medium">v{{ scope.row.version }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.suspensionState === 1">激活</el-tag>
          <el-tag type="warning" v-if="scope.row.suspensionState === 2">挂起</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="部署时间" align="center" prop="deploymentTime" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown>
            <span class="el-dropdown-link">
              更多操作<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-edit-outline" @click.native="handleLoadXml(scope.row)">
                编辑
              </el-dropdown-item>
              <el-dropdown-item icon="el-icon-delete" @click.native="handleDelete(scope.row)">
                删除
              </el-dropdown-item>
              <el-dropdown-item icon="el-icon-video-pause" @click.native="handleUpdateSuspensionState(scope.row)"
                                v-if="scope.row.suspensionState === 1">
                挂起
              </el-dropdown-item>
              <el-dropdown-item icon="el-icon-video-play" @click.native="handleUpdateSuspensionState(scope.row)"
                                v-if="scope.row.suspensionState === 2">
                激活
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

    <!-- 流程图 -->
    <el-dialog :title="readImage.title" :visible.sync="readImage.open" width="70%" append-to-body>
      <flow :xml-data="readImage.xml"/>
    </el-dialog>

    <!-- bpmn20.xml导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
          ref="upload"
          :limit="1"
          accept=".xml,.bpmn,.bpmn20.xml"
          :headers="upload.headers"
          :action="
          upload.url + '?name=' + upload.name + '&category=' + upload.category
        "
          :disabled="upload.isUploading"
          :before-upload="beforeUpload"
          :on-progress="handleFileUploadProgress"
          :on-success="handleFileSuccess"
          :auto-upload="false"
          drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅支持["bpmn20.xml,".bpmn",".xml"]格式</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="cancelSubmit">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {listDefinition, delDefinition, updateState, definitionView, readXml} from "@/api/flowable/definition"
import {bpmnListCategory} from "@/api/flowable/bpmn";
import Flow from "@/views/flowable/record/flow";
export default {
  name: "index",
  data() {
    return {
      loading: true,
      // 总条数
      total: 0,
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
      // 流程定义表格数据
      definitionList: [],
      categoryList: [],
      // bpmn.xml 导入
      upload: {
        // 是否显示弹出层（xml导入）
        open: false,
        // 弹出层标题（xml导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        api: "",
        // 上传的地址
        url: "",
        headers: null
      },
      readImage: {
        open: false,
        title: "",
        xml: ""
      },
    }
  },
  created() {
    this.getList();
    this.getBpmnCategory();
  },
  components: {
    Flow,
  },
  methods: {
    /** 查询流程定义列表 */
    getList() {
      this.loading = true;
      listDefinition(this.queryParams).then((res) => {
        console.log(res)
        if (res.code == 200) {
          this.definitionList = res.data.list;
          this.total = res.data.total;
          this.loading = false;
        }
      });
    },
    getBpmnCategory() {
      bpmnListCategory().then((res) => {
        if (res.code == 200) {
          this.categoryList = res.data
        }
      })
    },
    /** 流程图查看 */
    handleReadImage(row) {
      this.readImage.title = "流程图";
      this.readImage.open = true;
      readXml(row.deploymentId).then((res) => {
        this.readImage.xml = res.data
      });
    },
    /** 跳转到流程设计页面 */
    handleLoadXml(row) {
      this.$router.push({path: "/flowable/definition/model", query: {deployId: row.deploymentId}});
    },
    initUpload() {
      let api = "/api/ceva-mutirent"; //线上环境
      if (process.env.NODE_ENV === "development") {
        api = "/api"; //开发
      }
      this.upload.api = api;
      this.upload.url = api + "/flowable/definition/import";
    },
    /** 导入bpmn.xml文件 */
    handleImport() {
      this.upload.title = "流程文件导入";
      this.upload.open = true;
      this.initUpload()
    },
    //上传前校验文件限制类型
    beforeUpload(file) {
      const fileSuffix = file.name.substring(file.name.lastIndexOf(".") + 1);
      const whiteList = ["xml", "bpmn", "bpmn20.xml"];
      if (whiteList.indexOf(fileSuffix) === -1) {
        this.$message.error("上传文件只能是xml, bpmn, bpmn20.xml格式");
        return false;
      }
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$message.success(response.msg);
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
      this.resetUpload();
    },
    cancelSubmit() {
      this.$refs.upload.clearFiles();
      this.upload.open = false;
    },
    // 表单重置
    resetUpload() {
      this.upload = {
        // 弹出层标题（xml导入）
        title: "",
        name: null,
        category: null,
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const params = {
        deployId: row.deploymentId
      };
      const that = this;
      this.$confirm("是否确认删除流程定义编号为\"" + params.deployId + "的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delDefinition(params);
      }).then(() => {
        this.getList();
        this.$message.success("删除成功");
      });
    },
    /** 挂起/激活流程 */
    handleUpdateSuspensionState(row) {
      let state = 1;
      if (row.suspensionState === 1) {
        state = 2;
      }
      // row.suspensionState = state;
      const params = {
        deploymentId: row.deploymentId,
        suspensionState: JSON.stringify(state)
      };
      updateState(params).then((res) => {
        if (res.code == 200) {
          this.$message.success(res.msg);
          this.getList();
        }
      });
    },
  }
}
</script>

<style scoped lang="scss">
#index {

}
</style>
