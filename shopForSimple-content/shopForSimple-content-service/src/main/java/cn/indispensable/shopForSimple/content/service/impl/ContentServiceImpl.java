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

import cn.indispensable.shopForSimple.common.pojo.EasyUIDataGridResult;
import cn.indispensable.shopForSimple.common.utils.E3Result;
import cn.indispensable.shopForSimple.content.service.ContentService;
import cn.indispensable.shopForSimple.dao.TbContentMapper;
import cn.indispensable.shopForSimple.pojo.TbContent;
import cn.indispensable.shopForSimple.pojo.TbContentCategoryExample;
import cn.indispensable.shopForSimple.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author cicicc
 * @since 1.0.0
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {
    //注入ContentMapper
    @Resource
    private TbContentMapper contentMapper;

    /**
     * 页面所要数据结构 {total:查询结果总数量,rows[{id:1,title:aaa,subtitle:bb,...}]}
     * @param categoryId 分类 id
     * @param page 查询的页面页码
     * @param rows 每页显示的数据数目
     * @return EasyUIDataGridResult对象
     */
    @Override
    public EasyUIDataGridResult queryContentList(Long categoryId, int page, int rows) {
        //请求的 url：/content/query/list
        PageHelper.startPage(page, rows);
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> contentList = contentMapper.selectByExampleWithBLOBs(example);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        //将结果封装到result对象中去
        PageInfo<TbContent> pageInfo = new PageInfo(contentList);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    /**
     * 页面传递过来的数据的格式
     * categoryId: 89
     * title: abcd
     * subTitle:   cdsa
     * titleDesc:   aaaa
     * url:
     * pic: http://192.168.25.133/group1/M00/00/00/wKgZhVshAb6APvq6AABa-Iw3w9o956.jpg
     * pic2: http://192.168.25.133/group1/M00/00/00/wKgZhVshAdCAAOS1AABa-Iw3w9o126.jpg
     * content: <h1 style="font-family:&quot;">
     * 	简单商城
     * </h1>
     * @param tbContent 页面要进行保存的TBContent数据
     * @return E3Result
     */
    @Override
    public E3Result saveContent(TbContent tbContent) {
        //提交表单请求的 url：/content/save
        //1、把 TbContent 对象属性补全。
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        //2、向 tb_content 表中插入数据。
        contentMapper.insert(tbContent);
        //3、返回 E3Result
        return E3Result.ok();
    }

    /**
     * 编辑广告(content)信息
     * @param Content 修改后的内容
     * @return E3Result对象
     */
    @Override
    public E3Result editContent(TbContent Content) {
        //1、把 TbContent 对象属性补全。
        Content.setUpdated(new Date());
        //2、向 tb_content 表中插入数据。
        contentMapper.updateByPrimaryKey(Content);
        //3、返回 E3Result
        return E3Result.ok();
    }

    /**
     * 根据id删除content对象
     * @param ids 以,连接的id
     * @return E3Result对象
     */
    @Override
    public E3Result deleteContent(String ids) {
        //将id提取出来
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            Long newId = Long.parseLong(id);
            contentMapper.deleteByPrimaryKey(newId);
        }
        return E3Result.ok();
    }

    /**
     * 根据id查询内容(广告)信息 并且展示到首页
     * @param categoryId 广告所属分类id
     * @return  List<TbContent>
     */
    @Override
    public List<TbContent> queryContentByCategoryId(Long categoryId) {
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        return  contentMapper.selectByExampleWithBLOBs(example);
    }
}
