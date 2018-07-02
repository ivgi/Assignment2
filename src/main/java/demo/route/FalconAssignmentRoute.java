package demo.route;

import demo.gol.Game;
import demo.gol.Input;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jsonvalidator.JsonValidationException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class FalconAssignmentRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:push").streamCaching()
                .doTry()
                .to("json-validator:schema.json")
                .convertBodyTo(String.class)
                .to("atmosphere-websocket:///push?sendToAll=true")
                .doCatch(org.apache.camel.component.jsonvalidator.JsonValidationException.class)
                .process("jsonValidationProcessor")
                .end()
                .wireTap("activemq:gol.input");

        from("activemq:gol.input")
                .unmarshal().json(JsonLibrary.Jackson, Input.class)
                .bean(Game.class, "play")
                .to("jpa:demo.gol.Output");

        from("atmosphere-websocket:///push").to("stream:out");
    }
}
