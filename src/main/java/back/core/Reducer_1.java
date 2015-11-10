package back.core;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Reducer_1 implements ReducerFactory<String, Long, Long> 
{
    public Reducer<Long, Long> newReducer(final String actor) 
    {
        return new Reducer<Long, Long>()
	    {
	        private Long sum;
	        
	        public void beginReduce()  // una sola vez en cada instancia
	        {
	            sum = 0L;
	        }
	
	        public void reduce(Long value) 
	        {
	        	sum += value;
	        }
	
	        public Long finalizeReduce() 
	        {
	            return sum;
	        }
	    };
	}
}