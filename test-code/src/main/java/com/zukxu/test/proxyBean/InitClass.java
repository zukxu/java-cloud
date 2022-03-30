package com.zukxu.test.proxyBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-03-30 11:55
 */
//@Component //TODO 测试该功能的时候打开
public class InitClass implements InitializingBean, ApplicationContextAware {

    ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        MyBean2 myBean2 = this.applicationContext.getBean(MyBean2.class); // 第一行
        MyBean2 myBean21 = this.applicationContext.getBean(MyBean2.class);  // 第二行
        MyBean1 myBean1 = this.applicationContext.getBean(MyBean1.class);    // 第三行
        myBean2.hello();
        myBean21.hello();
        myBean1.hello();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}