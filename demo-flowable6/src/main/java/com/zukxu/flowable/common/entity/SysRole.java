package com.zukxu.flowable.common.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 角色表 sys_role
 *
 * @author zukxu
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId("id")
    private String id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 30, message = "角色名称长度不能超过30个字符")
    private String roleName;

    /**
     * 角色权限
     */
    @NotBlank(message = "权限字符不能为空")
    @Size(max = 100, message = "权限字符长度不能超过100个字符")
    private String roleCode;

    /**
     * 角色类别（0管理员角色 1普通角色）
     */
    @ApiModelProperty("角色类别（0管理员角色 1普通角色）默认普通角色")
    private String roleType;

    /**
     * 角色排序
     */
    private Integer sort;

    /**
     * 角色状态（0正常 1停用）
     */
    @ApiModelProperty("角色状态（0正常 1停用）")
    private String status;

    /**
     * 菜单组
     */
    @TableField(exist = false)
    private String[] menuIds;

    /**
     * 部门组（数据权限）
     */
    @TableField(exist = false)
    private String[] deptIds;

    public SysRole() {}

    public SysRole(String id) {
        this.id = id;
    }

    public static boolean isAdmin(String roleId) {
        return StrUtil.isNotBlank(roleId) && "1".equals(roleId);
    }

    public boolean isAdmin() {
        return isAdmin(this.id);
    }

}
