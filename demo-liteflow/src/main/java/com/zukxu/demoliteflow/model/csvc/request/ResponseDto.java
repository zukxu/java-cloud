package com.zukxu.demoliteflow.model.csvc.request;

import com.bonc.flowable.constant.CSVCConstant;
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

    private String transIDO;//必选	同服务使用方交易流水号

    private String transIDH;//可选	服务提供方处理交易流水号

    private String transIDHTime;//服务提供方唯一标识一个交易的流水号，系统内唯一

    private String cutOffDay;//注：服务提供方返回报文时填写。

    private String providePartyID;//可选	服务提供方处理请求的时间YYYYMMDDHHMMSS

    private Response response;//注：服务提供方返回报文时填写

    private String result;//必选	基础服务平台日切点

    public ResponseDto() {}

    public ResponseDto(String cutOffDay, String transIDO, String providePartyId) {
        this.cutOffDay = cutOffDay;
        this.transIDO = transIDO;
        this.providePartyID = providePartyId;
    }

    @Data
    public class Response implements Serializable {

        private String rspCode;

        private String rspDesc;

        public Response() {
            this.rspCode = CSVCConstant.JT_SUCCESS_CODE;
            this.rspDesc = CSVCConstant.JT_SUCCESS_MSG;
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
