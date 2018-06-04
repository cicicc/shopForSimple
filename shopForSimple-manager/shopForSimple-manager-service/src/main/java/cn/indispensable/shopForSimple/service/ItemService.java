package cn.indispensable.shopForSimple.service;

import cn.indispensable.shopForSimple.pojo.TbItem;

public interface ItemService {
    /**
     * 参数：商品 id
     * 返回值：TbItem
     * 业务逻辑：根据商品 id 查询商品信息。
     */
    TbItem selectItemById(Long id);
}
