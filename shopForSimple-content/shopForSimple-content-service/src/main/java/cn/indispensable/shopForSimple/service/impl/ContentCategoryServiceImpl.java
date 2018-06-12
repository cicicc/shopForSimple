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
package cn.indispensable.shopForSimple.service.impl;

import cn.indispensable.shopForSimple.common.pojo.EasyUITreeNode;
import cn.indispensable.shopForSimple.dao.TbContentCategoryMapper;
import cn.indispensable.shopForSimple.pojo.TbContentCategory;
import cn.indispensable.shopForSimple.pojo.TbContentCategoryExample;
import cn.indispensable.shopForSimple.service.ContentCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cicicc
 * @since 1.0.0
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {


    //注入对应的Mapper对象
    @Resource
    private TbContentCategoryMapper contentCategoryMapper;
    /**
     * 请求的 url：/content/category/list
     * 请求的参数：id，当前节点的 id。第一次请求是没有参数，需要给默认值“0”
     * 响应数据：List<EasyUITreeNode>（@ResponseBody）
     * Json 数据。
     * [{id:1,text:节点名称,state:open(closed)},
     * {id:2,text:节点名称 2,state:open(closed)},
     * {id:3,text:节点名称 3,state:open(closed)}]
     * @param parentId 父节点的id
     * @return   List<EasyUITreeNode>
     */
    @Override
    public List<EasyUITreeNode> findAllContentCategory(Long parentId) {
        //1、取查询参数 id，parentId
        //2、根据 parentId 查询 tb_content_category，查询子节点列表。
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //3、得到 List<TbContentCategory>
        List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(example);
        //4、把列表转换成 List<EasyUITreeNode>
        List<EasyUITreeNode> easyUITreeNodes = new ArrayList<>();
        for (TbContentCategory category : categoryList) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(category.getId());
            easyUITreeNode.setText(category.getName());
            easyUITreeNode.setState(category.getIsParent()?"closed":"open");
            easyUITreeNodes.add(easyUITreeNode);
        }
        return easyUITreeNodes;
    }
}
