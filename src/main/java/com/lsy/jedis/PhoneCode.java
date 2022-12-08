package com.lsy.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.Scanner;

public class PhoneCode {

    //手机验证码
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //创建 redis 对象
        Jedis jedis = new Jedis("1.15.140.192",6379);
        //设置 redis 密码
        jedis.auth("1234");

        verifyCode("17585626185",jedis);

        String code = scanner.next();
        getRedisCode("17585626185",code,jedis);

    }


    //获取六位随机验证码
    public static String getCode() {
        Random random = new Random();
        String code = String.valueOf(random.nextInt(1000000));
        System.out.println("验证码为：" + code);
        return code;
    }

    //每个手机只能发送三次，验证码放到 redis 中，设置过期时间
    public static void verifyCode(String phone,Jedis jedis) {
        //拼接 key
        //发送次数 key
        String countKey = "VerifyCode" + phone + ":count";
        //验证码 key
        String codeKey = "VerifyCode" + phone + ":code";

        String count = jedis.get(countKey);

        System.out.println("count = " + count);

        if (count == null) {
            jedis.setex(countKey,24*60*60,"1");
        }else if (Integer.parseInt(count)>3) {
            System.out.println("已发送三次");
            jedis.close();
            return;
        }
        jedis.incr(countKey);

        jedis.setex(codeKey,120,getCode());
        jedis.close();

    }

    //验证码校验
    public static void getRedisCode(String phone,String code,Jedis jedis) {
        String codeKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey);

        if ("".equals(redisCode) || redisCode == null) {
            return;
        }

        if (redisCode.equals(code)) {
            System.out.println("通过");
            return;
        }
        System.out.println("失败");
    }

}
