package com.zukxu.test.refn;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zukxu.test.utils.JDBCUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class SatisfactionService {
    private static final Logger log = LoggerFactory.getLogger(SatisfactionService.class);

    public static void main(String[] args) {
        Connection conn = null;
        Statement stat = null;
        ResultSet res = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from test_satisfaction_t_s_config where org_type='phone' order by sort";
            log.info(sql);
            stat = conn.createStatement();
            res = stat.executeQuery(sql);
            List<SatisfactionTSConfig> list = new ArrayList<>();
            while (res.next()) {
                SatisfactionTSConfig build = SatisfactionTSConfig.builder()
                        .id(res.getString("id"))
                        //.orgType(res.getString("org_type"))
                        .tableHead(res.getString("table_head"))
                        .parentId(res.getString("parent_id"))
                        //.sort(res.getInt("sort"))
                        .build();
                list.add(build);
            }
            SatisfactionService satisfactionService = new SatisfactionService();
            List<TreeSelect> treeSelects = satisfactionService.buildTreeSelect(list);
            log.info(JSON.toJSONString(treeSelects));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(res, stat, conn);
        }
    }

    public List<TreeSelect> buildTreeSelect(List<SatisfactionTSConfig> tsList) {
        List<SatisfactionTSConfig> tsTrees = buildDeptTree(tsList);
        return tsTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public List<SatisfactionTSConfig> buildDeptTree(List<SatisfactionTSConfig> tsList) {
        List<SatisfactionTSConfig> returnList = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        for (SatisfactionTSConfig dept : tsList) {
            idList.add(dept.getId());
        }
        for (SatisfactionTSConfig ts : tsList) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!idList.contains(ts.getParentId())) {
                recursionFn(tsList, ts);
                returnList.add(ts);
            }
        }
        if (returnList.isEmpty()) {
            returnList = tsList;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SatisfactionTSConfig> list, SatisfactionTSConfig t) {
        // 得到子节点列表
        List<SatisfactionTSConfig> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SatisfactionTSConfig tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }
    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SatisfactionTSConfig> list, SatisfactionTSConfig t) {
        return getChildList(list, t).size() > 0;
    }
    /**
     * 得到子节点列表
     */
    private List<SatisfactionTSConfig> getChildList(List<SatisfactionTSConfig> list, SatisfactionTSConfig t) {
        return list.stream()
                .filter(n -> ObjectUtil.isNotNull(n.getParentId()) && StrUtil.contains(n.getParentId(), t.getId()))
                .collect(Collectors.toList());
    }


}
