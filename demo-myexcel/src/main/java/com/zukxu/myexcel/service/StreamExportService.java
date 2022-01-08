package com.zukxu.myexcel.service;

import com.github.liaochong.myexcel.core.DefaultStreamExcelBuilder;
import com.github.liaochong.myexcel.core.templatehandler.FreemarkerTemplateHandler;
import com.github.liaochong.myexcel.utils.AttachmentExportUtil;
import com.zukxu.myexcel.entity.ArtCrowd;
import com.zukxu.myexcel.utils.MyExcelUtils;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * 流式导出
 * 区别于默认导出方式在于：流式导出采用生产者消费者模式，允许分批获取数据，分批写入Excel
 * 且默认采用SXSSF模式，内存占用极低，真正意义上实现海量数据导出，还支持zip压缩等独有特性
 */
@Service
public class StreamExportService {

    /**
     * 流式导出 默认
     */
    @SneakyThrows
    public void streamExport(HttpServletResponse response) {
        //1、配置导出
        DefaultStreamExcelBuilder<ArtCrowd> streamExport = DefaultStreamExcelBuilder
                //如果已存在导出实体模板类
                .of(ArtCrowd.class)
                // 如果需要导出Map类型的数据，请使用Map.class
                //.of(Map.class)
                //如果需要将数据追加到已存在的excel中，请使用如下配置,不支持多sheet追加
                //.of(ArtCrowd.class,new FileInputStream(new File("/user.xlsx")))
                //或者如下配置
                //.of(ArtCrowd.class, Paths.get("/user.xlsx"))
                .threadPool(Executors.newFixedThreadPool(10))//构建线程池
                .templateHandler(FreemarkerTemplateHandler.class)//追加模板数据
                .capacity(10000)//容量设定，在数据量达到设置的值时，会自动生成新的excel
                .start();
        //2.1 复杂模板定义
        /*
         * 对于某些情况下可能需要某些个性化的表头，涉及到了合并行，合并列，合并样式等复杂布局，单纯的class实体类已经无法满足导入导出了
         * 所以我们可以通过模板追加的方式，在模板中定义复杂的布局，追加方式如下
         * */

        //2、数据追加
        //append参数可为列表，也可为单个数据，建议使用单个数据追加，如Bean、Map
        streamExport.append(MyExcelUtils.getDataList());
        //多线程异步追加数据
        streamExport.asyncAppend(MyExcelUtils::getDataList);

        //3、完成构建导出
        //如需最大化提升导出性能，请调用noStyle()方法全面禁止样式
        //DefaultStreamExcelBuilder默认采用SXSSF模式（内存占用低）导出，该模式下不支持自动列宽.
        Workbook workbook = streamExport.build();
        AttachmentExportUtil.export(workbook, "temp", response);

    }

    /**
     * 多文件导出
     *
     * @param response
     */
    @SneakyThrows
    public void multiStreamExport(HttpServletResponse response) {
        //1、配置导出
        DefaultStreamExcelBuilder<ArtCrowd> streamExport = DefaultStreamExcelBuilder
                //如果已存在导出实体模板类
                .of(ArtCrowd.class)
                .threadPool(Executors.newFixedThreadPool(10))//构建线程池
                .templateHandler(FreemarkerTemplateHandler.class)//追加模板数据
                .capacity(10000)//容量设定，在数据量达到设置的值时，会自动生成新的excel
                .start();
        //2、数据追加
        //多线程异步追加数据
        streamExport.asyncAppend(MyExcelUtils::getDataList);

        //3、完成构建导出
        List<Path> paths = streamExport.buildAsPaths();
        paths.forEach(path -> {
            AttachmentExportUtil.export(path, "temp", response);
        });
    }

    /**
     * zip文件导出
     *
     * @param response
     */
    @SneakyThrows
    public void zipStreamExport(HttpServletResponse response) {
        //1、配置导出
        DefaultStreamExcelBuilder<ArtCrowd> streamExport = DefaultStreamExcelBuilder
                //如果已存在导出实体模板类
                .of(ArtCrowd.class)
                .threadPool(Executors.newFixedThreadPool(10))//构建线程池
                .templateHandler(FreemarkerTemplateHandler.class)//追加模板数据
                .capacity(10000)//容量设定，在数据量达到设置的值时，会自动生成新的excel
                .start();
        //2、数据追加
        //多线程异步追加数据
        streamExport.asyncAppend(MyExcelUtils::getDataList);

        //3、完成构建导出
        Path zipPath = streamExport.buildAsZip("test");
        AttachmentExportUtil.export(zipPath, "test.zip", response);
    }

}
