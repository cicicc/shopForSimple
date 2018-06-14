package cn.indispensable.shopForSimple.content.service;


import cn.indispensable.shopForSimple.common.pojo.EasyUIDataGridResult;
import cn.indispensable.shopForSimple.common.utils.E3Result;
import cn.indispensable.shopForSimple.pojo.TbContent;

import java.util.List;

public interface ContentService {

    //内容列表查询
    EasyUIDataGridResult queryContentList(Long categoryId, int page, int rows);

    //保存新建的内容对象
    E3Result saveContent(TbContent tbContent);

    //编辑广告信息
    E3Result editContent(TbContent Content);

    //删除广告信息
    E3Result deleteContent(String ids);

    //根据分类id查询广告
    List<TbContent> queryContentByCategoryId(Long categoryId);
}
