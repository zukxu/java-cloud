import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export const constRoutes = [
    /*{
        path: '/redirect',
        component: Layout,
        hidden: true,
        children: [
            {
                path: '/redirect/:path(.*)',
                component: (resolve) => require(['@/views/redirect'], resolve)
            }
        ]
    },  {
        path: '',
        component: Layout,
        redirect: 'index',
        children: [
            {
                path: 'index',
                component: (resolve) => require(['@/views/index'], resolve),
                name: 'Index',
                meta: { title: '首页', icon: 'dashboard', affix: true }
            }
        ]
    },*/
    {
        path: '/tool/gen',
        component: (resolve) => require(['@/views/tool/gen/index'], resolve),
        name: 'GenEdit',
        // meta: {title: '修改生成配置', activeMenu: '/tool/gen'}
        meta: {title: '代码生成列表'}
    },
    {
        path: '/gen/edit/:tableId(\\d+)',
        component: (resolve) => require(['@/views/tool/gen/editTable'], resolve),
        name: 'GenEdit',
        meta: {title: '修改生成配置'}
    }
]
export default new Router({
    mode: "hash",
    scrollBehavior: () => ({y: 0}),
    routes: constRoutes
})