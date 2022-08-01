package com.zukxu.demoliteflow.test.parser;

import com.yomahub.liteflow.parser.el.ClassXmlFlowELParser;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/7/28 14:59:03
 */
public class NacosXmlParser extends ClassXmlFlowELParser {

    @Override
    public String parseCustom() {
        System.out.println("进入自定义parser");
        String xmlContent = "";
        //这里需要自己扩展从自定义的地方获取配置
        return xmlContent;
    }

}
