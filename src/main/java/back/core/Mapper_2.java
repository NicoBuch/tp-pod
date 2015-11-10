package back.core;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import back.model.Movie;

public class Mapper_2 implements Mapper<String, Movie, String, Movie> {

	private static final long serialVersionUID = 1L;

	private int tope;

	public Mapper_2(String tope) {
		this.tope = Integer.valueOf(tope);
	}

	public void map(String key, Movie movie, Context<String, Movie> context) {
		if (Integer.valueOf(movie.getYear()) > tope) {
			context.emit(movie.getYear(), movie);
		}
	}

}
