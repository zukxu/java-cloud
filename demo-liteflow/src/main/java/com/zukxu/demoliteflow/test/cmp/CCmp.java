package com.zukxu.demoliteflow.test.cmp;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/7/28 14:33:15
 */
@Component("c")
public class CCmp extends NodeComponent {

    @Override
    public void process() {
        //do your business
        System.out.println("Running C …………");
    }

}
