package com.fzb.common.http.handle;

import com.fzb.common.IOUtil;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HttpFileHandle extends HttpHandle<File> {

    private String filePath;

    public HttpFileHandle(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean handle(HttpRequest request, HttpResponse response) {
        request.getFirstHeader("");
        setT(new File(filePath + "t.tx"));
        FileOutputStream fin;
        try {
            fin = new FileOutputStream(getT());
            if (response.getEntity() != null) {
                fin.write(IOUtil.getByteByInputStream(response.getEntity().getContent()));
                fin.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
