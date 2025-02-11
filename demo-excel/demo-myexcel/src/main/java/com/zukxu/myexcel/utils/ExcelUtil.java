package com.zukxu.myexcel.utils;

import com.sun.rowset.internal.Row;
import com.zukxu.common.exception.BusinessException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-01-26 15:17
 */
public class ExcelUtil {
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 读入excel文件，解析后返回
     *
     * @param file
     * @throws IOException
     */
    public static List<String[]> readExcel(MultipartFile file) throws IOException {
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<>();
        getStringsFromWorkbook(workbook, list);
        return list;
    }

    /**
     * 读入excel文件，解析后返回
     *
     * @param workbook list
     * @throws IOException
     */
    public static List<String[]> getStringsFromWorkbook(Workbook workbook, List<String[]> list) throws IOException {
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环所有行
                for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    //循环当前行
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
            workbook.close();
        }
        return list;
    }

    /**
     * 读入excel文件，解析后返回
     * 注意：表头有几列就读取几列
     *
     * @param workbook list
     * @throws IOException
     */
    public static List<String[]> getStringsFromWorkbookByHeader(Workbook workbook, List<String[]> list) throws IOException {
        if (workbook != null) {

            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {

                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();

                //计数器
                int count = 0;
                //表头个数
                int headerNum = 0;
                //循环所有行
                for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                    count++;
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    //                    int lastCellNum = row.getPhysicalNumberOfCells();
                    //取第表头的列数
                    if (count == 1) {
                        headerNum = row.getPhysicalNumberOfCells();
                    }
                    String[] cells = new String[headerNum];
                    //循环当前行
                    for (int cellNum = firstCellNum; cellNum < headerNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
            workbook.close();
        }
        return list;
    }

    /**
     * 解析txt/dat文件，统计业务数据，转存为excel文件
     *
     * @param txtPath
     * @param excelPath
     * @param sheetName
     * @param headNames
     * @param resMap
     */
    public static void transToExcel(String txtPath, String excelPath, String sheetName, String[] headNames, Map<String, Object> resMap) {
        //Excel
        HSSFWorkbook wb = new HSSFWorkbook();
        //sheet表
        HSSFSheet sheet = wb.createSheet(sheetName);
        //标题行
        HSSFRow head = sheet.createRow(0);
        //定义样式
        HSSFCellStyle style = wb.createCellStyle();
        //字体样式
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());//字体颜色
        style.setFont(font);
        for (int i = 0; i <= headNames.length - 1; i++) {
            //标题列
            HSSFCell headCell = head.createCell(i);
            //标题内容
            headCell.setCellValue(headNames[i]);
            //应用样式
            headCell.setCellStyle(style);
        }
        //行内容
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(txtPath), "UTF-8"));
            Integer lineNum = 1;
            String line = "";
            /*业务统计*/
            //  `call_num` varchar(200) DEFAULT NULL COMMENT '外呼用户数',
            //  `answer_num` varchar(200) DEFAULT NULL COMMENT '接听用户数',
            //  `confirm_num` varchar(200) DEFAULT NULL COMMENT '授权用户数',
            int callNum = 0;
            int answerNum = 0;
            int confirmNum = 0;
            /*业务统计*/
            while ((line = bufferedReader.readLine()) != null) {
                //创建行
                HSSFRow row = sheet.createRow(lineNum);
                String[] cells = line.trim().split("\\|");
                for (int i = 0; i <= cells.length - 1; i++) {
                    HSSFCell cell = row.createCell(i);
                    if (i == 0) {
                        cells[i] = cells[i].equals("0") ? "移动外呼" : "企业外呼";
                    }
                    if (i == 7) {
                        callNum = callNum + Integer.valueOf(cells[i]);
                    }
                    if (i == 8 || i == 9) {
                        if (cells[i].equals("0")) {
                            cells[i] = "是";
                            if (i == 8) {
                                answerNum++;
                            } else {
                                confirmNum++;
                            }
                        } else {
                            cells[i] = "否";
                        }
                    }
                    cell.setCellValue(cells[i]);
                }
                lineNum += 1;
            }
            resMap.put("callNum", callNum);
            resMap.put("answerNum", answerNum);
            resMap.put("confirmNum", confirmNum);
            FileOutputStream os = new FileOutputStream(excelPath);
            wb.write(os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取excel
    public static Workbook readExcel(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    //转存excel
    public static void saveExcel(String filePath, byte[] bytes) {
        Workbook wb = null;
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(bytes);
            if (".xls".equals(extString)) {
                wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = null;
            }
            FileOutputStream os = new FileOutputStream(filePath);
            wb.write(os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkFile(MultipartFile file) throws IOException {
        //判断文件是否存在
        if (null == file) {
            logger.error("文件不存在！");
            throw new BusinessException("文件不存在");
        }
        //获得文件名
        String fileName = file.getOriginalFilename().equals("") ? file.getName() : file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
            logger.error(fileName + "不是excel文件");
            throw new BusinessException("不是excel文件");
        }
    }

    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(xls)) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(xlsx)) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC: //数字
                //把数字当成String来读，避免出现1读成1.0的情况
                cell.setCellType(CellType.STRING);
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: //空值
                cellValue = "";
                break;
            case ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

   /* public static  void main (String[] args){
        DecimalFormat df = new DecimalFormat("#0.00");
        Double dd = 15592.6529d;
        System.out.println(df.format(dd));
    }*/
}
