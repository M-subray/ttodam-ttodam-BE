package com.ttodampartners.ttodamttodam.global.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import java.util.List;


@Slf4j
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        try {
            return mapper.writeValueAsString(stringList);
        } catch (JsonProcessingException e) {
            log.error("Error converting List<String> to JSON", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        try {
            return mapper.readValue(string, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            log.error("Error converting JSON to List<String>. Input string: {}", string, e);
            throw new RuntimeException(e);
        }
    }
}
