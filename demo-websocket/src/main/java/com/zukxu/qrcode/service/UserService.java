package com.zukxu.qrcode.service;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSONObject;
import com.zukxu.qrcode.entity.UserEntity;
import com.zukxu.qrcode.mapper.UserMapper;
import com.zukxu.qrcode.server.QrcodeSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author zukxu
 * CreateTime: 2021/7/13 0013 16:54
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public String createQRC() {
        String uuid = String.valueOf(UUID.randomUUID());
        UserEntity user = new UserEntity().setUuid(uuid).setUserId("1").setCreateTime(LocalDateTime.now()).setState(0);
        userMapper.addQRC(user);
        return uuid;
    }

    public String bindUserIdAndToken(Integer userId, String token, Integer projId) throws Exception {

        UserEntity user = userMapper.selectOne(userId, token);

        if (null == user) {
            throw new Exception("错误的请求！");
        }

        LocalDateTime createTime = user.getCreateTime();
        if (LocalDateTime.now().isAfter(createTime.plusMinutes(30))) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 500);
            jsonObject.put("msg", "二维码失效！");
            QrcodeSocketServer.sendInfo(jsonObject.toJSONString(), token);

            throw new Exception("二维码失效！");
        }

        user.setLoginTime(LocalDateTime.now());

        int i = userMapper.updateById(user);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);
        jsonObject.put("msg", "ok");
        jsonObject.put("userId", userId);

        QrcodeSocketServer.sendInfo(jsonObject.toJSONString(), token);

        if (i > 0) {
            return null;
        } else {
            throw new Exception("服务器异常！");
        }
    }
}
