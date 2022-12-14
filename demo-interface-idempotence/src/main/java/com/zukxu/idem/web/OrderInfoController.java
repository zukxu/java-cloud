package com.zukxu.idem.web;

import com.zukxu.idem.anno.IdempotenceRequired;
import com.zukxu.idem.entity.OrderInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @IdempotenceRequired
    @PostMapping(value = "addOrderInfo")
    public String addOrderInfo(OrderInfo orderInfo) {
        System.out.println(orderInfo);
        return "success";
    }

}
