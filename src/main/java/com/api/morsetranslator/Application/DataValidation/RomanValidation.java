package com.api.morsetranslator.Application.DataValidation;

import com.api.morsetranslator.Application.DataValidation.Interfaces.IRomanValidation;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RomanValidation implements IRomanValidation {

    @Override
    public HashMap<String, String> ValidateRequest(String humanText) {
        HashMap<String, String> errors = new HashMap<>();

        if(isEmpty(humanText))
            errors.put("error", "Empty request");

        if(!isHumanText(humanText))
            errors.put("error", "The value entered is not suitable for humans :(");

        return errors;
    }

    @Override
    public boolean isEmpty(String humanText) {
        return humanText.isEmpty() || humanText.isBlank();
    }

    @Override
    public boolean isHumanText(String humanText) {
        return humanText.matches("^[a-zA-Z0-9.]+$");
    }

}
