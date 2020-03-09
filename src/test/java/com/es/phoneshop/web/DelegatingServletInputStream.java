package com.es.phoneshop.web;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DelegatingServletInputStream extends ServletInputStream {

    private ByteArrayInputStream inputStream;

    public DelegatingServletInputStream(byte[] bytes) {
        inputStream = new ByteArrayInputStream(bytes);
    }

    @Override
    public boolean isFinished() {
        return inputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }
}
