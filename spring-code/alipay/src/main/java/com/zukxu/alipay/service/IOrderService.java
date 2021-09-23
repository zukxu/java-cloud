package com.zukxu.alipay.service;

import com.zukxu.alipay.entity.AlipayEntity;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/12 0012 16:07
 */
public interface IOrderService {

	AlipayEntity findByOutTradeNo(String outTradeNo);
}
