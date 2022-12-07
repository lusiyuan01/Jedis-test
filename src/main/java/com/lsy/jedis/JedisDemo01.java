package com.lsy.jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class JedisDemo01 {

    public static void main(String[] args) {

        //创建 jedis 对象
        //传入 host：主机地址 port：端口号
        Jedis jedis = new Jedis("1.15.140.192",6379);

        //测试
        //若能连接返回 pong
        String value = jedis.ping();
        System.out.println("value = " + value);

    }

    //操作 key
    @Test
    void keyDemo() {

        //创建 jedis 对象
        //传入 host：主机地址 port：端口号
        Jedis jedis = new Jedis("1.15.140.192",6379);
        //若设置密码，需要调用 auth 方法
        jedis.auth("1234");

        Set<String> keys = jedis.keys("*");

        for (String key: keys) {
            System.out.println("key = " + key);
        }

        String result = jedis.set("k1", "lsy");
        System.out.println("result = " + result);

        String k1 = jedis.get("k1");
        System.out.println("k1 = " + k1);

    }

}
