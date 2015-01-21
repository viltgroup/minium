package minium.web.internal.expression;

import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonCoercer implements Coercer {

    private final ObjectMapper mapper;

    public JsonCoercer() {
        this(null);
    }

    public JsonCoercer(ObjectMapper objectMapper) {
        this.mapper = objectMapper == null ? new ObjectMapper() : objectMapper;
    }

    @Override
    public boolean handles(Object obj, Type clazz) {
        return obj instanceof String;
    }

    @Override
    public Object coerce(Object obj, Type type) {
        try {
            JavaType javaType = TypeFactory.defaultInstance().constructType(type);
            return mapper.readValue(obj.toString(), javaType);
        } catch (JsonParseException e) {
            throw new CannotCoerceException(e);
        } catch (JsonMappingException e) {
            throw new CannotCoerceException(e);
        } catch (IOException e) {
            throw new CannotCoerceException(e);
        }
    }

}
