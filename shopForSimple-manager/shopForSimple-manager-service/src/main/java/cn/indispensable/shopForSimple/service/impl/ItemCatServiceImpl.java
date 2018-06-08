package cn.indispensable.shopForSimple.service.impl;

import cn.indispensable.shopForSimple.common.pojo.EasyUITreeNode;
import cn.indispensable.shopForSimple.dao.TbItemCatMapper;
import cn.indispensable.shopForSimple.pojo.TbItemCat;
import cn.indispensable.shopForSimple.pojo.TbItemCatExample;
import cn.indispensable.shopForSimple.service.ItemCatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    //注入映射文件
    @Resource
    private TbItemCatMapper itemCatMapper;


    /**
     * 查询的表：
     * tb_item_cat
     * 查询列：
     * Id、name、isparent
     * 查询条件 parentId
     * 参数：long parentId
     * 业务逻辑：
     * 1、根据 parentId 查询节点列表
     * 2、转换成 EasyUITreeNode 列表。
     * 3、返回。
     * @return List<EasyUITreeNode>
     */
    @Override
    public List<EasyUITreeNode> findCatList(long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> itemCatList = this.itemCatMapper.selectByExample(example);
        ArrayList<EasyUITreeNode> easyUITreeNodes = new ArrayList<>();
        for (TbItemCat itemCat : itemCatList) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(itemCat.getId());
            easyUITreeNode.setText(itemCat.getName());
            easyUITreeNode.setState(itemCat.getIsParent()?"closed":"open");
            easyUITreeNodes.add(easyUITreeNode);
        }
        return easyUITreeNodes;
    }
}
