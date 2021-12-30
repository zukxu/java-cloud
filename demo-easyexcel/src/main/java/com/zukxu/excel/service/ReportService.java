package com.zukxu.excel.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zukxu.excel.mapper.ReportMapper;
import com.zukxu.excel.model.SatisfactionSn;
import com.zukxu.excel.model.satisfaction.Sn5g;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void saveData(Set<String> headField, Map<String, Object> reqMap, List<Sn5g> dataList) {
        log.info("==============================================");
        log.info(JSON.toJSONString(headField));
        List<SatisfactionSn> g5List = new ArrayList<>();
        SatisfactionSn sn = SatisfactionSn.builder()
                .year((String) reqMap.get("year"))
                .period((String) reqMap.get("period"))
                .orgType((String) reqMap.get("orgType"))
                .build();
        int i = 0;
        for (String kpiName : headField) {
            sn.setId(UUID.fastUUID().toString());
            sn.setKpiName(kpiName);
            for (Sn5g sn5g : dataList) {
                String id = reportMapper.getIdByName(sn5g.getVar2());
                if (StrUtil.isEmpty(id)) {
                    sn.setCityId(sn5g.getVar1());
                } else {
                    sn.setCityId(id);
                }
                sn.setCityName(sn5g.getVar2());
                sn.setYdSatisfaction(Double.valueOf(sn5g.getVar3()));
                g5List.add(sn);
            }
        }
        log.info(JSON.toJSONString(g5List));
    }
}
