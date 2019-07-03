package com.guolala.zxx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guolala.zxx.dao.CommentMapper;
import com.guolala.zxx.entity.model.Comment;
import com.guolala.zxx.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/22
 * @Description:
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        commentMapper.insert(comment);
    }

    @Override
    public PageInfo<Comment> getCommentsByPage(String goodsId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = this.getCommentList(goodsId);
        return new PageInfo<>(list);
    }

    private List<Comment> getCommentList(String goodsId) {
        return commentMapper.selectByGoodsId(goodsId);
    }
}
