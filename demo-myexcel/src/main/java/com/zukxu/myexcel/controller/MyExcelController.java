package com.zukxu.myexcel.controller;

import com.zukxu.common.result.R;
import com.zukxu.myexcel.service.exports.*;
import com.zukxu.myexcel.service.imports.MyExcelImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 导入文件
 * </p>
 *
 * @author xupu
 * @since 2022-01-07 18:00
 */
@RestController
@RequestMapping("excel")
public class MyExcelController {

    @Autowired
    private MyExcelImportService myExcelImportService;
    @Autowired
    private DefaultExportService defaultExportService;
    @Autowired
    private StreamExportService streamExportService;
    @Autowired
    private DynamicExportService dynamicExportService;
    @Autowired
    private TemplateBuildService templateBuildService;
    @Autowired
    private CsvExportService csvExportService;

    @PostMapping("/import/default")
    public R<?> defaultImportExcel(MultipartFile file) {
        myExcelImportService.defaultImport(file);
        return R.ok();
    }

    @PostMapping("/import/sax")
    public R<?> saxImportExcel(MultipartFile file) {
        myExcelImportService.saxImport(file);
        return R.ok();
    }

    @PostMapping("/export/default")
    public R<?> defaultExportExcel(HttpServletResponse response) {
        defaultExportService.defaultExport(response);
        return R.ok();
    }

    @PostMapping("/export/stream")
    public R<?> streamExportExcel(HttpServletResponse response) {
        streamExportService.streamExport(response);
        return R.ok();
    }

    @PostMapping("/export/dynamic")
    public R<?> dynamicExportExcel(HttpServletResponse response) {
        dynamicExportService.dynamicTitleExport(response);
        return R.ok();
    }

    @PostMapping("/export/template")
    public R<?> templateExportExcel(HttpServletResponse response) {
        templateBuildService.freemarkerExport(response);
        return R.ok();
    }

    @PostMapping("/export/csv")
    public R<?> csvExportExcel(HttpServletResponse response) {
        csvExportService.generalCSVExport(response);
        return R.ok();
    }

}
