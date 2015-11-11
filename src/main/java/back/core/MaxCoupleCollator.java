package back.core;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import back.model.ActorCouple;

import com.hazelcast.mapreduce.Collator;


public class MaxCoupleCollator implements Collator<Map.Entry<ActorCouple, List<String>>, Map<ActorCouple, List<String>>> {

	  public Map<ActorCouple, List<String>> collate( Iterable<Map.Entry<ActorCouple, List<String>>> values) {
		  Map<ActorCouple, List<String>> map = new HashMap<ActorCouple, List<String>>();
		  int maxMovies = 0;
		  for(Entry<ActorCouple, List<String>> entry : values){
			  int entrySize = entry.getValue().size();
			  if(entrySize > maxMovies){
				  map.clear();
				  map.put(entry.getKey(), entry.getValue());
				  maxMovies = entrySize;
			  }
			  else if(maxMovies == entrySize){
				  map.put(entry.getKey(), entry.getValue());
			  }
		  }
		  return map;
	  }
	  
}