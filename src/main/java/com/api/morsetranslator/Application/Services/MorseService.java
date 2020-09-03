package com.api.morsetranslator.Application.Services;

import com.api.morsetranslator.Application.Services.Interfaces.IBinaryService;
import com.api.morsetranslator.Application.Services.Interfaces.IParserService;
import com.api.morsetranslator.Domain.Shared.Bit;
import com.api.morsetranslator.Domain.ValueObject.Interfaces.ITranslation;
import com.api.morsetranslator.Domain.ValueObject.Morse;
import com.api.morsetranslator.Domain.ValueObject.Roman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.HashMap;

@Service
public class MorseService implements IParserService {

    private HashMap<Integer, Integer> memoization;

    @Qualifier("morse")
    private ITranslation _morse;

    @Qualifier("roman")
    private ITranslation _roman;

    private IBinaryService _binaryService;

    @Autowired
    public MorseService(IBinaryService binaryService,
                        ITranslation morse,
                        ITranslation roman)
    {
        _binaryService = binaryService;
        _roman = roman;
        _morse = morse;
        memoization = new HashMap<>();
    }

    @Override
    public String decodeBits2Morse(String binary) throws OperationNotSupportedException {

        int[] bits = _binaryService.getBitsArrayFromString(binary);

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
        return _morse.toString();
    }

    @Override
    public String translate2Human(String morseWord)
    {
        try{

            return translate(morseWord, _roman);

        }catch(Exception exception){
            throw exception;
        }
    }

    @Override
    public String translate2Morse(String romanWord) {
        try{

            return translate(romanWord.toUpperCase(), _morse);

        }catch(Exception exception){
            throw exception;
        }
    }

    @Override
    public String translate(String word, ITranslation translation){

        String[] lettersToTranslate = getLettersInWordToTranslate(word, translation);

        for (int i=0; i<lettersToTranslate.length; i++)
        {
            String letter = lettersToTranslate[i];
            String translatedLetter = getTranslatedLetter(letter, translation);
            translation.addLetter(translatedLetter);
        }

        return translation.toString();
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

    private void generateMorseTranslation(int bit, int quantityPulses) throws OperationNotSupportedException {

        Bit digit = bit == Bit.PULSE.getValue()? Bit.PULSE : Bit.PAUSE;

        String morseLetter = _morse.getLetter(digit, quantityPulses) ;

        _morse.addLetter(morseLetter);

    }

    private String[] getLettersInWordToTranslate(String word, ITranslation translation ){
        boolean isTranslationToRoman = translation instanceof Roman;

        String[] lettersToTranslate = isTranslationToRoman?
                word.split(" ") : word.split("");

        return lettersToTranslate;
    }

    private String getTranslatedLetter(String letter, ITranslation translation){
        int index = translation.equals(_roman)? _morse.indexOfLetter(letter) : _roman.indexOfLetter(letter);
        return translation.translate(index);
    }

}

