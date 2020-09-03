package com.api.morsetranslator.Application.DataValidation.Interfaces;

import java.util.HashMap;

public interface IRomanValidation {
    HashMap<String, String> ValidateRequest(String humanText);
    boolean isEmpty(String humanText);
    boolean isHumanText(String humanText);
}
