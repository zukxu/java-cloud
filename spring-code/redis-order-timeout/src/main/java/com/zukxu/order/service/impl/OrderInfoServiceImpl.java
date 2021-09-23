package com.zukxu.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zukxu.common.utils.redis.RedisUtils;
import com.zukxu.order.entity.OrderInfo;
import com.zukxu.order.mapper.OrderInfoMapper;
import com.zukxu.order.service.IOrderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * $END
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/25 0025 16:02
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {
	private final Logger log = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

	@Resource
	OrderInfoMapper orderInfoMapper;

	@Autowired
	RedisUtils redisUtils;


	@Override
	public IPage<OrderInfo> pageInfo(Integer type, String name, Integer current, Integer size) {
		LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
		Page<OrderInfo> page = new Page<>(current, size);
		return orderInfoMapper.selectPage(page, wrapper);
	}

	@Override
	public List<OrderInfo> addOrderInfo(List<OrderInfo> orderInfos) throws Exception {
		//生成组合订单支付ID
		String groupId = String.valueOf(IdWorker.getId());
		OrderInfo orderInfo = orderInfos.get(0);
		//生成订单
		//设置订单其余信息
		orderInfo.setGroupId(groupId);
		//生成订单
		orderInfoMapper.insert(orderInfo);
		log.info("orderId:{}", orderInfo.getId());
		//使用redis记录未支付超时时间: 30分钟未支付自动取消
		try {
			redisUtils.setEx("ORDER:" + orderInfo.getId(), orderInfo.getId(), 30, TimeUnit.SECONDS);
			log.info("存入redis成功:key：{}，value：{}", "ORDER:" + orderInfo.getId(), orderInfo.getId());
		} catch (Exception e) {
			log.error("存入redis失败，key：{}，value：{}", "ORDER:" + orderInfo.getId(), orderInfo.getId());
			e.printStackTrace();
		}
		return orderInfos;
	}

	@Override
	public boolean updOrderStatus(OrderInfo orderInfo) {
		return baseMapper.updateById(orderInfo) > 0;
	}
}

