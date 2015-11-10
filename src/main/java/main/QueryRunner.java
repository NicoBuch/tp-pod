package main;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import service.MovieReader;
import back.core.ActorVotesCollator;
import back.core.Mapper_1;
import back.core.Reducer_1;
import back.model.Movie;
import back.utils.Analyzer;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

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

		IMap<String, Movie> myMap = client.getMap("a");
		MovieReader reader = new MovieReader(path);
		JobTracker tracker = client.getJobTracker("default");
		try {
			reader.readMovies(myMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		KeyValueSource<String, Movie> source = KeyValueSource.fromMap(myMap);
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
		String stringN = analyzer.get("N").toString();
		Integer n = Integer.valueOf(stringN);
		ICompletableFuture<PriorityQueue<Entry<String, Long>>> future = job
		 .mapper(new Mapper_1())
		 .reducer(new Reducer_1())
		 .submit(new ActorVotesCollator());
		try {
			PriorityQueue<Entry<String, Long>> rta = future.get();
			System.out.println("Los " + n  + " actores más votados fueron:");
			for(int i = 0; i < n; i++){
				Entry<String, Long> actorWithVotes = rta.remove();
				System.out.println(actorWithVotes.getKey() + ": " + actorWithVotes.getValue() + " votos");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	private void analyzeQuery2(Job<String, Movie> job) {
		String tope = analyzer.get("TOPE").toString();
	}

	private void analyzeQuery3(Job<String, Movie> job) {
	}

	private void analyzeQuery4(Job<String, Movie> job) {
	}
}
