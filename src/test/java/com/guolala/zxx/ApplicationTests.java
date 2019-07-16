package com.guolala.zxx;

import com.google.common.collect.Maps;
import com.guolala.zxx.entity.req.FileUploadReq;
import com.guolala.zxx.util.BeanUtil;
import com.guolala.zxx.util.HttpUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	private static final String LOCAL_HOST = "http://localhost:9012";


	@Test
	public void contextLoads() {
	}



	@Test
	public void fileUploadTest() throws Exception {
		FileInputStream fis = new FileInputStream(new File("E:\\eye.png"));
		byte[] dataByte = IOUtils.toByteArray(fis);
		Base64.Encoder encoder = Base64.getEncoder();
		String fileData = encoder.encodeToString(dataByte);
		FileUploadReq fileUploadReq = new FileUploadReq();
		fileUploadReq.setBizType("titles");
		fileUploadReq.setFileData(fileData);
		fileUploadReq.setFileName("eye.png");
		Map<String, String> heads = Maps.newHashMap();
		heads.put("sign", "0ec342fd5d09fea9df62494638be76bd");
		heads.put("requestId", "a7c8456245ca415f97a4008a6f3fec0e");
		heads.put("Content-Type","application/json");
		String respData = HttpUtil.post(LOCAL_HOST + "/file/v1/upload", BeanUtil.beanToMap(fileUploadReq), heads);
		System.out.println("\n" + respData + "\n");

	}

}
