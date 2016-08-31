package com.taotao.rest.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by XDStation on 2016/8/6 0006.
 */
public class JedisTest {
    /**
     * 单机版连接方式一，常规方式
     */
    @Test
   public void testRedisSingle(){
       //创建连接
       Jedis jedis = new Jedis("192.168.153.22",6379);
        //调用方法
       jedis.set("test","hello,XDStation!");
       String s = jedis.get("test");
       System.out.println(s);
        //关闭连接
       jedis.close();
   }

    /**
     * 使用连接池连接
     */
    @Test
    public void testRedisPoll(){
        //创建连接池
        JedisPool jedisPool = new JedisPool("192.168.153.22",6379);
        //得到jedis对象
        Jedis jedis = jedisPool.getResource();
        //调用方法
        Long s = jedis.del("test");
        System.out.println(s);
        //关闭连接和连接池
        jedis.close();
        jedisPool.close();
    }

    /**
     * 集群版连接
     */
    @Test
    public void testRedisCluster(){
        Set<HostAndPort> set = new HashSet<HostAndPort>();
        set.add(new HostAndPort("192.168.153.22",7001));
        set.add(new HostAndPort("192.168.153.22",7002));
        set.add(new HostAndPort("192.168.153.22",7003));
        set.add(new HostAndPort("192.168.153.22",7004));
        set.add(new HostAndPort("192.168.153.22",7005));
        set.add(new HostAndPort("192.168.153.22",7006));

        JedisCluster jedisCluster = new JedisCluster(set);
        jedisCluster.set("testCluster","hello,XDStation!");
        String s = jedisCluster.get("testCluster");
        System.out.println(s);
        jedisCluster.close();
    }

    /**
     * 集群版整合redis
     */
    @Test
    public void testSpringRedis(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisCluster jedisCluster = (JedisCluster) applicationContext.getBean("redisClient");
        jedisCluster.set("testSpringRedis","hello,XDStation!");
        Long s = jedisCluster.del("testSpringRedis");
        System.out.println(s);
        jedisCluster.close();
    }
    /**
     * 单机版整合redis
     */
    @Test
    public void testSpringRedis1(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisPool jedisPool = (JedisPool) applicationContext.getBean("redisClient");
        Jedis jedis = jedisPool.getResource();
        jedis.del("a");
        jedis.close();
        jedisPool.close();
    }
}
