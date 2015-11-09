package service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import back.model.Movie;

import com.hazelcast.core.IMap;

public class MovieReader {
	
	private String filepath;

	public MovieReader(String filepath) {
		this.filepath = filepath;
	}
	
	public void readMovies(IMap<String, Movie> theIMap) throws JsonParseException, JsonMappingException, IOException{
		Reader aReader = new InputStreamReader(MovieReader.class.getResourceAsStream(filepath));
		Movie[] movies = new ObjectMapper().readValue(aReader, Movie[].class);
		for(Movie m : movies){
			theIMap.set(m.getTitle() + "-" + m.getDirector(), m);
		}
	}
}
