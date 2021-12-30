package com.zukxu.excel.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.zukxu.excel.constant.Const;
import com.zukxu.excel.listener.Sn5GListener;
import com.zukxu.excel.model.satisfaction.Sn5g;
import com.zukxu.excel.service.ReportService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * excel控制器
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/6 0006 15:43
 */
@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {
	@Autowired
	private ReportService reportService;

	/**
	 * 读取数据
	 *
	 * @param file 文件名称
	 * @return list
	 */
	@PostMapping("/readExcel")
	@SneakyThrows
	public void readExcel(@RequestParam("file") MultipartFile file,
						  @RequestParam("tenantId") String tenantId,
						  @RequestParam("orgType") String orgType) {
		if (StrUtil.equalsIgnoreCase(Const.SN, tenantId)) {
			if (StrUtil.equalsIgnoreCase(Const.G5, orgType)) {

				//1、最简单的读,不用额外写一个Listener
				//simpleReadExcel(file);

				//2、匿名内部类读取
				//annoyReadExcel(file);

				//3、指定监听类
				sn5gListenerReadExcel(file);

				//4、reader 读取
				//readerExcel(file);

				//5、全sheet读取
				//mapReadExcel(file);

			}
		} else {
			System.out.println("待定……");
		}
	}

	@SneakyThrows
	private void mapReadExcel(MultipartFile file) {
		List<Map<Integer, String>> listMap = EasyExcel.read(file.getInputStream()).sheet().doReadSync();
		for (Map<Integer, String> data : listMap) {
			// 返回每条数据的键值对 分别表示所在的列和所在列的值
			log.info("读取到数据:{}", JSON.toJSONString(data));
		}
	}

	@SneakyThrows
	private void readerExcel(MultipartFile file) {
		// 一个文件一个reader 也需要指定监听器
		ExcelReader excelReader = null;
		try {
			excelReader = EasyExcel.read(file.getInputStream(), Sn5g.class, new Sn5GListener()).build();
			// 构建一个sheet 这里可以指定名字或者no
			ReadSheet readSheet = EasyExcel.readSheet(0).build();
			// 读取一个sheet
			excelReader.read(readSheet);
		} finally {
			if (excelReader != null) {
				// 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
				excelReader.finish();
			}
		}
	}

	@SneakyThrows
	private void sn5gListenerReadExcel(MultipartFile file) {
		//封装excel对应实体进行读取，需要编写指定实体类的监听类
		EasyExcel.read(file.getInputStream(), Sn5g.class, new Sn5GListener(reportService)).sheet().doRead();
	}

	@SneakyThrows
	private void annoyReadExcel(MultipartFile file) {
		// 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
		EasyExcel.read(file.getInputStream(), Sn5g.class, new ReadListener<Sn5g>() {
			/**
			 * 单次缓存的数据量
			 */
			public static final int BATCH_COUNT = 100;
			/**
			 * 临时存储
			 */
			private List<Sn5g> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

			@Override
			public void invoke(Sn5g data, AnalysisContext context) {
				cachedDataList.add(data);
				if (cachedDataList.size() >= BATCH_COUNT) {
					saveData();
					// 存储完成清理 list
					cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
				}
			}

			@Override
			public void doAfterAllAnalysed(AnalysisContext context) {
				saveData();
			}

			/**
			 * 加上存储数据库
			 */
			private void saveData() {
				log.info("{}条数据，开始存储数据库！", cachedDataList.size());
				log.info("存储数据库成功！");
			}
		}).sheet().doRead();
	}

	/**
	 * 编写指定的监听器
	 *
	 * @param file
	 */
	@SneakyThrows
	private void simpleReadExcel(MultipartFile file) {
		EasyExcel.read(file.getInputStream(), Sn5g.class, new PageReadListener<Sn5g>(dataList -> {
			for (Sn5g demoData : dataList) {
				log.info("读取到一条数据{}", JSON.toJSONString(demoData));
			}
		})).sheet().doRead();
	}
}

