package io.github.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

class Fastjson implements JsonUtils.IParser {

    public Object parseObject(String text, Class<?> clazz) {
        return JSON.parseObject(text, clazz);
    }

    @Override
    public List parseArray(String text, Class<?> clazz) {
        return JSON.parseArray(text, clazz);
    }

    public String toJsonString(Object object) {
        return JSON.toJSONString(object, SerializerFeature.WriteTabAsSpecial);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> fromJson(String jsonstr) {
        return (Map<String, Object>) JSON.parse(jsonstr);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> fromBean(Object object) {
        return (Map<String, Object>) JSON.toJSON(object);
    }
}
