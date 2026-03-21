package com.github.henriquemb.controller;

import com.github.henriquemb.exception.UnsupportedMathOperationException;
import com.github.henriquemb.math.SimpleMath;
import com.github.henriquemb.request.converters.NumberConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {
    private final SimpleMath math = new SimpleMath();

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) {
        NumberConverter.validateNumber(numberOne, numberTwo);

        return math.sum(
                NumberConverter.convertToDouble(numberOne),
                NumberConverter.convertToDouble(numberTwo)
        );
    }

    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        NumberConverter.validateNumber(numberOne, numberTwo);

        return math.subtraction(
                NumberConverter.convertToDouble(numberOne),
                NumberConverter.convertToDouble(numberTwo)
        );
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        NumberConverter.validateNumber(numberOne, numberTwo);

        return math.multiplication(
                NumberConverter.convertToDouble(numberOne),
                NumberConverter.convertToDouble(numberTwo)
        );
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    public Double division(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        NumberConverter.validateNumber(numberOne, numberTwo);

        double divisor = NumberConverter.convertToDouble(numberTwo);

        if(divisor == 0)
            throw new UnsupportedMathOperationException("Please specify a valid divider!");

        return math.division(
                NumberConverter.convertToDouble(numberOne),
                NumberConverter.convertToDouble(numberTwo)
        );
    }

    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        NumberConverter.validateNumber(numberOne, numberTwo);

        return math.mean(
                NumberConverter.convertToDouble(numberOne),
                NumberConverter.convertToDouble(numberTwo)
        );
    }

    @RequestMapping("/squareroot/{number}")
    public Double squareRoot(
            @PathVariable String number
    ) {
        NumberConverter.validateNumber(number);

        return math.squareRoot(
                NumberConverter.convertToDouble(number)
        );
    }
}
