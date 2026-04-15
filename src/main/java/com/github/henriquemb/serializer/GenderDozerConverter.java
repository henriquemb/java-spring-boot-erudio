package com.github.henriquemb.serializer;

import com.github.dozermapper.core.DozerConverter;

public class GenderDozerConverter extends DozerConverter<String, String> {
    public GenderDozerConverter() {
        super(String.class, String.class);
    }

    @Override
    public String convertTo(String source, String destination) {
        return switch (source) {
            case "M" -> "Male";
            case "F" -> "Female";
            default -> source;
        };
    }

    @Override
    public String convertFrom(String source, String destination) {
        if (source == null) return null;

        return "Male".equalsIgnoreCase(source) ? "M" : "F";
    }
}
