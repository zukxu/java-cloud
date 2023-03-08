package com.zukxu.design.behavioral.state.demo2;

import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2023/3/8 16:19:47
 */
@Data
public class User {

    private Long id;

    private String name;

    private List<String> permissions;

    public boolean hasPermission(String permission) {
        return this.permissions.contains(permission);
    }

}
