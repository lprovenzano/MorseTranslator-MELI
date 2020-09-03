package com.api.morsetranslator.Domain.ValueObject;

import com.api.morsetranslator.Domain.Shared.Bit;
import com.api.morsetranslator.Domain.ValueObject.Interfaces.ITranslation;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class Morse implements ITranslation {

    private String morseWordTranslated;

    private ArrayList<String> Alphabet = new ArrayList<>(Arrays.asList(".-", "-...", "-.-.", "-..",
            ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..",
            "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-",
            "...-", ".--", "-..-", "-.--", "--..", "-----", ".----",
            "..---", "...--", "....-", ".....", "-....", "--...", "---..",
            "----.", ".-.-.-"));

    private int DASH_QUANTITY_POINTS = 3;
    private int SHORT_GAP = 3;
    private int FULL_STOP_GAP = 7;

    private String DASH = "-";
    private String DOT = ".";
    private String PAUSE = " ";
    private String FULL_STOP = " .-.-.-";

    public Morse(){
        morseWordTranslated = "";
    }

    public void addLetter(String letter){
        morseWordTranslated += letter;
    }

    public String getLetter(Bit bit, int quantityRepeatedBits){

        String letter = "";

        if (bit.equals(Bit.PULSE))
        {
            if (quantityRepeatedBits >= DASH_QUANTITY_POINTS)
                letter += DASH;
            else
                letter += DOT;
        }else{
            boolean isFullStop = quantityRepeatedBits > FULL_STOP_GAP;

            if (quantityRepeatedBits >= SHORT_GAP)
                letter += PAUSE;

            if (isFullStop)
                letter += FULL_STOP;
        }

        return letter;
    }

    public int indexOfLetter(String letter){
         return Alphabet.indexOf(letter);
    }

    public String translate(int positionLetter){
        String foundLetter = "";
        boolean isInAlphabetRange = positionLetter >= 0 && positionLetter<=Alphabet.toArray().length;

        if(isInAlphabetRange)
            foundLetter = Alphabet.get(positionLetter) + " ";

        return foundLetter;
    }

    @Override
    public void Flush(){
        morseWordTranslated = "";
    }

    @Override
    public String toString() {
        String morseTranslation = morseWordTranslated.trim();

        Flush();

        return morseTranslation;
    }
}
