package com.api.morsetranslator.Application.Services.Interfaces;

import com.api.morsetranslator.Domain.ValueObject.Interfaces.ITranslation;

import javax.naming.OperationNotSupportedException;

public interface IParserService {
     String decodeBits2Morse(String binary) throws OperationNotSupportedException;

     String translate2Human(String morse);

     String translate2Morse(String roman);

     String translate(String word, ITranslation translation);
}
