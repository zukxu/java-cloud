package com.zukxu.flowable.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 流程任务关联单对象
 * </p>
 *
 * @author xupu
 * @since 2021/12/14 10:16
 */
@Data
@ApiModel
public class SysFormTask implements Serializable {
    private static final long serialVersionUID = -1L;
    @TableId("id")
    private String id;
    /**
     * 表单主键
     */
    private String formId;

    /**
     * 所属任务
     */
    private String taskId;

}
