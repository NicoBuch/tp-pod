package main;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastBootstrapper {

	public static HazelcastInstance boorstrap() {

		String name = System.getProperty("name");
		if (name == null) {
			name = "dev";
		}
		String pass = System.getProperty("pass");
		if (pass == null) {
			pass = "dev-pass";
		}
		System.out.println(String.format("Connecting with cluster: %s:%s", name, pass));

		ClientConfig ccfg = new ClientConfig();
		ccfg.getGroupConfig().setName(name).setPassword(pass);

		String addresses = System.getProperty("addresses");
		if (addresses != null) {
			String[] arrayAddresses = addresses.split("[,;]");
			ClientNetworkConfig net = new ClientNetworkConfig();
			net.addAddress(arrayAddresses);
			ccfg.setNetworkConfig(net);
		}
		HazelcastInstance client = HazelcastClient.newHazelcastClient(ccfg);

		System.out.println(client.getCluster());

		return client;
	}

}
