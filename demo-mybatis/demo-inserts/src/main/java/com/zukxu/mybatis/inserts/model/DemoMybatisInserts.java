package com.zukxu.mybatis.inserts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoMybatisInserts {

    /**
     *id
     */
    private Integer id;

    /**
     *用户名
     */
    private String username;

    /**
     *密码
     */
    private String password;

    /**
     *工号
     */
    private String _No;

}

