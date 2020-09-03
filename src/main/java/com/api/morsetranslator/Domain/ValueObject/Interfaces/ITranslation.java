package com.api.morsetranslator.Domain.ValueObject.Interfaces;

public interface ITranslation {
    String Translate(int positionLetter);
    int indexOfLetter(String letter);
}
