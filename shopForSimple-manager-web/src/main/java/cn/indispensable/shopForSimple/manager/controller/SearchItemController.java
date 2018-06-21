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
package cn.indispensable.shopForSimple.manager.controller;

import cn.indispensable.shopForSimple.common.utils.E3Result;
import cn.indispensable.shopForSimple.search.service.SearchItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author cicicc
 * @since 1.0.0
 */
@Controller
public class SearchItemController {

    //注入service层对象
    @Resource
    private SearchItemService searchItemService;
    /**
     * 添加数据到solr的索引库中去
     * @return E3Result对象
     */
    @RequestMapping("/index/item/import")
    @ResponseBody
    public E3Result addDocumentToSolr(){

        return searchItemService.importItems();
    }
}
