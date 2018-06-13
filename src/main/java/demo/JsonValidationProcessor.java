package demo;

import com.networknt.schema.ValidationMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jsonvalidator.JsonValidationException;
import org.springframework.stereotype.Component;

/**
 * Created because the DefaultJsonValidatorErrorHandler does not log errors properly.
 */
@Component
public class JsonValidationProcessor implements Processor {

    private void print(JsonValidationException ex) {

        for (ValidationMessage msg : ex.getErrors()) {
            System.out.println(msg.toString());
        }
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        JsonValidationException exception = (JsonValidationException) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
        String errorText = "";
        for (ValidationMessage msg : exception.getErrors()) {
            errorText += msg.getMessage() + ";";
        }

        exchange.getOut().setBody(String.format("{\"error\": \"%s\"}", errorText));
    }
}
