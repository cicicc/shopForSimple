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
package cn.indispensable.shopForSimple.search.test;


import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author cicicc
 * @since 1.0.0
 */
public class TestActiveMQ {
    /**
     * 使用配置文件创建得1
     * @throws JmsException
     */
    @Test
    public void simpleUseTest() throws JmsException {
        //初始化 spring 容器
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring/applicationContext-activemq.xml");
        //从 spring 容器中获得 JmsTemplate 对象
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        //从 spring 容器中取 Destination 对象
        Destination destination = (Destination) applicationContext.getBean("queueDestination");
        //使用 JmsTemplate 对象发送消息。
        jmsTemplate.send(
                destination, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        //创建一个消息对象并返回
                        return session.createTextMessage("Hello activemq");
                    }
                });
    }

}
