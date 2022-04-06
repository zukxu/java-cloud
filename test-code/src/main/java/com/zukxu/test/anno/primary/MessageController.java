package com.zukxu.test.anno.primary;

import com.zukxu.common.config.aop.PostSingleParam;
import com.zukxu.common.result.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-06 9:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("message")
public class MessageController {
    private MessageService messageService;

    @PostMapping("send")
    public R<?> sendMsg(@PostSingleParam String msg) {
        return R.ok(messageService.sendMessage(msg));
    }
}
