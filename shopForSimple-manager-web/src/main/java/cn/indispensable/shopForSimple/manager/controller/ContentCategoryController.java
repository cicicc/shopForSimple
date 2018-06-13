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

import cn.indispensable.shopForSimple.common.pojo.EasyUITreeNode;
import cn.indispensable.shopForSimple.common.utils.E3Result;
import cn.indispensable.shopForSimple.content.service.ContentCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * ContentCategory的控制层代码
 * @author cicicc
 * @since 1.0.0
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    //注入ContentCategoryService层数据
    @Resource
    private ContentCategoryService contentCategoryService;

    /**
     * 获取内容分类的所有数据
     * @param parentId 要查询节点们所对应的父节点id
     * @return List<EasyUITreeNode>
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.findAllContentCategory(parentId);
    }

    /**
     * 增加内容分类的新节点
     * @param parentId 增加节点所属父节点id
     * @param name 增加节点名称
     * @return json数据 E3Result对象
     */
    @RequestMapping("/create")
    @ResponseBody
    public E3Result createContentCategory(Long parentId, String name) {
        return contentCategoryService.createContentCategory(parentId, name);
    }

    /**
     * 调用服务 根据id查找用户并更新其用户名 完成后 返回成功标志
     * @param id 当前节点id
     * @param name 更新后的节点名称
     * @return E3Result对象
     */
    @RequestMapping("/update")
    @ResponseBody
    public E3Result updateContentCategory(Long id, String name) {
        return contentCategoryService.updateContentCategory(id, name);
    }

    /**
     * 根据id级联删除节点
     * @param id 要删除的节点id
     * @return E3Result对象
     */
    @RequestMapping("/delete")
    @ResponseBody
    public E3Result deleteContentCategory(Long id) {
        return contentCategoryService.deleteContentCategory(id);
    }
}
