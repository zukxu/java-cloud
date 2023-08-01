package com.zukxu.qrcode.mapper;

import com.zukxu.qrcode.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zukxu
 * CreateTime: 2021/7/13 0013 16:57
 */
@Mapper
public interface UserMapper {

    void addQRC(@Param("u") UserEntity user);

    UserEntity selectOne(Integer userId, String token);

    int updateById(UserEntity user);
}
