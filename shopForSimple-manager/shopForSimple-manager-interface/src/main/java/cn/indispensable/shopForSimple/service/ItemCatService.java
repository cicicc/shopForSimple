package cn.indispensable.shopForSimple.service;

import cn.indispensable.shopForSimple.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    /**
     * 查询分类集合
     * 参数：long parentId
     * 业务逻辑：
     * 1、根据 parentId 查询节点列表
     * 2、转换成 EasyUITreeNode 列表。
     * 3、返回 返回值：List<EasyUITreeNode>
     */
    List<EasyUITreeNode> findCatList(long parentId);
}
