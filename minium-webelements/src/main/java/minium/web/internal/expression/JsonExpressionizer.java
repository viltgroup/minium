package minium.web.internal.expression;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

public class JsonExpressionizer implements Expressionizer {

    private final ObjectMapper mapper;

    public JsonExpressionizer() {
        this(null);
    }

    public JsonExpressionizer(ObjectMapper objectMapper) {
        this.mapper = objectMapper == null ? new ObjectMapper() : objectMapper;
    }

    @Override
    public boolean handles(Object obj) {
        // let's try to handle everything...
        return true;
    }

    @Override
    public Expression apply(Object obj) {
        try {
            String json = mapper.writeValueAsString(obj);
            return new BasicExpression(json);
        } catch (JsonProcessingException e) {
            throw Throwables.propagate(e);
        }
    }
}