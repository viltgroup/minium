package minium.cucumber;

import java.util.Map;
import java.util.Map.Entry;

import minium.cucumber.config.ConfigProperties;
import minium.script.rhinojs.RhinoEngine;
import minium.script.rhinojs.RhinoEngine.RhinoCallable;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.json.JsonParser;
import org.mozilla.javascript.json.JsonParser.ParseException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.MultiValueMap;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

public class JsVariablePostProcessor implements BeanDefinitionRegistryPostProcessor {

    private Map<String, String> variableNames = Maps.newHashMap();

    @Autowired
    private ApplicationContext context;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                MethodMetadata metadata = ((AnnotatedBeanDefinition) beanDefinition).getFactoryMethodMetadata();
                if (metadata == null) continue;
                MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(JsVariable.class.getName());
                if (attrs == null) continue;
                String varName = (String) attrs.getFirst("value");
                variableNames.put(beanDefinitionName, varName == null ? beanDefinitionName : varName);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // nothing to do here
    }

    public void populateEngine(AutowireCapableBeanFactory beanFactory, RhinoEngine engine) {
        for (Entry<String, String> entry : variableNames.entrySet()) {
            String beanName = entry.getKey();
            String jsVar = entry.getValue();
            Object value = getVal(beanFactory, engine, beanName);
            put(engine, jsVar, value);
        }
    }

    protected Object getVal(AutowireCapableBeanFactory beanFactory, RhinoEngine engine, String beanName) {
        Object bean = beanFactory.getBean(beanName);
        if (bean == null) return null;
        return bean instanceof ConfigProperties ? parseJson(engine, ((ConfigProperties) bean).toJson()) : bean;
    }

    protected void put(RhinoEngine engine, String name, Object value) {
        engine.put(name, value);
    }

    protected Object parseJson(final RhinoEngine engine, final String json) {
        return engine.runWithContext(new RhinoCallable<Object, RuntimeException>() {
            @Override
            protected Object doCall(Context cx) throws RuntimeException {
                try {
                    return new JsonParser(cx, engine.getScope()).parseValue(json);
                } catch (ParseException e) {
                    throw Throwables.propagate(e);
                }
            }
        });
    }

}
