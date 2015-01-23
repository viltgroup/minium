package minium.visual.internal;

import java.util.Arrays;

import org.sikuli.script.Region;

public class Regions {

    public static Region union(Region ... regions) {
        return union(Arrays.asList(regions));
    }

    public static Region union(Iterable<Region> regions) {
        Region result = null;
        for (Region region : regions) {
            result = result == null ? region : result.union(region);
        }
        return result;
    }
}
