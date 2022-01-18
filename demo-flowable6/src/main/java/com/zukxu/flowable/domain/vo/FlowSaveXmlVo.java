package com.zukxu.flowable.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * json 保存为xml实体
 * </p>
 *
 * @author xupu
 * @since 2021/12/15 20:57
 */
@Data
@Accessors(chain = true)
public class FlowSaveXmlVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程分类
     */
    private String category;

    /**
     * xml 文件
     */
    private String xml;
}
