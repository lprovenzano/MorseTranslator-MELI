package com.api.morsetranslator.Application.DataValidation;

import com.api.morsetranslator.Application.DataValidation.Interfaces.IBinaryValidation;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class BinaryValidation implements IBinaryValidation {

    public HashMap<String, String> ValidateRequest(String binary){
        HashMap<String, String> Errors = new HashMap<>();

        if(isEmpty(binary))
            Errors.put("error", "Empty request");

        if(!isBinary(binary))
            Errors.put("error", "The value entered is not binary");

        return Errors;
    }

    @Override
    public boolean isBinary(String binary) {
        return binary.matches("^[01]+$");
    }

    @Override
    public boolean isEmpty(String binary) {
        return binary.isEmpty() || binary.isBlank();
    }

}
