package com.guolala.zxx.service;

import com.github.pagehelper.PageInfo;
import com.guolala.zxx.entity.model.Comment;

/**
 * @Author: pei.nie
 * @Date:2019/4/22
 * @Description:
 */
public interface CommentService {
    /**
     * 保存评论
     *
     * @param comment
     */
    void saveComment(Comment comment);

    /**
     * 分页查询评论
     *
     * @param goodsId
     * @param pageNum
     * @param pageSize
     */
    PageInfo<Comment> getCommentsByPage(String goodsId, Integer pageNum, Integer pageSize);
}
