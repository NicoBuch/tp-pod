package front.service;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import back.model.Movie;

public class MovieDeserializer extends JsonDeserializer<Movie>{

	@Override
	public Movie deserialize(JsonParser jp, DeserializationContext ctct)
			throws IOException, JsonProcessingException {
				return null;
//        JsonNode node = jp.getCodec().readTree(jp);
//        int id = (Integer) ((IntNode) node.get("id")).getNumberValue();
//        String itemName = node.get("itemName").asText();
//        int userId = (Integer) ((IntNode) node.get("createdBy")).getNumberValue();
// 
//        return new Item(id, itemName, new User(userId, null));
	}

}
