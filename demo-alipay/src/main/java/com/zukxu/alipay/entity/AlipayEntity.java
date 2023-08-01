package com.zukxu.alipay.entity;

import com.zukxu.alipay.entity.enums.Payment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/12 0012 16:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AlipayEntity extends BaseEntity {
    public static final int pay_status_waiting = 0;//待支付
    public static final int pay_status_paid = 1;//已支付
    public static final int getPay_status_waiting_refund = 2;//待退款
    public static final int pay_status_refunded = 3;//已退款
    public static final int pay_status_closed = 4;//已关闭
    private static final long serialVersionUID = -2473867012773410917L;
    private static final String PRODUCT_CODE = "FAST_INSTANT_TRADE_PAY";
    /**
     * 商户订单号，必填
     */
    private String outTradeNo;
    /**
     * 交易标题，必填
     */
    private String subject;
    /**
     * 商品描述，可空
     */
    private String body;
    /**
     * 订单总金额，单位为元，精确到小数点后两位
     * totalAmount=discountableAmount+unDiscountableAmount
     */
    private BigDecimal totalAmount;
    /**
     * 参与优惠计算的金额，单位为元，精确到小数点后两位
     */
    private BigDecimal discountableAmount;
    /**
     * 不参与优惠计算的金额，单位为元，精确到小数点后两位
     */
    private BigDecimal unDiscountableAmount;
    /**
     * 支付模式
     */
    private Payment payment;
    /**
     * 支付状态
     */
    private Integer status = pay_status_waiting;
    /**
     * 商品交易码
     */
    private String product_code = PRODUCT_CODE;
}
