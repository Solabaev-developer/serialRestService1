package presentation.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.utils.Configs;
import spark.ResponseTransformer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

class JsonUtil {

    private static ObjectMapper mapper = null;

    private JsonUtil() {
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return getMapper().writeValueAsString(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }

    private static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            DateFormat df = new SimpleDateFormat(Configs.getDateFormat());
            mapper.setDateFormat(df);
        }
        return mapper;
    }
}