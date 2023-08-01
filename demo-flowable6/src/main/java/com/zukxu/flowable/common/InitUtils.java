package com.zukxu.flowable.common;

import com.zukxu.flowable.common.entity.SysRole;
import com.zukxu.flowable.common.entity.SysUser;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-01-18 11:34
 */
public class InitUtils {
    public static List<SysUser> getUserList() {
        List<SysUser> list = new ArrayList<>();
        list.add(new SysUser().setUserName("admin").setId("1").setDeptId("100"));
        list.add(new SysUser().setUserName("小赵").setId("2").setDeptId("100"));
        list.add(new SysUser().setUserName("小钱").setId("3").setDeptId("100"));
        list.add(new SysUser().setUserName("小孙").setId("4").setDeptId("100"));
        list.add(new SysUser().setUserName("小李").setId("5").setDeptId("100"));
        list.add(new SysUser().setUserName("小周").setId("6").setDeptId("100"));
        list.add(new SysUser().setUserName("小吴").setId("7").setDeptId("100"));
        list.add(new SysUser().setUserName("小郑").setId("8").setDeptId("100"));
        list.add(new SysUser().setUserName("小王").setId("9").setDeptId("100"));
        list.add(new SysUser().setUserName("小冯").setId("10").setDeptId("100"));
        return list;
    }

    public static SysUser getUserByUserId(String userId) {
        List<SysUser> userList = getUserList();
        for (SysUser t : userList) {
            if (userId.equals(t.getId())) {
                return t;
            }
        }
        return null;
    }

    public static List<SysRole> getRoleList() {
        List<SysRole> list = new ArrayList<>();
        list.add(new SysRole().setRoleName("超级管理员").setRoleCode("superManager").setId("1"));
        list.add(new SysRole().setRoleName("普通角色").setRoleCode("common").setId("2"));
        list.add(new SysRole().setRoleName("三级经理").setRoleCode("thirdManager").setId("3"));
        list.add(new SysRole().setRoleName("二级经理").setRoleCode("secondManager").setId("4"));
        return list;
    }

    public static SysRole getRoleById(String roleId) {
        List<SysRole> roleList = getRoleList();
        for (SysRole t : roleList) {
            if (roleId.equals(t.getId())) {
                return t;
            }
        }
        return null;
    }
}
