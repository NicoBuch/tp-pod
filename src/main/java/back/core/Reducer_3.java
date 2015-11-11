package back.core;

import java.util.ArrayList;
import java.util.List;

import back.model.ActorCouple;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Reducer_3 implements ReducerFactory<ActorCouple, String, List<String>> 
{
    public Reducer<String, List<String>> newReducer(ActorCouple couple) 
    {
        return new Reducer<String, List<String>>()
	    {
	        private List<String> movies;
	        
	        public void beginReduce()  // una sola vez en cada instancia
	        {
	            movies = new ArrayList<String>();
	        }
	
	        public void reduce(String movie) 
	        {
	        	movies.add(movie);
	        }
	
	        public List<String> finalizeReduce() 
	        {
	            return movies;
	        }
	    };
	}
}