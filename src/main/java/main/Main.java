package main;


import java.util.concurrent.ExecutionException;

import com.hazelcast.core.HazelcastInstance;

public class Main {
	// private static final String MAP_NAME = "tp-pod";

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		HazelcastInstance client = HazelcastBootstrapper.boorstrap();

		QueryRunner argParser = new QueryRunner(args, client);
		argParser.runQuery();


		// TODO: This and MAP_NAME should be in QueryRunner
		/*
		 * // Preparar la particion de datos y distribuirla en el cluster a
		 * trav�s // del IMap IMap<String, Votacion> myMap =
		 * client.getMap(MAP_NAME); try {
		 * 
		 * } catch (Exception e) { throw new RuntimeException(e); }
		 * 
		 * // Ahora el JobTracker y los Workers! JobTracker tracker =
		 * client.getJobTracker("default");
		 * 
		 * // Ahora el Job desde los pares(key, Value) que precisa MapReduce
		 * KeyValueSource<String, Votacion> source =
		 * KeyValueSource.fromMap(myMap); Job<String, Votacion> job =
		 * tracker.newJob(source);
		 * 
		 * // // Orquestacion de Jobs y lanzamiento
		 * ICompletableFuture<Map<String, FormulaTupla>> future = job.mapper(new
		 * Mapper_5()).reducer(new Reducer_5()) .submit();
		 * 
		 * // Tomar resultado e Imprimirlo Map<String, FormulaTupla> rta =
		 * future.get();
		 * 
		 * for (Entry<String, FormulaTupla> e : rta.entrySet()) {
		 * System.out.println(String.format("Distrito %s => Ganador %s",
		 * e.getKey(), e.getValue())); }
		 */

		System.exit(0);

	}

}
