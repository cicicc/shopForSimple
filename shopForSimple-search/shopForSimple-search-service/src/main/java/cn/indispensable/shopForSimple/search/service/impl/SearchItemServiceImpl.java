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
package cn.indispensable.shopForSimple.search.service.impl;

import cn.indispensable.shopForSimple.common.pojo.SearchItem;
import cn.indispensable.shopForSimple.common.utils.E3Result;
import cn.indispensable.shopForSimple.search.mapper.SearchItemMapper;
import cn.indispensable.shopForSimple.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cicicc
 * @since 1.0.0
 */
@Service
@Transactional
public class SearchItemServiceImpl  implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;
    @Autowired
    private SolrServer searchItemMappersolrServer;

    /**
     * 添加商品到索引库中去
     * @return 返回给页面的信息 表示数据存入索引库成功或者失败
     */
    @Override
    public E3Result importItems() {
        ////查询商品列表
        List<SearchItem> itemList = searchItemMapper.getItemList();
        //导入索引库
        for (SearchItem searchItem : itemList) {
            //创建文档对象
            SolrInputDocument inputDocument = new SolrInputDocument();
            //向文档中添加域
            inputDocument.addField("id", searchItem.getId());
            inputDocument.addField("item_title", searchItem.getTitle());
            inputDocument.addField("item_sell_point", searchItem.getSell_point());
            inputDocument.addField("item_price", searchItem.getPrice());
            inputDocument.addField("item_image", searchItem.getImage());
            inputDocument.addField("item_category_name", searchItem.getCategory_name());
        }
        //写入索引库
        //solrServer.add(document);
        //}
        //提交
        //solrServer.commit();
        //返回成功
        return null;
    }
}
