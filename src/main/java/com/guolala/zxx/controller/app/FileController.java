package com.guolala.zxx.controller.app;

import com.guolala.zxx.entity.vo.FileUploadVo;
import com.guolala.zxx.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @Author: pei.nie
 * @Date:2019/4/18
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("/app/file")
@Api(value = "【app】 文件相关接口", tags = {"FileController"})
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/v1/upload")
    @ResponseBody
    @ApiOperation(value = "文件上传", httpMethod = "POST", notes = "使用base64方式提交文件内容")
    public String upload(@RequestBody FileUploadVo fileUploadVo) {
        String fileId = fileService.upload(fileUploadVo);
        return fileId;
    }


    @GetMapping("/v1/download")
    @ApiOperation(value = "文件下载", httpMethod = "GET", notes = "")
    public ResponseEntity<byte[]> download(@RequestParam("fileId") Integer fileId) throws Exception {
        ResponseEntity<byte[]> file = fileService.download(fileId);
        return file;
    }



}
