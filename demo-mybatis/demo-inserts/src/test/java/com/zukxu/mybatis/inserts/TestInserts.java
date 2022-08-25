package com.zukxu.mybatis.inserts;

import com.zukxu.mybatis.inserts.model.DemoMybatisInserts;
import com.zukxu.mybatis.inserts.service.InsertsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/7/7 10:33:35
 */
@SpringBootTest
public class TestInserts {

    @Autowired
    private InsertsService insertsService;

    @Test
    void testDynamic() {
        List<DemoMybatisInserts> list = new ArrayList<>();
        for(int i = 0; i < 100000; i++) {
            list.add(DemoMybatisInserts.builder()
                                       .username("test" + i)
                                       .password("123456")
                                       .No("0119" + i)
                                       .build());
        }
        StopWatch sw = new StopWatch();
        sw.start();
        insertsService.saveBatch(list,10);
        sw.stop();
        System.out.println("总共耗时："+sw.getTotalTimeMillis());
    }

}
