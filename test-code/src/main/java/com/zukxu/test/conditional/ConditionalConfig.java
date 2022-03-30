package com.zukxu.test.conditional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-03-30 14:31
 */
@Slf4j
@Configuration
public class ConditionalConfig {
    @Bean
    public City city() {
        City city = new City();
        city.setCityName("贵阳市");
        return city;
    }

    @Bean
    //@ConditionalOnBean(City.class)
    //@ConditionalOnMissingBean(People.class)
    @Primary
    public People people(City city) {
        //这里如果city实体没有成功注入 这里就会报空指针
        city.setCityCode(851);
        return new People("小小", 3, city);
    }

    @Bean
    public People people2(City city) {
        city.setCityCode(852);
        city.setCityName("遵义市");
        return new People("大大", 4, city);
    }
}
