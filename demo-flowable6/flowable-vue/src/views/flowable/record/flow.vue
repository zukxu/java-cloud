<template>
  <div>
    <!--<bpmn-modeler ref="refNode" :xml="xmlData" :is-view="true" :taskList="taskData"/>-->
    <flow-view :xmlData="xmlData" :taskList="taskData"/>
  </div>
</template>
<script>
import bpmnModeler from "workflow-bpmn-modeler";
import FlowView from './flowView'
export default {
  name: "Flow",
  components: {
    bpmnModeler,
    FlowView
  },
  props: {
    xmlData: {
      type: String,
      default: "",
    },
    taskData: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {};
  },
  mounted() {
  },
};
</script>
