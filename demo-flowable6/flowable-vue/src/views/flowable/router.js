export default [

  {
    name: "definition",
    path: "/flowable/definition",
    component: () => import("@/views/flowable/definition/index"),
    meta: {parentName: "definitionMenu"}
  },
  {
    name: "Category",
    path: "/flowable/category",
    component: () =>
      import ("@/views/process/flowable/category/index.vue"),
    meta: {
      parentName: "definitionMenu"
    }
  },
  {
    name: "Model",
    path: "/flowable/definition/model",
    component: () => import("@/views/flowable/definition/model"),
    hidden: true,
    meta: {
      parentName: "definitionMenu"
    }
  },
  {
    name: "Form",
    path: "/flowable/form",
    component: () => import("@/views/flowable/form/index"),
    meta: {
      parentName: "definitionMenu"
    }
  },
  {
    path: "/flowable/form/build",
    component: () => import("@/views/flowable/form/build"),
    hidden: true,
    meta: {
      parentName: "definitionMenu"
    }
  },
  {
    name: "instanceList",
    path: "/flowable/instanceList",
    component: () => import("@/views/flowable/instance/instanceList"),
    meta: {
      parentName: "definitionMenu"
    }
  }, {
    name: "todoList",
    path: "/flowable/todoList",
    component: () => import("@/views/flowable/task/todoList"),
    meta: {
      parentName: "definitionMenu"
    }
  }, {
    name: "finishedList",
    path: "/flowable/finishedList",
    component: () => import("@/views/flowable/task/finishedList"),
    meta: {
      parentName: "definitionMenu"
    }
  },
  {
    path: "/flowable/record",
    component: () => import("@/views/flowable/record/index"),
    hidden: true,
    meta: {
      parentName: "definitionMenu"
    }
  }
];

