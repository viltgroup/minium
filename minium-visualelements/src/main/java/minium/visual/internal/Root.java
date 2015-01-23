package minium.visual.internal;

import java.util.Arrays;
import java.util.Collection;

import minium.visual.VisualElements;

import org.sikuli.script.Region;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

public class Root<T extends VisualElements> extends BaseInternalVisualElements<T> {

    private final Collection<Region> regions;

    public Root(Collection<Region> regions) {
        this.regions = Preconditions.checkNotNull(regions);
    }

    @Override
    public Iterable<Region> matches(VisualContext context) {
        return regions;
    }

    @Override
    public Region candidateRegion() {
        Region union = Regions.union(regions);
        return union == null ? screen() : union;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Root.class.getSimpleName())
                .add("regions", Arrays.toString(regions.toArray()))
                .toString();
    }
}
