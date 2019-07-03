package com.guolala.zxx.service.impl;

import com.guolala.zxx.constant.SysCode;
import com.guolala.zxx.dao.AttachmentMapper;
import com.guolala.zxx.entity.model.Attachment;
import com.guolala.zxx.entity.vo.FileUploadVo;
import com.guolala.zxx.exception.GLLException;
import com.guolala.zxx.service.FileService;
import com.guolala.zxx.util.GUtil;
import com.guolala.zxx.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Base64;
import java.util.Date;

/**
 * @Author: pei.nie
 * @Date:2019/4/18
 * @Description:
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Value("${files.path}")
    private String fileDirectory;


    @Override
    public String upload(FileUploadVo fileUploadVo) {
        ValidateUtil.validateParam(fileUploadVo);
        if (StringUtils.isEmpty(fileUploadVo.getFileName()) && StringUtils.isEmpty(fileUploadVo.getFileSuffix())) {
            throw new GLLException(SysCode.ILLEGAL_PARAM, "文件后缀不能为空");
        }
        String fileData = fileUploadVo.getFileData();
        fileData = fileData.replace("\r\n", "");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(fileData);//将字符串转换成文件流
        String filename = fileUploadVo.getFileName();
        if (StringUtils.isEmpty(filename)) {
            filename = GUtil.getUUID() + "." + fileUploadVo.getFileSuffix().replace(".", "");
        }
        String filePath = fileDirectory + File.separator + fileUploadVo.getBizType() + File.separator + filename;
        File file = new File(filePath);
        try (InputStream is = new ByteArrayInputStream(bytes); OutputStream os = new FileOutputStream(file);) {
            byte temp[] = new byte[1024];
            int size = -1;
            while ((size = is.read(temp)) != -1) { // 每次读取1KB，直至读完
                os.write(temp, 0, size);
            }
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new GLLException(SysCode.FILE_UPLOAD_FAIL);
        }
        Attachment attachment = new Attachment();
        attachment.setBizNo(fileUploadVo.getBizNo());
        attachment.setBizType(fileUploadVo.getBizType());
        attachment.setFilePath(filePath);
        attachment.setCreateTime(new Date());
        Integer fileId = attachmentMapper.insert(attachment);
        return String.valueOf(fileId);
    }

    @Override
    public ResponseEntity<byte[]> download(Integer fileId) {
        ResponseEntity<byte[]> file = null;
        log.info("开始下载文件[{}]", fileId);
        Attachment attachment = attachmentMapper.selectByPrimaryKey(fileId);
        if (null == attachment) {
            throw new GLLException(SysCode.FILE_NOT_FOUND);
        }
        File sourceFile = new File(attachment.getFilePath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            headers.setContentDispositionFormData("attachment", new String(sourceFile.getName().getBytes("UTF-8"), "ISO8859-1"));
            file = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(sourceFile), headers, HttpStatus.OK);
            log.info("文件[{}]下载成功", fileId);
        } catch (Exception e) {
            log.error("文件[{}]下载失败", fileId, e);
            throw new GLLException(SysCode.FILE_DOWNLOAD_FAIL);
        }
        return file;
    }


}
