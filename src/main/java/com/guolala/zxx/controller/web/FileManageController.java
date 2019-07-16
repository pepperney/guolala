package com.guolala.zxx.controller.web;

import com.guolala.zxx.entity.req.FileUploadReq;
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
 * @Date:2019/7/3
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("/web/file")
@Api(value = "【web】 文件相关接口", tags = {"FileManageController"})
public class FileManageController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    @ResponseBody
    @ApiOperation(value = "文件上传", httpMethod = "POST", notes = "使用base64方式提交文件内容")
    public String upload(@RequestBody FileUploadReq fileUploadReq) {
        String fileId = fileService.upload(fileUploadReq);
        return fileId;
    }


    @GetMapping("/download")
    @ApiOperation(value = "文件下载", httpMethod = "GET", notes = "")
    public ResponseEntity<byte[]> download(@RequestParam("fileId") Integer fileId) throws Exception {
        ResponseEntity<byte[]> file = fileService.download(fileId);
        return file;
    }

    @ApiIgnore
    @GetMapping("/downloadLocal")
    public void downloadLocal(HttpServletResponse resp, @RequestParam("name") String name) throws Exception {
        log.info("开始下载文件[{}]", name);
        File file = new File(name);
        InputStream in = new FileInputStream(file);

        String fileName = URLEncoder.encode(name, "UTF-8");//对文件进行url编码
        resp.setContentType("application/force-download");//应用程序强制下载
        resp.setHeader("Content-Disposition", "attachment;filename=" + fileName); //设置响应头
        resp.setContentLength(in.available());

        OutputStream out = resp.getOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }
        out.flush();
        out.close();
        in.close();
        log.info("结束下载文件[{}]", name);
    }


}
