package minium.visual.config;

import static minium.visual.VisualModules.baseModule;
import static minium.visual.VisualModules.combine;
import static minium.visual.VisualModules.debugModule;
import static minium.visual.VisualModules.interactableModule;
import static minium.visual.VisualModules.positionModule;
import minium.Elements;
import minium.Minium;
import minium.script.js.JsVariable;
import minium.visual.CoreVisualElements.DefaultVisualElements;
import minium.visual.VisualElementsFactory;
import minium.visual.VisualModule;
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
    public Elements root(Screen screen) {
        VisualDebugInteractionPerformer performer = new VisualDebugInteractionPerformer();
        VisualModule visualModule = combine(
                baseModule(screen, DefaultVisualElements.class),
                positionModule(),
                interactableModule(performer),
                debugModule(performer));
        VisualElementsFactory.Builder<DefaultVisualElements> builder = new VisualElementsFactory.Builder<>();
        visualModule.configure(builder);
        DefaultVisualElements root = builder.build().createRoot();
        Minium.set(root);
        return root;
    }
}
