package main;

import java.util.concurrent.ExecutionException;

import com.hazelcast.core.HazelcastInstance;

public class Main {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		HazelcastInstance client = HazelcastBootstrapper.boorstrap();

		QueryRunner argParser = new QueryRunner(args, client);
		argParser.runQuery();

		System.exit(0);

	}

}
