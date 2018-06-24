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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * 练习消息总线ActiveMQ的使用
 *
 * 1）Queue，队列模式，生产者生产了一个消息，只能由一个消费者进行消费
 * 2）Topic，发布/订阅模式，生产者生产了一个消息，可以由多个消费者进行消费
 * @author cicicc
 * @since 1.0.0
 */
public class TestActiveMQ {


    private Session session;
    private Connection connection;
    @Before
    public void init() throws JMSException {
        //第一步：创建一个 ConnectionFactory 对象。
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.134:61616");
        //第二步：从 ConnectionFactory 对象中获得一个 Connection 对象。
        connection = connectionFactory.createConnection();
        //第三步：开启连接。调用 Connection 对象的 start 方法。
        connection.start();
        //第四步：使用 Connection 对象创建一个 Session 对象。
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    @After
    public void closeSource() throws JMSException {
        session.close();
        connection.close();
    }

    /**
     * 练习使用关于消息队列的生产者代码书写
     */
    @Test
    public void testProducer() throws JMSException {
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
        System.out.println("All Right");
    }
    /**
     * 练习使用关于消息队列的消费者代码书写
     */
    @Test
    public void testConsumer() throws JMSException, IOException {
        //消费者：接收消息。
        //第五步：使用 Session 对象创建一个 Destination 对象。和发送端保持一致 queue，并且队列的名称一致。
        Queue queue = session.createQueue("MQQueue");
        //第六步：使用 Session 对象创建一个 Consumer 对象。
        MessageConsumer consumer = session.createConsumer(queue);
        //第七步：接收消息。
//        Message message = consumer.receive();
       /* consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                //接收文本内容
                String text ;
                try {
                    text = textMessage.getText();
                    //第八步：打印消息。
                    System.out.println("==========================================");
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });*/
        Message message = consumer.receive();
        System.out.println("==============");
        System.out.println(message);
        //等待队列中新的消息输入
        System.in.read();
//        System.out.println(message);
        //第九步：关闭资源
        consumer.close();
    }
    /**
     * 练习使用关于topic的生产者代码书写
     */
    @Test
    public  void testTopicProducer() throws JMSException {
        //第五步：使用 Session 对象创建一个 Destination 对象（topic、queue），此处创建一个 Topic 对象。
        Topic topic = session.createTopic("test_topic");
        //第六步：使用 Session 对象创建一个 Producer 对象。
        MessageProducer producer = session.createProducer(topic);
        //第七步：创建一个 Message 对象，创建一个 TextMessage 对象。
        /*TextMessage message = new ActiveMQTextMessage();
        message.setText("汪纯是小猪");*/
        TextMessage message = session.createTextMessage("hello activeMq,this is my topic test");
        //第八步：使用 Producer 对象发送消息。
        producer.send(message);
        //第九步：关闭资源。
        producer.close();
        System.out.println("All Right!");
    }


    /**
     * 练习使用关于topic的消费者代码书写
     */
    @Test
    public void testTopicConsumer() throws JMSException, IOException {
        //第五步：使用 Session 对象创建一个 Destination 对象。和发送端保持一致 topic，并且话题的名称一致。
        Topic topic = session.createTopic("test_topic");
        //第六步：使用 Session 对象创建一个 Consumer 对象。
        MessageConsumer consumer = session.createConsumer(topic);
        //第七步：接收消息。
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    String text ;
                    // 取消息的内容
                    text = textMessage.getText();
                    // 第八步：打印消息。
                    System.out.println("===================");
                    System.out.println(text);
                    System.out.println("===================");
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("Are you ok?");
        //等待键盘输入
        System.in.read();
        //第九步：关闭资源
        consumer.close();
    }
}
