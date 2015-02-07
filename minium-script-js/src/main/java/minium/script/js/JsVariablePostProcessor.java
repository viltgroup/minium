package minium.script.js;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.MultiValueMap;

import com.google.common.collect.Maps;

public class JsVariablePostProcessor implements BeanDefinitionRegistryPostProcessor, BeanFactoryAware {

    private Map<String, String> variableNames = Maps.newHashMap();
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

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

    public void populateEngine(JsEngine engine) {
        for (Entry<String, String> entry : variableNames.entrySet()) {
            Object bean = beanFactory.getBean(entry.getKey());
            if (bean == null) continue;

            String jsVar = entry.getValue();
            if (bean instanceof Jsonable) {
                engine.putJson(jsVar, ((Jsonable) bean).toJson());
            } else {
                engine.put(jsVar, bean);
            }
        }
    }
}
