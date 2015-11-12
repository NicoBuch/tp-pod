package main;


import java.util.concurrent.ExecutionException;

import com.hazelcast.core.HazelcastInstance;

public class Main {
	// private static final String MAP_NAME = "tp-pod";

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		HazelcastInstance client = HazelcastBootstrapper.boorstrap();

		QueryRunner argParser = new QueryRunner(args, client);
		argParser.runQuery();
		
		System.exit(0);

	}

}
