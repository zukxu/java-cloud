package com.zukxu.flowable.model.dto;

import com.zukxu.flowable.common.entity.SysRole;
import com.zukxu.flowable.common.entity.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 动态人员、组
 * </p>
 *
 * @author xupu
 * @since 2021/12/16 17:15
 */
@Data
public class FlowNextDTO implements Serializable {

    private static final long serialVersionUID = 5191128519586577496L;
    private String type;

    private String vars;

    private List<SysUser> userList;

    private List<SysRole> roleList;
}
