package com.zukxu.myexcel.service;

import com.github.liaochong.myexcel.core.DefaultExcelBuilder;
import com.github.liaochong.myexcel.utils.AttachmentExportUtil;
import com.github.liaochong.myexcel.utils.FileExportUtil;
import com.zukxu.myexcel.entity.ArtCrowd;
import com.zukxu.myexcel.entity.People;
import com.zukxu.myexcel.utils.MyExcelUtils;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态导出
 * 分为三种
 * 1、动态指定标题，字段顺序，不用在实体类中硬编码写死
 * 2、字段分组
 * 3、Map导出
 */
@Service
public class DynamicExportService {

    /**
     * 动态导出 动态指定标题
     */
    @SneakyThrows
    public void dynamicTitleExport(HttpServletResponse response) {
        // title
        List<String> titles = new ArrayList<>();
        titles.add("姓名");
        titles.add("年龄");

        // field display order
        List<String> order = new ArrayList<>();
        order.add("name");
        order.add("age");
        List<People> dataList = MyExcelUtils.getPeopleDataList();
        //1、配置导出
        Workbook workbook = DefaultExcelBuilder
                //如果已存在导出实体模板类
                .of(People.class)
                .titles(titles)
                .fieldDisplayOrder(order)
                .build(dataList);

        AttachmentExportUtil.export(workbook, "temp", response);

    }

    /**
     * 字段分组
     *
     * @param response
     */
    @SneakyThrows
    public void fieldGroupExport(HttpServletResponse response) {
        //使用注解属性标识@ExcelColumn(title="姓名",groups={People.class})
         DefaultExcelBuilder
                //如果已存在导出实体模板类
                .of(ArtCrowd.class);
                //.build(People.class);

    }

    /**
     * Map导出
     *
     * @param response
     */
    @SneakyThrows
    public void mapExport(HttpServletResponse response) {
        //1、配置头
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("a", "测试A");
        headerMap.put("b", "测试B");

        List<Map> dataMapList = new ArrayList<>();
        Map<String, Object> v1 = new HashMap<>();
        v1.put("a", "数据a1");
        v1.put("b", 3);

        Map<String, Object> v2 = new HashMap<>();
        //Map导出默认不支持格式化，需自行格式化，
        //如需指定Map中value类型，如超链接等，value需设置如下：
        //v2.put("2", Pair.of(LinkUrl.class, "http://www.baidu.com"));
        v2.put("a", "数据a2");
        v2.put("b", 5);

        dataMapList.add(v1);
        dataMapList.add(v2);

        List<String> titles = new ArrayList(headerMap.values());
        List<String> orders = new ArrayList(headerMap.keySet());
        Workbook workbook = DefaultExcelBuilder.of(Map.class)
                                               .sheetName("sheet1")
                                               .titles(titles)
                                               .widths(10,20)
                                               .fieldDisplayOrder(orders)
                                               .build(dataMapList);
        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\zz.xlsx"));
    }

    public static void main(String[] rgs) {
        DynamicExportService service = new DynamicExportService();
        service.mapExport(null);
    }
}
