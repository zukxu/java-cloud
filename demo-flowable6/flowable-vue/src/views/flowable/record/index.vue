<script src="../../../api/flowable/test.js"></script>
<template>
  <div id="index">
    <el-tabs tab-position="top">
      <!--流程图-->
      <el-tab-pane label="流程图">
        <el-card class="box-card">
          <flow :xmlData="xmlData" :taskData="taskList"/>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="基础信息" v-if="!finished">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <!--<span class="el-icon-document">基础信息</span>-->
            <el-button style="float: right;" type="primary" @click="backTo">返回</el-button>
          </div>

          <!--流程处理表单模块-->
          <el-col :span="16" :offset="6" v-if="hasNodeForm">
            <div>
              <v-form-render :form-json="formJson" :form-data="formData" :option-data="optionData" ref="vFormRef"/>
            </div>
            <div id="comment">
              <el-form :model="taskForm" :rules="commentRules" ref="commentForm">
                <el-form-item prop="comment">
                  <div style="padding: 0 15px; font-size: 16px; ">审批信息</div>
                  <el-input style="width: 100%;margin-right: 34%" type="textarea" v-model="taskForm.comment"
                            placeholder="请输入审批信息"/>
                </el-form-item>
              </el-form>
              <br>
            </div>
            <div style="margin-left:10%;margin-bottom: 20px;font-size: 14px;">
              <el-button icon="el-icon-user-solid" type="primary" size="mini" @click="handleAssignee" v-if="isAssignee">
                指派
              </el-button>
              <el-button icon="el-icon-edit-outline" type="success" size="mini" @click="handleComplete"
                         :disabled="isAssignee&&!this.taskForm.variables.approval">审批
              </el-button>
              <!--<el-button icon="el-icon-user" type="info" size="mini" @click="handleDelegate">委派</el-button>-->
              <!--<el-button icon="el-icon-edit" type="info" size="mini" @click="handleTurn">转办</el-button>-->
              <!--<el-button icon="el-icon-edit-outline" type="info" size="mini" @click="handleClaim">签收</el-button>-->
              <el-button icon="el-icon-refresh-left" type="warning" size="mini" @click="handleReturn">退回</el-button>
              <el-button icon="el-icon-circle-close" type="danger" size="mini" @click="handleReject">驳回</el-button>
            </div>
          </el-col>
        </el-card>
      </el-tab-pane>

      <el-tab-pane label="流程流转记录">
        <!--流程流转记录-->
        <el-card class="box-card" v-if="flowRecordList">
          <el-col :span="16" :offset="4">
            <div class="block">
              <el-timeline>
                <el-timeline-item v-for="(item, index) in flowRecordList" :key="index" :icon="setIcon(item.endTime)"
                                  :color="setColor(item.endTime)">
                  <p style="font-weight: 700">{{ item.taskName }}</p>
                  <el-card :body-style="{ padding: '10px' }">
                    <label v-if="item.assigneeName" style="font-weight: normal;margin-right: 30px;"
                    >实际办理：
                      {{ item.assigneeName }}
                      <el-tag type="info" size="mini">{{ item.deptName }}</el-tag>
                    </label>
                    <label v-if="item.candidate" style="font-weight: normal;margin-right: 30px;">候选办理： {{
                        item.candidate
                      }}</label>
                    <label style="font-weight: normal">接收时间： </label><label
                      style="color:#8a909c;font-weight: normal">{{ item.createTime }}</label>
                    <label v-if="item.endTime" style="margin-left: 30px;font-weight: normal">办结时间： </label>
                    <label style="color:#8a909c;font-weight: normal">{{ item.endTime }}</label>
                    <label v-if="item.duration" style="margin-left: 30px;font-weight: normal">耗时： </label>
                    <label style="color:#8a909c;font-weight: normal">{{ item.duration }}</label>
                    <div style="margin: 20px 0" v-if="item.endTime&&item.formContent">
                      <el-button type="normal" @click="nodeFormDetails(item.formKey)">节点表单详情</el-button>
                    </div>
                    <div style="display:none" ref="node">
                      <v-form-render :form-json="JSON.parse(item.formContent)" :form-data="formData"
                                     :option-data="optionData" ref="vFormRefItem"/>
                    </div>
                    <p v-if="item.comment">
                      <el-tag type="success" v-if="item.comment.type === '1'"> {{ item.comment.comment }}</el-tag>
                      <el-tag type="warning" v-if="item.comment.type === '2'"> {{ item.comment.comment }}</el-tag>
                      <el-tag type="danger" v-if="item.comment.type === '3'"> {{ item.comment.comment }}</el-tag>
                    </p>
                  </el-card>
                </el-timeline-item>
              </el-timeline>
            </div>
          </el-col>
        </el-card>
      </el-tab-pane>
    </el-tabs>
    <!-- 指派表单-->
    <el-dialog :title="assigneeTitle" :visible.sync="assigneeOpen" width="80%" append-to-body>
      <el-row :gutter="20" class="dialog-div">
        <!--部门数据-->
        <el-col :span="8" :xs="24">
          <h6>部门列表</h6>
          <div class="head-container">
            <el-input
                placeholder="输入关键字进行过滤"
                v-model="deptFilterText">
            </el-input>
            <el-tree
                class="filter-tree"
                :props="defaultProps"
                :filter-node-method="filterNode"
                @node-click="handleNodeClick"
                :load="loadDeptNode"
                lazy
                accordion
                ref="tree">
            </el-tree>
          </div>
        </el-col>
        <!--待选人员-->
        <el-col :span="10" :xs="24">
          <h6>待选人员</h6>
          <el-table ref="selectingUserTable" :data="selectingUserList" border style="width: 100%"
                    @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="50" align="center"/>
            <el-table-column label="用户名" align="center" prop="user_name"/>
            <el-table-column label="部门" align="center" prop="dept_name"/>
          </el-table>
          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
                      :limit.sync="queryParams.pageSize" @pagination="getDeptTreeUser"/>
        </el-col>
        <!--已选人员-->
        <el-col :span="4" :xs="24">
          <h6>已选人员</h6>
          <el-tag v-for="tag in tempList" :key="tag.user_name" closable @close="handleClose(tag)"> {{ tag.user_name }}
            {{ tag.dept_name }}
          </el-tag>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
          <el-row :gutter="20">
            <el-button @click="formCancel">取 消</el-button>
            <el-button type="primary" @click="formComplete">确 定</el-button>
          </el-row>
        </span>
    </el-dialog>
  </div>
