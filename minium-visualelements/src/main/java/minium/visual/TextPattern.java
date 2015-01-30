package minium.visual;

import minium.visual.Pattern.AbstractPattern;

public class TextPattern extends AbstractPattern<TextPattern> {

    private final String text;

    public TextPattern(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
