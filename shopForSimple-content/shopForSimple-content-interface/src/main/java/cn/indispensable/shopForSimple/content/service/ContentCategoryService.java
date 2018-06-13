package cn.indispensable.shopForSimple.content.service;

import cn.indispensable.shopForSimple.common.pojo.EasyUITreeNode;
import cn.indispensable.shopForSimple.common.utils.E3Result;

import java.util.List;

public interface ContentCategoryService {
    /**
     * 查询内容分类的信息
     * @param parentId 父节点的id
     * @return 封装了数据的LIST集合
     */
    List<EasyUITreeNode> findAllContentCategory(Long parentId);

    /**
     * 增加一个内容分类的节点
     * @param parentId 增加节点所属父节点
     * @param name 节点名称
     */
    E3Result createContentCategory(Long parentId, String name);


    /**
     * 重命名节点
     * @param id 节点id
     * @param name 重命名的name
     */
    E3Result updateContentCategory(Long id, String name);

    /**
     * 删除当前节点对应的数据
     * @param id 要删除节点的id
     */
    E3Result deleteContentCategory(Long id);

}
