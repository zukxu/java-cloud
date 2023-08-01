package com.zukxu.demoliteflow.model.csvc.request;

import com.zukxu.demoliteflow.constant.CSVC;
import com.zukxu.demoliteflow.enums.RespCode;
import com.zukxu.demoliteflow.model.csvc.CsvcReqRespMessage;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 集团调用返回结果集封装
 * </p>
 *
 * @author xupu
 * @since 2022-04-27 19:30
 */
@Data
@Accessors(chain = true)
public class ResponseDto implements Serializable {

    private static final long serialVersionUID = -6499800642221699957L;

    /**
     * 同服务使用方交易流水号
     */
    private String transIDO;

    /**
     * 服务提供方处理交易流水号
     */
    private String transIDH;

    /**
     * 服务提供方处理请求的时间 YYYYMMDDHHMMSS
     */
    private String transIDHTime;

    /**
     * 基础服务平台日切点 yyyymmdd
     */
    private String cutOffDay;

    /**
     * 服务提供方partyid 机构DOMAIN+机构交换节点
     */
    private String providePartyID;

    /**
     * 应答节点
     */
    private Response response;

    /**
     * 业务响应结果
     * result内容为业务参数对象；如果服务的应答参数为空时不返回此节点
     */
    private String result;

    public ResponseDto() {}

    public ResponseDto(String cutOffDay, String transIDO, String providePartyId) {
        this.cutOffDay = cutOffDay;
        this.transIDO = transIDO;
        this.providePartyID = providePartyId;
    }

    public static ResponseDto buildResponseDto(CsvcReqRespMessage reqRespMessage) {
        ResponseDto vo = new ResponseDto(reqRespMessage.getCutOffDay(), reqRespMessage.getTransIDO(), CSVC.PROVIDE_PARTY_ID);
        vo.setResponse(vo.new Response());
        return vo;
    }

    @Data
    public class Response implements Serializable {

        private String rspCode;

        private String rspDesc;

        public Response() {
            this.rspCode = RespCode.SUCCESS.getCode();
            this.rspDesc = RespCode.SUCCESS.getDesc();
        }

        public Response(RespCode respCode) {
            this.rspCode = respCode.getCode();
            this.rspDesc = respCode.getDesc();
        }

        public String getRspCode() {
            return rspCode;
        }

        public void setRspCode(String rspCode) {
            this.rspCode = rspCode;
        }

        public String getRspDesc() {
            return rspDesc;
        }

        public void setRspDesc(String rspDesc) {
            this.rspDesc = rspDesc;
        }

    }
}
