/**
 * Copyright &copy; 2016-2020 公众学业 All rights reserved.
 */
package com.zukxu.cv.common.web;

import cn.hutool.core.util.EscapeUtil;
import com.alibaba.fastjson.JSON;
import com.zukxu.cv.common.utils.Constants;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 控制器支持类
 */
public abstract class BaseController {

	/**
	 * 添加Model消息
	 *
	 * @param messages
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		model.addAttribute("message", sb.toString());
	}

	/**
	 * 添加Flash消息
	 *
	 * @param messages
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}


	/**
	 * 客户端返回JSON字符串
	 *
	 * @param response
	 * @param object
	 * @return
	 */
	protected void renderString(HttpServletResponse response, Object object) {
		try {
			response.reset();
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			PrintWriter writer = response.getWriter();
			writer.write(JSON.toJSONString(object));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 客户端返回图片类型
	 *
	 * @param response
	 * @param object
	 */
	protected void renderImage(HttpServletResponse response, byte[] object) {
		try {
			response.reset();
			response.setContentType("image/*");
			ServletOutputStream output = response.getOutputStream();
			output.flush();
			output.write(object);
			output.close();
		} catch (IOException e) {
			// 如果是ClientAbortException异常，可以不用管，原因是页面参数变化太快，response请求被中断
			try {
				// response.reset();
				PrintWriter writer = response.getWriter();
				response.setContentType("text/html;charset=utf-8");
				writer.write("无法打开图片!");
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * 客户端返回字符串
	 *
	 * @param response
	 * @return
	 */
	protected void renderString(HttpServletResponse response) {
		renderString(response, Constants.SUCCESS);
	}

	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : EscapeUtil.escapeHtml4(text.trim()));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
	}

}
