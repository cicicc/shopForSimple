package cn.indispensable.shopForSimple.search.service;

import cn.indispensable.shopForSimple.common.utils.E3Result;
import cn.indispensable.shopForSimple.pojo.TbItem;
import cn.indispensable.shopForSimple.pojo.TbItemDesc;

public interface SearchItemService {
    /**
     * 添加数据到索引库中
     */
    E3Result importItems();

    E3Result addItemsTODocument(Long itemId);

    TbItem selectItemById(Long id);

    TbItemDesc selectItemDescById(Long id);
}
