package com.fzb.common.http.handle;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;

public class CloseResponseHandle extends HttpHandle<CloseableHttpResponse> {

    @Override
    public boolean handle(HttpRequest request, HttpResponse response) {
        setT((CloseableHttpResponse) response);
        return false;
    }
}
