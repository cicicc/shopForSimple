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
package cn.indispensable.shopForSimple.search.listener;

import cn.indispensable.shopForSimple.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *监听商品添加事件 当商品添加后向索引发送添加索引的请求
 * @author cicicc
 * @since 1.0.0
 */
public class ItemChangeListener implements MessageListener {

    @Autowired
    private SearchItemService searchItemService;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage;
        Long itemId ;
        if (message instanceof TextMessage) {
            textMessage = (TextMessage) message;
            try {
                itemId = Long.valueOf(textMessage.getText());
                searchItemService.addItemsTODocument(itemId);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
