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
package cn.indispensable.shopForSimple.manager.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

/**
 * 练习消息总线ActiveMQ的使用
 *
 * 1）Queue，队列模式，生产者生产了一个消息，只能由一个消费者进行消费
 * 2）Topic，发布/订阅模式，生产者生产了一个消息，可以由多个消费者进行消费
 * @author cicicc
 * @since 1.0.0
 */
public class TestActiveMQ {

    /**
     * 练习使用关于消息队列的生产者代码书写
     */
    @Test
    public void testProducer() throws JMSException {
        //第一步：创建 ConnectionFactory 对象，需要指定服务端 ip 及端口号。
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory( "tcp://192.168.25.132:61616");
        //第二步：使用 ConnectionFactory 对象创建一个 Connection 对象。
        Connection connection = connectionFactory.createConnection();
        //第三步：开启连接，调用 Connection 对象的 start 方法。
        connection.start();
        //第四步：使用 Connection 对象创建一个 Session 对象。
        //第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
        //第二个参数：当第一个参数为 false 时，才有意义。消息的应答模式。1、自动应答 2、手动应答。一般是自动应答。
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //第五步：使用 Session 对象创建一个 Destination 对象（topic、queue），此处创建一个 Queue 对象。 参数：队列的名称
        Queue queue = session.createQueue("MQQueue");
        //第六步：使用 Session 对象创建一个 Producer 对象。
        MessageProducer producer = session.createProducer(queue);
        //第七步：创建一个 Message 对象，创建一个 TextMessage 对象。
        TextMessage message = new ActiveMQTextMessage();
        message.setText("Hello ActiveMQ");
        //第八步：使用 Producer 对象发送消息。
        producer.send(message);
        //第九步：关闭资源。
        producer.close();
        session.close();
        connection.close();
        System.out.println("All Right");
    }

    @Test
    public void testConsumer() throws JMSException {
        //消费者：接收消息。
        //第一步：创建一个 ConnectionFactory 对象。
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.132:61616");
        //第二步：从 ConnectionFactory 对象中获得一个 Connection 对象。
        Connection connection = connectionFactory.createConnection();
        //第三步：开启连接。调用 Connection 对象的 start 方法。
        connection.start();
        //第四步：使用 Connection 对象创建一个 Session 对象。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //第五步：使用 Session 对象创建一个 Destination 对象。和发送端保持一致 queue，并且队列的名称一致。
        Queue queue = session.createQueue("MQQueue");
        //第六步：使用 Session 对象创建一个 Consumer 对象。
        MessageConsumer consumer = session.createConsumer(queue);
        //第七步：接收消息。
//        Message message = consumer.receive();
        //第八步：打印消息。

        System.out.println("==========================================");
//        System.out.println(message);
        System.out.println("==========================================");
        //第九步：关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
