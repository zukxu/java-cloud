package com.zukxu.shiro.config.realm;

import cn.hutool.core.util.ObjectUtil;
import com.zukxu.shiro.entity.Role;
import com.zukxu.shiro.entity.User;
import com.zukxu.shiro.service.PermissionService;
import com.zukxu.shiro.service.RoleService;
import com.zukxu.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author xupu
 * @Description 用户Realm，负责授权和认证用户
 * @Date 2021-09-24 9:51
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;

    /**
     * 执行授权
     *
     * @param principalCollection 凭证集合
     * @return 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1、获取登录对象
        User principal = (User) principalCollection.getPrimaryPrincipal();
        //2、生成授权集合实体
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //3、业务逻辑,从数据库获取权限数据,并将role放入一个Set集合中,role_id 放入一个list中
        Stream<Role> roleList = roleService.findRoleByUserId(principal.getId()).stream();
        Set<String> roleSet = new HashSet<>();
        List<Integer> roleIds = new ArrayList<>();
        roleList.forEach(role -> {
            roleSet.add(role.getRole());
            roleIds.add(role.getId());
        });
        //4、根据角色id获取相对应的权限
        Set<String> permIdSet = permissionService.findByRoleIds(roleIds);
        //5、将权限放入授权实体中
        authorizationInfo.setRoles(roleSet);
        authorizationInfo.setStringPermissions((permIdSet));

        return authorizationInfo;
    }

    /**
     * 执行认证
     *
     * @param authenticationToken token
     * @return 认证
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1、获取登录token
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findByUsername(token.getUsername());
        if (ObjectUtil.isNotNull(user))
            return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        return null;
    }
}
