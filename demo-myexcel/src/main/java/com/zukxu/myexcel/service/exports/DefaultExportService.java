package com.zukxu.myexcel.service.exports;

import com.github.liaochong.myexcel.core.DefaultExcelBuilder;
import com.github.liaochong.myexcel.core.strategy.WidthStrategy;
import com.github.liaochong.myexcel.utils.AttachmentExportUtil;
import com.github.liaochong.myexcel.utils.FileExportUtil;
import com.zukxu.myexcel.entity.ArtCrowd;
import com.zukxu.myexcel.entity.People;
import com.zukxu.myexcel.entity.School;
import com.zukxu.myexcel.utils.MyExcelUtils;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * 默认导出
 */
@Service
public class DefaultExportService {

    /**
     * 默认导出 浏览器下载
     * 采用普通的List<Class<T> 的方式导出，适合小数据量的情况，如果数据量>10W 建议使用DefaultStreamBuilder进行导出，避免内存占用过多
     * 需要使用到如下注解
     * 1、@ExcelModel(includeAllField,excludeParent,workbookType,sheetName,useFieldNameAsTitle,defaultValue)（可选，用于全局设定，一般情况下只需要使用sheetName
     * 2、@IgnoreColumn（可选，用于排出不需要导出的字段）
     * 3、@ExcelColumn(title,order,format,groups,defaultValue,style)
     * <p>
     * 默认导出默认计算宽度、斑马线背景色，若无需上述样式，请调用 noStyle() 方法
     */
    @SneakyThrows
    public void defaultExport(HttpServletResponse response) {

        List<ArtCrowd> dataList = MyExcelUtils.getArtCrowdDataList();
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .sheetName("sheet1")
                                               .widthStrategy(WidthStrategy.AUTO_WIDTH)
                                               .build(dataList);
        AttachmentExportUtil.export(workbook, "艺术生信息", response);
        //FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\default_excel"));
        //加密导出
        AttachmentExportUtil.encryptExport(workbook, "艺术生信息", response, "password");
        //FileExportUtil.encryptExport(workbook, new File("E:\\temp\\myexcel\\default_excel"),"password");
    }


    /**
     * 多个sheet导出 保存到文件
     * 模板导出情况下，每一个table就是一个sheet。     *
     * 如需多table导出在同一sheet中，v3.11.3及其后续版本，新增sheetStrategy(SheetStrategy.ONE_SHEET)支持
     */
    @SneakyThrows
    public void multiSheetExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .sheetName("sheet1")
                                               .widthStrategy(WidthStrategy.AUTO_WIDTH)
                                               .build(MyExcelUtils.getArtCrowdDataList());
        //新建sheet，跟在上一个之后
        workbook = DefaultExcelBuilder.of(People.class, workbook)
                                      .sheetName("sheet2")
                                      .widthStrategy(WidthStrategy.AUTO_WIDTH)
                                      .build(MyExcelUtils.getPeopleDataList());
        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\multiSheet_excel"));
        //加密导出
        FileExportUtil.encryptExport(workbook, new File("E:\\temp\\myexcel\\multiSheet_excel"), "password");
    }

    /**
     * 聚合列导出
     * 聚合列指的是导出Bean，大部分属性相同，仅部分属性不同，用一个Bean将同一属性不同的值以List收集作为列，
     * 聚合列要求有两点：
     * 1、字段类型必须是List；
     * 2、使用注解 @MultiColumn；
     */
    @SneakyThrows
    public void multiColumnExport() {
        Workbook workbook = DefaultExcelBuilder.of(School.class)
                                               .sheetName("sheet1")
                                               .widthStrategy(WidthStrategy.AUTO_WIDTH)
                                               .build(MyExcelUtils.getSchoolDataList());

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\multiColumn_excel"));
        //加密导出
        //FileExportUtil.encryptExport(workbook, new File("E:\\temp\\myexcel\\multiColumn"), "password");
    }

    public static void main(String[] args) {
        DefaultExportService defaultExportService = new DefaultExportService();
        defaultExportService.multiColumnExport();
    }

}
