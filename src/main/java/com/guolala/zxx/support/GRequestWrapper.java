package com.guolala.zxx.support;

import com.google.common.collect.Maps;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

/**
 * @Author: pei.nie
 * @Date:2019/4/16
 * @Description:
 */
public class GRequestWrapper extends HttpServletRequestWrapper {
    private Map<String, String[]> paramMap = Maps.newHashMap();
    private byte[] requestBody = null;

    GRequestWrapper(HttpServletRequest request) {
        super(request);
        this.paramMap.putAll(request.getParameterMap());
        try {
            this.requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getRequestURI() {
        return super.getRequestURI();
    }

    @Override
    public String getParameter(String name) {
        String[] values = this.paramMap.get(name);
        if (null == values || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.paramMap;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return new Vector<>(this.paramMap.keySet()).elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.paramMap.get(name);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if(requestBody == null){
            requestBody= new byte[0];
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

}
