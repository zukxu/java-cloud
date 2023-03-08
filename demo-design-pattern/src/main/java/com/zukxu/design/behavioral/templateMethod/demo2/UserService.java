package com.zukxu.design.behavioral.templateMethod.demo2;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 我们通过继承BaseUserService类来实现用户管理的功能。
 * 在这个具体实现中，我们重写了beforeSave和beforeDelete方法，分别用于校验用户信息和校验用户权限
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 15:31:32
 */
@Service
public class UserService extends BaseUserService<User> {

    @Override
    protected void beforeSave(User user) {
        // 校验用户信息
        if(StringUtils.isEmpty(user.getName())) {
            throw new RuntimeException("User name cannot be empty");
        }
        if(StringUtils.isEmpty(user.getEmail())) {
            throw new RuntimeException("User email cannot be empty");
        }
    }

    @Override
    protected void beforeDelete(User user) {
        // 校验用户权限
        if(!user.isAdmin()) {
            throw new RuntimeException("Only admin users can delete users");
        }
    }

}

