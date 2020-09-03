package com.api.morsetranslator.Application.Services;

import com.api.morsetranslator.Application.Services.Interfaces.IBinaryService;
import org.springframework.stereotype.Service;

@Service
public class BinaryService implements IBinaryService {

    @Override
    public int[] getBitsArrayFromString(String binaryString) {

        String binaryTrimmed = binaryString.trim();

        int[] binaries = new int[binaryTrimmed.length()];

        for (int i = 0; i < binaryTrimmed.length(); i++)
            binaries[i] = letterToNumber(binaryTrimmed.charAt(i));

        return binaries;
    }

    private int letterToNumber(char letter){
        String literalLetter = Character.toString(letter);
        return Integer.parseInt(literalLetter);
    }
}
