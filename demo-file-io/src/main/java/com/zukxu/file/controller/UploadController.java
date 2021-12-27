package com.zukxu.file.controller;

import com.zukxu.file.dto.FileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @author xupu
 * @Description 上传
 * @Date 2021-09-16 17:51
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
    @Value("${file.uploadPath}")
    private String UPLOAD_PATH;
    @Value("${file.returnPath}")
    private String RETURN_PATH;

    @PostMapping({"/files"})
    @ResponseBody
    public FileDTO uploadFiles(MultipartFile file) {
        //文件新名称
        Random rand = new Random();
        int random = Math.abs(rand.nextInt());
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String newFileName = random + fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

        //设置文件存储路径，可以存放在你想要指定的路径里面
        String filePath = UPLOAD_PATH + "/" + newFileName;
        File newFile = new File(filePath);
        //判断目标文件所在目录是否存在
        if (!newFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }

        //将内存中的数据写入磁盘
        try {
            //使用此方法保存必须要绝对路径且文件夹必须已存在,否则报错
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //图片上传保存url
        String url = RETURN_PATH + "/" + newFileName;
        FileDTO fileDTO = new FileDTO();
        fileDTO.setName(file.getOriginalFilename());
        fileDTO.setUrl(url);
        fileDTO.setUploadPath(filePath);

        return fileDTO;
    }

    /**
     * 删除
     *
     * @param url
     */
    @DeleteMapping("{url}")
    public void delete(@PathVariable("url") String url) {
        String name = url.substring(url.lastIndexOf("/") + 1);
        url = UPLOAD_PATH + name;
        File file = new File(url);
        file.delete();
    }
}
