package minium;

import platypus.Mixin;

import com.google.common.reflect.TypeToken;

/**
 * @see Mixin
 * @author rui.figueira
 */
public interface AsIs {

    public abstract boolean is(Class<?> clazz);

    public abstract boolean is(TypeToken<?> type);

    public abstract <T> T as(Class<T> clazz);

    public abstract <T> T as(TypeToken<T> type);

}
