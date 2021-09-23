package com.zukxu.alipay.service.impl;

import com.zukxu.alipay.entity.AlipayEntity;
import com.zukxu.alipay.entity.enums.Payment;
import com.zukxu.alipay.service.IOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/12 0012 16:08
 */
@Service
public class OrderServiceImpl implements IOrderService {

	@Override
	public AlipayEntity findByOutTradeNo(String outTradeNo) {
		//模拟从数据库获取数据
		AlipayEntity alipayEntity = new AlipayEntity();
		alipayEntity.setOutTradeNo(outTradeNo)
				  .setSubject("Java教程")
				  .setBody("Java-0基础开发教程")
				  .setTotalAmount(new BigDecimal("99.99"))
				  .setPayment(Payment.Alipay);
		return alipayEntity;
	}
}
