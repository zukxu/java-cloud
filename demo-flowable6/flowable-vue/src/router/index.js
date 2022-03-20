import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/task'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: {title: 'Dashboard', icon: 'dashboard'}
    }]
  },
  {
    path: '/flowable',
    component: Layout,
    redirect: '/flowable/definition',
    name: 'flowable',
    meta: {title: 'flowable', icon: 'el-icon-s-help'},
    children: [
      {
        path: 'definition',
        name: 'definition',
        component: () => import('@/views/flowable/definition/index'),
        meta: {title: '流程定义', icon: 'table'}
      },
      {
        path: '/flowable/definition/model',
        component: () => import('@/views/flowable/definition/model'),
        hidden: true
      },
      {
        path: 'form',
        name: 'form',
        component: () => import('@/views/flowable/form/index'),
        meta: {title: '流程表单', icon: 'tree'}
      },
      {
        path: '/flowable/form/build',
        component: () => import('@/views/flowable/form/build'),
        hidden: true
      },
      {
        path: 'instanceList',
        name: 'instanceList',
        component: () => import('@/views/flowable/instance/instanceList'),
        meta: {title: '流程实例', icon: 'tree'}
      }, {
        path: 'todoList',
        name: 'todoList',
        component: () => import('@/views/flowable/task/todoList'),
        meta: {title: '待办工单', icon: 'tree'}
      }, {
        path: 'finishedList',
        name: 'finishedList',
        component: () => import('@/views/flowable/task/finishedList'),
        meta: {title: '已办工单', icon: 'tree'}
      },
      {
        path: '/flowable/record',
        component: () => import('@/views/flowable/record/index'),
        hidden: true
      },
    ]
  },
  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true},

//  自定义跳转路由
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
