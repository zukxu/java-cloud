package com.zukxu.alipay.web;

import com.alipay.api.AlipayApiException;
import com.zukxu.alipay.entity.AlipayEntity;
import com.zukxu.alipay.entity.enums.Payment;
import com.zukxu.alipay.constant.RuntimeConstant;
import com.zukxu.alipay.service.IOrderService;
import com.zukxu.alipay.util.AlipayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/12 0012 16:09
 */
@Controller
@RequestMapping("/pay")
@Slf4j
public class AlipayController {

	@Autowired
	private IOrderService orderService;
	@Autowired
	AlipayUtil alipayUtil;

	@PostMapping(value = "alipay")
	public String alipay(String outTradeNo, Model model) throws AlipayApiException {
		//模拟通过商户订单号获取订单生成支付记录
		AlipayEntity alipayEntity = orderService.findByOutTradeNo(outTradeNo);
		if (alipayEntity == null) {
			model.addAttribute("message", "订单编号不存在！");
			return RuntimeConstant.RUNTIME_ERROR;
		}
		//根据支付记录判断支付方式
		if (alipayEntity.getPayment() == Payment.Alipay) {
			//表示是支付宝支付
			String payView = null;
			//判断是否是手机浏览器访问

			try {
				payView = alipayUtil.generatePayView(alipayEntity.getOutTradeNo(), alipayEntity.getTotalAmount(), alipayEntity
						.getSubject(), alipayEntity.getBody());
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
			model.addAttribute("payView", payView);
			return "pay/alipay";
		}
		//其他的支付方式进行追加即可
		return RuntimeConstant.RUNTIME_ERROR;
	}

	/**
	 * 支付宝异步通知接口
	 *
	 * @return
	 */
	@PostMapping("/notify")
	@ResponseBody
	public String alipayNotify(HttpServletRequest request,Model model) {
		//检查异步通知的签名是否合法
		log.info("进行支付宝异步通知验签");

		if (!alipayUtil.isValid(request)) {
			log.error("验签失败");
			return "fail";
		}
		log.info("验签通过");
		//进行相关的参数校验
		Map<String, String[]> requestParams = request.getParameterMap();
		//订单号
		String outTradeNo = request.getParameter("out_trade_no");
		//支付宝交易号
		String tradeNo = request.getParameter("trade_no");
		//支付金额
		String totalAmount = request.getParameter("total_amount");
		//支付状态
		String tradeStatus = request.getParameter("trade_status");
		// 对参数进行验证
		if (StringUtils.isEmpty(outTradeNo)) {
			log.error("订单编号为空");
			return "fail";
		}
		if (StringUtils.isEmpty(tradeNo)) {
			log.error("支付宝交易号为空");
			return "fail";
		}
		if (StringUtils.isEmpty(totalAmount)) {
			log.error("支付金额为空");
			return "fail";
		}
		if (StringUtils.isEmpty(tradeStatus)) {
			log.error("支付状态为空");
			return "fail";
		}
		//直接对比金额是否正常
		Optional<AlipayEntity> optional = Optional.ofNullable(orderService.findByOutTradeNo(outTradeNo));
		if (!optional.isPresent()) {
			log.error("订单编号不存在：【{}】", outTradeNo);
			return "fail";
		} else {
			AlipayEntity alipayEntity = optional.get();
			if (alipayEntity.getTotalAmount().compareTo(new BigDecimal(totalAmount)) < 0) {
				log.error("支付金额相同【{}】", totalAmount);
				return "fail";
			}
			//验签通过，进行相关业务操作
			log.info("业务操作执行开始");
			log.info("更新订单状态");
			alipayEntity.setStatus(AlipayEntity.pay_status_paid);
			//模拟更新数据库的数据
			log.info("业务操作执行完毕");
		}

		return "success";
	}
}

