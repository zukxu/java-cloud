package com.zukxu.upload_big_file.annotation;


import com.zukxu.upload_big_file.enu.UploadModeEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Component
@Inherited
public @interface UploadMode {

    UploadModeEnum mode();

}
