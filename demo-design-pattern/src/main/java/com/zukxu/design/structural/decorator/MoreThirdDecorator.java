package com.zukxu.design.structural.decorator;

import com.zukxu.design.structural.decorator.service.WorkFlowService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * <p>
 * 三方装饰类
 * </p>
 *
 * @author xupu
 * @since 2023/2/28 15:33:01
 */
@Slf4j
public class MoreThirdDecorator extends ThirdDecorator {

    public MoreThirdDecorator(WorkFlowService thirdService) {
        super(thirdService);
    }

    @Override
    public String dispatchCSS(HashMap<String, Object> param) {
        log.info("三方===>省分-派发装饰加强:{}", param);
        checkRecharge();
        return "三方===>省分-派发装饰加强";
    }

    private void checkRecharge() {
        System.out.print("RechargeDecorator 也在增强,看看这个货卡里面充了有多少,就来上网");
    }

}
