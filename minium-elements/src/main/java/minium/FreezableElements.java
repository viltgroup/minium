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
package minium;

/**
 * Everytime an {@link Elements} expression is evaluated, the corresponding
 * set of values needs to be computed. That means that the same {@link Elements}
 * expression, when evaluated multiple times, can evaluate into different sets.
 * <p>This is a very handful behaviour, specially when interacting with elements,
 * but it has a computational cost. To avoid computing the same set of results
 * multiple times, a {@link FreezableElements} expression memoizes evaluated results
 * once they are not empty, so that next evaluations don't require computation.
 */
public interface FreezableElements<T extends Elements> extends Elements {

    /**
     * Memoizes the result of an evaluation once it returns a non-empty set, so
     * that next evaluations will return the exact same results instead of
     * computing them again.
     *
     * @return a new {@link Elements} that memoizes evaluation results once they
     *         are non-empty
     */
    public T freeze();
}
