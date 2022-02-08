// 统一管理api接口



// let api = "http://rap2api.taobao.org/app/mock/295046"; //开发
var api = "/ceva-mutirent"; //生产
if (process.env.NODE_ENV === "development") {
    api = "/api/ceva-mutirent" //开发
}
export const baseApi = {
    user: { //用户
        login: api + "/doLogin", //登录
        captcha: api + "/captcha", //验证码
        initInfo: api + "/initInfo", //初始化登录信息
        logout: api + "/logout", //退出
        goToJTDY: api + "/sso/goToJTDY", //跳转大音平台
    },
    common: { //公用接口
        getAreaCode: api + "/common/getAreaCode", //各区县编码
    },
    authority: { //权限管理
        menuTree: api + "/sys/menu/tree", //获取菜单树
        addMenu: api + "/sys/menu", //新增菜单
        menuDetails: api + "/sys/menu", //菜单项请
        deleteMenu: api + "/sys/menu", //删除菜单
        editMenu: api + "/sys/menu", //新增菜单
        userList: api + "/sys/user/list", //用户管理列表
        addUser: api + "/sys/user", //新增用户
        userDetails: api + "/sys/user", //根据id查询用户详情
        editUser: api + "/sys/user", //更新用户
        deleteUser: api + "/sys/user", //删除用户
        treeSelectDept: api + "/sys/dept/treeSelect", //获取部门下拉列表
        optionSelectRole: api + "/sys/role/optionSelect", //获取角色下拉列表
        optionSelectPost: api + "/sys/post/optionSelect", //获取岗位下拉列表
        roleList: api + "/sys/role/list", //获取角色列表
        addRole: api + "/sys/role", //新增角色
        roleDetails: api + "/sys/role", //获取角色详情
        editRole: api + "/sys/role", //编辑角色
        deleteRole: api + "/sys/role", //删除角色
        deptList: api + "/sys/dept/list", //获取部门列表
        addDept: api + "/sys/dept", //新增部门
        deptDetails: api + "/sys/dept", //根据部门编号获取详细信息
        editDept: api + "/sys/dept", //修改部门
        deleteDept: api + "/sys/dept", //删除部门
        postList: api + "/sys/post/list", //获取岗位列表
        addPost: api + "/sys/post", //新增岗位
        postDetails: api + "/sys/post", //根据岗位编号获取详细信息
        editPost: api + "/sys/post", //修改岗位
        deletePost: api + "/sys/post", //删除岗位
        logList: api + "/monitor/log/list", //日志列表
        // cleanLog: api + "/monitor/opLog/clean" //清除日志

    },
    workOrder: { //工单管理
        DispatchCSS: api + "/sync/gz/DispatchCSS", // 工单派发
        ReplyCSS: api + "/sync/gz/ReplyCSS", //工单回复
        QueryCSS: api + "/sync/workflow/QueryDetail", // 工单查询
        QueryCSSList: api + "/sync/workflow/QueryCSSList", // 工单查询
        upload: api + "/sync/gz/upload", //文件上传
        identyDetail: api + "/sync/workflow/typeList", //工单类型查询
        UrgeCSS: api + "/sync/gz/UrgeCSS", //工单催办
        StatementCSS: api + "/sync/gz/StatementCSS", //归档
        WithdrawCSS: api + "/sync/gz/WithdrawCSS", //撤单x
        ReprocessCSS: api + "/sync/gz/ReprocessCSS", //再处理
        QueryCSSTimeLine: api + "/sync/gz/QueryCSS", //流程
        QueryCSSCount: api + "/sync/workflow/QueryCSSCount", //工单统计
    },
    research: { //调研管理
        customer: {
            customerList: api + "/customer/list", //客户群模型分页列表查询
            getCustomerScale: api + "/customer/getCustomerScale", //获取客群规模
            getSave: api + "/customer/save", //客群保存
            customerDelete: api + "/customer/delete", //删除客群
            getListInfo: api + "/customer/info", //客群数据查询
            getUpdate: api + "/customer/update", //客群数据更新
        },
        areaController: {
            getCity: api + "/area/getCity", //获取地市信息
            getCounty: api + "/area/getCounty", //获取区县信息
        },
        labelManage: {
            getCategoryedLabel: api + '/label/getCategoryedLabel', //获取多级分类的标签
            getFinalCategoryedLabel: api + '/label/getFinalCategoryedLabel', //获取最终分类的标签
            getLabelCategory: api + '/label/getLabelCategory', //获取标签分类
            getLabelList: api + '/label/list', //展示标签列表
            updateHotLabel: api + '/label/updateHotLabel' //更新热门标签列表
        },
        taskController: {
            getTaskList: api + '/researchTask/list', // 调研任务分页查询
            getSave: api + '/researchTask/save', // 调研任务保存
            getPushEcope: api + '/researchTask/pushEcop', // 推送ECOP
            getInfo: api + '/researchTask/info', // 调研任务查看
            delTask: api + '/researchTask/delete', // 调研任务删除
            getUpdate: api + '/researchTask/update', // 调研任务修改
        },

        quota: {
            getQuotaList: api + '/quota/list', //配额管理列表
            quotaInterface: api + '/quota', //增删改
        },
        testNumbe: {
            getListNum: api + '/testNumber/getListNum', //查询测试号码获取列表
            addNum: api + '/testNumber/addNum', //增加测试号码
            deleteNum: api + '/testNumber/deleteNum', //删除测试号码
            editorNum: api + '/testNumber/editorNum', //修改删除号码
        }
    },
    indicator: { //指标看板

        SmartHomeServices: api + '/endToEnd/SQS/SmartHomeServices', //服务质量标准-智慧家庭业务
        SmartHomeServices_hb: api + '/endToEnd/SQS/SmartHomeServices_hb', ////服务质量标准-智慧家庭业务环比
        ShuttleService: api + '/endToEnd/SQS/ShuttleService', //服务质量标准-专线服务
        ShuttleService_hb: api + '/endToEnd/SQS/ShuttleService_hb', //服务质量标准-专线服务环比
        EnterpriseBroadband: api + '/endToEnd/SQS/EnterpriseBroadband', //服务质量标准-企业宽带
        EnterpriseBroadband_hb: api + '/endToEnd/SQS/EnterpriseBroadband_hb', //服务质量标准-企业宽带环比
        ServiceProcessQuality: api + '/endToEnd/ServiceProcessQuality', //端到端服务过程质量
        ServiceProcessQuality_hb: api + '/endToEnd/ServiceProcessQuality_hb', //端到端服务过程质量环比
        MarketingActivities_mon: api + '/endToEnd/MarketingActivities_mon', //业务营销过程_月份
        RightsInterestsDimension: api + '/endToEnd/RightsInterestsDimension', //权益维度
        MarketingActivitiesDimension: api + '/endToEnd/MarketingActivitiesDimension', //营销活动维度
        BusinessTypeDimension: api + '/endToEnd/BusinessTypeDimension', //业务类型维度


        oneType: api + '/NewendToEnd/oneType', //二级名称分类
        threeType: api + '/NewendToEnd/threeType', //三级名称分类
        targetInfo: api + '/NewendToEnd/targetInfo', //根据三级编码获取指标值
        lastMonTarget: api + '/NewendToEnd/lastMonTarget', //根据指标编码获取最后一个月的指标值和环比值
        cityHb: api + '/NewendToEnd/cityHb', //根据指标编码获取各地环比
        twelveMonTarget: api + '/NewendToEnd/twelveMonTarget', //根据指标编码获取前12个月的指标值
    },
    report: { //报表
        //满意度
        downloadTemplate: api + '/report/common/template', //报表模板下载
        getReportConfig: api + '/report/common/getReportConfig', //满意度配置相关
        export: api + '/report/common/export', //报表通用导出
        importReport: api + '/report/common/import', //报表通用导入
        getTableList: api + '/satisfaction_jt/list', //满意度报表
        getTableZqList: api + '/satisfaction_jt_zq/list', //集团政企满意度
        getTableCityMYDList: api + '/satisfaction_sn/getCityMYD', //省内地市满意度
        getTableProvinceMYDList: api + '/satisfaction_sn/getProvinceMYD', //省内全省满意度
        getTableProvinceSNList: api + '/satisfaction_sn_zq/list', //省内政企满意度
        collectSn: api + '/collect/sn', //省内汇总


        //投诉
        complaintHandling: api + '/complaints/complaintHandling', //投诉处理表格
        getCityId: api + '/complaints/cityId' //获取区域编码
    },
    otherApi: {
        testVaultgoto: 'http://135.10.139.140:18080/vaultgoto_Test/vaultAction.do?method=initNew'
    }

};
