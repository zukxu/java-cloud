package com.zukxu.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zukxu.comment.dto.BlogCommentDTO;
import com.zukxu.comment.entity.BlogComment;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangziqi
 * @since 2020-09-15
 */
public interface IBlogCommentService extends IService<BlogComment> {
    /**
     * 分页接口
     *
     * @param current 当前页
     * @param size    数量
     * @param blogId  视频id
     * @return list
     */
    List<BlogCommentDTO> pageInfo(Integer current, Integer size, String blogId);
}
