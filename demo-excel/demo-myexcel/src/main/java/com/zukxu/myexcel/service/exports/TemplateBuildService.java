package com.zukxu.myexcel.service.exports;

import com.github.liaochong.myexcel.core.ExcelBuilder;
import com.github.liaochong.myexcel.core.FreemarkerExcelBuilder;
import com.github.liaochong.myexcel.core.HtmlToExcelFactory;
import com.github.liaochong.myexcel.utils.FileExportUtil;
import com.zukxu.myexcel.utils.DataInitUtils;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * Excel模板构建
 * 所有Excel构建器均不可单例化，务必注意！！！
 * 模板构建，核心原理是根据模板引擎渲染出符合需求布局的Html Table元素，构建器迭代table元素中的单元格渲染出Excel单元格
 * 已内置Freemarker、Groovy、Beetl、Thymeleaf等常用模板引擎Excel构建器
 * 模板引擎默认均未被引入，使用者可根据自身需要选择在pom.xml中声明引入。
 * 推荐使用Beetl
 */
@Service
public class TemplateBuildService {

    public static void main(String[] args) {
        TemplateBuildService templateBuildService = new TemplateBuildService();
        templateBuildService.htmlExport(null);
        //templateBuildService.freemarkerExport(null);

    }

    /**
     * 已有html文件导出
     * 不强制放在resources下
     * 1、 已存在Html文件时，使用这种方式，Html文件不局限于放在项目的classpath（如：resources）下，也无需模板引擎
     */
    @SneakyThrows
    public void htmlExport(HttpServletResponse response) {
        // get html file
        //URL htmlToExcelExampleURL = this.getClass().getResource("/templates/htmlToExcelExample.html");
        //Path path = Paths.get(htmlToExcelExampleURL.toURI());
        //Workbook workbook = HtmlToExcelFactory.readHtml(path.toFile()).build();
        //AttachmentExportUtil.export(workbook, "转换示例", response);

        File htmlFile = new File("E:\\Cloud\\java-cloud\\demo-myexcel\\src\\main\\resources\\templates\\htmlToExcelExample.html");
        Workbook workbook = HtmlToExcelFactory.readHtml(htmlFile).useDefaultStyle().build();

        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\html_excel.xlsx"));

    }

    /**
     * 模板引擎文件构建Excel生成器，则模板文件应该放在ClassPath下
     *
     * @param response
     */
    @SneakyThrows
    public void freemarkerExport(HttpServletResponse response) {
        Map<String, Object> dataMap = DataInitUtils.getProductDataMap();
        try (ExcelBuilder excelBuilder = new FreemarkerExcelBuilder()) {
            Workbook workbook = excelBuilder
                    // fileTemplate(dirPath,fileName)
                    .classpathTemplate("/templates/freemarkerToExcelExample.ftl").build(dataMap);

            //AttachmentExportUtil.export(workbook, "freemarker_excel", response);
            FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\freemarker_excel.xlsx"));
        }

    }

    /**
     * 斜线绘制
     * 1、模板
     * // 2代表斜线的宽度width
     * <td slant="solid 2 #000000"></td>
     * 斜线样式：SOLID、DOT、DASH、LG_DASH、DASH_DOT、LG_DASH_DOT、LG_DASH_DOT_DOT、SYS_DASH、SYS_DOT、SYS_DASH_DOT、SYS_DASH_DOT_DOT
     */
    @SneakyThrows
    public void solidExport() {
        Workbook workbook = new FreemarkerExcelBuilder()
                // fileTemplate(dirPath,fileName)
                .classpathTemplate("/templates/freemarkerToExcelExample.ftl").build(DataInitUtils.getProductDataMap());
        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\solid_excel"));
    }

    /**
     * 当前仅模板方式支持设置批注。
     * <td comment-text="批注内容" comment-author="作者"></td>
     */
    @SneakyThrows
    public void commentExport() {
        Workbook workbook = new FreemarkerExcelBuilder()
                // fileTemplate(dirPath,fileName)
                .classpathTemplate("/templates/freemarkerToExcelExample.ftl").build(DataInitUtils.getProductDataMap());
        FileExportUtil.export(workbook, new File("E:\\temp\\myexcel\\comment_excel"));
    }

}
