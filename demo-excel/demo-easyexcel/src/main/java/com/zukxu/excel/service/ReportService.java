package com.zukxu.excel.service;

import com.alibaba.fastjson.JSON;
import com.zukxu.excel.mapper.ReportMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2021-12-29 19:41
 */
@Slf4j
@Service
public class ReportService {
    @Autowired
    private ReportMapper reportMapper;

    public void saveData(Map<Integer, String> head2FieldMap, List<Map<Integer, String>> rowDataList, String entityClassName) {
    }

    public void saveData(Set<String> headField, Map<String, Object> reqMap, List<Object> dataList) {
        log.info("==============================================");
        log.info(JSON.toJSONString(headField));
    }
}
