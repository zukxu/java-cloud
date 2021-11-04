package com.zukxu.java8.lambda.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xupu
 * @date 2021/11/4 21:33:03
 */
@Data
@Builder
@Accessors(chain = true)
public class Project {

    private String name;

    private Integer starts;

}
