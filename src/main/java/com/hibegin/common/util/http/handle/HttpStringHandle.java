package com.hibegin.common.util.http.handle;

import com.hibegin.common.util.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;

public class HttpStringHandle extends HttpHandle<String> {
    @Override
    public boolean handle(HttpRequestBase request, HttpResponse response) {
        try {
            setT(IOUtils.getStringInputStream(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
