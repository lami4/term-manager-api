package com.selyuto.termbase.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import javax.persistence.AttributeConverter;

public class JsonHashmapConverter implements AttributeConverter<Map<String, Object>, String> {
    ObjectMapper objectMapper = new ObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(JsonHashmapConverter.class);

    @Override
    public String convertToDatabaseColumn(Map<String, Object> termPropertiesAsMap) {

        String termPropertiesAsJsonString = null;
        try {
            termPropertiesAsJsonString = objectMapper.writeValueAsString(termPropertiesAsMap);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return termPropertiesAsJsonString;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String termPropertiesAsJsonString) {

        Map<String, Object> termPropertiesAsMap = null;
        try {
            termPropertiesAsMap = objectMapper.readValue(termPropertiesAsJsonString, Map.class);
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }

        return termPropertiesAsMap;
    }
}
