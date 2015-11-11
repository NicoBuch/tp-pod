package main;


import java.util.concurrent.ExecutionException;

import com.hazelcast.core.HazelcastInstance;

public class Main {
	// private static final String MAP_NAME = "tp-pod";

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		long startTime = System.currentTimeMillis();
		HazelcastInstance client = HazelcastBootstrapper.boorstrap();

		QueryRunner argParser = new QueryRunner(args, client);
		argParser.runQuery();
		
		System.out.println("Elapsed Time in ms: " + (System.currentTimeMillis() - startTime));
		
		System.exit(0);

	}

}
