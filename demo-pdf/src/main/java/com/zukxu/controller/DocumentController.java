package com.zukxu.controller;

import com.zukxu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文档操作类
 * </p>
 *
 * @author xupu
 * @since 2025/4/28 15:19:27
 */
@RestController("/document")
public class DocumentController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private WordService wordService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private PPTService pptService;

    @Autowired
    private PDFService pdfService;

}
