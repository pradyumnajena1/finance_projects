package com.pd.finance.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    public  static <T> String serialize(T value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(value);
        return jsonString;
    }

    public  static <T> T deserialize(String jsonString,Class<T> type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        T  value = objectMapper.readValue(jsonString,type);
        return value;
    }
}
