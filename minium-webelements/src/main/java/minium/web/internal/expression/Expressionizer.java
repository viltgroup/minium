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
package minium.web.internal.expression;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public interface Expressionizer extends Function<Object, Expression> {

    boolean handles(Object obj);

    @Override
    public Expression apply(Object obj);

    public static class Composite implements Expressionizer {

        private final List<Expressionizer> expressionizers = Lists.newArrayList();

        public Composite add(Expressionizer processor) {
            expressionizers.add(processor);
            return this;
        }

        public Composite addAll(Collection<? extends Expressionizer> processors) {
            expressionizers.addAll(processors);
            return this;
        }

        @Override
        public boolean handles(Object obj) {
            Expressionizer processor = expressionizerFor(obj);
            return processor != null;
        }

        @Override
        public Expression apply(Object obj) {
            Expressionizer processor = expressionizerFor(obj);
            return processor.apply(obj);
        }

        protected Expressionizer expressionizerFor(Object obj) {
            for (Expressionizer expressionizer : Lists.reverse(expressionizers)) {
                if (expressionizer.handles(obj)) return expressionizer;
            }
            return null;
        }
    }

}