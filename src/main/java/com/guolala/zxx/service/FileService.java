package com.guolala.zxx.service;

import com.guolala.zxx.entity.req.FileUploadReq;
import org.springframework.http.ResponseEntity;

/**
 * @Author: pei.nie
 * @Date:2019/4/18
 * @Description:
 */
public interface FileService {

    /**
     * 文件上传
     *
     * @param fileUploadReq
     * @return
     */
    String upload(FileUploadReq fileUploadReq);

    /**
     * 文件下载
     *
     * @param fileId
     * @return
     */
    ResponseEntity<byte[]> download(Integer fileId);


}
