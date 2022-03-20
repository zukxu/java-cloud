package com.zukxu.test.refn;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2021-12-28 15:52
 */
@RestController
@RequestMapping("/refn")
public class ReFnController {

    private static final Logger log = LoggerFactory.getLogger(ReFnController.class);

    @GetMapping
    public List<TreeSelect> list() {
        Connection conn = null;
        Statement stat = null;
        ResultSet res = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from test_refn";
            log.info(sql);
            stat = conn.createStatement();
            res = stat.executeQuery(sql);
            List<ReFnEntity> list = new ArrayList<>();
            while(res.next()) {
                ReFnEntity build = ReFnEntity.builder()
                                             .id(res.getString("id"))
                                             .title(res.getString("title"))
                                             .pId(res.getString("p_id"))
                                             .build();
                list.add(build);
            }
            List<TreeSelect> treeSelects = this.buildTreeSelect(list);
            log.info(JSON.toJSONString(treeSelects));
            return treeSelects;
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(res, stat, conn);
        }
        return null;
    }

    public List<TreeSelect> buildTreeSelect(List<ReFnEntity> tsList) {
        List<ReFnEntity> tsTrees = buildTree(tsList);
        return tsTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public List<ReFnEntity> buildTree(List<ReFnEntity> tsList) {
        List<ReFnEntity> returnList = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        for(ReFnEntity reFn : tsList) {
            idList.add(reFn.getId());
        }
        for(ReFnEntity ts : tsList) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if(!idList.contains(ts.getPId())) {
                recursionFn(tsList, ts);
                returnList.add(ts);
            }
        }
        if(returnList.isEmpty()) {
            returnList = tsList;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ReFnEntity> list, ReFnEntity t) {
        // 得到子节点列表
        List<ReFnEntity> childList = getChildList(list, t);
        t.setChildren(childList);
        for(ReFnEntity tChild : childList) {
            if(hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ReFnEntity> list, ReFnEntity t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 得到子节点列表
     */
    private List<ReFnEntity> getChildList(List<ReFnEntity> list, ReFnEntity t) {
        return list.stream()
                   .filter(n -> ObjectUtil.isNotNull(n.getPId()) && StrUtil.contains(n.getPId(), t.getId()))
                   .collect(Collectors.toList());
    }


}
