package com.api.morsetranslator.Domain.ValueObject;

import com.api.morsetranslator.Domain.Shared.Bit;
import com.api.morsetranslator.Domain.ValueObject.Interfaces.ITranslation;
import org.springframework.stereotype.Component;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class Roman implements ITranslation {

    private String romanWordTranslation;
    private final ArrayList<String> Alphabet = new ArrayList<>(Arrays.asList("A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z", "0", "1",
            "2", "3", "4", "5", "6", "7", "8",
            "9", "."));

    public Roman(){
        romanWordTranslation = "";
    }

    public String translate(int positionLetter){
        String foundLetter = "";
        boolean isInAlphabetRange = positionLetter >= 0 && positionLetter<=Alphabet.toArray().length;

        if(isInAlphabetRange)
            foundLetter = Alphabet.get(positionLetter);

        return foundLetter;
    }

    public int indexOfLetter(String letter){
        return Alphabet.indexOf(letter);
    }

    @Override
    public void addLetter(String letter) {
        romanWordTranslation+= letter;
    }

    @Override
    public String getLetter(Bit bit, int quantityRepeatedBits) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    @Override
    public void Flush() {
        romanWordTranslation = "";
    }

    @Override
    public String toString() {
        String word = romanWordTranslation;

        Flush();

        return word;
    }
}
