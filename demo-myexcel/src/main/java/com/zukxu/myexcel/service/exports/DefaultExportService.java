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

    /**
     * 样式自定义
     * 1、注解样式自定义 @ExcelColumn(style={"title->color:red","cell->color:green"})
     * 2、方法样式自定义 .style("title->color:red","background-color:green;")
     * 使用 -> 分隔符：
     * <p>
     * title标明该样式针对标题；
     * cell标明该样式针对内容行；
     * odd标明该样式针对奇数内容行；
     * even标明该样式针对偶数内容行；
     * 详细使用：https://github.com/liaochong/myexcel/wiki/Style-support
     */
    @SneakyThrows
    public void customStyleExport() {
        Workbook workbook = DefaultExcelBuilder.of(School.class)
                                               .sheetName("sheet1")
                                               .widthStrategy(WidthStrategy.AUTO_WIDTH)
                                               .style("title->color:red", "background-color:green;")
                                               .build(MyExcelUtils.getSchoolDataList());

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\customStyle_excel"));
        //加密导出
        //FileExportUtil.encryptExport(workbook, new File("E:\\temp\\myexcel\\multiColumn"), "password");
    }

    /**
     * 多级表头导出
     * 1、注解 @ExcelColumn(title="拓展信息->年龄")
     * 默认以->作为分隔符，也可自定义分隔符：@ExcelModel(titleSeparator="#")
     * 2、方法 titles(List<String> titles)
     * List<String> titles=new ArrayList<>();
     * titles.add("拓展信息->年龄");
     */
    @SneakyThrows
    public void multiTitlesExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(MyExcelUtils.getArtCrowdDataList());

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\multiTitles_excel"));
    }

    /**
     * 图片导出
     * 1、注解 标识列为图片 @ExcelColumn(fileType = FileType.IMAGE)属性类型必须时File
     */
    @SneakyThrows
    public void imageExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(MyExcelUtils.getArtCrowdDataList());

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\image_excel"));
    }

    /**
     * 超链接
     * 1、模板中
     * 超链接
     * <td url="http://www.google.com" style="color:blue">谷歌</td>
     * <td><a href="http://www.baidu.com">百度</a></td>
     * 邮件
     * <td email="mailto:poi@apache.org?subject=Hyperlinks" style="color:blue">邮件地址</td>
     * <td style="color:blue"><a href="mailto:poi@apache.org?subject=Hyperlinks">邮件地址</a></td>
     * <p>
     * 2、Bean中 注解 @ExcelColumn(linkType=LinkType.URL)
     */
    @SneakyThrows
    public void hyperlinkExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(MyExcelUtils.getArtCrowdDataList());

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\hyperlink_excel"));
    }

    /**
     * 下拉列表
     * 1、注解  @ExcelColumn(title="下拉列表")
     * 只需要一个一个List或者Array 列表总字符不可超过250字符
     * <p>
     * 2、模板
     * 在td单元格上加上属性“dropDownList”，单元格内容如：选项1,选项2,选项3，以英文逗号,分隔
     */
    @SneakyThrows
    public void dropDownListExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(MyExcelUtils.getArtCrowdDataList());

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\dropDownList_excel"));
    }

    /**
     * 自定义转化映射
     * 导出数据包含可枚举字段，如性别，存储在数据库中以0、1，导出为男、女。
     * 1、注解简单映射 @ExcelColumn(title="性别",mapping="0:男,1:女")
     * 使用 mapping 属性，以 , 分隔成组，每组以 :分隔，需要注意的是，该属性仅支持简单映射，不支持含有:,,特殊字符的映射
     *
     * 2、复杂映射 编写映射转换类
     * 如
     * 需要读取数据库来决定转化，则简单映射无法满足，此时需要自定义转化
     * 2.1、编写转换类，实现CustomWriteConverter接口
     * 2.2、实体字段上添加@ExcelColumn(writeConverter = MyConverter.class)
     *
     * 2.3、如果在方法中使用需要引入该转换类 @Autowired
     * 使用.binding(defaultCustomWriteConverter)
     */
    @SneakyThrows
    public void customConvertExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(MyExcelUtils.getArtCrowdDataList());

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\customConvert_excel"));
    }

    public static void main(String[] args) {
        DefaultExportService defaultExportService = new DefaultExportService();
        defaultExportService.multiColumnExport();
    }

}
