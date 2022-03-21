package com.zukxu.flowable.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zukxu.flowable.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 流程表单对象 sys_form
 * </p>
 *
 * @author xupu
 * @since 2021/12/14 10:15
 */
@Data
@ApiModel
@EqualsAndHashCode(callSuper = true)
@TableName("sys_form")
public class SysForm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表单主键
     */
    @TableId
    private String formId;

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 表单内容
     */
    private String formContent;

}
