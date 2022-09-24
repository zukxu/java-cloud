package com.zukxu.demoliteflow.constant;

import lombok.Getter;

/**
 * <p>
 * 基础服务平台响应代码
 * </p>
 *
 * @author xupu
 * @since 2022-09-25 00:36:10
 */
@Getter
public enum RespCode {
    SUCCESS("00000", "  Success"),
    //1****	授权业务异常
    ERROR11002("11002", "request method must be post"),
    ERROR12001("12001", "appId is empty"),
    ERROR13005("13005", "authorize expire"),
    ERROR13006("13006", "authorize invalidate."),
    ERROR13007("13007", "appSecret is invalidate"),
    ERROR13008("13008", "Privilege exception"),
    ERROR13009("13009", "Can not find the app_id"),
    ERROR13011("13011", "Can not find the user."),
    ERROR13012("13012", "Can not find the provide"),
    ERROR14001("14001", "SERVER ERROR"),
    //2****	调用或基础服务平台本身异常返回
    ERROR20000("20000", "基础服务系统内部错误"),
    ERROR20002("20002", "Repeat call"),
    ERROR20003("20003", "Http Action Not Allowed"),
    ERROR20004("20004", "OSN Service Unavailable"),
    ERROR20005("20005", "HSN Service Error"),
    ERROR20007("20007", "Request Content Type Error"),
    ERROR20008("20008", "通知交易拥赛，请联系管理员"),
    ERROR20009("20009", "基础服务平台内部转发异常，请联系管理员"),
    ERROR20010("20010", "基础服务平台内部网管转发异常，请联系管理员"),
    ERROR20101("20101", "Missing Required Args"),
    ERROR20104("20104", "Mapper REQ to object Error"),
    ERROR20105("20105", "Format req stream to string Error"),
    ERROR20106("20106", "Req service name invalidate"),
    ERROR20207("20207", "Invalid Signature Method"),
    ERROR20209("20209", "Invalid Timestamp"),
    ERROR20210("20210", "Timestamp Timeout"),
    ERROR20211("20211", "Response parameter invalidate"),
    ERROR20212("20212", "Response is 0。"),
    ERROR20213("20213", "Response format error."),
    ERROR20214("20214", "Response parameter missing;"),
    ERROR20215("20215", "请求报文超出设定大小（最大报文为2M）"),
    ERROR20216("20216", "应答报文超出设定大小（最大报文为2M）"),
    ERROR20303("20303", "User no permission"),
    ERROR20305("20305", "Invalid Token"),
    ERROR20306("20306", "Token no permission"),
    ERROR20307("20307", "User Partner call limited"),
    ERROR20308("20308", "User call service limited"),
    ERROR20310("20310", "Call service limited"),
    ERROR20311("20311", "Expired Token"),
    ERROR20312("20312", "Provider bad status"),
    ERROR20313("20313", "Service bad status"),
    ERROR20314("20314", "User bad status"),
    ERROR20315("20315", "User not registered service."),
    ERROR20316("20316", "Provider no service."),
    ERROR20401("20401", "Missing service Instance"),
    ERROR20402("20402", "transido is empty"),
    ERROR20403("20403", "Haprty Service Unavailable."),
    ERROR20404("20404", "Haprty read time out"),
    ERROR20406("20406", "Can not find hparty destination"),
    ERROR20407("20407", "User and provider is the same"),
    ERROR20408("20408", "User Password encryption failed"),
    ERROR20409("20409", "Provider Password encryption failed"),
    ERROR20501("20501", "Route type invalidate."),
    ERROR20502("20502", "Route value invalidate."),
    ERROR20503("20503", "Request header parameter  invalidate"),
    ERROR20504("20504", "Response base parameter  invalidate"),
    ERROR20505("20505", "Request body parameter  invalidate"),
    ERROR20506("20506", "Response body parameter  invalidate"),
    ERROR21000("21000", "  请求数据内容和数据库配置不符"),
    //3****	落地业务错误
    ERROR30000("30000", "业务处理失败");

    private String code;

    private String desc;

    RespCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
