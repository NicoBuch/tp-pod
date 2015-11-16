package service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import back.model.Movie;

import com.hazelcast.core.IMap;

public class MovieReader {

	private String filepath;

	public MovieReader(String filepath) {
		this.filepath = filepath;
	}

	public void readMovies(IMap<String, Movie> theIMap) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("Inicio de lectura del archivo: "
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:SSSS")));
		long startWorking = System.currentTimeMillis();
		Reader aReader = new InputStreamReader(MovieReader.class.getResourceAsStream(filepath));
		List<Movie> movies = new ObjectMapper().readValue(aReader, new TypeReference<List<Movie>>() {
		});
		for (Movie m : movies) {
			if (m.isMovie()) {
				theIMap.put(m.getTitle(), m);
			}
		}
		System.out.println("Fin de lectura del archivo: "
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:SSSS")));
		System.out.println("La lectura duró: " + (System.currentTimeMillis() - startWorking));
	}
}
