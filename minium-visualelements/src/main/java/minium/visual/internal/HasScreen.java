package minium.visual.internal;

import org.sikuli.script.Screen;

public interface HasScreen {

    public Screen screen();

    public static class Impl implements HasScreen {

        private final Screen screen;

        public Impl(Screen screen) {
            this.screen = screen;
        }

        @Override
        public Screen screen() {
            return screen;
        }
    }

}
