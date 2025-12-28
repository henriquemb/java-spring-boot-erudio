package com.github.henriquemb.controller;

import com.github.henriquemb.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) {
        validateNumbers(numberOne, numberTwo);
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        validateNumbers(numberOne, numberTwo);
        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        validateNumbers(numberOne, numberTwo);
        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    public Double division(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        validateNumbers(numberOne, numberTwo);

        double divisor = convertToDouble(numberTwo);

        if(divisor == 0)
            throw new UnsupportedMathOperationException("Please specify a valid divider!");

        return convertToDouble(numberOne) / divisor;
    }

    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        validateNumbers(numberOne, numberTwo);
        return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
    }

    @RequestMapping("/squareroot/{number}")
    public Double squareRoot(
            @PathVariable String number
    ) {
        validateNumber(number);

        return Math.sqrt(convertToDouble(number));
    }

    private Double convertToDouble(String number) {
        if(number == null || number.isEmpty())
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        number = parseNumber(number);

        return Double.parseDouble(number);
    }

    private boolean isNumeric(String strNumber) {
        String number = parseNumber(strNumber);
        String regex = "^[-+]?[0-9]*\\.?[0-9]*$";

        return number.matches(regex);
    }

    private String parseNumber(String number) {
        return number.trim().replace(",", ".");
    }

    private void validateNumber(String number) {
        if(!isNumeric(number))
            throw new UnsupportedMathOperationException("Please set a numeric value!");
    }

    private void validateNumbers(String numberOne, String numberTwo) {
        validateNumber(numberOne);
        validateNumber(numberTwo);
    }
}
