package com.zukxu.mybatis.inserts.handler;

import com.zukxu.mybatis.inserts.model.SysUser;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 自定义结果集handler
 * </p>
 *
 * @author xupu
 * @since 2022/8/26 11:15:25
 */
public class DemoResultHandler implements ResultHandler<SysUser> {

    //@formatter:off
    // 这是每批处理的大小
    private final static int BATCH_SIZE = 1000;
    private final List<SysUser> list;
    private int size;
    // 存储每批数据的临时容器
    private Set<String> temp;
    //@formatter:on

    public DemoResultHandler() {
        this.list = new ArrayList<>();
    }

    @Override
    public void handleResult(ResultContext<? extends SysUser> resultContext) {
        // 这里获取流式查询每次返回的单条结果
        SysUser resultObject = resultContext.getResultObject();
        // 你可以看自己的项目需要分批进行处理或者单个处理，这里以分批处理为例
        resultObject.setUserName(resultObject.getUserName() + "HANDLER");
        list.add(resultObject);
        size++;
        if (size == BATCH_SIZE) {
            temp.add("a");
            handle();
        }
    }

    private void handle() {
        try {
            //TODO 在这里可以对你获取到的批量结果数据进行需要的业务处理
        } finally {
            // 处理完每批数据后后将临时清空
            size = 0;
            temp.clear();
        }
    }

    /**
     * 这个方法给外面调用，用来完成最后一批不到BATCH_SIZE的数据处理
     */
    public void end() {
        handle();
    }

    public List<SysUser> getResultList() {
        return this.list;
    }

}
