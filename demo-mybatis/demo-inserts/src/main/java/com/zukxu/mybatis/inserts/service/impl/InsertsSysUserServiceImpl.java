package com.zukxu.mybatis.inserts.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zukxu.mybatis.inserts.mapper.SysUserMapper;
import com.zukxu.mybatis.inserts.model.SysUser;
import com.zukxu.mybatis.inserts.service.InsertsSysUserService;
import com.zukxu.mybatis.inserts.utils.CustomResultHandler;
import com.zukxu.mybatis.inserts.utils.DownloadProcessor;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.cursor.Cursor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/25 10:32:26
 */
@Service
public class InsertsSysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements InsertsSysUserService {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> list(Integer limit) throws IOException {
        List<SysUser> list = new ArrayList<>();
        try (Cursor<SysUser> cursor = sysUserMapper.scan(limit)) {

        }
        return list;
    }

    @Override
    public void export(SysUser demoMybatisInserts) {
        StopWatch sw = new StopWatch();
        sw.start();
        sw.stop();
    }

    /**
     * stream读数据写文件方式
     */
    public void streamDownload(HttpServletResponse httpServletResponse) throws IOException {
        CustomResultHandler customResultHandler = new CustomResultHandler(new DownloadProcessor(httpServletResponse));
        sqlSessionTemplate.select("com.zukxu.mybatis.inserts.mapper.SysUserMapper.streamByExample", new SysUser(), customResultHandler);
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }

    /**
     * 传统下载方式
     */
    public void traditionDownload(HttpServletResponse httpServletResponse) throws IOException {
        List<SysUser> authors = sysUserMapper.selectByExample(new SysUser());
        DownloadProcessor downloadProcessor = new DownloadProcessor(httpServletResponse);
        authors.forEach(downloadProcessor::processData);
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }
}
