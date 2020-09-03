package com.api.morsetranslator.Controller;

import com.api.morsetranslator.Application.Configuration.JSON.JsonResult;
import com.api.morsetranslator.Application.DTO.Request;
import com.api.morsetranslator.Application.DTO.Response;
import com.api.morsetranslator.Application.DataValidation.Interfaces.IBinaryValidation;
import com.api.morsetranslator.Application.DataValidation.Interfaces.IMorseValidation;
import com.api.morsetranslator.Application.DataValidation.Interfaces.IRomanValidation;
import com.api.morsetranslator.Application.Services.Interfaces.IBinaryService;
import com.api.morsetranslator.Application.Services.Interfaces.IParserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "translate/")
public class TranslatorController {

    private IParserService _parserService;
    private IBinaryValidation _binaryValidations;
    private IMorseValidation _morseValidations;
    private IRomanValidation _romanValidation;

    public TranslatorController(IParserService parserService,
                                IBinaryValidation binaryValidations,
                                IMorseValidation morseValidations,
                                IRomanValidation romanValidation)
    {
        _parserService = parserService;
        _binaryValidations = binaryValidations;
        _morseValidations = morseValidations;
        _romanValidation = romanValidation;
    }

    @RequestMapping(value = "2morse", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> translate2morse(@RequestBody Request request) {

        try {

            String text = request.text.toUpperCase();

            HashMap<String, String> hasErrors = _romanValidation.ValidateRequest(text);

            if (!hasErrors.isEmpty())
                return ResponseEntity.badRequest()
                        .body(new JsonResult<HashMap<String, String>>().Serialize(hasErrors));

            String resultTranslation = _parserService.translate2Morse(text);

            Response response = new Response(resultTranslation, HttpStatus.OK.value());

            return ResponseEntity.ok()
                    .body(new JsonResult<Response>().Serialize(response));

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new JsonResult<String>().Serialize("Internal server error"));
        }
    }

    @RequestMapping(value = "2text", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> translate2text(@RequestBody Request request) {

        try {
            HashMap<String, String> hasErrors = _morseValidations.ValidateRequest(request.text);

            if (!hasErrors.isEmpty())
                return ResponseEntity.badRequest()
                        .body(new JsonResult<HashMap<String, String>>().Serialize(hasErrors));

            String resultTranslation = _parserService.translate2Human(request.text);

            Response response = new Response(resultTranslation, HttpStatus.OK.value());

            return ResponseEntity.ok()
                    .body(new JsonResult<Response>().Serialize(response));

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new JsonResult<String>().Serialize("Internal server error"));
        }
    }

    @RequestMapping(value = "decodeBits/2morse", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> decodeBits2morse(@RequestBody Request request) {
        try {
            HashMap<String, String> hasErrors = _binaryValidations.ValidateRequest(request.text);

            if (!hasErrors.isEmpty())
                return ResponseEntity.badRequest()
                        .body(new JsonResult<HashMap<String, String>>().Serialize(hasErrors));

            String resultTranslation = _parserService.decodeBits2Morse(request.text);

            Response response = new Response(resultTranslation, HttpStatus.OK.value());

            return ResponseEntity.ok()
                    .body(new JsonResult<Response>().Serialize(response));

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new JsonResult<String>().Serialize("Internal server error"));
        }

    }
}
