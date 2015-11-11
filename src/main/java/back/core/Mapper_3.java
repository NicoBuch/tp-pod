package back.core;


import back.model.ActorCouple;
import back.model.Movie;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

//Parametrizar con los tipos de keyInput, ,valueInput, keyoutput, valueOutput
public class Mapper_3 implements Mapper<String, Movie, ActorCouple, String> 
{
 public void map(String keyinput, Movie valueinput, Context<ActorCouple, String> context)
 {
	 for(int i = 1; i < valueinput.getActors().length; i++){
		 ActorCouple couple = new ActorCouple(valueinput.getActors()[i-1], valueinput.getActors()[i]);
		 context.emit(couple, valueinput.getTitle());
	 }
   }
}