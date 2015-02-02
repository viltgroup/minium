package minium.visual.internal;

import java.lang.reflect.Method;

import minium.Elements;
import minium.internal.HasElementsFactory;
import minium.visual.VisualElements;
import minium.visual.VisualElementsFactory;

import com.google.common.reflect.AbstractInvocationHandler;

public class VisualElementsAdapterInvocationHandler extends AbstractInvocationHandler {

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        Elements elems = (Elements) proxy;
        VisualElementsFactory<?> factory = elems.as(HasElementsFactory.class).factory().as(VisualElementsFactory.class);
        VisualElements adapter = factory.createNative(elems);
        return method.invoke(adapter, args);
    }

}
