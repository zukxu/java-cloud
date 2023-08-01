package com.zukxu.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.zukxu.excel.service.ReportService;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * <p>
 * Excel 读取监听类
 * 有特殊业务需求的都可以在这个类里面自定义实现，比如边读边写库啊，数据过滤和处理等等
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/6 0006 16:26
 */
@Slf4j
public class Sn5GListener implements ReadListener<Map> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<Map> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private Set<String> headField = new LinkedHashSet<>();
    private Map<String, Object> reqMap = new HashMap<>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private ReportService reportService;

    public Sn5GListener() {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
        reportService = new ReportService();
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param reportService
     */
    public Sn5GListener(ReportService reportService, Map<String, Object> map) {
        this.reportService = reportService;
        this.reqMap = map;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(Map data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        headMap.keySet().forEach(key -> {
            headField.add(headMap.get(key).getStringValue());
        });
        headField.remove("编码");
        headField.remove("地州名称");
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        reportService.saveData(headField, reqMap, Collections.singletonList(cachedDataList));
        log.info("存储数据库成功！");
    }
}
