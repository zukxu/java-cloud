package com.zukxu.gencode.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 分页查询相关实体
 *
 * @author xupu
 * @Date 2021-11-01 14:33
 */
@Data
@ApiModel("分页相关实体")
@Accessors(chain = true)
public class PageBody {
    @ApiModelProperty("当前页")
    private Integer page = 1;
    @ApiModelProperty("每页数量")
    private Integer size = 10;
    @ApiModelProperty("排序列")
    private String orderBy;
    @ApiModelProperty("搜索值")
    private String searchValue;
    @ApiModelProperty("查询参数")
    private Map<String, Object> queryParams;
}
