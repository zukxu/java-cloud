<template>
  <div id="model">
    <div id="backTo">
      <el-button
          type="primary"
          size="mini"
          @click="backTo"
      >返回
      </el-button>
    </div>
    <bpmn-modeler
        ref="refNode"
        :xml="xml"
        :users="users"
        :groups="groups"
        :categorys="categorys"
        :is-view="false"
        @save="save"
    />
  </div>
</template>

<script>
import {readXml, saveXml} from "@/api/flowable/definition"
import {bpmnListCategory, bpmnListUser, bpmnListGroups} from "@/api/flowable/bpmn"
import bpmnModeler from "workflow-bpmn-modeler";

export default {
  components: {
    bpmnModeler,
  },
  created() {
    const deployId = this.$route.query && this.$route.query.deployId;
    this.getBpmnDataList()
    //  查询流程xml
    if (deployId) {
      this.getModelDetail(deployId);
    }
  },
  data() {
    return {
      xml: "", // 后端查询到的xml
      users: [],
      groups: [],
      categorys: [],
    };
  },
  methods: {
    getBpmnDataList() {
      bpmnListCategory().then((res) => {
        this.categorys = res.data
      })
      bpmnListUser().then((res) => {
        this.users = res.data
        let initiator = {name: "流程发起人", id: "${INITIATOR}"}
        this.users.unshift(initiator)
        let approval = {name: "${approval}", id: "${approval}"}
        this.users.unshift(approval)
      })
      bpmnListGroups().then((res) => {
        this.groups = res.data
        // let approval = {name: "${approval}", id: "${approval}"}
        // this.groups.unshift(approval)
      })
    },
    getModelDetail(deployId) {
      // 发送请求，获取xml
      readXml(deployId).then((res) => {
        this.xml = res.data;
        this.modeler = res.data
      });
    },
    backTo() {
      console.log("返回")
      // 关闭当前标签页并返回上个页面
      this.$router.go(-1)
    },
    save(data) {
      const pda = {
        name: data.process.name,
        category: data.process.category,
        xml: data.xml
      }
      saveXml(pda).then((res) => {
        if (res.code == 200) {
          this.$message.success(res.msg)
        } else {
          this.$message.error(res.msg)
        }
        // 关闭当前标签页并返回上个页面
        this.$router.go(-1)
      });
    },
  },
};
</script>
<style scoped lang="scss">
#model {
  #backTo {
    float: right;
    margin-top: 10px;
    margin-left: -10px;
  }
}
</style>
