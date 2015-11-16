package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import service.MovieReader;
import back.core.Collator_1;
import back.core.Mapper_1;
import back.core.Mapper_3;
import back.core.Mapper_4;
import back.core.Collator_4;
import back.core.Collator_3;
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

import back.core.Collator_2;
import back.core.Mapper_2;
import back.core.Reducer_2;

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
		String startReading;
		String finishReading;
		long readingTime = 0;
		long startWorking = 0;
		try {
			startReading = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:SSSS"));
			startWorking = System.currentTimeMillis();
			reader.readMovies(myMap);
			readingTime = System.currentTimeMillis() - startWorking;
			finishReading = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:SSSS"));
		} catch (Exception e) {
			System.out.println("Ocurrió un error en la lectura");
			return;
		}

		KeyValueSource<String, Movie> source = KeyValueSource.fromMap(myMap);
		Job<String, Movie> job = tracker.newJob(source);
		
		
		String mapReduceStartTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:SSSS"));
		startWorking = System.currentTimeMillis();
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
			return;
		}
		System.out.println("Inicio de lectura del archivo: " + startReading);
		
		System.out.println("Fin de lectura del archivo: " + finishReading);
		
		System.out.println("La lectura duró: " + readingTime);
		
		System.out.println();
		
		System.out.println("Inicio del map reduce: " + mapReduceStartTime);
		
		System.out.println("Fin del trabajo de map reduce: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:SSSS")));
		
		System.out.println("La lectura duró: " + (System.currentTimeMillis() - startWorking));
		
		analyzer.dump();
	}

	private void analyzeQuery1(Job<String, Movie> job) {
		String stringN = analyzer.get("N") == null ? "1" : analyzer.get("N").toString();
		Integer n = Integer.valueOf(stringN);
		if (n < 0) {
			System.out.println();
			System.out.println("N debe ser mayor o igual a 0, se usara N=1");
			n = 1;
		}

		ICompletableFuture<PriorityQueue<Entry<String, Integer>>> future = job.mapper(new Mapper_1())
				.reducer(new Reducer_1()).submit(new Collator_1());
		try {
			PriorityQueue<Entry<String, Integer>> rta = future.get();
			System.out.println();
			if (n == 1) {
				System.out.println("El actor más votado fue: ");
			} else {
				System.out.println("Los " + n + " actores más votados fueron:");
			}
			System.out.println();
			int size = rta.size();
			for (int i = 0; i < n && i < size; i++) {
				Entry<String, Integer> actorWithVotes = rta.remove();
				System.out.println(actorWithVotes.getKey() + ": " + actorWithVotes.getValue() + " votos");
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void analyzeQuery2(Job<String, Movie> job) {
		String tope = analyzer.get("TOPE") == null ? "0" : analyzer.get("TOPE").toString();
		if (Integer.valueOf(tope) < 0) {
			System.out.println();
			System.out.println("TOPE debe ser mayor o igual a 0, se usara TOPE=0");
			tope = "0";
		}
		ICompletableFuture<PriorityQueue<Entry<String, List<Movie>>>> future = job.mapper(new Mapper_2(tope))
				.reducer(new Reducer_2()).submit(new Collator_2());
		try {
			PriorityQueue<Entry<String, List<Movie>>> rta = future.get();
			System.out.println();
			System.out.println("Las peliculas con mejor metascore por año mayores a " + tope + ", son:");
			System.out.println();
			int queueSize = rta.size();
			for (int i = 0; i < queueSize; i++) {
				Entry<String, List<Movie>> yearWithMovies = rta.remove();
				System.out.println(yearWithMovies.getKey() + ":");
				printList(yearWithMovies.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void analyzeQuery3(Job<String, Movie> job) {
		ICompletableFuture<Map<ActorCouple, List<String>>> future = job.mapper(new Mapper_3()).reducer(new Reducer_3())
				.submit(new Collator_3());
		try {
			Map<ActorCouple, List<String>> rta = future.get();
			System.out.println();
			System.out.println("Las parejas de actores que más veces actuaron juntos fueron: ");
			System.out.println();
			for (Entry<ActorCouple, List<String>> entry : rta.entrySet()) {
				System.out.println(entry.getKey());
				printList(entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void analyzeQuery4(Job<String, Movie> job) {
		ICompletableFuture<Map<String, List<String>>> future = job.mapper(new Mapper_4()).reducer(new Reducer_4())
				.submit(new Collator_4());

		Map<String, List<String>> rta;
		try {
			System.out.println();
			System.out.println("Los actores fetiche para cada director son: ");
			System.out.println();
			rta = future.get();
			for (Entry<String, List<String>> entry : rta.entrySet()) {
				System.out.println(entry.getKey() + ":");
				printList(entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private <T> void printList(List<T> l) {
		for (T elem : l) {
			System.out.println("\t" + elem);
		}
		System.out.println();
	}

}
