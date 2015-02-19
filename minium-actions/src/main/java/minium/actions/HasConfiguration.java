package minium.actions;

public interface HasConfiguration {

    public Configuration configure();

    public static class Impl implements HasConfiguration {

        private final Configuration configuration;

        public Impl(Configuration configuration) {
            this.configuration = configuration;
        }

        @Override
        public Configuration configure() {
            return configuration;
        }
    }
}
