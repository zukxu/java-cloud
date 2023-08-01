package com.zukxu.flowable.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户对象 sys_user
 *
 * @author zukxu
 */
@Data
@ApiModel
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId("id")
    private String id;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门编号")
    private String deptId;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户账号不能为空")
    @Size(max = 30, message = "用户账号长度不能超过30个字符")
    private String userName;

    /**
     * 登录名称
     */
    @Size(max = 30, message = "登录名长度不能超过30个字符")
    private String loginName;

}