</template>

<script>
import Flow from "@/views/flowable/record/flow";
import {flowRecord} from "@/api/flowable/instance";
import {bpmnTreeDept} from "@/api/flowable/bpmn";
import {sysUserList} from "@/api/flowable/test";
import {complete} from "@/api/flowable/task";

export default {
  name: "index",
  components: {
    Flow
  },
  data() {
    return {
      commentRules: {
        comment: [
          {required: true, message: '请输入审批意见', trigger: 'blur'}
        ]
      },
      //初始化相关
      finished: false,
      //流程图相关
      xmlData: "",//流程xml
      taskList: [],//流程已完结节点
      flowRecordList: [],//流程流转记录
      //表单相关
      hasNodeForm: false,//节点是否存在表单
      formJson: {},
      formData: {},
      optionData: {},
      //审批相关
      taskForm: {
        workId: "",//工单编号，唯一
        type: "",//审批类型
        userId: "",//操作人ID
        procDefId: "", // 流程定义编号
        processInstanceId: "", // 流程实例编号
        taskId: "", // 流程任务编号
        comment: "", // 审批批注
        variables: {}//传递参数
      },

      //指派相关
      isAssignee: false,//是否存在指派按钮
      assigneeOpen: false,
      assigneeTitle: "指派",
      selectingUserList: [],//待选择用户
      selectedUser: undefined,//已选择人员
      userData: {},
      tempList: [],
      //部门相关
      deptFilterText: '',//筛选字段
      defaultProps: {
        children: 'children',
        label: 'deptName'
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        key: undefined,
      },
      total: 0,
      //指派部门树查询参数
      deptParams: {
        key: 0,
        name: undefined
      }
    }
  },
  created() {
    //初始化携带过来的参数
    this.taskForm.processInstanceId = this.$route.query && this.$route.query.procInsId;
    this.taskForm.taskId = this.$route.query && this.$route.query.taskId;
    this.finished = this.$route.query && this.$route.query.finished;
    // 初始化流转记录
    this.getFlowRecordList()
  },
  watch: {
    deptFilterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    /**
     * 获取流程流转记录
     */
    getFlowRecordList() {
      const params = {
        procId: this.taskForm.processInstanceId,
        taskId: this.taskForm.taskId,
        isFinished: this.finished
      }
      flowRecord(params).then((res) => {
        this.flowRecordList = res.data.flowRecordList
        this.xmlData = res.data.xmlData
        if (res.data.formJson) {
          this.hasNodeForm = true
          this.formJson = res.data.formJson;
          this.hasAssigneeBtn()
        }
        this.taskList = res.data.finishedTask
        this.$nextTick(() => {
          let vForm = this.$refs.vFormRefItem;
          for (var i = 0; i < vForm.length; i++) {
            vForm[i].disableForm()
          }
        })
      })
    },
    /**
     * 是否显示挂载表单
     * @param key
     */
    nodeFormDetails(key) {
      let i = 0
      this.flowRecordList.forEach(function (item, index) {
        if (item.formKey === key) {
          i = index
        }
      })
      let node = this.$refs.node[i];

      if (node.style.display === "none") {
        node.style.display = "block";
      } else {
        node.style.display = "none";
      }
    },
    /**
     * 是否显示指派按钮
     */
    hasAssigneeBtn() {
      let arr = this.formJson.widgetList[0].widgetList;
      if (arr) {
        let t = arr[arr.length - 1]
        this.isAssignee = (t.options.name === "button_assignee")
      }
    },
    /**===================================*/
    /**
     *
     *初始化指派相关的部门数据信息
     */
    loadDeptNode(node, resolve) {
      if (node.level === 0) {
        this.deptParams.key = '0'
      } else {
        this.deptParams.key = node.data.deptId
      }
      bpmnTreeDept(this.deptParams).then((res) => {
        return resolve(res.data)
      }).catch(err => {
        setTimeout(() => {
          const data = [];
          resolve(data);
        }, 500);
      })
    },
    // 部门树节点单击事件
    handleNodeClick(data) {
      this.queryParams.key = data.deptId;
      this.getDeptTreeUser();
    },
    getDeptTreeUser() {
      sysUserList(this.queryParams).then((res) => {
        this.selectingUserList = res.data.list
      })
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.deptName.indexOf(value) !== -1;
    },

    /**===================================*/

    /**===================================*/
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.userData = selection;
      this.handlerMultiList();
      const val = this.tempList.map((item) => item.id);
      this.handleVariables(val)
    },
    /**
     * 处理重复数据
     */
    handlerMultiList() {
      var _arr = [];
      for (var i = 0; i < this.userData.length; i++) {
        _arr.push(this.userData[i]);
      }
      for (var i = 0; i < this.tempList.length; i++) {
        var flag = true;
        for (var j = 0; j < this.userData.length; j++) {
          if (this.tempList[i] === this.userData[j]) {
            flag = false;
            break;
          }
        }
        if (flag) {
          _arr.push(this.tempList[i]);
        }
      }
      this.tempList = _arr;
    },
    // 关闭标签,切换表格选择状态
    handleClose(tag) {
      console.log(tag)
      this.userData.splice(this.userData.indexOf(tag), 1);
      this.$refs.selectingUserTable.toggleRowSelection(tag);
      this.tempList = this.userData
      console.log(this.tempList)
    },
    /** 指派人员流程变量赋值 */
    handleVariables(val) {
      if (val instanceof Array) {
        this.taskForm.variables = {
          approval: val.join(","),
        };
      } else {
        this.taskForm.variables = {
          approval: val,
        };
      }
    },
    /**===================================*/

    //取消审批
    formCancel() {
      this.assigneeOpen = false
      this.$refs.selectingUserTable.clearSelection();
      this.tempList = [];
    },
    /** 审批任务 */
    formComplete() {
      this.assigneeOpen = false
    },
    /**===================================*/

    /**
     * 审批
     */
    handleComplete() {
      this.$refs['commentForm'].validate((valid) => {
        if (valid) {
          this.$refs.vFormRef.getFormData().then(formData => {
            Object.assign(this.taskForm.variables, formData)
          })
          complete(this.taskForm).then((res) => {
            this.$message.success(res.msg)
            this.backTo()
          })
        } else {
          return false;
        }
      });
    },
    /**
     * 指派人员
     */
    handleAssignee() {
      this.assigneeOpen = true
    },
    /**
     * 委派
     */
    handleDelegate() {

    },
    /**
     * 转办
     */
    handleTurn() {

    },
    /**
     * 签收
     */
    handleClaim() {
    },
    /**
     * 退回
     */
    handleReturn() {
    },
    /**
     * 驳回
     */
    handleReject() {
    },

    backTo() {
      // 关闭当前标签页并返回上个页面
      this.$router.go(-1);
    },

    //  内部私有方法
    setColor(val) {
      if (val) {
        return "#2bc418";
      } else {
        return "#b3bdbb";
      }
    },
    setIcon(val) {
      if (val) {
        return "el-icon-check";
      } else {
        return "el-icon-time";
      }
    },
  }
}
</script>

<style scoped lang="scss">
#index {
  #comment {
    margin: 20px 0;
  }
}

.el-dialog__wrapper {
  height: 78vh;
  overflow: auto;
}
</style>
