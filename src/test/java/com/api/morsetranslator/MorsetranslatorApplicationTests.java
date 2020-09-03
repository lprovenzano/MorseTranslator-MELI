package com.api.morsetranslator;

import com.api.morsetranslator.Application.DataValidation.Interfaces.IBinaryValidation;
import com.api.morsetranslator.Application.DataValidation.Interfaces.IMorseValidation;
import com.api.morsetranslator.Application.DataValidation.Interfaces.IRomanValidation;
import com.api.morsetranslator.Application.Services.Interfaces.IParserService;
import com.api.morsetranslator.Domain.Shared.Bit;
import com.api.morsetranslator.Domain.ValueObject.Interfaces.ITranslation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import javax.naming.OperationNotSupportedException;
import java.util.HashMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MorsetranslatorApplicationTests {

	@Autowired
	private IParserService _morseService;

	@Autowired
	private IBinaryValidation _binaryValidation;

	@Autowired
	private IMorseValidation _morseValidation;

	@Autowired
	private IRomanValidation _romanValidation;

	@Qualifier("morse")
	@Autowired
	private ITranslation _morse;

	@Qualifier("morse")
	@Autowired
	private ITranslation _roman;

	@Test
	public void Given_Binary_String_decode2morse_Should_Be_True() throws OperationNotSupportedException {
		//Arrange
		String binary = "000000001101101100111000001111110001111110011111100000001110111111110111011100000001100011111100000111111001111110000000110000110111111110111011100000011011100000000000";
		String expected = ".... --- .-.. .- -- . .-.. ..  .-.-.-";

		//Act
		String result = _morseService.decodeBits2Morse(binary);

		//Assert
		Assert.isTrue(expected.equals(result), result);
	}

	@Test
	public void Given_Binary_String_decode2morse_Should_Be_False() throws OperationNotSupportedException {
		//Arrange
		String binary = "0000000011011011001110000011111100011111100111111000000011101111111101110111000000011000111111000001111110011111100000001100001101111111101110111000000110111";
		String expected = ".... --- .-.. .- -- . .-.. ..  .-.-.-";

		//Act
		String result = _morseService.decodeBits2Morse(binary);

		//Assert
		Assert.isTrue(!expected.equals(result), result);
	}

	@Test
	public void Given_Morse_String_translate2human_Should_Be_True() {
		//Arrange
		String morse = ".... --- .-.. .- -- . .-.. ..";
		String expected = "HOLAMELI";

		//Act
		String result = _morseService.translate2Human(morse);

		//Assert
		Assert.isTrue(expected.equals(result), result);
	}

	@Test
	public void Given_Morse_String_translate2human_Should_Be_False() {
		//Arrange
		String morse = ".... . --- .-.. .- -- . .-.. ..  .-.-.-";
		String expected = "HOLAMELI";

		//Act
		String result = _morseService.translate2Human(morse);

		//Assert
		Assert.isTrue(!expected.equals(result), result);
	}

	@Test
	public void Given_Roman_String_translate2morse_Should_Be_True() {
		//Arrange
		String roman = "HOLAMELI";
		String expected = ".... --- .-.. .- -- . .-.. ..";

		//Act
		String result = _morseService.translate2Morse(roman);

		//Assert
		Assert.isTrue(expected.equals(result), result);
	}

	@Test
	public void Given_Roman_String_translate2morse_Should_Be_False() {
		//Arrange
		String roman = "HOLA2MELI";
		String expected = ".... --- .-.. .- -- . .-.. ..";

		//Act
		String result = _morseService.translate2Morse(roman);

		//Assert
		Assert.isTrue(!expected.equals(result), result);
	}

	//DataValidation

	@Test
	public void Given_NonBinary_String_HashMap_Should_Be_Not_Empty(){
		//Arrange
		String request = "01111001011111     101";

		//Act
		HashMap<String, String> errors = _binaryValidation.ValidateRequest(request);

		//Assert
		Assert.isTrue(!errors.isEmpty(), "Non binary");
	}

	@Test
	public void Given_Binary_String_HashMap_Should_Be_Empty(){
		//Arrange
		String request = "01111001011111101";

		//Act
		HashMap<String, String> errors = _binaryValidation.ValidateRequest(request);

		//Assert
		Assert.isTrue(errors.isEmpty(), "Binary");
	}

	@Test
	public void Given_NonMorse_String_HashMap_Should_Be_Not_Empty(){
		//Arrange
		HashMap<String, String> errors;
		String request = ".... --- .-.. .- -_- . .-.. ..";

		//Act
		errors = _morseValidation.ValidateRequest(request);

		//Assert
		Assert.isTrue(!errors.isEmpty(), "Non Morse");
	}

	@Test
	public void Given_Morse_String_HashMap_Should_Be_Empty(){
		//Arrange
		String request = ".... --- .-.. .- -- . .-.. ..";

		//Act
		HashMap<String, String> errors = _morseValidation.ValidateRequest(request);

		//Assert
		Assert.isTrue(errors.isEmpty(), "Morse");
	}

	@Test
	public void Given_NonRoman_String_HashMap_Should_Be_Not_Empty(){
		//Arrange
		String request = "HOLAMELI!";

		//Act
		HashMap<String, String> errors = _romanValidation.ValidateRequest(request);

		//Assert
		Assert.isTrue(!errors.isEmpty(), "Non Roman");
	}

	@Test
	public void Given_Roman_String_HashMap_Should_Be_Empty(){
		//Arrange
		String request = "HOLAMELI";

		//Act
		HashMap<String, String> errors = _romanValidation.ValidateRequest(request);

		//Assert
		Assert.isTrue(errors.isEmpty(), "Binary");
	}

	//Translations
	@Test
	public void Given_Pulse_Should_Return_Morse_Dot() throws OperationNotSupportedException {
		//Arrange
		Bit pulse = Bit.PULSE;
		int QUANTITY = 1;

		//Act
		String letter = _morse.getLetter(pulse, QUANTITY);

		Assert.isTrue(letter.equals("."), "DOT");
	}

	@Test
	public void Given_Pulse_Should_Return_Morse_Dash() throws OperationNotSupportedException {
		//Arrange
		Bit pulse = Bit.PULSE;
		int QUANTITY = 4;

		//Act
		String letter = _morse.getLetter(pulse, QUANTITY);

		Assert.isTrue(letter.equals("-"), "DASH");
	}

	@Test
	public void Given_Pulse_Should_Return_Morse_Pause() throws OperationNotSupportedException {
		//Arrange
		Bit pulse = Bit.PAUSE;
		int QUANTITY = 4;

		//Act
		String letter = _morse.getLetter(pulse, QUANTITY);

		Assert.isTrue(letter.equals(" "), "SHORT_GAP");
	}

	@Test
	public void Given_Pulse_Should_Return_Morse_Full_Stop() throws OperationNotSupportedException {
		//Arrange
		Bit pulse = Bit.PAUSE;
		int QUANTITY = 9;

		//Act
		String letter = _morse.getLetter(pulse, QUANTITY);

		Assert.isTrue(letter.equals("  .-.-.-"), "FULL_STOP");
	}




}
