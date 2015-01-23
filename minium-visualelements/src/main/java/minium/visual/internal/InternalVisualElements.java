package minium.visual.internal;

import minium.visual.VisualElements;

import org.sikuli.script.Region;

public interface InternalVisualElements extends VisualElements {
    public Region candidateRegion();
    public Iterable<Region> matches(VisualContext context);
    public Iterable<Region> matches();
}
