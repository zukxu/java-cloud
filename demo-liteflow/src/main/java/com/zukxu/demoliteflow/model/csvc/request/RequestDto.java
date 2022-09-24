package com.zukxu.demoliteflow.model.csvc.request;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 请求公共参数
 */
@Data
@Accessors(chain = true)
public class RequestDto<T> implements Serializable {

    private static final long serialVersionUID = -6212033577540330042L;

    /**
     * 业务内容
     */
    T content;

    /**
     * 时间戳（YYYYMMDDHHMMSS）
     */
    private String timeStamp;

    /**
     * 流水号
     */
    private String transIDO;

    /**
     * 基础服务平台日切点，基础服务平台填写
     * 格式：yyyymmdd，清分对帐用
     * 可选
     */
    private String cutOffDay;

    /**
     * 业务流水号
     * 单原子服务取值与transIDO相同；
     */
    private String sessionID;

    /**
     * 数字签名
     * 对服务请求参数按照5.7.3章节算法生成
     */
    private String sign;

    private String signMethod = "md5";

    private String accessToken;

    /**
     * 测试与正式数据标识
     * 正式是1，测试0
     */
    private String envFlag = "1";

    /**
     * 服务版本号，业务使用的服务版本号。详见业务分册服务属性
     */
    private String version = "1.0.1";

    /**
     * 服务提供方应用域代码  参见附录 domain编码表中DOMAIN列
     * CSVC
     */
    private String domain = "CSVC";

    /**
     * 00：按省（位置）代码路由;
     * 01: 按手机号码路由
     */
    private String routeType = "00";

    /**
     * 路由关键值
     * 当RouteType为00时，取值为需要到达各省交换节点或业务平台接入的交换节点；如北京省BOSS为100；
     * 当RouteType为01时，取值为手机号
     * 贵州851 集团998
     */
    private String routeValue = "998";

    /**
     * 业务编码 参见附录 8.5 业务编码
     * 如物联网集中化业务的服务固定填写CTBS
     */
    private String busType = "CSVC";

    /**
     * 服务使用方partyId
     */
    private String userPartyID = "COP8510";

    public static <T> RequestDto<T> buildRequestDTO(String envFlag) {
        RequestDto<T> requestDto = new RequestDto<>();
        requestDto.setTimeStamp(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.PURE_DATETIME_PATTERN));
        requestDto.setCutOffDay(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.PURE_DATE_PATTERN));
        //交易流水号
        String uuid = UUID.fastUUID().toString().replace("-", "");
        requestDto.setTransIDO(uuid);
        requestDto.setSessionID(uuid);
        requestDto.setAccessToken("");
        requestDto.setEnvFlag(envFlag);
        return requestDto;
    }

}
