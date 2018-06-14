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

import cn.indispensable.shopForSimple.common.pojo.EasyUIDataGridResult;
import cn.indispensable.shopForSimple.common.utils.E3Result;
import cn.indispensable.shopForSimple.content.service.ContentService;
import cn.indispensable.shopForSimple.pojo.TbContent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author cicicc
 * @since 1.0.0
 * 操作content表的controller层对象(广告)
 */
@Controller
public class ContentController {
    //注入service层对象
    @Resource
    private ContentService contentService;

    /**
     * 查询所属分类下的所有商品
     * @param categoryId 分类 id
     * @param page 查询的页面页码
     * @param rows 每页显示的数据数目
     * @return EasyUIDataGridResult的json对象格式
     */
    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult queryContentList(Long categoryId, int page, int rows){

        return contentService.queryContentList(categoryId, page, rows);
    }

    /**
     * 保存内容对象(广告使用)
     * @param tbContent 页面新建的TBContent对象
     * @return E3Result的json对象
     */
    @RequestMapping("/content/save")
    @ResponseBody
    public E3Result saveContent(TbContent tbContent){
        return contentService.saveContent(tbContent);
    }

    /**
     * 修改广告(content)信息
     * 请求地址 http://localhost:8081/rest/content/edit
     * @param tbContent 编辑之后的TbContent对象
     * @return json格式的E3Result对象
     */
    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public E3Result updateContent(TbContent tbContent){
        return contentService.editContent(tbContent);
    }

    /**
     *根据id删除content信息,可同时删除多个
     * 请求地址 http://localhost:8081/content/delete
     * @param ids 要删除的content的id 以','隔开
     * @return json格式的E3Result对象(成功是返回 此处未对异常进行处理)
     */
    @RequestMapping("/content/delete")
    @ResponseBody
    public E3Result deleteContent(String ids){
        return contentService.deleteContent(ids);
    }
}
