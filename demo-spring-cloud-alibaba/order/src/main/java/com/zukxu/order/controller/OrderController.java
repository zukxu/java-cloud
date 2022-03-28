package com.zukxu.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/add")
    public String add(@RequestParam Integer num) {
        String url = "http://127.0.0.1:18082/stock/reduce";
        System.out.println(restTemplate.getForObject(url+"?num="+num, String.class));
        return "下单成功:" + (num * 256) + "元";
    }

}
