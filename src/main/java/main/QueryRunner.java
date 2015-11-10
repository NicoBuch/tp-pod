package main;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import back.core.Collector_2;
import back.core.Mapper_2;
import back.core.Reducer_2;
import back.model.Movie;
import back.utils.Analyzer;
import service.MovieReader;

public class QueryRunner {

	private Analyzer analyzer;
	private HazelcastInstance client;

	public QueryRunner(String[] args, HazelcastInstance client) {
		this.analyzer = new Analyzer(args);
		this.client = client;
	}

	public void runQuery() {
		int query = Integer.valueOf(analyzer.get("QUERY").toString());
		String path = analyzer.get("PATH").toString();

		IMap<String, Movie> myMap = client.getMap("imdb");
		MovieReader reader = new MovieReader(path);
		JobTracker tracker = client.getJobTracker("default");
		KeyValueSource<String, Movie> source = KeyValueSource.fromMap(myMap);
		try {
			reader.readMovies(myMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Job<String, Movie> job = tracker.newJob(source);

		switch (query) {
		case QueryType.QUERY_1:
			analyzeQuery1(job);
			break;
		case QueryType.QUERY_2:
			analyzeQuery2(job);
			break;
		case QueryType.QUERY_3:
			analyzeQuery3(job);
			break;
		case QueryType.QUERY_4:
			analyzeQuery4(job);
			break;
		default:
			System.out.println("Incorrect query!");
			System.exit(1);
		}
		analyzer.dump();
	}

	private void analyzeQuery1(Job<String, Movie> job) {
		String n = analyzer.get("N").toString();
		// ICompletableFuture<Map<String, FormulaTupla>> future = job
		// .mapper(new Mapper_5())
		// .reducer(new Reducer_5())
		// .submit();

	}

	private void analyzeQuery2(Job<String, Movie> job) {
		String tope = analyzer.get("TOPE").toString();
		ICompletableFuture<PriorityQueue<Entry<String, List<Movie>>>> future = job.mapper(new Mapper_2(tope))
				.reducer(new Reducer_2()).submit(new Collector_2());
		try {
			PriorityQueue<Entry<String, List<Movie>>> rta = future.get();

			for (int i = 0; i < rta.size(); i++) {
				Entry<String, List<Movie>> actorWithVotes = rta.remove();
				System.out.println(actorWithVotes.getKey() + ": " + actorWithVotes.getValue() + " votos");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void analyzeQuery3(Job<String, Movie> job) {
	}

	private void analyzeQuery4(Job<String, Movie> job) {
	}
}
