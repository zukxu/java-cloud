package com.zukxu.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * <p>
 * PDF业务类
 * </p>
 *
 * @author xupu
 * @since 2025/4/28 15:20:49
 */
@Service
public class PDFService {
    public void pdfToFileFromWeb(MultipartFile file, String type) {}

    public void pdfToFileFromLocal(File file, String type) {}
}
