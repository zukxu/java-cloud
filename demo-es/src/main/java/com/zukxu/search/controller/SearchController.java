package com.zukxu.search.controller;

import com.zukxu.search.service.RestHighLevelClientService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>
 * 搜素接口控制类
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/25 0025 11:29
 */
@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	RestHighLevelClientService service;

	@RequestMapping("/search")
	public String search() {
		SearchHits hits = null;
		try {
			SearchResponse search = service.search("name", "s", "idx_s");
			hits = search.getHits();
			Object[] collapseValues = hits.getCollapseValues();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return hits.toString();
	}
}
