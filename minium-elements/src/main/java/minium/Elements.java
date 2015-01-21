package minium;

import com.google.common.reflect.TypeToken;

public interface Elements {
    public boolean is(Class<?> clazz);
    public boolean is(TypeToken<?> type);
    public <T> T as(Class<T> clazz);
    public <T> T as(TypeToken<T> type);
}
