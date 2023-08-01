package com.zukxu.validator.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/9 0009 10:02
 */
@Data
public class User {

    private String id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 4, max = 8, message = "用户名长度不在4-8之间")
    private String name;

    @NotNull(message = "密码不能为空")
    @Pattern(regexp = "[0-9]\\d+", message = "密码不符合规范")
    private String password;

    @NotNull(message = "年龄不能为空")
    @Range(min = 1, max = 140, message = "年龄值不太正常")
    private Integer age;

    @Max(value = 10, message = "级别超过最大值了")
    @Min(value = 1, message = "级别低于最小值")
    private Integer level;

    private String mobile;
}
