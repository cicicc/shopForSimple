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

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 测试solr的使用
 * @author cicicc
 * @since 1.0.0
 *
 */
public class TestSolr {
    private SolrServer solrServer;
    @Before
    public void init(){
        //第一步：创建一个 SolrServer 对象。
        solrServer = new HttpSolrServer("http://192.168.25.132:8080/solr");
    }

    /**
     * 添加文档对象到solr中区域
     */
    @Test
    public void addDocumentToSolr() throws IOException, SolrServerException {
        //第三步：创建一个文档对象 SolrInputDocument 对象。
        SolrInputDocument inputDocument = new SolrInputDocument();
        //第四步：向文档中添加域。必须有 id 域，域的名称必须在 schema.xml 中定义。
        //item_title" type="text_ik" indexed="true" stored="true"/>
        //<field name="item_sell_point" type="text_ik" indexed="true" stored="true"/>
        //<field name="item_price" type="long" indexed="true" stored="true"/>
        //<field name="item_image" type="string" indexed="false" stored="true" />
        //<field name="item_category_name" type="string" indexed="true" stored="true" />
        inputDocument.addField("id", "test002");
        inputDocument.addField("item_price",2500L);
        inputDocument.addField("item_sell_point","测试商品");
        //第五步：把文档添加到索引库中。
        solrServer.add(inputDocument);
        //第六步：提交。
        solrServer.commit();
    }
    /**
     * 根据 id删除文档
     */
    @Test
    public void deleteDocumentById() throws IOException, SolrServerException {
        //第二步：调用 SolrServer 对象的根据 id 删除的方法。
        solrServer.deleteById("test001");
        //第三步：提交
        solrServer.commit();
    }
    /**
     * 根据查询删除文档对象
     */
    @Test
    public void deleteDocumentByQuery() throws IOException, SolrServerException {
        //第二步：调用 SolrServer 对象的根据查询删除的方法。
        solrServer.deleteByQuery("id:test001");
        //第三步：提交
        solrServer.commit();

    }

    /**
     * 查询索引库
     */
    @Test
    public void searchDocument() throws SolrServerException {
        //第二步：创建一个 SolrQuery 对象。
        SolrQuery solrQuery = new SolrQuery();
        //第三步：向 SolrQuery 中添加查询条件、过滤条件。。。
        solrQuery.setQuery("*:*");
        //第四步：执行查询。得到一个 Response 对象。
        QueryResponse queryResponse = solrServer.query(solrQuery);
        //第五步：取查询结果。
        SolrDocumentList results = queryResponse.getResults();
        //第六步：遍历结果并打印。
        System.out.println("查询结果的总记录数：" + results.getNumFound());
        // 第六步：遍历结果并打印。
        for (SolrDocument solrDocument : results) {
            System.out.println("商品编号===="+solrDocument.get("id"));
            System.out.println("商品卖点===="+solrDocument.get("item_sell_point"));
            System.out.println("商品价格===="+solrDocument.get("item_price"));
            System.out.println("=================================");
        }
    }

    /**
     * 高亮查询索引库
     */
    @Test
    public void searchDocumentWithHighLighting() throws SolrServerException {
        //查询步骤：
        //第二步：创建一个 SolrQuery 对象。
        SolrQuery solrQuery = new SolrQuery();
        //第三步：向 SolrQuery 中添加查询条件、过滤条件。。。
        solrQuery.setQuery("测试商品");
        //指定默认搜索域
        solrQuery.set("df", "item_keywords");
        //开启高亮显示
        solrQuery.setHighlight(true);
        //高亮显示的域
        solrQuery.addHighlightField("item_sell_point");
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");
        //第四步：执行查询。得到一个 Response 对象。
        QueryResponse queryResponse = solrServer.query(solrQuery);
        //第五步：取查询结果。
        SolrDocumentList results = queryResponse.getResults();
        //第六步：遍历结果并打印。
        System.out.println("查询结果的总记录数：" + results.getNumFound());
        for (SolrDocument solrDocument : results) {
            System.out.println("=================================");
            System.out.println("商品编号===="+solrDocument.get("id"));
            //遍历高亮查询的结果
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_sell_point");
            String itemSellPoint ;
            if (list != null && list.size() > 0) {
                itemSellPoint = list.get(0);
            } else {
                itemSellPoint = (String) solrDocument.get("item_title");
            }
            System.out.println("高亮之后所得数据" + itemSellPoint);
        }
    }
}
