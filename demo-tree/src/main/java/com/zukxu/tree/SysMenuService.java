package com.zukxu.tree;

import com.alibaba.fastjson.JSON;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zukxu
 * @since 2022/1/2 20:38:41
 */
public class SysMenuService {

    private String driverClassName = "com.mysql.cj.jdbc.Driver";

    private String url = "jdbc:mysql://124.221.255.102:3306/java_cloud?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true";

    private String user = "root";

    private String password = "123456";

    public static void main(String[] args) {
        SysMenuService menuService = new SysMenuService();
        menuService.testTreeNode();
    }

    private void buildTree(List<SysMenu> menuList) {
        ConvertTree<SysMenu> convertTree = new ConvertTree<>();
        //硬编码
        List<TreeNode<SysMenu>> forest = convertTree.getForest(menuList, "id", "pid");
        System.out.println(JSON.toJSONString(forest));

        //注解方式
        List<TreeNode<SysMenu>> forest1 = convertTree.getForest(menuList);
        System.out.println(JSON.toJSONString(forest1));
    }

    private void testTreeNode() {
        Connection conn = null;
        Statement stat = null;
        ResultSet res = null;
        try {
            Class.forName(driverClassName);
            conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from sys_menu_tree";
            stat = conn.createStatement();
            res = stat.executeQuery(sql);
            List<SysMenu> menuList = new ArrayList<>();
            while(res.next()) {
                String id = res.getString("id");
                String name = res.getString("name");
                String pid = res.getString("pid");
                menuList.add(new SysMenu().setId(id).setName(name).setPid(pid));
            }
            buildTree(menuList);
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(res != null) {
                try {
                    res.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stat != null) {
                try {
                    stat.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
