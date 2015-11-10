package back.core;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import com.hazelcast.mapreduce.Collator;

public class ActorVotesCollator implements Collator<Map.Entry<String, Long>, PriorityQueue<Entry<String, Long>>> {

	  public PriorityQueue<Entry<String, Long>> collate( Iterable<Map.Entry<String, Long>> values) {
		  PriorityQueue<Entry<String, Long>> answer = new PriorityQueue<Entry<String, Long>>(new ActorComparator());
		  for(Entry<String, Long> entry : values){
			  
			  answer.offer(entry);
		  }
		  return answer;
	  }
	  
	  
	  private class ActorComparator implements Comparator<Entry<String, Long>>{

		public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
			Number value1 = o1.getValue();
			Number value2 = o2.getValue();
			
			// Dont know why, but long values need -1 with the compareTo
			return -1*(new Long(value1.longValue())).compareTo(new Long(value2.longValue()));
		}
		  
	  }
}