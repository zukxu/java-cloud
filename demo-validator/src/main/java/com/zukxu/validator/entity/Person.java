package com.zukxu.validator.entity;

import com.zukxu.validator.grop.GroupA;
import com.zukxu.validator.grop.GroupB;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/9 0009 10:43
 */
@Data
public class Person {
    /**
     * 用户id
     */
    @NotBlank
    @Range(min = 1, max = Integer.MAX_VALUE, message = "必须大于0", groups = {GroupA.class})
    private Integer userId;
    /**
     * 用户名
     */
    @NotBlank
    @Length(min = 4, max = 20, message = "必须在[4,20]", groups = {GroupB.class})
    private String userName;
    /**
     * 年龄
     */
    @NotBlank
    @Range(min = 0, max = 100, message = "年龄必须在[0,100]", groups = {Default.class})
    private Integer age;
    /**
     * 性别 0：未知；1：男；2：女
     */
    @Range(min = 0, max = 2, message = "性别必须在[0,2]", groups = {GroupB.class})
    private Integer sex;
}
