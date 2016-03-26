package com.fzb.common.http.handle;

import com.fzb.common.IOUtil;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import java.io.IOException;

public class HttpStringHandle extends HttpHandle<String> {
    @Override
    public boolean handle(HttpRequest request, HttpResponse response) {
        try {
            setT(IOUtil.getStringInputStream(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
