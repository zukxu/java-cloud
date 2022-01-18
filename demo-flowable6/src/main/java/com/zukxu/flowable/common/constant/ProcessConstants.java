package com.zukxu.flowable.common.constant;

/**
 * <p>
 * 流程常量信息
 * </p>
 *
 * @author xupu
 * @since 2021/12/14 20:01
 */
public interface ProcessConstants {

    /**
     * 动态数据
     */
    String DATA_TYPE = "dynamic";

    /**
     * 单个审批人
     */
    String USER_TYPE_ASSIGNEE = "assignee";


    /**
     * 候选人
     */
    String USER_TYPE_USERS = "candidateUsers";


    /**
     * 审批组
     */
    String USER_TYPE_GROUPS = "candidateGroups";

    /**
     * 单个审批人
     */
    String PROCESS_APPROVAL = "approval";

    /**
     * 会签人员
     */
    String PROCESS_MULTI_INSTANCE_USER = "userList";

    /**
     * nameapace
     */
    String NAMESPACE = "http://flowable.org/bpmn";

    /**
     * 会签节点
     */
    String PROCESS_MULTI_INSTANCE = "multiInstance";

    /**
     * 自定义属性 dataType
     */
    String PROCESS_CUSTOM_DATA_TYPE = "dataType";

    /**
     * 自定义属性 userType
     */
    String PROCESS_CUSTOM_USER_TYPE = "userType";

    /**
     * 初始化人员
     */
    String PROCESS_INITIATOR = "INITIATOR";


    /**
     * 流程跳过
     */
    String FLOWABLE_SKIP_EXPRESSION_ENABLED = "_FLOWABLE_SKIP_EXPRESSION_ENABLED";
    /**
     * 流程文件后缀
     */
    String BPMN_FILE_SUFFIX = ".bpmn";

    String[] BPMN_FILE_SUFFIX_ARR = {".bpmn", ".bpmn20.xml", ".xml"};
}
