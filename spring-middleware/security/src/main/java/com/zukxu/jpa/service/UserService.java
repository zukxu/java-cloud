package com.zukxu.jpa.service;

import com.zukxu.jpa.dao.UserDao;
import com.zukxu.jpa.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-26 11:50
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDao.findUserByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }
}