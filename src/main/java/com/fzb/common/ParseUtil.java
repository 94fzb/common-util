package com.fzb.common;

import com.fzb.common.http.HttpUtil;
import flexjson.JSONDeserializer;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

public class ParseUtil {

    private static String BAIDU_TRANSLATE = "http://openapi.baidu.com/public/2.0/bmt/translate";

    private ParseUtil() {
    }

    public static String clearHtmlSpecialChars(String str) {
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("<div(.*)|(.*)(\n*)</div>", "");
        str = str.replaceAll("<script(.*)|(.*)(\n*)</script>", "");
        return str;
    }

    public static String digest(String str, int length) {
        if (str.length() > length) {
            str = str.substring(0, length) + " ...";
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(translate("我要看看百度翻译的质量"));
    }

    public static String translate(String str) {
        try {
            Properties properties = new Properties();
            properties.load(ParseUtil.class.getResourceAsStream("/common.properties"));
            String url = BAIDU_TRANSLATE + "?client_id=" + properties.get("translate.clientId") + "&q=" + URLEncoder.encode(str, "UTF-8") + "&from=auto&to=auto";
            Map result = new JSONDeserializer<Map>().deserialize(HttpUtil.getTextByUrl(url));
            List<Map<String, Object>> ja = (List) result.get("trans_result");
            return ja.get(0).get("dst").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
