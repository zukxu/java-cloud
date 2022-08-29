package com.zukxu.alanpoi.handler;

import com.alanpoi.analysis.excel.imports.ExcelSheetData;
import com.alanpoi.analysis.excel.imports.handle.ExcelConsumeInterface;
import com.alanpoi.analysis.excel.imports.handle.ExcelError;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 导入
 * </p>
 *
 * @author xupu
 * @since 2022/7/18 17:16:27
 */
public class ImportConsume implements ExcelConsumeInterface {

    @Override
    public void error(ExcelError excelError) {
        //校验失败回调
    }

    @Override
    public void validData(String workbookId, List<ExcelSheetData> sheetDataList, Map<Serializable, Object> excelParam) {
        //校验逻辑
    }

    @Override
    public void end(List<ExcelSheetData> sheetDataList, Map<Serializable, Object> excelParam) {
        //返回校验成功的数据，可以在此进行业务操作
    }

}
