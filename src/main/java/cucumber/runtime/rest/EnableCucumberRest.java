package cucumber.runtime.rest;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Indicates that Cucumber REST support should be enabled.
 * <p/>
 * This should be applied to a Spring java config and should have an accompanying '@Configuration' annotation.
 * <p/>
 * Loads all required beans defined in @see CucumberRestConfig
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@Import(CucumberRestConfig.class)
public @interface EnableCucumberRest {
}
