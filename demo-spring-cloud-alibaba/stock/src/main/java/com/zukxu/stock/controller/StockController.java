
package com.zukxu.stock.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
public class StockController {

    @GetMapping("/reduce")
    public String reduce(@RequestParam Integer num) {
        return "库存扣减" + num;
    }

}
