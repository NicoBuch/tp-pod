package main;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;

import service.MovieReader;
import back.core.ActorVotesCollator;
import back.core.Mapper_1;
import back.core.Mapper_3;
import back.core.Mapper_4;
import back.core.MaxActorsPerDirectorCollator;
import back.core.MaxCoupleCollator;
import back.core.Reducer_1;
import back.core.Reducer_3;
import back.core.Reducer_4;
import back.model.ActorCouple;
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

		IMap<String, Movie> myMap = client.getMap("query:" + query + "-data:" + path);
		myMap.clear();
		MovieReader reader = new MovieReader(path);
		JobTracker tracker = client.getJobTracker("default");
		try {
			long startReading = System.currentTimeMillis();
			reader.readMovies(myMap);
			System.out.println("Tiempo de lectura del archivo en ms: " + (System.currentTimeMillis() - startReading));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		KeyValueSource<String, Movie> source = KeyValueSource.fromMap(myMap);
		Job<String, Movie> job = tracker.newJob(source);

		long startWorking = System.currentTimeMillis();
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
		System.out.println("El map reduce duró: " + (System.currentTimeMillis() - startWorking));
		analyzer.dump();
	}

	private void analyzeQuery1(Job<String, Movie> job) {
		String stringN = analyzer.get("N").toString();
		Integer n = Integer.valueOf(stringN);
		ICompletableFuture<PriorityQueue<Entry<String, Integer>>> future = job.mapper(new Mapper_1())
				.reducer(new Reducer_1()).submit(new ActorVotesCollator());
		try {
			PriorityQueue<Entry<String, Integer>> rta = future.get();

			System.out.println("Los " + n + " actores más votados fueron:");
			for (int i = 0; i < n; i++) {
				Entry<String, Integer> actorWithVotes = rta.remove();
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
		ICompletableFuture<Map<ActorCouple, List<String>>> future = job.mapper(new Mapper_3()).reducer(new Reducer_3())
				.submit(new MaxCoupleCollator());
		try {
			Map<ActorCouple, List<String>> rta = future.get();
			System.out.println("Las parejas de actores que más veces actuaron juntos fueron: ");
			for (Entry<ActorCouple, List<String>> entry : rta.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void analyzeQuery4(Job<String, Movie> job) {
		ICompletableFuture<Map<String, List<String>>> future = job.mapper(new Mapper_4()).reducer(new Reducer_4())
				.submit(new MaxActorsPerDirectorCollator());

		Map<String, List<String>> rta;
		try {
			rta = future.get();
			for (Entry<String, List<String>> entry : rta.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
