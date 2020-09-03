package com.api.morsetranslator.Application.Services;

import com.api.morsetranslator.Application.Services.Interfaces.IParserService;
import com.api.morsetranslator.Domain.Shared.Bit;
import com.api.morsetranslator.Domain.ValueObject.Interfaces.ITranslation;
import com.api.morsetranslator.Domain.ValueObject.Morse;
import com.api.morsetranslator.Domain.ValueObject.Roman;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MorseService implements IParserService {

    private HashMap<Integer, Integer> memoization;
    private Morse morse;
    private Roman roman;

    public MorseService(){
        roman = new Roman();
        morse = new Morse();
        memoization = new HashMap<>();
    }

    @Override
    public String decodeBits2Morse(int[] bits) {

        for (int i = 0; i < bits.length; i++)
        {
            try{
                int current = bits[i];

                while (i <= bits.length - 2 && current == bits[i + 1]){
                    memorizeCountOfBits(current);
                    i++;
                }

                if (isMemorizeValue(current))
                {
                    generateMorseTranslation(current, getQuantity(current));
                    memoization.remove(current);
                }

            }catch (Exception exception){
                throw exception;
            }
        }
        return morse.toString();
    }

    @Override
    public String translate2Human(String morseWord)
    {
        try{

            return translate(morseWord, roman);

        }catch(Exception exception){
            throw exception;
        }
    }

    @Override
    public String translate2Morse(String romanWord) {
        try{

            return translate(romanWord, morse);

        }catch(Exception exception){
            throw exception;
        }
    }

    @Override
    public String translate(String word, ITranslation translation){
        String translatedWord = "";

        String[] lettersToTranslate = getLettersInWordToTranslate(word, translation);

        for (int i=0; i<lettersToTranslate.length; i++)
        {
            String letter = lettersToTranslate[i];
            translatedWord += getTranslatedLetter(letter, translation);
        }

        return translatedWord;
    }

    private void memorizeCountOfBits(int key)
    {
        if (memoization.containsKey(key))
        {
            memoization.put(key, memoization.get(key) + 1);
            return;
        }

        memoization.put(key, 1);
    }

    private boolean isMemorizeValue(int index)
    {
        return memoization.containsKey(index);
    }

    private int getQuantity(int bit)
    {
        return memoization.get(bit);
    }

    private void generateMorseTranslation(int bit, int quantityPulses){

        Bit digit = bit == Bit.PULSE.getValue()? Bit.PULSE : Bit.PAUSE;

        String morseLetter = morse.getLetter(digit, quantityPulses);

        morse.addLetter(morseLetter);
    }

    private String[] getLettersInWordToTranslate(String word, ITranslation translation ){
        boolean isTranslationToRoman = translation instanceof Roman;

        String[] lettersToTranslate = isTranslationToRoman?
                word.split(" ") : word.split("");

        return lettersToTranslate;
    }

    private String getTranslatedLetter(String letter, ITranslation translation){
        int index = translation.equals(roman)? morse.indexOfLetter(letter) : roman.indexOfLetter(letter);
        return translation.Translate(index);
    }

}

