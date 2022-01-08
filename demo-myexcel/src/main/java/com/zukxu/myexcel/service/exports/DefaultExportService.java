package com.zukxu.myexcel.service.exports;

import com.github.liaochong.myexcel.core.DefaultExcelBuilder;
import com.github.liaochong.myexcel.utils.AttachmentExportUtil;
import com.github.liaochong.myexcel.utils.FileExportUtil;
import com.zukxu.myexcel.entity.ArtCrowd;
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
    public void defaultExport(HttpServletResponse response) {

        List<ArtCrowd> dataList = MyExcelUtils.getArtCrowdDataList();
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(dataList);
        AttachmentExportUtil.export(workbook, "艺术生信息", response);
        //加密导出
        AttachmentExportUtil.encryptExport(workbook, "艺术生信息", response, "password");
    }


    /**
     * 导出 保存到文件
     */
    @SneakyThrows
    public void defaultExportToFile() {
        List<ArtCrowd> dataList = MyExcelUtils.getArtCrowdDataList();
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class)
                                               .build(dataList);
        FileExportUtil.export(workbook, new File("/User/demo.xlsx"));
        //加密导出
        FileExportUtil.encryptExport(workbook, new File("/User/demo.xlsx"),"password");
    }


}
