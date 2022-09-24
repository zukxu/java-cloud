package com.zukxu.demoliteflow.model.csvc.request;

import cn.hutool.core.lang.UUID;
import com.zukxu.demoliteflow.constant.CSVC;
import com.zukxu.demoliteflow.utils.WFUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 请求公共参数
 */
@Data
@Accessors(chain = true)
public class RequestDto implements Serializable {

    private static final long serialVersionUID = -6212033577540330042L;

    /**
     * 业务内容
     */
    String content;

    /**
     * API服务编码
     * 参见业务分册，如分册中没有则不填写
     */
    String apiCode;

    /**
     * 组合服务名称，组合服务时填写
     */
    String servicePackName;

    /**
     * 时间戳（YYYYMMDDHHMMSS）
     */
    private String timeStamp;

    /**
     * 流水号
     */
    private String transIDO;

    /**
     * 业务流水号
     * 单原子服务取值与transIDO相同；
     */
    private String sessionID;

    /**
     * 数字签名
     * {@link com.zukxu.demoliteflow.utils.SignUtil}
     */
    private String sign;

    /**
     * 测试与正式数据标识
     * 正式是1，测试0
     * {@link com.zukxu.demoliteflow.enums.Env}
     */
    private String envFlag;

    /**
     * 平台令牌token 服务使用方时必填
     */
    private String accessToken;

    /**
     * 服务版本号，业务使用的服务版本号。详见业务分册服务属性
     */
    private String version = CSVC.VERSION;

    /**
     * 参数的加密方法选择，默认md5，可选值是：md5
     */
    private String signMethod = CSVC.SIGN_METHOD_MD5;

    /**
     * 服务使用方partyId 机构DOMAIN+机构交换节点
     */
    private String userPartyID = CSVC.USER_PARTY_ID;

    /**
     * 基础服务平台日切点，基础服务平台填写
     * 格式：yyyymmdd，清分对帐用
     * 可选
     */
    private String cutOffDay;

    /**
     * 服务提供方应用域代码  参见附录 domain编码表中DOMAIN列
     * CSVC
     */
    private String domain = CSVC.DOMAIN;

    /**
     * 00：按省（位置）代码路由;
     * 01: 按手机号码路由
     */
    private String routeType = CSVC.ROUTE_TYPE_LOCATION;

    /**
     * 路由关键值
     * 当RouteType为00时，取值为需要到达各省交换节点或业务平台接入的交换节点；如北京省BOSS为100；
     * 当RouteType为01时，取值为手机号
     * 贵州851 集团998
     */
    private String routeValue = CSVC.ROUTE_VALUE_JT;

    /**
     * 业务编码 参见附录 8.5 业务编码
     */
    private String busType = CSVC.BUS_TYPE;


    public static RequestDto buildRequestDTO(String envFlag) {
        RequestDto requestDto = new RequestDto();
        LocalDateTime now = LocalDateTime.now();
        String uuid = UUID.fastUUID().toString().replace("-", "");

        requestDto.setTimeStamp(WFUtil.timeFormat(now)).setCutOffDay(WFUtil.dateFormat(now))
                  //交易流水号
                  .setTransIDO(uuid).setSessionID(uuid).setAccessToken("").setEnvFlag(envFlag);
        return requestDto;
    }

}
