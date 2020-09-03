package com.api.morsetranslator.Domain.ValueObject.Interfaces;

import com.api.morsetranslator.Domain.Shared.Bit;

import javax.naming.OperationNotSupportedException;

public interface ITranslation {
    String translate(int positionLetter);
    int indexOfLetter(String letter);
    void addLetter(String letter);
    String getLetter(Bit bit, int quantityRepeatedBits) throws OperationNotSupportedException;
    void Flush();
}
