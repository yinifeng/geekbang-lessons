package org.geektimes.configuration.microprofile.config.convert;

import org.eclipse.microprofile.config.spi.Converter;

public class StringConvert implements Converter<String> {
    @Override
    public String convert(String value) throws IllegalArgumentException, NullPointerException {
        return value == null ? "" : value;
    }
}
