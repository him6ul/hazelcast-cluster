package org.example;

import java.util.Map;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Server
{
	public static void startServer()
	{
		Config helloWorldConfig1 = new Config();
		Config helloWorldConfig2 = new Config();

		helloWorldConfig1.setInstanceName("Instance_1");
		HazelcastInstance hz1 = Hazelcast.newHazelcastInstance(helloWorldConfig1);
		System.out.println("Instance 1 started -> " + hz1);

		helloWorldConfig2.setInstanceName("Instance_2");
		HazelcastInstance hz2 = Hazelcast.newHazelcastInstance(helloWorldConfig2);
		System.out.println("Instance 2 started -> " + hz2);

		Map<String, String> map = hz1.getMap("my-distributed-map");
		map.put("1", "John");
		map.put("2", "Mary");
		map.put("3", "Jane");

		System.out.println(map.get("1"));
		System.out.println(map.get("2"));
		System.out.println(map.get("3"));
	}

	public static void main(String[] args)
	{
		System.out.println("Starting the server");
		Server.startServer();
	}
}