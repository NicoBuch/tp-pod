package main;

import java.util.Properties;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastBootstrapper {

	public static HazelcastInstance boorstrap() {
		
//		TODO: Where do I get these properties from? 
		String name = System.getProperty("name");
		String pass = System.getProperty("pass");
		if (pass == null) {
			pass = "dev-pass";
		}
		System.out.println(String.format("Connecting with cluster dev-name [%s]", name));

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
