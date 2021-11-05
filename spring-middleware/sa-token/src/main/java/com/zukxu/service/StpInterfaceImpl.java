package com.zukxu.service;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限相关服务
 *
 * @author xupu
 * @Date 2021-11-05 15:19
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    //@Autowired
    //private UserMapper userMapper;
    //
    //@Autowired
    //private UserRoleMapper userRoleMapper;
    //
    //@Autowired
    //private RoleMapper roleMapper;
    //
    //@Autowired
    //private PermissionMapper permissionMapper;
    //
    //@Autowired
    //private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<String> getPermissionList(Object userId, String s) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<>();
        list.add("101");
        list.add("user-add");
        list.add("user-delete");
        list.add("user-update");
        list.add("user-get");
        list.add("article-get");
        return list;
        // 用户存在，查找角色
        //LambdaQueryWrapper<UserRole> userRoleQueryWrapper = new LambdaQueryWrapper<>.eq(UserRole::getUserId, userId);
        //List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);
        //
        //// 角色查找权限
        //LambdaQueryWrapper<RolePermission> rolePermissionQueryWrapper = new LambdaQueryWrapper<>
        //        .in(RolePermission::getRoleId,
        //            userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
        //List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionQueryWrapper);
        //
        //LambdaQueryWrapper<SysPermission> permissionQueryWrapper = new LambdaQueryWrapper<>
        //        .in(SysPermission::getId,
        //            rolePermissions.stream()
        //                           .map(RolePermission::getPermissionId)
        //                           .distinct()
        //                           .collect(Collectors.toList()));
        //List<SysPermission> sysPermissions = permissionMapper.selectList(permissionQueryWrapper);
        //
        //return sysPermissions.stream()
        //                     .map(SysPermission::getCode)
        //                     .distinct()
        //                     .collect(Collectors.toList());

    }

    @Override
    public List<String> getRoleList(Object userId, String s) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("super-admin");
        return list;
        // 用户存在，查找角色
        //LambdaQueryWrapper<UserRole> userRoleQueryWrapper = new LambdaQueryWrapper<>().eq(UserRole::getUserId, userId);
        //List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);
        //
        //// 查询角色
        //LambdaQueryWrapper<SysRole> sysRoleQueryWrapper = new LambdaQueryWrapper<>()
        //        .in(SysRole::getId, userRoles.stream()
        //                                     .map(UserRole::getRoleId)
        //                                     .collect(Collectors.toList()));
        //List<SysRole> sysRoles = roleMapper.selectList(sysRoleQueryWrapper);
        //return sysRoles.stream().map(SysRole::getRoleName).distinct().collect(Collectors.toList());
    }

}
