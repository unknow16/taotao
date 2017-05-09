package com.taotao.rest;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestJedis {

	@Test
	public void testJedisSingle() {
		Jedis jedis = new Jedis("192.168.11.140", 6379);
		jedis.set("key1", "value1");
		String v1 = jedis.get("key1");
		System.out.println(v1);
		jedis.close();
	}
	
	@Test
	public void testJedisPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(30);
		config.setMaxIdle(2); //最大空闲数
		JedisPool pool = new JedisPool(config, "192.168.11.140", 6379);
		Jedis jedis  = pool.getResource();
		jedis.set("key2", "value2");
		String k2 = jedis.get("key2");
		System.out.println(k2);
		jedis.close();
		pool.close();
	}
	
	@Test
	public void testJedisCluster() {
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.11.140", 7001));
		nodes.add(new HostAndPort("192.168.11.140", 7002));
		nodes.add(new HostAndPort("192.168.11.140", 7003));
		nodes.add(new HostAndPort("192.168.11.140", 7004));
		nodes.add(new HostAndPort("192.168.11.140", 7005));
		nodes.add(new HostAndPort("192.168.11.140", 7006));
		JedisCluster cluster = new JedisCluster(nodes);
		//cluster.set("key3", "value3");
		String k3 = cluster.get("key3");
		System.out.println(k3);
		cluster.close();
	}
	
	@Test
	public void testSingle() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool jp = (JedisPool)ac.getBean("redisClient");
		Jedis j = jp.getResource();
		String v1 = j.get("key1");
		System.out.println(v1);
		jp.close();
	}
	
	@Test
	public void testSpringJedisCluster() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster jedisCluster =  (JedisCluster) applicationContext.getBean("redisClient");
		String string = jedisCluster.get("key1");
		System.out.println(string);
		jedisCluster.close();
	}

}
