package back.core;

import back.model.ActorCouple;
import back.model.Movie;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Mapper_3 implements Mapper<String, Movie, ActorCouple, String> {
	private static final long serialVersionUID = 1L;

	public void map(String keyinput, Movie valueinput, Context<ActorCouple, String> context) {
		for (int i = 0; i < valueinput.getActors().length - 1; i++) {
			for (int j = i + 1; j < valueinput.getActors().length; j++) {
				ActorCouple couple = new ActorCouple(valueinput.getActors()[i], valueinput.getActors()[j]);
				context.emit(couple, valueinput.getTitle());
			}
		}
	}
}