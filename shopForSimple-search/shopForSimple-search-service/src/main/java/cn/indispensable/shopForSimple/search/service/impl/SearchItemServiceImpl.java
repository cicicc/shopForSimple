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
import cn.indispensable.shopForSimple.dao.TbItemDescMapper;
import cn.indispensable.shopForSimple.dao.TbItemMapper;
import cn.indispensable.shopForSimple.pojo.TbItem;
import cn.indispensable.shopForSimple.pojo.TbItemDesc;
import cn.indispensable.shopForSimple.pojo.TbItemExample;
import cn.indispensable.shopForSimple.search.mapper.SearchItemMapper;
import cn.indispensable.shopForSimple.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    private SolrServer solrServer;
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;



    /**
     * 一次性导入数据库中所有商品到索引库中去
     * @return 返回给页面的信息 表示数据存入索引库成功或者失败
     */
    @Override
    public E3Result importItems() {
        //查询商品列表
        List<SearchItem> itemList = searchItemMapper.getItemList();
        try {
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
                //写入索引库
                solrServer.add(inputDocument);
            }
            //提交
            solrServer.commit();
            //返回成功
            return E3Result.ok();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return E3Result.build(500, "添加索引出错");
        }


    }

    /**
     * 根据商品id将商品添加到索引库中去
     * @param itemId 商品ID
     * @return 是否正确执行的E3Result对象
     */
    @Override
    public E3Result addItemsTODocument(Long itemId) {
        //1、根据商品 id 查询商品信息。
        SearchItem searchItem = searchItemMapper.getItemById(itemId);
        //2、创建一 SolrInputDocument 对象。
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        //向文档中添加域
        solrInputDocument.addField("id", searchItem.getId());
        solrInputDocument.addField("item_title", searchItem.getTitle());
        solrInputDocument.addField("item_sell_point", searchItem.getSell_point());
        solrInputDocument.addField("item_price", searchItem.getPrice());
        solrInputDocument.addField("item_image", searchItem.getImage());
        solrInputDocument.addField("item_category_name", searchItem.getCategory_name());
        //3、使用 SolrServer 对象写入索引库。
        try {
            solrServer.add(solrInputDocument);
            //提交solrServer对象
            solrServer.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return E3Result.build(500, itemId + "添加到索引库中出错");
        }

        //4、返回成功，返回 e3Result。
        //返回值：e3Result
        return E3Result.ok();
    }

    /**
     * 根据商品id查询商品的信息(不包括图片信息)
     * @param id 商品id
     * @return TbItem
     */
    @Override
    public TbItem selectItemById(Long id) {
        //TbItem tbItem = itemMapper.selectByPrimaryKey(id);
        TbItemExample itemExample = new TbItemExample();
        itemExample.createCriteria().andIdEqualTo(id);
        List<TbItem> tbItems = itemMapper.selectByExample(itemExample);
        if (tbItems != null && tbItems.size() > 0) {
            return tbItems.get(0);
        }
        return null;
    }

    /**
     * 根据商品id查询商品的详情信息
     * @param id 商品id
     * @return TbItemDesc
     */
    @Override
    public TbItemDesc selectItemDescById(Long id) {
        return itemDescMapper.selectByPrimaryKey(id);
    }
}
