package com.zukxu.alanpoi.service.impl;

import cn.hutool.core.date.StopWatch;
import com.alanpoi.analysis.common.ExportMultipleSheetParam;
import com.alanpoi.analysis.common.utils.ExcelExportUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zukxu.alanpoi.mapper.SysUserMapper;
import com.zukxu.alanpoi.model.SysUser;
import com.zukxu.alanpoi.model.SysUser2;
import com.zukxu.alanpoi.service.SysUserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public void export(String type, HttpServletRequest request, HttpServletResponse response, SysUser demoMybatisInserts) throws IOException {
        StopWatch sw = new StopWatch();
        sw.start();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.apply("sex=1");
        List<SysUser> list = sysUserMapper.scanMapper(wrapper);

        //方式一. 直接导出到浏览器
        ExcelExportUtil.export(list, SysUser.class, request, response, "test.xlsx");
        //方式二. 调用getWorkbook获取工作表,自行处理workbook
        Workbook workbook2 = ExcelExportUtil.getWorkbook(list, SysUser.class);
        //方式三：导出指定列
        List<String> colList = new ArrayList<>();
        //按照顺序仅导出add的列
        colList.add("user_name");
        colList.add("password");
        //调用获取workbook对象；也可以直接调用exportSpecifyCol方法导出到浏览器
        Workbook workbook3 = ExcelExportUtil.getWorkbookSpecifyCol(list, SysUser.class, colList);
        //ExcelExportUtil.exportSpecifyCol(ExcelType.EXCEL_2007,list,SysUser.class,request,response,"fileName.xlsx",colList);
        //方式四：多sheet导出
        ExportMultipleSheetParam param = new ExportMultipleSheetParam();
        param.put(0, "测试1", SysUser.class, list);
        List<SysUser2> list2 = new ArrayList<>();
        param.put(1, "测试2", SysUser2.class, list2);
        //调用获取workbook对象；也可以直接调用exportByMultiSheet方法导出到浏览器
        Workbook workbook = ExcelExportUtil.getByMultiSheet(param);
        //ExcelExportUtil.exportByMultiSheet(param, "多sheet导出.xlsx", request, response);
        sw.stop();
        System.out.println("=======================================");
        System.out.println(sw.getTotalTimeMillis());
    }

}
