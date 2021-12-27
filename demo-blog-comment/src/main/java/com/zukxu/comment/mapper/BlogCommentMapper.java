package com.zukxu.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zukxu.comment.dto.BlogCommentDTO;
import com.zukxu.comment.entity.BlogComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wangziqi
 * @since 2020-09-15
 */
@Mapper
public interface BlogCommentMapper extends BaseMapper<BlogComment> {
    /**
     * 通过id进行查询返回DTO对象
     *
     * @param blogId   视频id
     * @param parentId 父id
     * @param current  当前页
     * @param size     数量
     * @return list
     */
    List<BlogCommentDTO> selectCommentById(@Param("blogId") String blogId, @Param("parentId") String parentId, @Param("current") Integer current, @Param("size") Integer size);
}
