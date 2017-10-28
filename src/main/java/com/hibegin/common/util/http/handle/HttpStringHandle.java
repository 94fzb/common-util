package com.hibegin.common.util.http.handle;

import com.hibegin.common.util.IOUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;

public class HttpStringHandle extends HttpHandle<String> {

    private int statusCode;

    @Override
    public boolean handle(HttpRequestBase request, HttpResponse response) {
        try {
            statusCode = response.getStatusLine().getStatusCode();
            setT(IOUtil.getStringInputStream(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
