package com.zukxu.idem.web;

import com.zukxu.common.config.RedisUtils;
import com.zukxu.idem.entity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/13 0013 10:36
 */
@RestController
@RequestMapping("/order")
public class OrderInfoController {
    @Autowired
    RedisUtils redisUtil;

    @PostMapping(value = "addOrderInfo")
    public String addOrderInfo(OrderInfo orderInfo) throws Exception {
        //获取redis判断当前用户是不是重复提交订单
        String userId = "1";
        Optional<Object> o = Optional.ofNullable(redisUtil.get("ORDER:" + userId + ":" + orderInfo.getCommodityId()));
        if (o.isPresent()) {
            return "请勿重复提交订单";
        }
        redisUtil.set("ORDER:" + userId + ":" + orderInfo.getCommodityId(), userId, 60 * 10, TimeUnit.SECONDS);
        return "success";
    }
}
