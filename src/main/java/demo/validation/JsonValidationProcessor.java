package demo.validation;

import com.networknt.schema.ValidationMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jsonvalidator.JsonValidationException;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created because the DefaultJsonValidatorErrorHandler does not log errors properly.
 * Here we process the exception and display correct error message.
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
        Set<ValidationMessage> errors = exception.getErrors();
        String errorText = "";
        if (errors == null) {
            errorText = exception.toString();
        } else {
            for (ValidationMessage msg : errors) {
                errorText += msg.getMessage() + ";";
            }
        }

        exchange.getOut().setBody(String.format("{\"error\": \"%s\"}", errorText));
    }
}
