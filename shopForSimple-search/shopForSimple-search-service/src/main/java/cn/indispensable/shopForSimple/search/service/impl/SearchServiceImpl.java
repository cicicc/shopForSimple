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

import cn.indispensable.shopForSimple.common.pojo.SearchResult;
import cn.indispensable.shopForSimple.search.dao.SearchDao;
import cn.indispensable.shopForSimple.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * @author cicicc
 * @since 1.0.0
 */
@Service
@Transactional
public class SearchServiceImpl implements SearchService {
    //注入dao层
    @Resource
    private SearchDao searchDao;
    //加载配置文件中的默认搜索域
    @Value("${DEFAULT_FIELD}")
    private String DEFAULT_FIELD;

    /**
     * 根据关键字拼装成为SolrQuery对象 并调用dao层查询
     * @param keyWord 查询的关键字
     * @param page 起始页面页码数
     * @param rows 每页显示的数据数目
     * @return 封装了查询结果的SearchResult对象
     */
    @Transactional(readOnly = true)
    @Override
    public SearchResult searchByCondition(String keyWord, int page, int rows) throws Exception {
        //拼装查询条件
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q", "item_title");
        solrQuery.setStart((page - 1) * rows);
        solrQuery.setRows(rows);
        solrQuery.set("df", DEFAULT_FIELD);
        //设置关于高亮域的相关操作---开启高亮域 设置高亮字段前后缀
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style='color:red'>");
        solrQuery.setHighlightSimplePost("</em>");
        //执行查询并将结果返回
        SearchResult searchResult = searchDao.searchByCondition(solrQuery);
        //计算总页码数
        int recordCount = searchResult.getRecordCount();
        int pages = recordCount / rows;
        if (recordCount % rows > 0){
            pages++;
        }
        searchResult.setRecordCount(pages);
        return searchResult;
    }
}
