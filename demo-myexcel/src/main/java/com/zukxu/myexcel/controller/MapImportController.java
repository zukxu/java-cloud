package com.zukxu.myexcel.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.liaochong.myexcel.core.ColumnSaxExcelReader;
import com.github.liaochong.myexcel.core.SaxExcelReader;
import com.zukxu.common.result.R;
import com.zukxu.myexcel.entity.BsAreaCounty;
import com.zukxu.myexcel.enums.OrgTypeEnum;
import com.zukxu.myexcel.enums.TenantEnum;
import com.zukxu.myexcel.mapper.CommonMapper;
import com.zukxu.myexcel.model.SatisfactionJt;
import com.zukxu.myexcel.model.SatisfactionJtZq;
import com.zukxu.myexcel.model.SatisfactionSn;
import com.zukxu.myexcel.model.SatisfactionSnZq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * <p>
 * 导入接口
 * </p>
 *
 * @author xupu
 * @since 2022-01-10 10:16
 */
@Slf4j
@RestController
@RequestMapping("/report/common")
public class MapImportController {
    @Autowired
    private CommonMapper commonMapper;

    /**
     * 导入excel
     *
     * @param file     file
     * @param tenantId JT/SN
     * @param orgType  phone手机 5g5G broadband宽带 jcr决策人 lxr联系人 zx专线 qk企宽 gyy公有云
     * @param year     2021
     * @param period   001 002 003 004
     * @return R
     * @throws IOException
     */
    @PostMapping("/imports")
    public R<?> importGeneral(MultipartFile file, String tenantId, String orgType, String year, String period) throws IOException {
        //定义标识
        String keys = getKeysStr(tenantId, orgType);
        //读取数据标题
        List<Map> titleMap;
        //定义标题行数
        int titleRowNum = 0;
        //定义地州列数
        int cityNameColumnNum = 1;
        if (keys.endsWith("2")) {
            titleRowNum = 2;
            cityNameColumnNum = 0;
        }
        int finalTitleRowNum = titleRowNum;
        titleMap = SaxExcelReader.of(Map.class)
                .rowFilter(r -> r.getRowNum() == finalTitleRowNum)
                .read(file.getInputStream());

        Map map = titleMap.get(0);
        List<String> titles = new ArrayList<>();
        for (Object key : map.keySet()) {
            titles.add(String.valueOf(map.get(key)));
        }
        //读取地市列
        // 读取为字符串
        List<String> strings = ColumnSaxExcelReader.columnNum(1)
                .rowFilter(row -> row.getRowNum() > 0)
                .readAsString(file.getInputStream());

        List<String> cityNames = ColumnSaxExcelReader.columnNum(cityNameColumnNum)
                .rowFilter(row -> row.getRowNum() > finalTitleRowNum)
                .readAsString(file.getInputStream());
        //根据地市名称转换为地市编码
        List<String> cityCode = translateToCode(cityNames, getAreaCodeList());

        //读取数据
        List<Map> result = SaxExcelReader.of(Map.class)
                .rowFilter(row->row.getRowNum()>finalTitleRowNum)
                .ignoreBlankRow() // 是否忽略空行，可选，默认不忽略
                .stopReadingOnBlankRow()//遇到空行停止读取
                .read(file.getInputStream());// 可接收inputStream

        List insertList = new ArrayList();

        for (int i = titleRowNum + 1; i < result.size(); i++) {
            Map tMap = result.get(i);
            //"0853",   编码
            //"安顺",    地州
            //"247",    样本量
            //"79.28",  手机客户满意度
            //"79.09",  手机网络质量
            //"85.38",  语音通话质量
            //"74.90",  手机上网质量
            //"76.50",  上网体验-视频业务
            //"77.98",  上网体验-游戏业务
            //"75.23",  手机资费套餐
            //"72.67",  资费规则清晰度
            //"67.85",  套餐设计合理性
            //"74.09",  套餐适配度
            //"77.32",  套餐办理便捷性
            //"75.81",  套餐办理规范性
            //"83.06",  账单服务
            //"83.76",  触点服务质量
            //"81.15"   整体感知
            for (Object key : tMap.keySet()) {
                for (String kpiName : titles) {
                    if ("011".equals(keys)) {
                        SatisfactionSn temp = new SatisfactionSn();
                        //temp.setKpiName(kpiName);
                        insertList.add(temp);
                    }
                    if ("012".equals(keys)) {
                        SatisfactionSnZq temp = new SatisfactionSnZq();
                        insertList.add(temp);
                    }
                    if ("021".equals(keys)) {
                        SatisfactionJt temp = new SatisfactionJt();
                        insertList.add(temp);
                    }
                    if ("022".equals(keys)) {
                        SatisfactionJtZq temp = new SatisfactionJtZq();
                        insertList.add(temp);
                    }

                }
            }

        }
        return R.ok(result);
    }

    /**
     * 转换为cityCode
     *
     * @param cityNames
     * @return
     */
    private List<String> translateToCode(List<String> cityNames, List<BsAreaCounty> cityCodeList) {
        List<String> cityCode = new ArrayList<>();
        for (String cityName : cityNames) {
            for (BsAreaCounty areaCounty : cityCodeList) {
                if (areaCounty.getCityName().contains(cityName)) {
                    cityCode.add(areaCounty.getCityId());
                }
            }

        }
        System.out.println(JSON.toJSONString(cityNames));
        System.out.println(JSON.toJSONString(cityCode));
        return cityCode;
    }

    /**
     * 011 sn_5g
     * 012 sn_zq
     * 021 jt_5g
     * 022 jt_zq
     *
     * @param tenantId
     * @param orgType
     * @return
     */
    private String getKeysStr(String tenantId, String orgType) {
        String keys = "0";
        if (!StrUtil.equalsIgnoreCase(TenantEnum.SN.getType(), tenantId)) {
            keys += "1";
        } else {
            keys += "2";
        }
        if (Arrays.asList(OrgTypeEnum.getTypeArray()).contains(orgType)) {
            keys += "1";
        } else {
            keys += "2";
        }
        return keys;
    }

    /**
     * 导出excel
     *
     * @param tenantId JT/SN
     * @param orgType  phone手机 5g5G broadband宽带 jcr决策人 lxr联系人 zx专线 qk企宽 gyy公有云
     * @param year     2021
     * @param period   001 002 003 004
     * @return R
     */
    @GetMapping("/exports")
    public R<?> exportGeneral(String tenantId, String orgType, String year, String period) throws IOException {

        return R.ok();
    }

    /**
     * 下载模板
     *
     * @param tenantId JT/SN
     * @param orgType  orgType
     * @throws IOException
     */
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response, String tenantId, String orgType) throws IOException {
        //获取模板文件
        String fileName = "template_" + tenantId.toLowerCase() + "_" + orgType + ".xlsx";
        String templatePath = "excelTemplate/" + fileName;
        ClassPathResource classPathResource = new ClassPathResource(templatePath);
        InputStream inputStream = classPathResource.getInputStream();
        ServletOutputStream servletOutputStream = null;
        try {
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes(), StandardCharsets.UTF_8));
            servletOutputStream = response.getOutputStream();
            IoUtil.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            log.error("下载失败，文件模板{}不存在", fileName);
        } finally {
            if (servletOutputStream != null) {
                servletOutputStream.close();
            }
            inputStream.close();
        }
    }

    /**
     * 获取到地区树
     *
     * @return
     */
    public List<BsAreaCounty> getAreaCodeList() {
        return commonMapper.getAreaCodeList();
    }

    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
