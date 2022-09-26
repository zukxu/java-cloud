package com.zukxu.demoliteflow.constant;

/**
 * <p>
 * CSVC 工单常量池
 * </p>
 *
 * @author xupu
 * @since 2022-03-10 10:25
 */
public interface CSVC {

    /*请求报文配置*/
    //@formatter:off
    String VERSION = "1.0.1";
    String DOMAIN = "CSVC";
    String SIGN_METHOD_MD5 = "md5";
    //@formatter:on

    /**
     * 按省（位置）代码路由
     */
    String ROUTE_TYPE_LOCATION = "00";

    /**
     * 按手机号码路由
     */
    String ROUTE_TYPE_PHONE = "01";

    /**
     * 贵州851
     */
    String ROUTE_VALUE_SN = "851";

    /**
     * 集团998
     */
    String ROUTE_VALUE_JT = "998";

    String BUS_TYPE = "CSVC";

    /**
     * 服务使用方partyId
     */
    String USER_PARTY_ID = "COP8510";

    /*响应报文配置*/

    /**
     * 服务提供方partyid
     */
    String PROVIDE_PARTY_ID = "COP8510";

    /*开发配置*/
    //@formatter:off
    /**
     * 1-发起方是集团 2-发起方是省分
     */
    String UNIT_JT = "1";
    String UNIT_SN = "2";

    /**
     * 0057 集团 851 贵州
     */
    String CODE_JT = "0057";
    String CODE_SN = "851";
    //@formatter:on

    /*附件配置项*/
    //@formatter:off
    /**
     * 附件命名前缀
     * 工作流附件 前缀
     */
    String WORK_FILE_PREFIX = "GZLSEND_1001_851_";
    /**
     * 对账附件前缀
     */
    String RECONCILE_FILE_PREFIX = "GZLSEND_1002_851_";
    /**
     * 站店听音公告信息
     */
    String ZDTY_FILE_PREFIX = "GZLSEND_1003_851_";
    /**
     * 附件回传校验文件
     */
    String UPLOAD_CHECK_FILE_PREFIX = "GZLSEND_1005_851_";
    //@formatter:on
}
