package com.example.nightapp.netconnection;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetClinet {
    OkHttpClient okHttpClient = new OkHttpClient();
    String result = null;
    public String sendGet(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            result = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
