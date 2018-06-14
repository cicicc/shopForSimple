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
package cn.indispensable.shopForSimple.content.service.impl;

import cn.indispensable.shopForSimple.common.pojo.EasyUITreeNode;
import cn.indispensable.shopForSimple.common.utils.E3Result;
import cn.indispensable.shopForSimple.dao.TbContentCategoryMapper;
import cn.indispensable.shopForSimple.pojo.TbContentCategory;
import cn.indispensable.shopForSimple.pojo.TbContentCategoryExample;
import cn.indispensable.shopForSimple.content.service.ContentCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cicicc
 * @since 1.0.0
 */
@Service
@Transactional
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

    /**
     *
     * @param parentId 增加节点所属父节点
     * @param name 节点名称
     * @return 包含新建节点信息的E3Result对象
     */
    @Override
    public E3Result createContentCategory(Long parentId, String name) {
        //2、向 tb_content_category 表中插入数据。
        TbContentCategory category = new TbContentCategory();
        category.setParentId(parentId);
        category.setName(name);
        category.setCreated(new Date());
        category.setUpdated(new Date());
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
        category.setSortOrder(1);
        //状态。可选值:1(正常),2(删除)
        category.setStatus(1);
        contentCategoryMapper.insert(category);
        //3、判断父节点的 isparent 是否为 true，不是 true 需要改为 true。
        TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentCategory.getIsParent()) {
            parentCategory.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentCategory);
        }
        //5、返回 E3Result，其中包装 TbContentCategory 对象 category的id已经生成
        return E3Result.ok(category);
    }

    /**
     * 重命名节点
     * @param id 当前节点id
     * @param name 重命名的name
     * @return 封装了默认成功信息的E3Result对象
     */
    @Override
    public E3Result updateContentCategory(Long id, String name) {
        //业务逻辑：根据 id 更新记录。
        TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
        category.setName(name);
        contentCategoryMapper.updateByPrimaryKey(category);
        //返回值：返回 E3Result.ok()
        return E3Result.ok();
    }

    /**
     * 根据id删除对应节点数据 如果是父节点的话就进行级联删除
     * 代码效率分析:此处的代码效率并不高 当数据量大的时候 级联删除是所进行的查询极其消耗内存,耗时长 由于此处分类数据最多几百 所以没有采取其他方法
     * @param id 要删除节点的id
     * @return 包含是否成功删除数据的信息的E3Result对象
     */
    @Override
    public E3Result deleteContentCategory(Long id) {
        TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
        Long parentId = category.getParentId();
        //如果当前节点为父节点的话 就进行级联删除
        if (category.getIsParent()){
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(example);
            for (TbContentCategory contentCategory : categoryList) {
                deleteContentCategory(contentCategory.getId());
            }
            contentCategoryMapper.deleteByPrimaryKey(id);
        }
        //如果父节点没有其他子节点的话就将父节点的isParent设置为false
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(example);
        if (categoryList.isEmpty()) {
            TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
            parentCategory.setIsParent(false);
        }
        //返回E3Result对象
        return E3Result.ok();
    }
}
