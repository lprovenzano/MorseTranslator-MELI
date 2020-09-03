package com.api.morsetranslator.Application.DTO;


public class Response {
    private int code;
    private String response;

    public Response(String response, int code){
        this.code = code;
        this.response = response;
    }
}
