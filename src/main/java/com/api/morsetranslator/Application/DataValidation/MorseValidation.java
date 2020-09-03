package com.api.morsetranslator.Application.DataValidation;

import com.api.morsetranslator.Application.DataValidation.Interfaces.IMorseValidation;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MorseValidation implements IMorseValidation {

    public HashMap<String, String> ValidateRequest(String morse) {

        HashMap<String, String> Errors = new HashMap<>();

        if(isEmpty(morse)){
            Errors.put("error", "Empty request");
        }
        if(!isMorse(morse)){
            Errors.put("error", "The value entered is not morse code");
        }
        return Errors;
    }

    @Override
    public boolean isMorse(String morse) {

        String morseNonRepeatCharacters = removeDuplicates(morse);
        ArrayList<Character> allowedChars = getAlowedChars();

        for (int i=0; i<morseNonRepeatCharacters.length();i++){
            char current = morseNonRepeatCharacters.charAt(i);

            if(!allowedChars.contains(current))
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isEmpty(String morse) {
        return morse.isEmpty() || morse.isBlank();
    }

    @Override
    public String removeDuplicates(String morse) {
        char[] chars = morse.toCharArray();

        String morseNonRepeatCharacters = "";

        Set<Character> charSet = new LinkedHashSet<>();

        for (char character : chars)
            charSet.add(character);

        for (Character character : charSet)
            morseNonRepeatCharacters += Character.toString(character);

        return morseNonRepeatCharacters;
    }

    private ArrayList<Character> getAlowedChars(){
        ArrayList<Character> allowedChars = new ArrayList<Character>(3);

        allowedChars.add('-');
        allowedChars.add('.');
        allowedChars.add(' ');

        return allowedChars;
    }


}
