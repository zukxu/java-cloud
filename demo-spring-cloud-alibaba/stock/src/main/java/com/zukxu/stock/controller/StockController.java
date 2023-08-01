package com.zukxu.stock.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
public class StockController {

    @PostMapping("/reduce")
    public String reduce(@RequestParam(value = "num", required = false) int num) {
        return "库存扣减" + num;
    }

}
