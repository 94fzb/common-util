package com.fzb.common.http.handle;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.dom4j.Document;

public class HttpXmlHandle extends HttpHandle<Document> {
    @Override
    public boolean handle(HttpRequest request, HttpResponse response) {
        return true;
    }
}
