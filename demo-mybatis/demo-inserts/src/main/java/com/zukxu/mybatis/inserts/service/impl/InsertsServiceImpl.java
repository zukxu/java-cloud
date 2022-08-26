package com.zukxu.mybatis.inserts.service.impl;

import cn.hutool.core.date.StopWatch;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zukxu.mybatis.inserts.handler.DemoResultHandler;
import com.zukxu.mybatis.inserts.handler.ExcelResultHandler;
import com.zukxu.mybatis.inserts.mapper.DemoMybatisInsertsMapper;
import com.zukxu.mybatis.inserts.model.DemoMybatisInserts;
import com.zukxu.mybatis.inserts.service.InsertsService;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
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
public class InsertsServiceImpl extends ServiceImpl<DemoMybatisInsertsMapper, DemoMybatisInserts> implements InsertsService {

    @Resource
    private DemoMybatisInsertsMapper demoMybatisInsertsMapper;
    @Override
    public List<DemoMybatisInserts> list(Integer limit) throws IOException {
        try(Cursor<DemoMybatisInserts> cursor = demoMybatisInsertsMapper.scan(limit)) {
            cursor.forEach(rows -> {

            });
        }
        return null;
    }

    @Override
    public void export(DemoMybatisInserts demoMybatisInserts) {
        StopWatch sw = new StopWatch();
        sw.start();
        //定义导出的的表头，以及每个表头字段对应的对象变量名
        List<String> headerArray = Arrays.asList("姓名", "工号");
        List<String> fieldArray = Arrays.asList("username", "No");

        //定义要导出的excel的文件名，不带"xlsx"后缀。
        String exportExcelFileName = "文件测试";
        QueryWrapper<DemoMybatisInserts> wrapper = new QueryWrapper<>();
        wrapper.apply("id%7=0");
        //每次导出new一个handler对象，将headerArray,fieldArray,exportExcelFileName传递进去。
        ExcelResultHandler<DemoMybatisInserts> handler = new ExcelResultHandler<>(headerArray, fieldArray, exportExcelFileName) {
            public void tryFetchDataAndWriteToExcel() {
                //这里的this,ExcelResultHandler<DemoMybatisInserts> handler这个对象，在这里写mapper调用获取数据的调用
                demoMybatisInsertsMapper.scanMapper(wrapper,this);
            }
        };

        //真正调用excel的导出开始，在方法中exportExcel会调用写excel表头，
        //然后调用tryFetchDataAndWriteToExcel，进行驱动调用userMapper的方法，然后遍历结果集，一条一条写入excel,最后关闭盯应的流资源。
        handler.startExportExcel();
        sw.stop();
        System.out.println("=======================================");
        System.out.println(sw.getTotalTimeMillis());
    }

}
