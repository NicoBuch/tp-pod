package back.core;

import back.model.Movie;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

// Parametrizar con los tipos de keyInput, ,valueInput, keyoutput, valueOutput
public class Mapper_1 implements Mapper<String, Movie, String, Integer> 
{
    public void map(String keyinput, Movie valueinput, Context<String, Integer> context)
    {
         for(String actor : valueinput.getActors()){
        	 context.emit(actor,  valueinput.getVotes());
         }

      }
}
