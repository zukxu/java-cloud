import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store/index";
import axios from "axios";
import ElementUI from "element-ui";
import echarts from "echarts";
import "element-ui/lib/theme-chalk/index.css";
import "babel-polyfill";
import promise from "es6-promise";
import Fragment from "vue-fragment";

import Pagination from "@/components/Pagination";
import RightToolbar from "@/components/RightToolbar";
import VueAMap from "vue-amap";
import "./utils/directive.js";

promise.polyfill();
Vue.config.productionTip = false;
Vue.use(Fragment.Plugin);
Vue.use(ElementUI);
Vue.config.productionTip = false;
Vue.prototype.$axios = axios;
Vue.prototype.$echarts = echarts;
axios.defaults.withCredentials = true;


// 全局组件挂载
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)

Vue.use(VueAMap);
VueAMap.initAMapApiLoader({
  key: "c365db6b159687edff9ea558b4c72845",
  plugin: ["AMap.PolyEditor"],
});
if (process.env.VUE_APP_inclueMockApi) {
  require("../mock/webpackApi.js");
}
new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
