package com.zukxu.flowable.mapper;



import com.zukxu.flowable.domain.dto.FlowCategoryDTO;

import java.util.List;

/**
 * 流程相关mapper
 *
 * @author zukxu
 * @date 2021-03-30
 */
public interface FlowDefinitionMapper {
    List<FlowCategoryDTO> listCategory();

    String selectCategoryName(String categoryId);
}
