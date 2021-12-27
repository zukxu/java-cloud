package com.zukxu.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zukxu.comment.entity.BlogComment;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Description:
 *
 * @author zukxu
 * CreateTime: 2020/10/30 0030  14:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "评论DTO对象")
@JsonIgnoreProperties(value = {"handler"})
public class BlogCommentDTO {
    private BlogComment comment;
    private List<BlogCommentDTO> children;
}