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
package cn.indispensable.shopForSimple.search.dao;

import cn.indispensable.shopForSimple.common.pojo.SearchItem;
import cn.indispensable.shopForSimple.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询类代码的持久层
 * @author cicicc
 * @since 1.0.0
 */
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    /**
     * 根据查询条件查询索引库，返回对应的结果。
     * @param solrQuery 封装查询条件的SolrQuery对象
     * @return SearchResult
     */
    public SearchResult searchByCondition(SolrQuery solrQuery) throws Exception {
        //根据查询条件查询索引库
        QueryResponse response = solrServer.query(solrQuery);
        //取查询结果总记录数
        SolrDocumentList results = response.getResults();
        long resultsNumFound = results.getNumFound();
        //创建一个返回结果对象
        SearchResult searchResult = new SearchResult();
        searchResult.setRecordCount((int) resultsNumFound);
        //创建一个商品列表对象
        ArrayList<SearchItem> itemList = new ArrayList<>();
        //取商品列表
        //取高亮后的结果
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        //取商品信息
        for (SolrDocument solrDocument : results) {
            //取高亮结果
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) solrDocument.get("id"));
            searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
            searchItem.setImage((String) solrDocument.get("item_image"));
            searchItem.setPrice((Long) solrDocument.get("item_price"));
            searchItem.setSell_point((String) solrDocument.get("sell_point"));
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_category_name");
            String itemTitle;
            if (list != null && list.size() > 0) {
                itemTitle = list.get(0);
            } else {
                itemTitle = (String) solrDocument.get("item_title");
            }
            searchItem.setTitle(itemTitle);
            //添加到商品列表
            itemList.add(searchItem);
        }
        //把列表添加到返回结果对象中
        searchResult.setItemList(itemList);
        return searchResult;
    }
}
