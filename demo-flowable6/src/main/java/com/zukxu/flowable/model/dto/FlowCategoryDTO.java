package com.zukxu.flowable.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 分类DTO
 * </p>
 *
 * @author xupu
 * @since 2021-12-21 10:32
 */
@Data
@Accessors(chain = true)
public class FlowCategoryDTO implements Serializable {
    private static final long serialVersionUID = -1L;

    private String dictValue;
    private String dictLabel;
}
