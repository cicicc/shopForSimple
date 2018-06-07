package cn.indispensable.shopForSimple.service;

import cn.indispensable.shopForSimple.common.pojo.EasyUIDataGridResult;
import cn.indispensable.shopForSimple.pojo.TbItem;

public interface ItemService {
    /**
     * 参数：商品 id
     * 返回值：TbItem
     * 业务逻辑：根据商品 id 查询商品信息。
     */
    TbItem selectItemById(Long id);

    /**
     * 逆向工程所生成的代码不适合做分页查询 因为怕麻烦 所以在这里使用的是pageHelper
     * @param page 查询页的页码数
     * @param rows 每页显示的数据数目
     * @return 查询结果封装的pojo类对象
     */
    EasyUIDataGridResult getItemList(int page, int rows);
}
