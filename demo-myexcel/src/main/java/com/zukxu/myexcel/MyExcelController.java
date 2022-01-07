package com.zukxu.myexcel;

import com.zukxu.common.result.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 导入文件
 * </p>
 *
 * @author xupu
 * @since 2022-01-07 18:00
 */
@RestController
@RequestMapping("excel")
public class MyExcelController {
    @PostMapping
    public R<?> importExcel(MultipartFile file) {
        return R.ok();
    }
}
