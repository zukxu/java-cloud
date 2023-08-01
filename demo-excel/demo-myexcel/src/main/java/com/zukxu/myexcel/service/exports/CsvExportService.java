package com.zukxu.myexcel.service.exports;

import com.github.liaochong.myexcel.core.Csv;
import com.github.liaochong.myexcel.core.CsvBuilder;
import com.github.liaochong.myexcel.utils.AttachmentExportUtil;
import com.zukxu.myexcel.entity.ArtCrowd;
import com.zukxu.myexcel.entity.People;
import com.zukxu.myexcel.utils.DataInitUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CSV 文件导出
 */
@Service
public class CsvExportService {

    /**
     * 一般方式导出
     *
     * @param response
     */
    public void generalCSVExport(HttpServletResponse response) {
        Csv csv = CsvBuilder.of(ArtCrowd.class).build(DataInitUtils.getArtCrowdList());
        AttachmentExportUtil.export(csv.getFilePath(), "generalCSV.csv", response);
        //FileExportUtil.export(csv.getFilePath(),new File(""));
    }

    /**
     * Map 导出
     */
    public void mapCSVExport(HttpServletResponse response) {
        List<Map> list = new ArrayList<>();
        list.add(DataInitUtils.getProductDataMap());
        Csv csv = CsvBuilder.of(Map.class).build(list);
        AttachmentExportUtil.export(csv.getFilePath(), "mapCSV.csv", response);
        //FileExportUtil.export(csv.getFilePath(),new File(""));
    }

    /**
     * 追加方式 导出
     */
    public void attachCSVExport(HttpServletResponse response) {
        CsvBuilder<People> csvBuilder = CsvBuilder.of(People.class);
        for (int i = 0; i < 10; i++) {
            csvBuilder.append(DataInitUtils.getPeopleDataList());
        }
        Csv csv = csvBuilder.build();
        AttachmentExportUtil.export(csv.getFilePath(), "attachCSV.csv", response);
        //FileExportUtil.export(csv.getFilePath(),new File(""));
    }

    /**
     * 二次文件追加方式 导出
     */
    public void attachTwoCSVExport(HttpServletResponse response) {
        CsvBuilder<People> csvBuilder = CsvBuilder.of(People.class).noTitles();
        for (int i = 0; i < 10; i++) {
            csvBuilder.append(DataInitUtils.getPeopleDataList());
        }
        Csv csv = csvBuilder.build();
        //会在原文件的后面追加内容，不会覆盖
        csv.write(Paths.get("E:\\temp\\myexcel\\attachCSV.csv"), true);
    }

}
