package com.github.henriquemb.request.converters;

import com.github.henriquemb.exception.UnsupportedMathOperationException;

import java.util.Arrays;
import java.util.List;

public class NumberConverter {
    public static Double convertToDouble(String number) {
        if(number == null || number.isEmpty())
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        number = parseNumber(number);

        return Double.parseDouble(number);
    }

    public static boolean isNumeric(String strNumber) {
        String number = parseNumber(strNumber);
        String regex = "^[-+]?[0-9]*\\.?[0-9]*$";

        return number.matches(regex);
    }

    public static void validateNumber(String ...numbers) {
        List<String> nonNumbers = Arrays.stream(numbers).filter(number -> !isNumeric(number)).toList();

        if(!nonNumbers.isEmpty())
            throw new UnsupportedMathOperationException("Please set a numeric value!");
    }

    private static String parseNumber(String number) {
        return number.trim().replace(",", ".");
    }
}
