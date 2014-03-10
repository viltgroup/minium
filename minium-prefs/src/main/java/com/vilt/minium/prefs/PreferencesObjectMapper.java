/*
 * Copyright (C) 2013 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vilt.minium.prefs;

import java.io.IOException;

import org.springframework.util.SystemPropertyUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class PreferencesObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = -4748586342393419901L;

    public static final class PreferencesModule extends SimpleModule {

        private static final long serialVersionUID = 4961661605352445721L;

        public PreferencesModule() {
            setDeserializerModifier(new BeanDeserializerModifier() {
                @Override
                public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, final JsonDeserializer<?> deserializer) {
                    return new DelegatingDeserializer(deserializer) {

                        private static final long serialVersionUID = 1537749366674928595L;

                        @Override
                        protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> newDelegatee) {
                            return deserializer;
                        }

                        @Override
                        public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                            return deserializer.deserialize(new JsonParserDelegate(jp) {
                                @Override
                                public String getValueAsString() throws IOException, JsonParseException {
                                    return SystemPropertyUtils.resolvePlaceholders(super.getValueAsString(), true);
                                }
                                @Override
                                public String getValueAsString(String defaultVal) throws IOException, JsonParseException {
                                    return SystemPropertyUtils.resolvePlaceholders(super.getValueAsString(defaultVal), true);
                                }
                            }, ctxt);
                        }

                    };
                }
            });
        }
    }

    public PreferencesObjectMapper() {
        super();
        registerModule(new PreferencesModule());
    }

}