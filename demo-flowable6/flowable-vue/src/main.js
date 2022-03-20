import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import '@/styles/index.scss' // global css

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

//引入VForm库
import VForm from 'vform-builds'
import 'vform-builds/dist/VFormDesigner.css'

Vue.use(VForm)  //全局注册VForm(同时注册了v-form-designe、v-form-render等组件)
import '@/icons' // icon
import '@/permission' // permission control
import Pagination from "@/components/Pagination";
// 全局组件挂载
Vue.component('Pagination', Pagination)
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === 'production') {
  const {mockXHR} = require('../mock')
  mockXHR()
}

Vue.use(ElementUI)
Vue.use(VForm)  //全局注册VForm(同时注册了v-form-designe、v-form-render等组件)
// window.axios = axios

Vue.config.productionTip = false
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
