package com.zukxu.design.structural.decorator.demo1.service;

import java.util.HashMap;

/**
 * <p>
 * WorkFlowService
 * </p>
 *
 * @author xupu
 * @since 2022-03-31 11:47
 */
public interface WorkFlowService {

    /**
     * 派单
     *
     * @param param
     * @return
     */
    String dispatchCSS(HashMap<String, Object> param);

}
