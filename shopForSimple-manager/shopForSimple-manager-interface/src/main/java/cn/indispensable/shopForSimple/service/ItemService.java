package cn.indispensable.shopForSimple.service;

import cn.indispensable.shopForSimple.common.pojo.EasyUIDataGridResult;
import cn.indispensable.shopForSimple.common.utils.E3Result;
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

    /**
     * 保存商品信息
     * @return  封装了商品信息的E3Result对象
     */
    E3Result saveItem(TbItem item, String desc);

    /**
     * 删除商品
     * @param ids 要删除的商品id
     * @return 保存了是否删除成功的e3Result对象
     */
    E3Result deleteItem(String ids);

    /**
     * 下架商品
     * @param ids 要下架的商品们的Id
     * @return 保存了是否下架成功的信息的e3Result对象
     */
    E3Result undercarriageItem(String ids);

    /**
     * 上架商品
     * @param ids 要上架的商品们的Id
     * @return 保存了是否上架成功的信息的e3Result对象
     */
    E3Result putAwayItem(String ids);

}
