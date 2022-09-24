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
    //@formatter:on
    /*响应报文配置*/

    /**
     * 服务提供方partyid
     */
    String PROVIDE_PARTY_ID = "COP8510";


}
