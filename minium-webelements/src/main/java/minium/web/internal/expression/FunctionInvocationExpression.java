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

import static java.lang.String.format;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class FunctionInvocationExpression extends BaseExpression {

    private final Expression parentExpression;
    private final String function;
    private final List<Expression> argExpressions;

    public FunctionInvocationExpression(Expression parentExpression, String function, Expression ... argExpressions) {
        this(parentExpression, function, Lists.newArrayList(argExpressions));
    }

    public FunctionInvocationExpression(Expression parentExpression, String function, List<Expression> argExpressions) {
        this.parentExpression = parentExpression;
        this.function = function;
        this.argExpressions = argExpressions;
    }

    @Override
    public String getJavascript(VariableGenerator varGenerator) {
        if ("find".equals(function) && parentExpression instanceof RootExpression && parentExpression.getArgs() == null) {
            return format("$(%s)", argsAsString(varGenerator));
        }

        return format("%s.%s(%s)",
                parentExpression.getJavascript(varGenerator),
                function,
                argsAsString(varGenerator));
    }

    @Override
    public Object[] getArgs() {
        List<Object> allArgs = Lists.newArrayList();
        addArgs(allArgs, parentExpression.getArgs());
        for (Expression argExpression : argExpressions) {
            addArgs(allArgs, argExpression.getArgs());
        }
        return Iterables.toArray(allArgs, Object.class);
    }

    protected void addArgs(List<Object> args, Object[] expressionArgs) {
        if (expressionArgs != null) args.addAll(Arrays.asList(expressionArgs));
    }

    protected String argsAsString(VariableGenerator varGenerator) {
        List<String> argJavascripts = Lists.newArrayList();
        for (Expression argExpression : argExpressions) {
            argJavascripts.add(argExpression.getJavascript(varGenerator));
        }
        return Joiner.on(", ").join(argJavascripts);
    }

}
