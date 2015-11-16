
package back.core;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import com.hazelcast.mapreduce.Collator;

import back.model.Movie;

public class Collator_2 implements Collator<Map.Entry<String, List<Movie>>, PriorityQueue<Entry<String, List<Movie>>>> {

	public PriorityQueue<Entry<String, List<Movie>>> collate(Iterable<Entry<String, List<Movie>>> values) {
		PriorityQueue<Entry<String, List<Movie>>> answer = new PriorityQueue<Entry<String, List<Movie>>>(
				new MovieComparator());
		for (Entry<String, List<Movie>> entry : values) {
			answer.offer(entry);
		}
		return answer;
	}

	private class MovieComparator implements Comparator<Entry<String, List<Movie>>> {

		public int compare(Entry<String, List<Movie>> o1, Entry<String, List<Movie>> o2) {
			return Integer.valueOf(o2.getKey()) - Integer.valueOf(o1.getKey());
		}

	}

}