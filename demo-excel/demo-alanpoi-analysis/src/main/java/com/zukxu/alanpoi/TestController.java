package com.zukxu.alanpoi;

import com.alanpoi.analysis.common.utils.ExcelExportUtil;
import com.alanpoi.analysis.common.utils.ExcelImportUtil;
import com.zukxu.alanpoi.model.SysUser;
import com.zukxu.alanpoi.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 9:46:15
 */
@RestController
@RequestMapping("/alan")
@RequiredArgsConstructor
public class TestController {

    private final SysUserService sysUserService;

    @SneakyThrows
    @PostMapping("/import")
    public String importExcel(MultipartFile mFile) {
        InputStream ins = mFile.getInputStream();
        ExcelImportUtil.customImportData("sysUser", ins, mFile.getOriginalFilename());
        return "导入成功";
    }

    @GetMapping("/export/{type}")
    public void exportExcel(@PathVariable String type, HttpServletRequest request,HttpServletResponse response) throws IOException {
        sysUserService.export(type,request,response,new SysUser());
    }
}
