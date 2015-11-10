package back.core;

import back.model.Movie;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

// Parametrizar con los tipos de keyInput, ,valueInput, keyoutput, valueOutput
public class Mapper_1 implements Mapper<String, Movie, String, Long> 
{
    public void map(String keyinput, Movie valueinput, Context<String, Long> context)
    {
         for(String actor : valueinput.getActors()){
        	 context.emit(actor,  0L + valueinput.getVotes());
         }

      }
}
