package com.zukxu.flowable.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 流程实例关联表单对象
 * </p>
 *
 * @author xupu
 * @since 2021/12/14 10:15
 */
@Data
@ApiModel
public class SysFormDeploy implements Serializable {
    private static final long serialVersionUID = -1L;

    @TableId("id")
    private String id;
    /**
     * 表单主键
     */
    private String formId;

    /**
     * 流程定义主键
     */
    private String deployId;

}
