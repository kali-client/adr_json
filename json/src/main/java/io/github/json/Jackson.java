package io.github.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Jackson implements JsonUtils.IParser {

    private ObjectMapper mapper = new ObjectMapper();

    public Jackson() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    }

    @Override
    public Object parseObject(String text, Class<?> clazz) {
        try {
            return mapper.readValue(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List parseArray(String text, Class<?> clazz) {
        throw new UnsupportedOperationException("do it later");
    }

    @Override
    public String toJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> fromJson(String jsonstr) {
        try {
            return mapper.readValue(jsonstr, Map.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> fromBean(Object object) {
        return fromJson(toJsonString(object));
    }
}
