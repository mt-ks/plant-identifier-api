package com.mtks.http;

import com.google.gson.Gson;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

public class RequestBodyBuilder {

    public static String toJSON(Map<String, String> params) {
        return new Gson().toJson(params);
    }

    public static String createQuery(Map<String, String> params){
        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = params.entrySet();

        for (Map.Entry<String, String> entry : entrySet) {
            if (!query.isEmpty()) {
                query.append("&");
            }
            query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            query.append("=");
            query.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }

        return query.toString();
    }

}
