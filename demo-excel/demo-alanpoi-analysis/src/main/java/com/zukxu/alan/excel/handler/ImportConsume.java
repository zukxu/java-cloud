package com.zukxu.alan.excel.handler;

import com.alanpoi.analysis.excel.imports.ExcelSheetData;
import com.alanpoi.analysis.excel.imports.handle.ExcelConsumeInterface;
import com.alanpoi.analysis.excel.imports.handle.ExcelError;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/7/18 17:16:27
 */
public class ImportConsume implements ExcelConsumeInterface {

    @Override
    public void error(ExcelError excelError) {

    }

    @Override
    public void validData(String workbookId, List<ExcelSheetData> sheetDataList, Map<Serializable, Object> excelParam) {

    }

    @Override
    public void end(List<ExcelSheetData> sheetDataList, Map<Serializable, Object> excelParam) {

    }

}
