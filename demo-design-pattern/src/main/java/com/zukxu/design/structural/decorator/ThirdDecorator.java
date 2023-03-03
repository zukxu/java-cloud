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
public class ThirdDecorator implements WorkFlowService {

    private WorkFlowService thirdService;

    public ThirdDecorator(WorkFlowService thirdService) {
        this.thirdService = thirdService;
    }

    @Override
    public String dispatchCSS(HashMap<String, Object> param) {
        log.info("三方===>省分-派发装饰加强:{}", param);
        return "三方===>省分-派发装饰加强";
    }

}
