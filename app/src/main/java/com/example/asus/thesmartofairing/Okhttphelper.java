package com.example.asus.thesmartofairing;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by ASUS on 2017/2/20.
 */

public class Okhttphelper {
    private static OkHttpClient okHttpClient;
    private Gson gson;
    private Handler handler;
    private Okhttphelper(){
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);

        gson = new Gson();
        handler = new Handler(Looper.getMainLooper());
    };

    public static Okhttphelper getInstance(){

        return new Okhttphelper();
    }

    public void get(String url,BaseCallBack baseCallBack){
        Request request = buildRequest(url,null,HttpMothType.GET);
        doRequest(request, baseCallBack);
    }

    public void post(String url, Map<String,String> params, BaseCallBack baseCallBack){
        Request request = buildRequest(url,params,HttpMothType.POST);
        doRequest(request, baseCallBack);
    }

    public void doRequest(final Request request, final BaseCallBack baseCallBack){

        baseCallBack.onRequestBefore(request);
        okHttpClient.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                baseCallBack.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    URLDecoder.decode(resultStr,"utf-8");
                    if (baseCallBack.type == String.class) {
                        baseCallBack.onSuccess(response, resultStr);
                        callbackSuccess(baseCallBack, response, resultStr);
                    } else {
                        try {
                            Object object = gson.fromJson(resultStr, baseCallBack.type);
                            callbackSuccess(baseCallBack, response, object);
                        } catch (JsonParseException e) {
                            //baseCallBack.onError(response, response.code(), e);
                            callbackError(baseCallBack, response, e);
                        }

                    }
                } else {
                    baseCallBack.onError(response, response.code(), null);
                }
            }
        }
        );
    }

    private Request buildRequest(String url,Map<String,String> params,HttpMothType mothType){
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (mothType==HttpMothType.GET){
            builder.get();
        }else if (mothType==HttpMothType.POST){
            RequestBody body =buildFormData(params);
            builder.post(body);
        }

        return builder.build();
    }
    private void callbackSuccess(final BaseCallBack callback, final Response response, final Object object){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response,object);
            }
        });
    }
    private void callbackError(final BaseCallBack callback, final Response response, final Exception e){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response,response.code(),e);
            }
        });
    }

    private RequestBody buildFormData(Map<String,String> params){
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (params!=null){
            for (Map.Entry<String,String> entry :params.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
        }

        return builder.build();
    }

    enum HttpMothType{
        GET,POST
    }
}
