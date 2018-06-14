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

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * jedis的测试代码
 * @author cicicc
 * @since 1.0.0
 */
public class jedisTest {

    @Test
    public void testJedisSingle(){
        //第一步：创建一个 Jedis 对象。需要指定服务端的 ip 及端口。
        Jedis jedis = new Jedis("192.168.25.129", 6379);
        //第二步：使用 Jedis 对象操作数据库，每个 redis 命令对应一个方法。
        jedis.set("name", "hello");
        String name = jedis.get("name");
        //第三步：打印结果。
        System.out.println(name);
        //第四步：关闭 Jedis
        jedis.close();
    }
}
