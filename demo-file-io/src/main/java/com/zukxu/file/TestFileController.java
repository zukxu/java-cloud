package com.zukxu.file;

import cn.hutool.core.io.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author xupu
 * @Date 2021-11-08 17:41
 */
@Controller
@RequestMapping("/test/file")
public class TestFileController {

    @PostMapping("/upload")
    public void upload(MultipartFile mfile) {
        //File file1 = new File("C:/Users/17747/Downloads/ceshi/02.jpg");
        String parentPath = "D:\\TEMP\\dark\\";
        File file = new File(mfile.getOriginalFilename());
        System.out.println("getAbsolutePath:" + FileUtil.getAbsolutePath(file));
        System.out.println("getName:" + FileUtil.getName(file));
        System.out.println("getMimeType:" + FileUtil.getMimeType(String.valueOf(file)));
        System.out.println("getPrefix:" + FileUtil.getPrefix(file));
        System.out.println("getSuffix:" + FileUtil.getSuffix(file));
        String path = parentPath + mfile.getOriginalFilename();
        System.out.println("touch:"+FileUtil.touch(new File(path)));
    }

}
