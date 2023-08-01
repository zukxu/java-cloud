package com.zukxu.test.anno.postconstruct;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-06 10:13
 */
@Component
public class DemoConstruct {
    private List<String> list = new ArrayList<>();

    @PostConstruct
    public void hello() {
        list.add("Hello");
        list.add("World");
    }

    @PreDestroy
    public void clean() {
        list.clear();
    }
}
