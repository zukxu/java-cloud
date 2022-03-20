<template>
  <div>
    <bpmn-modeler
      ref="refNode"
      :xml="xml"
      :users="users"
      :groups="groups"
      :categorys="categorys"
      :is-view="false"
      @save="save"
      @showXML="showXML"
      @dataType="dataType"
    />
    <!--在线查看xml-->
    <el-dialog :title="xmlTitle" :visible.sync="xmlOpen" width="60%" append-to-body>
      <div>
        <pre v-highlight>
           <code class="xml">
                {{ xmlContent }}
           </code>
        </pre>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {listCategoryList, readXml, roleList, saveXml, userList} from "@/api/flowable/definition.js";
import bpmnModeler from '@/components/Process/index'
import vkbeautify from 'vkbeautify'
import Hljs from 'highlight.js'
import 'highlight.js/styles/atom-one-dark.css'

export default {
  name: "Model",
  components: {
    bpmnModeler,
    vkbeautify
  },
  // 自定义指令
  directives: {
    highlight: (el) => {
      let blocks = el.querySelectorAll('pre code');
      blocks.forEach((block) => {
        Hljs.highlightBlock(block)
      })
    }
  },
  data() {
    return {
      xml: "", // 后端查询到的xml
      modeler: "",
      xmlOpen: false,
      xmlTitle: '',
      xmlContent: '',
      users: [],
      groups: [],
      categorys: [],

    };
  },
  created() {
    const deployId = this.$route.query && this.$route.query.deployId;
    //  查询流程xml
    if (deployId) {
      this.getModelDetail(deployId);
    }

    this.getDataList()
  },
  methods: {
    /** xml 文件 */
    getModelDetail(deployId) {
      // 发送请求，获取xml
      readXml(deployId).then(res => {
        this.xml = res.data.data;
        this.modeler = res.data.data
      })
    },
    /** 保存xml */
    save(data) {
      const params = {
        name: data.process.name,
        category: data.process.category,
        xml: data.xml
      }
      saveXml(params).then(res => {
        this.$message.success(res.data.msg)
        // 关闭当前标签页并返回上个页面
        this.$store.dispatch("tagsView/delView", this.$route);
        this.$router.go(-1)
      })
    },
    /** 指定流程办理人员列表 */
    getDataList() {
      userList().then(res => {
        console.log(res.data)
        this.users = res.data.data;
        let arr = {loginName: "流程发起人", id: "${INITIATOR}"}
        this.users.push(arr)
      });
      roleList().then(res => {
        this.groups = res.data.data;
      });
      listCategoryList().then(res=>{
        this.categorys=res.data.data
      })
    },
    /** 展示xml */
    showXML(data) {
      this.xmlTitle = 'xml查看';
      this.xmlOpen = true;
      this.xmlContent = vkbeautify.xml(data);
    },
    /** 获取数据类型 */
    dataType(data) {
      this.users = [];
      this.groups = [];
      if (data) {
        if (data.dataType === 'dynamic') {
          if (data.userType === 'assignee') {
            this.users = [{loginName: "${INITIATOR}", id: "${INITIATOR}"},
              {loginName: "#{approval}", id: "#{approval}"}
            ]
          } else if (data.userType === 'candidateUsers') {
            this.users = [{loginName: "#{approval}", id: "#{approval}"}]
          } else {
            this.groups = [{roleName: "#{approval}", id: "#{approval}"}]
          }
        } else {
          this.getDataList()
        }
      }
    }
  },
};
</script>
