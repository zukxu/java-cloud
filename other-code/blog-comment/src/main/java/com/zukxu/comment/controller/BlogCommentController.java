package com.zukxu.comment.controller;

import com.zukxu.comment.dto.BlogCommentDTO;
import com.zukxu.comment.service.IBlogCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Description:博客评论接口控制层
 *
 * @author zukxu
 * CreateTime: 2020/9/21 0021 9:37
 */
@RestController
@RequestMapping("BlogComment")
@AllArgsConstructor
@Api(tags = "博客评论控制层")
public class BlogCommentController {
    @Autowired
    private final IBlogCommentService BlogCommentService;


    @ApiOperation("分页查询评论数据")
    @GetMapping("/pageInfo")
    @ApiImplicitParams({@ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1"), @ApiImplicitParam(name = "size", value = "每页数量", defaultValue = "10"), @ApiImplicitParam(name = "blogId", value = "视频Id")})
    public List<BlogCommentDTO> pageInfoApp(Integer current, Integer size, String blogId) {
        return BlogCommentService.pageInfo(current, size, blogId);
    }
}
