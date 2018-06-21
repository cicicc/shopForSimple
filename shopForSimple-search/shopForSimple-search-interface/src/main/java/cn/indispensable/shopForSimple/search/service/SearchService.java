package cn.indispensable.shopForSimple.search.service;

import cn.indispensable.shopForSimple.common.pojo.SearchResult;

public interface SearchService {

    /**
     * @param keyWord 查询的关键字
     * @param page 起始页面页码数
     * @param rows 每页显示的数据数目
     */
    SearchResult searchByCondition(String keyWord, int page, int rows) throws Exception;
}
