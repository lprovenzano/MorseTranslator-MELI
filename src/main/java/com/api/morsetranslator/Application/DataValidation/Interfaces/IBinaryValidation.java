package com.api.morsetranslator.Application.DataValidation.Interfaces;

import java.util.HashMap;

public interface IBinaryValidation {
    HashMap<String, String> ValidateRequest(String binary);
    boolean isBinary(String binary);
    boolean isEmpty(String binary);

}
