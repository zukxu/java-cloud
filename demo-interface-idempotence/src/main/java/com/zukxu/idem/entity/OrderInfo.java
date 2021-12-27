package com.zukxu.idem.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/13 0013 10:37
 */
@Data
@Accessors(chain = true)
public class OrderInfo {

    private static final long serialVersionUID = 1L;
    private String id;

    private String commodityId;

    private String commodityName;

    private BigDecimal commodityPrice;

    private Long count;
    /**
     * 0 待付款
     * 1 待发货
     * 2 待收货
     * 3 已完成
     * 4 订单取消
     * 5 退货中
     */
    private Integer orderStatus;

    private Long orderAmount;

    private String userId;
}