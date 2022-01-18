package com.zukxu.flowable.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author XuanXuan
 * @date 2021/3/28 15:50
 */
@Data
@Builder
public class FlowCommentDTO implements Serializable {

    private static final long serialVersionUID = 5888785837607392670L;
    /**
     * 意见类别 0 正常意见  1 退回意见 2 驳回意见
     */
    private String type;

    /**
     * 意见内容
     */
    private String comment;
}
