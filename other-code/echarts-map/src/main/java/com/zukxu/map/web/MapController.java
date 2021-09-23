package com.zukxu.map.web;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 疫情地图控制器
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/14 0014 17:23
 */
@RestController
@RequestMapping("/map")
public class MapController {
	@GetMapping
	public JSONObject getMapData() {
		String url = "http://zaixianke.com/yq/all";
		String s = HttpUtil.get(url);
		return JSONObject.parseObject(s);
	}

	@GetMapping("/ai")
	public JSONObject getAiData(@PathParam("q") String question) {
		String url = "https://api.jisuapi.com/iqa/query?appkey=62958a3a6ef3c56d&question=" + HttpUtil.encodeParams(question, StandardCharsets.UTF_8);
		String s = HttpUtil.get(url);
		return JSONObject.parseObject(s);

	}

	@GetMapping("/js")
	public String getJs() {
		String url = "http://cdn.zaixianke.com/china.js";
		String s = HttpUtil.get(url);
		Console.log(s);
		return s;

	}
}
