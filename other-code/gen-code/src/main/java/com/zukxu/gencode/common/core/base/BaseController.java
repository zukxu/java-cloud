package com.zukxu.gencode.common.core.base;


import com.github.pagehelper.PageHelper;

/**
 * <p>
 * Controller 基类
 * </p>
 *
 * @author xupu
 * @since 2021-12-02 17:18
 */
public class BaseController {

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        startPage(1, 10);
    }

    protected void startPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
    }

}
