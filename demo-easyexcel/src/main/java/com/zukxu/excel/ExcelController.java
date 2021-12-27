package com.zukxu.excel;

import com.alibaba.excel.EasyExcel;
import com.zukxu.excel.listener.ModelExcelListener;
import com.zukxu.excel.model.UserExcelModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * excel控制器
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/6 0006 15:43
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {
	/**
	 * 下载模板
	 */
	@GetMapping("/downloadTemplate")
	public void downloadTemplate(HttpServletResponse response) throws Exception {
		//获取模板文件
		ClassPathResource classPathResource = new ClassPathResource("excelTemplate/template.xlsx");
		InputStream inputStream = classPathResource.getInputStream();
		Workbook workbook = new XSSFWorkbook(inputStream);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("template.xlsx", "utf-8"));
		response.setHeader("Access-Control-Expose-Headers", "content-Disposition");
		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	/**
	 * 读取数据
	 *
	 * @param file 文件名称
	 * @return list
	 */
	@PostMapping("/readExcel")
	public List<UserExcelModel> readExcel(@RequestParam("file") MultipartFile file) {
		List<UserExcelModel> list = new ArrayList<>();
		try {
			list = EasyExcel.read(file.getInputStream(), UserExcelModel.class, new ModelExcelListener()).sheet().doReadSync();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 导出数据，尽量导出成低版本
	 */
	@GetMapping("/exportData")
	public void exportData(HttpServletResponse response) throws Exception {
		//生成工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		//定义列标题，尽量遵从实体类的index
		String[] columnNames = {"用户名", "年龄", "性别", "手机号"};

		//定义工作表
		Sheet sheet = workbook.createSheet();

		//定义标题样式
		Font titleFont = workbook.createFont();
		titleFont.setFontName("宋体");
		titleFont.setBold(true);
		titleFont.setColor(IndexedColors.BLACK.index);

		//设置单元格样式
		XSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		titleStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
		titleStyle.setFont(titleFont);

		//定义行
		Row titleRow = sheet.createRow(0);

		//设置列
		for (int i = 0; i < columnNames.length; i++) {
			Cell cell = titleRow.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(titleStyle);
		}
		//模拟构造数据
		List<UserExcelModel> dataList = new ArrayList<>();
		dataList.add(new UserExcelModel("张三", 12, "男", "13867098765"));
		dataList.add(new UserExcelModel("张三1", 12, "男", "13867098765"));
		dataList.add(new UserExcelModel("张三2", 12, "男", "13867098765"));
		dataList.add(new UserExcelModel("张三3", 12, "男", "13867098765"));

		//创建数据行并写入值
		for (UserExcelModel userExcelModel : dataList) {
			int lastRowNum = sheet.getLastRowNum();
			Row dataRow = sheet.createRow(lastRowNum + 1);

			// 此处注意index的顺序一定要严格按照模板文件的格式
			dataRow.createCell(0).setCellValue(userExcelModel.getName());
			dataRow.createCell(1).setCellValue(userExcelModel.getAge());
			dataRow.createCell(2).setCellValue(userExcelModel.getSex());
			dataRow.createCell(3).setCellValue(userExcelModel.getMobile());
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("easyexcel.xls", "utf-8"));
		response.setHeader("Access-Control-Expose-Headers", "content-Disposition");
		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

}

