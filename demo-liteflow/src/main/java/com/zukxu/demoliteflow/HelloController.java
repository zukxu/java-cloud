package com.zukxu.demoliteflow;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/liteFlow")
@RequiredArgsConstructor
public class HelloController {

    //@formatter:off
    private final ChainExecute chainExecute;
    //@formatter:on

    @GetMapping("/hello1")
    public void hello() {
        chainExecute.testConfig();
    }

}
