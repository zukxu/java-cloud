package com.zukxu.jpa.dao;

import com.zukxu.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-26 11:48
 */
public interface UserDao extends JpaRepository<UserEntity, Long> {
    UserEntity findUserByUsername(String username);
}
