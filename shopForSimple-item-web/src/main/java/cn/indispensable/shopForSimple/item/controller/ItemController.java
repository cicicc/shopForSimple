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
package cn.indispensable.shopForSimple.item.controller;

import cn.indispensable.shopForSimple.pojo.Item;
import cn.indispensable.shopForSimple.pojo.TbItem;
import cn.indispensable.shopForSimple.pojo.TbItemDesc;
import cn.indispensable.shopForSimple.search.service.SearchItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


/**
 * @author cicicc
 * @since 1.0.0
 */
@Controller
public class ItemController {
    //注入service层对象
    @Resource
    private SearchItemService searchItemService;


    /**
     *根据商品的id查询商品的信息
     * @param itemId 商品id
     * @param model 返回给页面的包含信息的model对象1
     * @return 逻辑视图
     */
    @RequestMapping("/item/{itemId}")
    public String selectItemById(@PathVariable Long itemId, Model model) {
        TbItem tbItem = searchItemService.selectItemById(itemId);
        Item item = new Item(tbItem);
        TbItemDesc tbItemDesc = searchItemService.selectItemDescById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);
        return "";
    }
}
