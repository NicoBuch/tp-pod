package back.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hazelcast.mapreduce.Collator;

public class MaxActorsPerDirectorCollator implements Collator<Map.Entry<String, Map<String, Integer>>, Map<String, List<String>>> {

	  public Map<String, List<String>> collate( Iterable<Map.Entry<String, Map<String, Integer>>> values) {
		  Map<String, List<String>> map = new HashMap<String, List<String>>();
		  for(Entry<String, Map<String, Integer>> entry : values){
			  int maxActors = 0;
			  List<String> actors = new ArrayList<String>();
			  for(Entry<String, Integer> actorCount : entry.getValue().entrySet()){
				  int count = actorCount.getValue();
				  if(count > maxActors){
					  actors.clear();
					  actors.add(actorCount.getKey());
					  maxActors = count;
				  }
				  else if(count == maxActors){
					  actors.add(actorCount.getKey());
				  }
			  }
			  map.put(entry.getKey(), actors);
		  }
		  return map;
	  }
	  
}