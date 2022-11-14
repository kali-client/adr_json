package io.github.json;

import java.util.List;
import java.util.Map;

public class JsonUtils {

    interface IParser {
        Object parseObject(String text, Class<?> clazz);

        List parseArray(String text, Class<?> clazz);

        String toJsonString(Object object);

        Map<String, Object> fromJson(String jsonstr);

        Map<String, Object> fromBean(Object object);
    }

    static IParser parser = new ParserProxy();

    static class ParserProxy implements IParser {
        IParser ip;

        ParserProxy() {
            try {
                @SuppressWarnings("unused")
                Class<?> c = Class.forName("com.alibaba.fastjson.JSON");
                ip = new Fastjson();
                return;
            } catch (ClassNotFoundException e) {
            }

            try {
                @SuppressWarnings("unused")
                Class<?> c = Class
                        .forName("com.fasterxml.jackson.databind.ObjectMapper");
                ip = new Jackson();
                return;
            } catch (ClassNotFoundException e) {
            }

            if (ip == null) {
                throw new NullPointerException("No json found");
            }
        }

        @Override
        public String toJsonString(Object object) {
            return ip.toJsonString(object);
        }

        @Override
        public Object parseObject(String text, Class<?> clazz) {
            return ip.parseObject(text, clazz);
        }

        @Override
        public List parseArray(String text, Class<?> clazz) {
            return ip.parseArray(text, clazz);
        }

        public Map<String, Object> fromJson(String jsonstr) {
            return ip.fromJson(jsonstr);
        }

        @Override
        public Map<String, Object> fromBean(Object object) {
            return ip.fromBean(object);
        }
    }

    ;

    public static void chooseParser(String name) {
        try {
            if ("fastjson".equals(name)) {
                parser = new Fastjson();
            } else if ("jackson".equals(name)) {
                parser = new Jackson();
            } else {
                throw new IllegalArgumentException("no such parser found");
            }
        } catch (Exception e) {
            throw new JsonwireException(name + "not found in library");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T parseObject(String text, Class<T> clazz) {
        return (T) parser.parseObject(text, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return parser.parseArray(text, clazz);
    }

    public static String toJsonString(Object object) {
        return parser.toJsonString(object);
    }

    public static Map<String, Object> fromJson(String jsonstr) {
        return parser.fromJson(jsonstr);
    }

    public static Map<String, Object> fromBean(Object object) {
        return parser.fromBean(object);
    }

    public static String getParserName() {
        return parser.getClass().getSimpleName().toLowerCase();
    }
}
