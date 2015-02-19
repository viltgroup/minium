package minium.web.internal.expression;

import static java.lang.String.format;

public interface VariableGenerator {

    public abstract String generate();

    public abstract int usedVariables();

    public static class Impl implements VariableGenerator {

        private int lastCounter = 0;
        private int counter = 0;

        @Override
        public String generate() {
            return format("args[%d]", counter++);
        }

        @Override
        public int usedVariables() {
            int used = counter - lastCounter;
            lastCounter = counter;
            return used;
        }
    }

}