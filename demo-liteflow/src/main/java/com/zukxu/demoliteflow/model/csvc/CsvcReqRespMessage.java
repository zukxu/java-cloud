package com.zukxu.demoliteflow.model.csvc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *  ${END}
 * </p>
 *
 * @author xupu
 * @since 2022-09-26 11:51:24
 */

/**
 * CSVC 请求/响应报文
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "csvc_req_resp_message")
public class CsvcReqRespMessage {

    /**
     * 服务使用方交易流水号 UUID去掉其中的四个减号
     */
    @TableId(value = "transIDO", type = IdType.INPUT)
    private String transIDO;

    /**
     * API服务编码
     */
    @TableField(value = "api_code")
    private String apiCode;

    /**
     * 组合服务名称，组合服务时填写
     */
    @TableField(value = "service_pack_name")
    private String servicePackName;

    /**
     * 服务使用方处理时间 (YYYYMMDDHHMMSS)
     */
    @TableField(value = "time_stamp")
    private String timeStamp;

    /**
     * 业务流水号,单原子服务取值与transIDO相同,组合服务的各原子服务sessionID保持一致。如异步业务受理服务,业务受理和业务受理结果反馈两个服务的sessionID须保持一致
     */
    @TableField(value = "sessionID")
    private String sessionID;

    /**
     * 数字签名
     */
    @TableField(value = "sign")
    private String sign;

    /**
     * 测试与正式数据标识 0：测试数据 1：正式数据
     */
    @TableField(value = "env_flag")
    private String envFlag;

    /**
     * 平台令牌token,服务使用方时必填
     */
    @TableField(value = "access_token")
    private String accessToken;

    /**
     * 服务版本号
     */
    @TableField(value = "version")
    private String version;

    /**
     * 参数的加密方法选择，默认md5，可选值是：md5；
     */
    @TableField(value = "sign_method")
    private String signMethod;

    /**
     * 服务使用方partyid,基础服务平台填写,组成方式为附录domain表的“机构DOMAIN+机构交换节点
     */
    @TableField(value = "user_partyID")
    private String userPartyID;

    /**
     * 基础服务平台日切点 (yyyymmdd)
     */
    @TableField(value = "cut_off_day")
    private String cutOffDay;

    /**
     * 服务提供方应用域代码  参见附录 domain编码表中DOMAIN列
     */
    @TableField(value = "`domain`")
    private String domain;

    /**
     * 00：按省（位置）代码路由;01: 按手机号码路由
     */
    @TableField(value = "route_type")
    private String routeType;

    /**
     * 路由关键值 当RouteType为00时，取值为需要到达各省交换节点或业务平台接入的交换节点；如北京省BOSS为100；当RouteType为01时，取值为手机号
     */
    @TableField(value = "route_value")
    private String routeValue;

    /**
     * 业务编码
     */
    @TableField(value = "bus_type")
    private String busType;

    /**
     * 请求报文内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 请求时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 1:工单派发,2：工单回复,3：工单归档,4：工单撤单,5：工单查询,6：工单再处理,7：工单催办,8：工单通用接口,9：工单信息同步,10：测试工单同步删除
     */
    @TableField(value = "op_type")
    private Integer opType;

    /**
     * 服务提供方处理交易流水号 服务提供方唯一标识一个交易的流水号，系统内唯一 注：服务提供方返回报文时填写
     */
    @TableField(value = "transIDH")
    private String transIDH;

    /**
     * 服务提供方处理请求的时间 (YYYYMMDDHHMMSS) 注：服务提供方返回报文时填写
     */
    @TableField(value = "transIDHTime")
    private String transIDHTime;

    /**
     * 服务提供方partyid 组成方式为附录domain表的“机构DOMAIN+机构交换节点”
     */
    @TableField(value = "provide_partyID")
    private String providePartyID;

    /**
     * 一级应答/错误代码
     */
    @TableField(value = "rsp_code")
    private String rspCode;

    /**
     * 应答/错误描述,成功时为sucess
     */
    @TableField(value = "rsp_desc")
    private String rspDesc;

    /**
     * 业务响应结果 如果服务的应答参数为空时不返回此节点
     */
    @TableField(value = "`result`")
    private String result;

    /**
     * 响应时间
     */
    @TableField(value = "resp_time")
    private LocalDateTime respTime;

}