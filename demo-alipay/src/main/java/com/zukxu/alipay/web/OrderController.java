package com.zukxu.alipay.web;

import com.zukxu.alipay.entity.AlipayEntity;
import com.zukxu.alipay.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/12 0012 16:09
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	IOrderService orderService;

	@GetMapping("/list")
	public String orderList(Model model) {
		String outTradeNo = "ZUK" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		AlipayEntity alipayEntity = orderService.findByOutTradeNo(outTradeNo);
		model.addAttribute("alipayBean", alipayEntity);
		return "order/list";
	}
}

