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
package cn.indispensable.shopForSimple.search.controller;

import cn.indispensable.shopForSimple.common.pojo.SearchResult;
import cn.indispensable.shopForSimple.search.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * 搜索功能的Controller层
 * @author cicicc
 * @since 1.0.0
 */
@Controller
public class SearchController {
    //注入service层对象
    @Resource
    private SearchService searchService;
    //注入当前页面应该显示的数据条数
    @Value("${DEFAULT_PAGE_ROWS}")
    private Integer DEFAULT_PAGE_ROWS;

    /**
     *
     * @param keyword 查询的关键字
     * @param page 查询的页面数 默认为1
     * @param model model对象
     * @return 跳转到对应的逻辑视图页面
     */
    @RequestMapping("/search")
    public String search(String keyword, @RequestParam(defaultValue="1") Integer page, Model model)
            throws Exception {
        //需要转码
        keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
        //调用 Service 查询商品信息
        SearchResult result = searchService.searchByCondition(keyword, page, DEFAULT_PAGE_ROWS);
        //把结果传递给 jsp 页面
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("recordCount", result.getRecordCount());
        model.addAttribute("page", page);
        model.addAttribute("itemList", result.getItemList());
        //返回逻辑视图
        return "/WEB-INF/jsp/search.jsp";
    }
}
