package minium.visual.config;

import static minium.visual.internal.VisualModules.baseModule;
import static minium.visual.internal.VisualModules.combine;
import static minium.visual.internal.VisualModules.debugModule;
import static minium.visual.internal.VisualModules.interactableModule;
import static minium.visual.internal.VisualModules.positionModule;
import minium.script.js.JsVariable;
import minium.visual.CoreVisualElements.DefaultVisualElements;
import minium.visual.VisualLocator;
import minium.visual.internal.VisualElementsFactory;
import minium.visual.internal.VisualModule;
import minium.visual.internal.actions.VisualDebugInteractionPerformer;

import org.sikuli.script.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties(VisualProperties.class)
public class VisualConfiguration {

    @Autowired
    @Bean
    @JsVariable("screen")
    public Screen screen(VisualProperties visualProperties, Environment env) {
        return new Screen();
    }

    @Bean
    @ConfigurationProperties(prefix = "minium.visual")
    public VisualProperties visualProperties() {
        return new VisualProperties();
    }


    @Autowired
    @Bean
    public VisualLocator<DefaultVisualElements> root(Screen screen) {
        VisualDebugInteractionPerformer performer = new VisualDebugInteractionPerformer();
        VisualModule visualModule = combine(
                baseModule(screen, DefaultVisualElements.class),
                positionModule(),
                interactableModule(performer),
                debugModule(performer));
        VisualElementsFactory.Builder<DefaultVisualElements> builder = new VisualElementsFactory.Builder<>();
        visualModule.configure(builder);
        DefaultVisualElements root = builder.build().createRoot();
        return new VisualLocator<DefaultVisualElements>(root);
    }
}
