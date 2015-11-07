package main;

import com.hazelcast.core.HazelcastInstance;

import back.utils.Analyzer;

public class QueryRunner {

	private Analyzer analyzer;
	private HazelcastInstance client;

	public QueryRunner(String[] args, HazelcastInstance client) {
		this.analyzer = new Analyzer(args);
		this.client = client;
	}

	public void runQuery() {
		int query = Integer.valueOf(analyzer.get("QUERY").toString());
		switch (query) {
		case QueryType.QUERY_1:
			analyzeQuery1();
			break;
		case QueryType.QUERY_2:
			analyzeQuery2();
			break;
		case QueryType.QUERY_3:
			analyzeQuery3();
			break;
		case QueryType.QUERY_4:
			analyzeQuery4();
			break;
		default:
			System.out.println("Incorrect query!");
			System.exit(1);
		}
		analyzer.dump();
	}

	private void analyzeQuery1() {
		String n = analyzer.get("N").toString();
		String path = analyzer.get("PATH").toString();
	}

	private void analyzeQuery2() {
		String tope = analyzer.get("TOPE").toString();
		String path = analyzer.get("PATH").toString();
	}

	private void analyzeQuery3() {
		String path = analyzer.get("PATH").toString();
	}

	private void analyzeQuery4() {
		String path = analyzer.get("PATH").toString();
	}
}
