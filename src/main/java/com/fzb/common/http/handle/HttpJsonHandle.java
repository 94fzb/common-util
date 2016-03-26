package com.fzb.common.http.handle;

import com.fzb.common.IOUtil;
import flexjson.JSONDeserializer;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class HttpJsonHandle extends HttpHandle<Map> {
    @Override
    public boolean handle(HttpRequest request, HttpResponse response) {
        try {
            String jsonStr = IOUtil.getStringInputStream(response.getEntity().getContent());
            setT(new JSONDeserializer<Map>().deserialize(jsonStr));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
