package com.example.asus.thesmartofairing;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ASUS on 2017/2/20.
 */

public abstract class BaseCallBack<T>  {

    public Type type;
    static Type getSuperclassTypeParameter(Class<?> subclass){
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class){
            throw new RuntimeException("Missing type parameter");
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public BaseCallBack(){
        type = getSuperclassTypeParameter(getClass());
    }

    public abstract void onRequestBefore(Request request);
    public abstract void onFailure(Request request, IOException e) ;
    public abstract void onSuccess(Response response, T t) ;
    public abstract void onError(Response response,int code,Exception e);
}
