package org.example;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class Client
{
	private String name, keyPrefix;

	public Client(String name, String keyPrefix)
	{
		this.name = name;
		this.keyPrefix = keyPrefix;
	}

	public void connectClient()
	{
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setInstanceName(name);
		clientConfig.getNetworkConfig().addAddress("10.32.81.212:5701", "10.32.81.212:5702");

		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

		IMap<String, String> mapCustomers = client.getMap("my-distributed-map");

		Thread threadWrite = new Thread(new Runnable()
		{

			int loop = 1;

			@Override
			public void run()
			{
				while (true)
				{
					mapCustomers.put(name + "_" + loop, loop + "_" + name);

					try
					{
						Thread.sleep(2000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					loop++;
				}
			}
		});
		threadWrite.setName(name + "_Write");
		threadWrite.start();

		Thread threadRead = new Thread(new Runnable()
		{

			int loop = 1;

			@Override
			public void run()
			{
				while (true)
				{
					String key = keyPrefix + "_" + loop++;

					System.out.println(
						"In clientName: " + name + "; mapCustomers.get(" + key + ") -> " + mapCustomers.get(key));

					try
					{
						Thread.sleep(5000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		threadRead.setName(name + "_Read");
		threadRead.start();
	}
}
