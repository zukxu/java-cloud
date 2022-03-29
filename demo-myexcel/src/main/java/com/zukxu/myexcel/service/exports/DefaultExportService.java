package com.zukxu.myexcel.service.exports;

import com.github.liaochong.myexcel.core.DefaultExcelBuilder;
import com.github.liaochong.myexcel.core.strategy.WidthStrategy;
import com.github.liaochong.myexcel.utils.AttachmentExportUtil;
import com.github.liaochong.myexcel.utils.FileExportUtil;
import com.github.liaochong.myexcel.utils.WatermarkUtil;
import com.zukxu.myexcel.entity.ArtCrowd;
import com.zukxu.myexcel.entity.People;
import com.zukxu.myexcel.entity.School;
import com.zukxu.myexcel.utils.DataInitUtils;
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

        List<ArtCrowd> dataList = DataInitUtils.getArtCrowdDataList();
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .sheetName("sheet1")
                                               .widthStrategy(WidthStrategy.AUTO_WIDTH)
                                               .build(dataList);
        AttachmentExportUtil.export(workbook, "defaultExport", response);
        //FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\defaultExport_excel"));
        //加密导出
        AttachmentExportUtil.encryptExport(workbook, "defaultExport", response, "password");
        //FileExportUtil.encryptExport(workbook, new File("E:\\temp\\myexcel\\defaultExport_excel"),"password");
    }


    /**
     * 多个sheet导出
     * 模板导出情况下，每一个table就是一个sheet。     *
     * 如需多table导出在同一sheet中，v3.11.3及其后续版本，新增sheetStrategy(SheetStrategy.ONE_SHEET)支持
     */
    @SneakyThrows
    public void multiSheetExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .sheetName("sheet1")
                                               .widthStrategy(WidthStrategy.AUTO_WIDTH)
                                               .build(DataInitUtils.getArtCrowdDataList());
        //新建sheet，跟在上一个之后
        workbook = DefaultExcelBuilder.of(People.class, workbook)
                                      .sheetName("sheet2")
                                      .widthStrategy(WidthStrategy.AUTO_WIDTH)
                                      .build(DataInitUtils.getPeopleDataList());
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
                                               .build(DataInitUtils.getSchoolDataList());

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
                                               .build(DataInitUtils.getSchoolDataList());

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
                                               .build(DataInitUtils.getArtCrowdDataList());

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\multiTitles_excel"));
    }

    /**
     * 图片导出
     * 1、注解 标识列为图片 @ExcelColumn(fileType = FileType.IMAGE)属性类型必须时File
     */
    @SneakyThrows
    public void imageExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(DataInitUtils.getArtCrowdDataList());

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
                                               .build(DataInitUtils.getArtCrowdDataList());

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
                                               .build(DataInitUtils.getArtCrowdDataList());

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\dropDownList_excel"));
    }

    /**
     * 自定义转化映射
     * 导出数据包含可枚举字段，如性别，存储在数据库中以0、1，导出为男、女。
     * 1、注解简单映射 @ExcelColumn(title="性别",mapping="0:男,1:女")
     * 使用 mapping 属性，以 , 分隔成组，每组以 :分隔，需要注意的是，该属性仅支持简单映射，不支持含有:,,特殊字符的映射
     * <p>
     * 2、复杂映射 编写映射转换类
     * 如
     * 需要读取数据库来决定转化，则简单映射无法满足，此时需要自定义转化
     * 2.1、编写转换类，实现CustomWriteConverter接口
     * 2.2、实体字段上添加@ExcelColumn(writeConverter = MyConverter.class)
     * <p>
     * 2.3、如果在方法中使用需要引入该转换类 @Autowired
     * 使用.binding(defaultCustomWriteConverter)
     */
    @SneakyThrows
    public void customConvertExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(DataInitUtils.getArtCrowdDataList());

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\customConvert_excel"));
    }

    /**
     * 公式使用
     * 1、模板 在td上添加属性：formula，公式无需加上=号，无需设定具体值
     */
    @SneakyThrows
    public void formulaExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(DataInitUtils.getArtCrowdDataList());

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\formula_excel"));
    }

    /**
     * 单元格类型设置
     * 默认情况下，程序会自动判别单元格内容类型，目前自动识别类型有以下三种：     *
     * 1、String
     * 2、Double
     * 3、Boolean
     * <p>
     * 设置
     * 1、模板
     * 在td上增加属性string设置该单元格类型为字符串
     * 在td上增加属性double设置该单元格类型为数值
     * 在td上增加属性double设置该单元格类型为数值
     */
    @SneakyThrows
    public void cellTypeExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(DataInitUtils.getArtCrowdDataList());

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\cellType_excel"));
    }

    /**
     * 表头冻结
     * 1、冻结全部  .fixedTitles()
     * 2、自定义冻结 .freezePane(new FreezePane(1,2))// 一行，两列
     * 3、模板 相同
     */
    @SneakyThrows
    public void freezeTitleExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .fixedTitles()
                                               //.freezePane(new FreezePane(1, 2))// 一行，两列
                                               .build(DataInitUtils.getArtCrowdDataList());
        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\freezeTitle_excel"));
    }

    /**
     * 提示
     * 1、注解 @ExcelColumn(prompt = @Prompt(title = "提示", text = "这是我的提示哦"))
     */
    @SneakyThrows
    public void promptExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(DataInitUtils.getArtCrowdDataList());
        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\prompt_excel"));
    }

    /**
     * 添加水印
     * 不支持.xls文件添加水印，支持XSSF、SXSSF模式下添加水印，如数据量过大，可能会造成内存溢出，请注意⚠️
     */
    @SneakyThrows
    public void waterMarkExport() {
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(DataInitUtils.getArtCrowdDataList());
        //使用工具生成水印
        WatermarkUtil.addWatermark(workbook,"MyExcel学习");
        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\waterMark_excel"));
    }

    public static void main(String[] args) {
        DefaultExportService defaultExportService = new DefaultExportService();
        defaultExportService.multiColumnExport();
    }

}
