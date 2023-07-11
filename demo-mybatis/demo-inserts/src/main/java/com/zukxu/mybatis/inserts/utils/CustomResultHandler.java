package com.zukxu.mybatis.inserts.utils;

import com.zukxu.mybatis.inserts.model.SysUser;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

/**
 * <p>
 * 自定义ResultHandler
 * </p>
 *
 * @author xupu
 * @since 2023/7/10 16:11:46
 */
public class CustomResultHandler implements ResultHandler {

    private final DownloadProcessor downloadProcessor;

    public CustomResultHandler(
            DownloadProcessor downloadProcessor) {
        super();
        this.downloadProcessor = downloadProcessor;
    }

    @Override
    public void handleResult(ResultContext resultContext) {
        SysUser authors = (SysUser) resultContext.getResultObject();
        downloadProcessor.processData(authors);
    }
}