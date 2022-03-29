package com.zukxu.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/add")
    public String add(@RequestParam(value = "num", required = false) int num) {
        String url = "http://127.0.0.1:18082/stock/reduce";
        System.out.println(restTemplate.postForObject(url, Integer.class, String.class, num));
        return "下单成功:" + (num * 256) + "元";
    }

}
