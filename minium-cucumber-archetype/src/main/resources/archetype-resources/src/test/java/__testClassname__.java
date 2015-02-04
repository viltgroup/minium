#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import minium.pupino.config.MiniumConfiguration;
import minium.pupino.cucumber.MiniumCucumber;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;

@RunWith(MiniumCucumber.class)
@SpringApplicationConfiguration(classes = MiniumConfiguration.class)
public class ${testClassname} {
}