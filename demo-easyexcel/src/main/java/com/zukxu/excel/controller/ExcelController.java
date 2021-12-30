package com.zukxu.excel.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.zukxu.excel.constant.Const;
import com.zukxu.excel.listener.NoModelDataListener;
import com.zukxu.excel.model.satisfaction.Sn5g;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
				List<Sn5g> list = new ArrayList<>();
				list = EasyExcel.read(file.getInputStream(), Sn5g.class, new NoModelDataListener()).sheet().doReadSync();
				System.out.println(JSON.toJSONString(list));
			}
		} else {
			System.out.println("待定……");
		}
	}

}

