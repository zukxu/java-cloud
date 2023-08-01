package com.zukxu.alipay.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.zukxu.alipay.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * 支付宝支付工具类
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/18 0018 14:56
 */
@Component
public class AlipayUtil {

    @Autowired
    AlipayConfig alipayConfig;

    public String generatePayView(String outTradeNo, BigDecimal totalAmount, String subject, String body) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(),
                                                            alipayConfig.getAppId(),
                                                            alipayConfig.getAppPrivateKey(),
                                                            "json",
                                                            alipayConfig.getCharset(),
                                                            alipayConfig.getAlipayPublicKey(),
                                                            alipayConfig.getSignType()
        );
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        HashMap<String, String> map = new HashMap<>();
        //商户订单号outTradeNo，商户网站订单系统中唯一订单号，必填
        map.put("out_trade_no", outTradeNo);
        //付款金额totalAmount，必填
        map.put("total_amount", totalAmount.toString());
        //订单名称subject，必填
        map.put("subject", subject);
        //商品描述body，可空
        map.put("body", body);
        //支付编码
        map.put("product_code", alipayConfig.getProdCode());
        //页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
        // 支付编码
        // alipayRequest.setProdCode(alipayConfig.getProdCode());
        //封装参数
        alipayRequest.setBizContent(JSONObject.toJSONString(map));

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        return result;
    }

    /**
     * 支付宝异步通知验签
     *
     * @param request
     * @return
     */
    public boolean isValid(HttpServletRequest request) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        try {
            return AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return false;
    }
}
