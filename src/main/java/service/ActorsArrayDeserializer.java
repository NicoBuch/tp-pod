package service;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class ActorsArrayDeserializer extends JsonDeserializer<String[]> {

    @Override
    public String[] deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        String actorsString = parser.getText();
        String[] actors = actorsString.split(", ");
        return actors;
    }

}