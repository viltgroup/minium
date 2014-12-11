package cucumber.runtime.rest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import cucumber.runtime.rest.CucumberRestSwaggerConfiguration;

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@Import({ CucumberRestSwaggerConfiguration.class })
public @interface EnableCucumberRestSwagger {
}
