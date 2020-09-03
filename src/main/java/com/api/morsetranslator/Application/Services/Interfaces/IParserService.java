package com.api.morsetranslator.Application.Services.Interfaces;

import com.api.morsetranslator.Domain.ValueObject.Interfaces.ITranslation;

public interface IParserService {
    public String decodeBits2Morse(int[] bits);
    public String translate2Human(String morse);
    public String translate2Morse(String roman);
    String translate(String word, ITranslation translation);
}
