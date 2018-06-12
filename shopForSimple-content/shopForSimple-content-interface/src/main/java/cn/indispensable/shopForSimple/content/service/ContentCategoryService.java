package cn.indispensable.shopForSimple.content.service;

import cn.indispensable.shopForSimple.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ContentCategoryService {
    /**
     * 查询内容分类的信息
     * @param parentId 父节点的id
     * @return 封装了数据的LIST集合
     */
    List<EasyUITreeNode> findAllContentCategory(Long parentId);
}
