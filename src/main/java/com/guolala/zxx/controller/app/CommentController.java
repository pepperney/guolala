package com.guolala.zxx.controller.app;

import com.github.pagehelper.PageInfo;
import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.model.Comment;
import com.guolala.zxx.entity.vo.GoodsVo;
import com.guolala.zxx.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: pei.nie
 * @Date:2019/4/11
 * @Description:
 */
@RestController
@RequestMapping("/app/comment")
@Api(value = "【app】 评论相关接口", tags = {"CommentController"})
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/v1/addCommont")
    @ApiOperation(value = "添加评论", httpMethod = "POST", notes = "")
    public void addComment(UserInfo userInfo, @RequestBody Comment comment) {
        commentService.saveComment(comment);
    }

    @GetMapping("/v1/list")
    @ApiOperation(value = "查看评论", httpMethod = "GET", notes = "")
    public PageInfo<Comment> listComments(@ApiParam(name = "goodsId", value = "商品id") String goodsId,
                                          @ApiParam(name = "pageNum", value = "分页的页码") @RequestParam(defaultValue = "1") Integer pageNum,
                                          @ApiParam(name = "pageSize", value = "每页条数") Integer pageSize) {
        return commentService.getCommentsByPage(goodsId, pageNum, pageSize);
    }

}
