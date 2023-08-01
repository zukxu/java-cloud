package com.zukxu.gencode.common.constant;

/**
 * <p>
 * 代码生成通用常量
 * </p>
 *
 * @author xupu
 * @since 2021/12/2 20:36
 */
public interface GenConstants {

    /** 单表（增删改查） */
    String TPL_CRUD = "crud";

    /** 树表（增删改查） */
    String TPL_TREE = "tree";

    /** 主子表（增删改查） */
    String TPL_SUB = "sub";

    /** 树编码字段 */
    String TREE_CODE = "treeCode";

    /** 树父编码字段 */
    String TREE_PARENT_CODE = "treeParentCode";

    /** 树名称字段 */
    String TREE_NAME = "treeName";

    /** 上级菜单ID字段 */
    String PARENT_MENU_ID = "parentMenuId";

    /** 上级菜单名称字段 */
    String PARENT_MENU_NAME = "parentMenuName";

    /** 数据库字符串类型 */
    String[] COLUMNTYPE_STR = {"char", "varchar", "nvarchar", "varchar2"};

    /** 数据库文本类型 */
    String[] COLUMNTYPE_TEXT = {"tinytext", "text", "mediumtext", "longtext"};

    /** 数据库时间类型 */
    String[] COLUMNTYPE_TIME = {"datetime", "time", "date", "timestamp"};

    /** 数据库数字类型 */
    String[] COLUMNTYPE_NUMBER = {"tinyint", "smallint", "mediumint", "int", "number", "integer", "bit", "bigint", "float", "double", "decimal"};

    /** 页面不需要编辑字段 */
    String[] COLUMNNAME_NOT_EDIT = {"id", "create_by", "create_time", "del_flag"};

    /** 页面不需要显示的列表字段 */
    String[] COLUMNNAME_NOT_LIST = {"id", "create_by", "create_time", "del_flag", "update_by", "update_time"};

    /** 页面不需要查询字段 */
    String[] COLUMNNAME_NOT_QUERY = {"id", "create_by", "create_time", "del_flag", "update_by", "update_time", "remark"};

    /** Entity基类字段 */
    String[] BASE_ENTITY = {"createBy", "createTime", "updateBy", "updateTime", "remark"};

    /** Tree基类字段 */
    String[] TREE_ENTITY = {"parentName", "parentId", "orderNum", "ancestors", "children"};

    /** 文本框 */
    String HTML_INPUT = "input";

    /** 文本域 */
    String HTML_TEXTAREA = "textarea";

    /** 下拉框 */
    String HTML_SELECT = "select";

    /** 单选框 */
    String HTML_RADIO = "radio";

    /** 复选框 */
    String HTML_CHECKBOX = "checkbox";

    /** 日期控件 */
    String HTML_DATETIME = "datetime";

    /** 图片上传控件 */
    String HTML_IMAGE_UPLOAD = "imageUpload";

    /** 文件上传控件 */
    String HTML_FILE_UPLOAD = "fileUpload";

    /** 富文本控件 */
    String HTML_EDITOR = "editor";

    /** 字符串类型 */
    String TYPE_STRING = "String";

    /** 整型 */
    String TYPE_INTEGER = "Integer";

    /** 长整型 */
    String TYPE_LONG = "Long";

    /** 浮点型 */
    String TYPE_DOUBLE = "Double";

    /**
     * 高精度计算类型
     */
    String TYPE_BIGDECIMAL = "BigDecimal";

    /**
     * 时间类型
     */
    String TYPE_DATE = "LocalDateTime";

    /**
     * 模糊查询
     */
    String QUERY_LIKE = "LIKE";

    /**
     * 需要
     */
    String REQUIRE = "1";
    /**
     * 生成代码类型:: 系统目录
     */
    String GEN_TYPE_PROJECT = "1";
    /**
     * 生成代码类型:: 自定义目录
     */
    String GEN_TYPE_CUSTOM = "2";

}
