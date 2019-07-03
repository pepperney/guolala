package com.guolala.zxx.support;

import com.guolala.zxx.constant.Const;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @Author: pei.nie
 * @Date:2019/4/16
 * @Description:
 */
public class GResponseWrapper extends HttpServletResponseWrapper {
    private ResponsePrintWriter writer;
    private OutputStreamWrapper outputWrapper;
    private ByteArrayOutputStream output;

    GResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        output = new ByteArrayOutputStream();
        outputWrapper = new OutputStreamWrapper(output);
        writer = new ResponsePrintWriter(output);
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        output.close();
        writer.close();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return outputWrapper;
    }

    public String getContent() {
        try {
            writer.flush();
            return writer.getByteArrayOutputStream().toString(Const.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            return "UnsupportedEncoding";
        }
    }

    @SuppressWarnings("unused")
    public void close() throws IOException {
        writer.close();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    private static class ResponsePrintWriter extends PrintWriter {
        ByteArrayOutputStream output;

        ResponsePrintWriter(ByteArrayOutputStream output) {
            super(output);
            this.output = output;
        }

        ByteArrayOutputStream getByteArrayOutputStream() {
            return output;
        }
    }

    private static class OutputStreamWrapper extends ServletOutputStream {
        ByteArrayOutputStream output;

        OutputStreamWrapper(ByteArrayOutputStream output) {
            this.output = output;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener listener) {
            throw new UnsupportedOperationException("UnsupportedMethod setWriteListener.");
        }

        @Override
        public void write(int b) throws IOException {
            output.write(b);
        }
    }
}
