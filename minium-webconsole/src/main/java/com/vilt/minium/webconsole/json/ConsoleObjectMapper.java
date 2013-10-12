package com.vilt.minium.webconsole.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Throwables;
import com.vilt.minium.webconsole.controller.EvalException;

public class ConsoleObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = -4748586342393419901L;

    public static class ThrowableJsonSerializer<T extends Throwable> extends JsonSerializer<T> {

        @Override
        public void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeStartObject();
            jgen.writeStringField("class", value.getClass().getName());
            jgen.writeStringField("message", value.getMessage());
            jgen.writeStringField("fullStackTrace", Throwables.getStackTraceAsString(value));
            writeAdditionalFields(value, jgen, provider);
            jgen.writeEndObject();
        }

        protected void writeAdditionalFields(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        }
    }
    
    public static class EvalExceptionJsonSerializer extends ThrowableJsonSerializer<EvalException> {

        protected void writeAdditionalFields(EvalException value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
            jgen.writeStringField("sourceName", value.getSourceName());
            jgen.writeNumberField("lineNumber", value.getLineNumber());
        }
    }


    public static final class ThrowableModule extends SimpleModule {

        private static final long serialVersionUID = 4961661605352445721L;

        public ThrowableModule() {
            addSerializer(EvalException.class, new EvalExceptionJsonSerializer());
            addSerializer(Exception.class, new ThrowableJsonSerializer<Throwable>());
        }
    }

    public ConsoleObjectMapper() {
        super();
        registerModule(new ThrowableModule());
    }


}