package com.zukxu.validator.entity;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/3/9 0009 10:33
 */
@Data
public class Demo3 {
    @Size(min = 3, max = 5, message = "list的Size在[3,5]")
    private List<String> list;

    @NotNull
    @Valid
    private Demo2 demo2;
}
