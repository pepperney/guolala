package com.guolala.zxx.dao;

import com.guolala.zxx.entity.model.Attachment;
import java.util.List;

public interface AttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attachment record);

    Attachment selectByPrimaryKey(Integer id);

    List<Attachment> selectAll();

    int updateByPrimaryKey(Attachment record);
}