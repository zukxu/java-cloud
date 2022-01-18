package com.zukxu.flowable.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Xuan xuan
 * @date 2021/4/21 20:55
 */
@Data
public class FlowViewerDTO implements Serializable {

    private static final long serialVersionUID = -6148030788525268163L;
    private String key;
    private boolean completed;
}
