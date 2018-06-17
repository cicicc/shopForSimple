/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.indispensable.test;

import cn.indispensable.shopForSimple.common.jedis.IjedisClient;
import cn.indispensable.shopForSimple.common.jedis.JedisClientPool;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

/**
 * jedis 的测试代码
 * @author cicicc
 * @since 1.0.0
 */
public class jedisTest {
    /**
     * 第一个简单测试程序
     */
    @Test
    public void testJedisSingle(){
        //第一步：创建一个 Jedis 对象。需要指定服务端的 ip 及端口。
        Jedis jedis = new Jedis("192.168.25.129", 6379);
        //第二步：使用 Jedis 对象操作数据库，每个 redis 命令对应一个方法。
        jedis.set("name", "hello");
        jedis.hset("name", "test1", "laochen");
        String hname = jedis.hget("name", "test1");
        System.out.println("jname=" + hname);
        String name = jedis.get("name");
        //第三步：打印结果。
        System.out.println(name);
        //第四步：关闭 Jedis
        jedis.close();
    }
    @Test
    public void testJedisconfig(){
        //获取配置文件的上下文对象
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext-redis.xml");
        IjedisClient ijedisClient = context.getBean(JedisClientPool.class);
        ijedisClient.set("name", "laochen");
        String name = ijedisClient.get("name");
        System.out.println(name);
    }
}
