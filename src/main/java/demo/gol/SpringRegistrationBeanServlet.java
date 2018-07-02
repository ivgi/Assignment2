package demo.gol;

import org.apache.camel.component.atmosphere.websocket.CamelWebSocketServlet;
import org.atmosphere.cpr.ContainerInitializer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SpringRegistrationBeanServlet {


    @Bean
    public EmbeddedAtmosphereInitializer atmosphereInitializer() {
        return new EmbeddedAtmosphereInitializer();
    }

    @Bean
    public ServletRegistrationBean camelWebSocketServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new CamelWebSocketServlet(), "/stream");

        bean.setName("CamelWsServlet");
        bean.setLoadOnStartup(0);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        Map<String, String> params = new HashMap<>();
        params.put("events", "true");
//        params.put("org.atmosphere.container.JSR356AsyncSupport.mappingPath", "/push");
//        params.put("org.atmosphere.websocket.suppressJSR356", "true");
        bean.setInitParameters(params);
        return bean;
    }

    private static class EmbeddedAtmosphereInitializer extends ContainerInitializer
            implements ServletContextInitializer {

        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            onStartup(Collections.<Class<?>>emptySet(), servletContext);
        }
    }
}