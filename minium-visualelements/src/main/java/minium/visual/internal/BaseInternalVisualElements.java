package minium.visual.internal;

import minium.visual.VisualElements;

import org.sikuli.script.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;

public abstract class BaseInternalVisualElements<T extends VisualElements> extends BaseVisualElements<T> implements InternalVisualElements {

    static {
        SikuliInitializer.init();
    }

    @Override
    public Iterable<Region> matches() {
        return matches(new VisualContext.Impl(screen(), candidateRegion()));
    }

    @Override
    public Region candidateRegion() {
        return parent() == null ? screen() : parent().as(InternalVisualElements.class).candidateRegion();
    }

    protected <IT extends Iterable<Region>> IT debugResults(IT results) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        if (logger.isDebugEnabled()) {
            if (Iterables.isEmpty(results)) {
                logger.debug("No match found for {}", this);
            } else {
                logger.debug("Found {} matches for {}:\n{}", Iterables.size(results), this, results);
            }
        }
        return results;
    }
}
