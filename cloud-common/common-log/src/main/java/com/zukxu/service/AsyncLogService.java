package com.zukxu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sun.security.util.SecurityConstants;

/**
 * <p>
 * 异步执行日志服务
 * </p>
 *
 * @author xupu
 * @since 2022/6/2 14:51:39
 */
@Service
public class AsyncLogService {

    @Autowired
    private RemoteLogService remoteLogService;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveSysLog(SysOperLog sysOperLog)
    {
        remoteLogService.saveLog(sysOperLog, SecurityConstants.INNER);
    }
}
