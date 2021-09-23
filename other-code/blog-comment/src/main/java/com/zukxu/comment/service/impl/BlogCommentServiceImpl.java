package com.zukxu.comment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zukxu.comment.dto.BlogCommentDTO;
import com.zukxu.comment.entity.BlogComment;
import com.zukxu.comment.mapper.BlogCommentMapper;
import com.zukxu.comment.service.IBlogCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  博客评论实现类
 * </p>
 *
 * @author zukxu
 * CreateTime:  2021/2/7 0007  16:58
 *
 */
@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements IBlogCommentService {
	@Resource
	BlogCommentMapper BlogCommentMapper;

	@Override
	public List<BlogCommentDTO> pageInfo(Integer current, Integer size, String blogId) {
		List<BlogCommentDTO> dtoList = BlogCommentMapper.selectCommentById(blogId, "0", (current - 1) * size, size);
		// 这里将套娃关系处理为二层关系
		return findParent(dtoList);
	}

	/**
	 * 处理每个父级评论的子级及其嵌套子级
	 */
	public List<BlogCommentDTO> findParent(List<BlogCommentDTO> comments) {

		for (BlogCommentDTO comment : comments) {

			// 新建立一个新集合
			ArrayList<BlogCommentDTO> fatherChildren = new ArrayList<>();

			// 递归处理子级的回复，即回复内有回复
			findChildren(comment, fatherChildren);

			// 将递归处理后的集合放回父级的孩子中
			comment.setChildren(fatherChildren);
		}
		return comments;
	}

	/**
	 * 找到所有含有子级回复的一级评论
	 *
	 * @param parent 父级
	 * @param fatherChildren 两层型list
	 */
	public void findChildren(BlogCommentDTO parent, List<BlogCommentDTO> fatherChildren) {

		// 找出直接子级
		List<BlogCommentDTO> childrenList = parent.getChildren();

		// 遍历直接子级的子级
		for (BlogCommentDTO child : childrenList) {

			// 若非空，则还有子级，递归
			if (!child.getChildren().isEmpty()) {
				findChildren(child, fatherChildren);
			}

			// 已经到了最底层的嵌套关系，将该回复放入新建立的集合
			fatherChildren.add(child);

			// 容易忽略的地方：将相对底层的子级放入新建立的集合之后
			// 则表示解除了嵌套关系，对应的其父级的子级应该设为空
			child.setChildren(new ArrayList<>());
		}
	}
}
