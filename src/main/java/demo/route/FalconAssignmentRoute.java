package demo.route;

import demo.gol.Game;
import demo.gol.Input;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jsonvalidator.JsonValidationException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * Configures the route our Input travels in order to become an output persisted in MySql.
 * All communication and protocols are abstracted as a camel components.
 * Route1: From rest endpoint to websockets and activemq
 * Route2: From activemq to db
 * Route3: Needed so that we asynchronously send to activemq
 * Route4: Establishes websockets server so that the html client can connect
 */
@Component
public class FalconAssignmentRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:push").streamCaching()
                .doTry()
                    .to("json-validator:schema.json")
                    .convertBodyTo(String.class)
                    .to("direct:activemq")
                    .to("atmosphere-websocket:///push?sendToAll=true")
                    .transform(constant("{\"STATUS\":\"SUCCESS\"}"))
                .doCatch(org.apache.camel.component.jsonvalidator.JsonValidationException.class)
                    .process("jsonValidationProcessor")
                .endDoTry();


        from("activemq:gol.input")
                .unmarshal().json(JsonLibrary.Jackson, Input.class)
                .bean(Game.class, "play")
                .to("jpa:demo.gol.Output");


        from("direct:activemq").wireTap("activemq:gol.input");
        from("atmosphere-websocket:///push").to("stream:out");
    }
}
