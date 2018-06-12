package cn.indispensable.shopForSimple.service;

import cn.indispensable.shopForSimple.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ContentCategoryService {
    /**
     * 请求的 url：/content/category/list
     * 请求的参数：id，当前节点的 id。第一次请求是没有参数，需要给默认值“0”
     * 响应数据：List<EasyUITreeNode>（@ResponseBody）
     * Json 数据。
     * [{id:1,text:节点名称,state:open(closed)},
     * {id:2,text:节点名称 2,state:open(closed)},
     * {id:3,text:节点名称 3,state:open(closed)}]
     * 业务逻辑：
     * 1、取查询参数 id，parentId
     * 2、根据 parentId 查询 tb_content_category，查询子节点列表。
     * 3、得到 List<TbContentCategory>
     * 4、把列表转换成 List<EasyUITreeNode>
     */
    List<EasyUITreeNode> findAllContentCategory(Long parentId);
}
