export default [{

        name: "Definition",
        path: "/flowable/definition",
        component: () =>
            import ("@/views/process/flowable/definition/index.vue"),
        meta: {
            parentName: "definitionMenu"
        }
    },
    {
        name: "Form",
        path: "/flowable/form",
        component: () =>
            import ("@/views/process/flowable/task/form/index.vue"),
        meta: {
            parentName: "definitionMenu"
        }
    },
    {
        name: "Process",
        path: "/task/process",
        hidden: false,
        component: () =>
            import ("@/views/process/flowable/task/process/index.vue"),
        meta: {
            parentName: "definitionMenu"
        }
    },
    {
        name: "Todo",
        path: "/task/todo",
        hidden: false,
        component: () =>
            import ("@/views/process/flowable/task/todo/index.vue"),
        meta: {
            parentName: "definitionMenu"
        }
    },
    {
        name: "Finished",
        path: "/task/finished",
        hidden: false,
        component: () =>
            import ("@/views/process/flowable/task/finished/index.vue"),
        meta: {
            parentName: "definitionMenu"
        }
    },
    {
        path: "/flowable/definition/model/",
        component: () =>
            import ("@/views/process/flowable/definition/model.vue"),
        name: "Model",
        meta: {
            parentName: "definitionMenu"
        }
    },
    {
        path: "/flowable/task/record/index",
        component: () =>
            import ("@/views/process/flowable/task/record/index.vue"),
        name: "Record",
        meta: {
            parentName: "definitionMenu"
        }
    },
    {
      path: "/tool/build/index",
      component: () => import("@/views/process/tool/build/index.vue"),
      name: "FormBuild",
      meta: { parentName: "definitionMenu" }
    }

];
