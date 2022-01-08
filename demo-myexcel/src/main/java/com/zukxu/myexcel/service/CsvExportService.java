package com.zukxu.myexcel.service;

import com.github.liaochong.myexcel.core.Csv;
import com.github.liaochong.myexcel.core.CsvBuilder;
import com.github.liaochong.myexcel.utils.AttachmentExportUtil;
import com.zukxu.myexcel.entity.ArtCrowd;
import com.zukxu.myexcel.utils.MyExcelUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * CSV 文件导出
 */
@Service
public class CsvExportService {

    public void generalCSVExport(HttpServletResponse response) {
        Csv csv = CsvBuilder.of(ArtCrowd.class).build(MyExcelUtils.getArtCrowdList());
        AttachmentExportUtil.export(csv.getFilePath(), "123.csv", response);
        //FileExportUtil.export(csv.getFilePath(),new File(""));
    }
}
