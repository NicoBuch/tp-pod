package back.core;

import java.util.HashMap;
import java.util.Map;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Reducer_4 implements ReducerFactory<String, String[], Map<String, Integer>> {
	private static final long serialVersionUID = 1L;

	public Reducer<String[], Map<String, Integer>> newReducer(String director) {
		return new Reducer<String[], Map<String, Integer>>() {
			private Map<String, Integer> actors;

			public void beginReduce() {
				actors = new HashMap<String, Integer>();
			}

			public void reduce(String[] mappedActors) {

				for (String actor : mappedActors) {
					Integer actorCount = actors.get(actor);
					if (actorCount == null)
						actorCount = 0;
					actors.put(actor, actorCount + 1);
				}
			}

			public Map<String, Integer> finalizeReduce() {
				return actors;
			}
		};
	}
}