package service;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class FlexibleFloatDeserializer extends JsonDeserializer<Integer> {

	@Override
	public Integer deserialize(JsonParser parser, DeserializationContext context) throws IOException {

		String floatString = parser.getText();
		if (floatString.contains(",")) {
			floatString = floatString.replace(",", "");
		}

		if (floatString.equals("N/A")) {
			return Integer.valueOf(0);
		}
		return Integer.valueOf(floatString);
	}

}
