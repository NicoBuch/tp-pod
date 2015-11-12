package back.core;

import back.model.Movie;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Mapper_4 implements Mapper<String, Movie, String, String[]> {
	private static final long serialVersionUID = 1L;

	public void map(String keyinput, Movie valueinput, Context<String, String[]> context) {
		context.emit(valueinput.getDirector(), valueinput.getActors());
	}
}