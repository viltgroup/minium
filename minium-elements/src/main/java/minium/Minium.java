package minium;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public class Minium {

    private static Supplier<Elements> rootSupplier;

    public static void set(Supplier<Elements> supplier) {
        Minium.rootSupplier = supplier;
    }

    public static void set(Elements root) {
        set(Suppliers.ofInstance(root));
    }

    public static void release() {
        rootSupplier = null;
    }

    public static Elements get() {
        return rootSupplier == null ? null : rootSupplier.get();
    }
}
