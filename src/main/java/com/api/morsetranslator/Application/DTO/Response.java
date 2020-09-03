package com.api.morsetranslator.Application.DTO;


import com.api.morsetranslator.Application.Configuration.JSON.JsonResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class Response {
    private int code;
    private String response;

    public Response(){}

    private Response(String response, int code){
        this.code = code;
        this.response = response;
    }

    public ResponseEntity get(String result, HttpStatus httpStatusCode){

        return ResponseEntity.status(httpStatusCode)
                .body(new JsonResult<Response>().Serialize(new Response(result, httpStatusCode.value())));
    }

    public ResponseEntity get(HashMap<String, String> result, HttpStatus httpStatusCode){

        return ResponseEntity.status(httpStatusCode)
                .body(new JsonResult<HashMap<String, String>>().Serialize(result));
    }

}
