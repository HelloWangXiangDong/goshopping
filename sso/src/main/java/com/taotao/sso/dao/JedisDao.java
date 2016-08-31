package com.taotao.sso.dao;

/**
 * Created by XDStation on 2016/8/6 0006.
 */
public interface JedisDao {
    //取值
    String get(String key);
    //放入值
    String set(String key, String value);
    //取Hash值
    String hget(String hkey, String key);
    //设置hash值，hkey为hashMap的名称，key为放入的键，value是键对应的值
    long hset(String hkey, String key, String value);
    //值+1
    long incr(String key);
    //设置键的有效期
    long expire(String key, int second);
    //看一看某个键还有多久过期
    long ttl(String key);
    //删除String数据
    long del(String key);
    //删除hash值
    long hdel(String hkey, String key);
}
