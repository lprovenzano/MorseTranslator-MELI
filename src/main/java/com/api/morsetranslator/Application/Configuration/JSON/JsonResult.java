package com.api.morsetranslator.Application.Configuration.JSON;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonResult<T> {

    private Gson gson;

    public JsonResult(){
        this.gson = new Gson();
    }

    public String Serialize(T object){
        return gson.toJson(object);
    }

    public T Deserialize(String json, T object){
        return gson.fromJson(json, (Type) object);
    }
}
