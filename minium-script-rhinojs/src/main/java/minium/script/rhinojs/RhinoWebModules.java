/*
 * Copyright (C) 2015 The Minium Authors
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
package minium.script.rhinojs;

import java.lang.reflect.Type;

import minium.web.internal.WebElementsFactory.Builder;
import minium.web.internal.WebModule;
import minium.web.internal.expression.BasicExpression;
import minium.web.internal.expression.Coercer;
import minium.web.internal.expression.Expression;
import minium.web.internal.expression.Expressionizer;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.json.JsonParser;
import org.mozilla.javascript.json.JsonParser.ParseException;

import com.google.common.base.Throwables;

public class RhinoWebModules {

    public static class FunctionExpressionizer implements Expressionizer {

        @Override
        public boolean handles(Object obj) {
            return obj instanceof Function;
        }

        @Override
        public Expression apply(Object obj) {
            Function fn = (Function) obj;
            Function toString = (Function) fn.getPrototype().get("toString", fn);
            Context cx = Context.enter();
            return new BasicExpression((String) toString.call(cx, fn, fn, new Object[0]));
        }
    }

    public static class RhinoObjectCoercer implements Coercer {

        @Override
        public boolean handles(Object obj, Type type) {
            if (type == Object.class) {
                return true;
            }
            if (type instanceof Class && Scriptable.class.isAssignableFrom((Class<?>) type)) {
                return true;
            }
            return false;
        }

        @Override
        public Object coerce(Object obj, Type type) {
            try {
                Context cx = Context.enter();
                if (obj instanceof String) {
                    return new JsonParser(cx, new NativeObject()).parseValue((String) obj);
                }
                return obj;
            } catch (ParseException e) {
                throw Throwables.propagate(e);
            }
        }
    }

    public static WebModule rhinoModule() {
        return new WebModule() {
            @Override
            public void configure(Builder<?> builder) {
                builder
                .withJsResources(
                        "minium/web/internal/lib/jquery.min.js",
                        "minium/script/js/internal/lib/jquery.functionCall.js"
                )
                .implementingInterfaces(JsFunctionWebElements.class)
                .withExpressionizers(new FunctionExpressionizer())
                .withCoercers(new RhinoObjectCoercer());
            }

            @Override
            public String toString() {
                return "WebModule[rhino]";
            }
        };
    }
}
