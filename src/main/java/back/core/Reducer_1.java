package back.core;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Reducer_1 implements ReducerFactory<String, Integer, Integer> 
{
    public Reducer<Integer, Integer> newReducer(final String actor) 
    {
        return new Reducer<Integer, Integer>()
	    {
	        private Integer sum;
	        
	        public void beginReduce()  // una sola vez en cada instancia
	        {
	            sum = 0;
	        }
	
	        public void reduce(Integer value) 
	        {
	        	sum += value;
	        }
	
	        public Integer finalizeReduce() 
	        {
	            return sum;
	        }
	    };
	}
}