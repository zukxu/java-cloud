package com.zukxu.hutool;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2024/10/16 14:18:15
 */
@RestController
@RequestMapping("/hutool")
public class HutoolController {
    @PostMapping("upload")
    public String fileUpload(MultipartFile file) {
        return "success";
    }
}
