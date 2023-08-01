package com.zukxu.test.refn;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReFnEntity {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    private String pId;

    @ApiModelProperty(value = "子集")
    private List<ReFnEntity> children;

}
