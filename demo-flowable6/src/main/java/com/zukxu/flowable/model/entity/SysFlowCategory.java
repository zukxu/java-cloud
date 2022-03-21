package com.zukxu.flowable.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 流程表单分类实体
 * </p>
 *
 * @author xupu
 * @since 2021/12/14 10:15
 */
@Data
@ApiModel
public class SysFlowCategory implements Serializable {
    private static final long serialVersionUID = -1L;

    @TableId("id")
    private String categoryId;
    /**
     * 分类名称
     */
    private String categoryName;

}
