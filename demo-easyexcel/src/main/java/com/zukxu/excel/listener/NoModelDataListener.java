package com.zukxu.excel.listener;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.bonc.satisfaction.service.ReportService;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2021-12-24 9:44
 */
@Slf4j
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {
    /**
     * 每隔100条存储数据库,然后清理list,方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 解析出来的头部
     */
    private Map<Integer, Set<String>> head2FieldMap = new HashMap<>();
    /**
     * 解析出的数据
     */
    private List<Map<Integer, String>> rowDataList = new ArrayList<>(BATCH_COUNT);
    /**
     * 业务service
     */
    private ReportService reportService;
    private String entityClassName;

    public NoModelDataListener() {}

    public NoModelDataListener(ReportService reportService, String entityClassName) {
        this.reportService = reportService;
        this.entityClassName = entityClassName;
    }

    @Override
    public void invoke(Map<Integer, String> rowData, AnalysisContext context) {
        //log.info("解析到一条数据:{}", JSON.toJSONString(rowData));
        rowDataList.add(rowData);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (rowDataList.size() >= BATCH_COUNT) {
            reportService.saveData(head2FieldMap, rowDataList,entityClassName);
            // 存储完成清理 list
            rowDataList = new ArrayList<>(BATCH_COUNT);
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        //log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        Set<String> set = new LinkedHashSet<>();
        //第一行为数据表名称，不解析
        if (context.readRowHolder().getRowIndex() != 0) {
            //移除第一列的表头
            headMap.remove(0);
            Collection<String> values = headMap.values().stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());
            set.addAll(values);
            head2FieldMap.put(context.readRowHolder().getRowIndex(), set);
            //log.info("存储头数据:{}", JSON.toJSONString(head2FieldMap));
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //所有行都解析完成
        reportService.saveData(head2FieldMap, rowDataList,entityClassName);
        log.info("所有数据解析完成！");
    }


    /**
     * 某行的数据解析失败
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        System.err.println("解析失败，继续解析下一行: " + exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException dataConvertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常", dataConvertException.getRowIndex(), dataConvertException.getColumnIndex());
        }
    }


}