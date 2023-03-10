package com.zukxu.design.structural.decorator.demo1;


import com.zukxu.design.structural.decorator.demo1.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 集团调用我们的省分大音平台
 */
@RestController
@RequestMapping({ "/sync/CSVC" })
public class WorkFlowCSVCController {

    @Autowired
    @Qualifier("SNService")
    private WorkFlowService snService;

    @Autowired
    @Qualifier("ThirdService")
    private WorkFlowService thirdService;

    @Autowired
    @Qualifier("CSVCService")
    private WorkFlowService csvcService;


    /**
     * 集团调用省分 派单入库省分
     *
     * @param param map
     */
    @PostMapping({ "/DispatchCSS" })
    public String DispatchCSS(@RequestBody HashMap<String, Object> param) {
        String s = csvcService.dispatchCSS(param);
        snService.dispatchCSS(param);
        ThirdDecorator thirdDecorator = new ThirdDecorator(csvcService);
        MoreThirdDecorator moreThirdDecorator = new MoreThirdDecorator(thirdService);
        //thirdService.dispatchCSS(param);
        moreThirdDecorator.dispatchCSS(param);
        return s;
    }


}
