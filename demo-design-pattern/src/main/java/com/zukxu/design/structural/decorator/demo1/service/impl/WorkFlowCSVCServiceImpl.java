package com.zukxu.design.structural.decorator.demo1.service.impl;

import com.zukxu.design.structural.decorator.demo1.service.WorkFlowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 * WorkFlowCSVCServiceImpl
 * </p>
 *
 * @author xupu
 * @since 2022-03-31 11:47
 */
@Slf4j
@Service("CSVCService")
@RequiredArgsConstructor
public class WorkFlowCSVCServiceImpl implements WorkFlowService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String dispatchCSS(HashMap<String, Object> param) {
        log.info("集团===>省分-派发:{}", param);
        return "集团===>省分-派发";
    }

}

