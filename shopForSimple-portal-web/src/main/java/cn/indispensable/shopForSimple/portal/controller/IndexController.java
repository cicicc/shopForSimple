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
package cn.indispensable.shopForSimple.portal.controller;

import cn.indispensable.shopForSimple.content.service.ContentService;
import cn.indispensable.shopForSimple.pojo.TbContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cicicc
 * @since 1.0.0
 */
@Controller
public class IndexController {
    @Value("${CONTENT_LUNBO_ID}")
    private Long CONTENT_LUNBO_ID;

    @Resource
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        //查询内容列表
        List<TbContent> ad1List = contentService.queryContentByCategoryId(CONTENT_LUNBO_ID);
        // 把结果传递给页面
        model.addAttribute("ad1List", ad1List);
        return "index";
    }
}
