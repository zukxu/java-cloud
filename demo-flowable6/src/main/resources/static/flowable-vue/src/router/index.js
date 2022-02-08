import Vue from "vue";
import VueRouter from "vue-router";
import "nprogress/nprogress.css";
import processRouter from '@v/process/router.js' //流程子路由
import indicator from '@v/indicator/router.js' //指标看板
import authority from '@v/authorityManagement/router.js' //权限管理
import report from '@v/report/router.js' //报表
import research from '@v/researchManagement/router.js' //调研管理
import workOrder from '@v/workOrderManagement/router.js' //工单管理

Vue.use(VueRouter);

const routes = [{
        path: "/login",
        name: "login",
        component: () =>
            import ("@/views/login.vue"),

        meta: {
            alive: true,
        },
    },
    {
        path: "/loginForOther",
        name: "loginForOther",
        component: () =>
            import ("@/views/loginForOther.vue"),

        meta: {
            alive: true,
        },
    }, {
        path: "/",
        name: "Layout",
        component: () =>
            import ("@/layout/index.vue"),
        redirect: "login",
        children: [{
                path: "index",
                name: "index",
                component: () =>
                    import ("@/views/index.vue"),
            },

            ...workOrder, //工单管理
            ...processRouter, //流程定义
            ...indicator, //指标看板
            ...authority, //权限管理
            ...processRouter, //流程定义
            ...report, //报表
            ...research, //调研管理


        ],
    },



    {
        path: "*",
        redirect: "/404", // 404 page must be placed at the end !!!
        hidden: true,
    }, {
        path: "/404",
        component: () =>
            import ("@/views/404.vue"),
        meta: {
            title: "404",
            isLogin: false,
            alive: true,
        },
        hidden: true,
    },
    {
        path: "/error",
        component: () =>
            import ("@/views/error.vue"),
        meta: {
            title: "error",
            isLogin: false,
            alive: true,
        },
        hidden: true,
    }
];

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
    mode: "hash",
    base: process.env.BASE_URL,
    routes,
});

export default router;