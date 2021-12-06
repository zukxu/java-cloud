import Vue from 'vue'
import App from './App.vue'
import router from './router'
import '@/assets/styles/index.scss' // global css
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import {addDateRange, download, handleTree, parseTime, resetForm, selectDictLabel} from "@/utils/common";
// 自定义表格工具扩展
import Pagination from "@/components/Pagination";
import RightToolbar from "@/components/RightToolbar";
// 全局方法挂载
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree

Vue.prototype.msgSuccess = function (msg) {
  this.$message({showClose: true, message: msg, type: "success"});
}

Vue.prototype.msgError = function (msg) {
  this.$message({showClose: true, message: msg, type: "error"});
}

Vue.prototype.msgInfo = function (msg) {
  this.$message.info(msg);
}

// 全局组件挂载
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.use(Element)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
