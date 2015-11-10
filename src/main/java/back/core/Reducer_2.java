package back.core;

import java.util.ArrayList;
import java.util.List;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import back.model.Movie;

public class Reducer_2 implements ReducerFactory<String, Movie, List<Movie>> {

	private static final long serialVersionUID = 1L;

	public Reducer<Movie, List<Movie>> newReducer(String arg0) {
		return new Reducer<Movie, List<Movie>>() {

			private int maxMetascore;
			private List<Movie> movies;

			@Override
			public void beginReduce() {
				super.beginReduce();
				maxMetascore = 0;
				movies = new ArrayList<Movie>();
			}

			@Override
			public void reduce(Movie movie) {
				int movieMetascore = movie.getMetascore();
				if (movieMetascore > maxMetascore) {
					maxMetascore = movieMetascore;
					movies.clear();
					movies.add(movie);
				} else if (movieMetascore == maxMetascore) {
					movies.add(movie);
				}
			}

			@Override
			public List<Movie> finalizeReduce() {
				return movies;
			}

		};
	}

}
