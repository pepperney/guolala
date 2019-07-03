package com.guolala.zxx;

import com.guolala.zxx.util.AESUtil;
import com.guolala.zxx.util.GUtil;
import com.guolala.zxx.util.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: pei.nie
 * @Date:2019/4/18
 * @Description:
 */
public class GTest {



    @Test
    public void testSign() {
        String requestId = GUtil.getUUID();
        System.out.println(requestId);
        String sign = GUtil.getSignStr(requestId);
        System.out.println(sign);
    }


    @Test
    public void testAES() {
        String data = "aaaabbbb";
        String key = "1234abcd5678EFGH";
        String encryptData = AESUtil.encryptData(data, key);
        System.out.println(encryptData);
        String decryptData = AESUtil.decryprtData(encryptData, key);
        System.out.println(decryptData);
    }

    @Test
    public void testToken(){
        String token = GUtil.getTokenStr(1);
        System.out.println(token);
    }


    @Test
    public void testOrderNo(){
        Map<Integer,String> map = new TreeMap<>();
        for(int i=1;i<=10;i++){
            map.put(i,GUtil.getOrderNo());
        }
        System.out.println(JsonUtil.toJson(map));
    }


}
