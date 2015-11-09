package main;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

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
		Job<String, Movie> job = tracker.newJob(source);

		try {
			reader.readMovies(myMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

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
	}

	private void analyzeQuery3(Job<String, Movie> job) {
	}

	private void analyzeQuery4(Job<String, Movie> job) {
	}
}
