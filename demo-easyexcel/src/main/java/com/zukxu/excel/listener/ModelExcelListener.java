package com.zukxu.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Excel 读取监听类
 * 有特殊业务需求的都可以在这个类里面自定义实现，比如边读边写库啊，数据过滤和处理等等
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/6 0006 16:26
 */
public class ModelExcelListener extends AnalysisEventListener {
    private static final Logger log = LoggerFactory.getLogger(ModelExcelListener.class);
    private final List<Object> dataList = new ArrayList<>();

    /**
     * 通过 AnalysisContext 对象可以获取到当前sheet，当前行等数据信息
     *
     * @param data    数据
     * @param context 表格对象
     */
    @Override
    public void invoke(Object data, AnalysisContext context) {
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        log.info("读取到数据{}", data);
        dataList.add(data);
        //根据业务自行处理，可以写入数据库等等
    }

    /**
     * 数据解析完成回调
     *
     * @param context 表格对象
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成");
    }
}
