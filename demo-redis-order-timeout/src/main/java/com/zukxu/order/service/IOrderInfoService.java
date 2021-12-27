package com.zukxu.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zukxu.order.entity.OrderInfo;

import java.util.List;

/**
 * <p>
 * $END
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/25 0025 16:02
 */
public interface IOrderInfoService extends IService<OrderInfo> {

	/**
	 * 查询用户订单列表信息
	 *
	 * @param status  订单状态
	 * @param name    商品名称
	 * @param current 当前页
	 * @param size    分页
	 * @return list
	 */
	IPage<OrderInfo> pageInfo(Integer status, String name, Integer current, Integer size);

	/**
	 * 生成订单
	 *
	 * @param orderInfoList 订单信息
	 * @return list
	 * @throws Exception 异常
	 */
	List<OrderInfo> addOrderInfo(List<OrderInfo> orderInfoList) throws Exception;

	/**
	 * 修改订单状态
	 *
	 * @param orderInfo 订单
	 * @return 是否修改成功
	 */
	boolean updOrderStatus(OrderInfo orderInfo);

}
