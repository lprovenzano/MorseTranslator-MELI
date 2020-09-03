package com.api.morsetranslator.Application.DataValidation.Interfaces;

import java.util.ArrayList;
import java.util.HashMap;

public interface IMorseValidation {

    HashMap<String, String> ValidateRequest(String morse);

    boolean isMorse(String morse);

    boolean isEmpty(String morse);

    String removeDuplicates(String morse);
}
