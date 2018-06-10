package cn.indispensable.shopForSimple.service.impl;

import cn.indispensable.shopForSimple.common.pojo.EasyUIDataGridResult;
import cn.indispensable.shopForSimple.common.utils.E3Result;
import cn.indispensable.shopForSimple.common.utils.IDUtils;
import cn.indispensable.shopForSimple.dao.TbItemDescMapper;
import cn.indispensable.shopForSimple.dao.TbItemMapper;
import cn.indispensable.shopForSimple.pojo.TbItem;
import cn.indispensable.shopForSimple.pojo.TbItemDesc;
import cn.indispensable.shopForSimple.pojo.TbItemExample;
import cn.indispensable.shopForSimple.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    //注入TBItemMapper对象
    @Resource
    private TbItemMapper itemMapper;
    //注入TbItemDescMapper对象
    @Resource
    private TbItemDescMapper itemDescMapper;

    /**
     * 调用dao层根据商品id查询Tb_Item表
     * @param id 商品id
     * @return 查询得到的商品信息
     */
    @Override
    @Transactional(readOnly = true)
    public TbItem selectItemById(Long id) {
        return this.itemMapper.selectByPrimaryKey(id);
    }


    /**
     * 逆向工程所生成的代码不适合做分页查询 因为怕麻烦 所以在这里使用的是pageHelper
     * @param page 查询页的页码数
     * @param rows 每页显示的数据数目
     * @return 查询结果封装的pojo类对象
     */
    @Override
    @Transactional(readOnly = true)
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //创建page对应的example对象
        TbItemExample example = new TbItemExample();
        //执行查询
        List<TbItem> itemList = itemMapper.selectByExample(example);
        //将查询结果封装到Result对象中去
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);
        result.setTotal(pageInfo.getTotal());
        result.setRows(itemList);
        return result;
    }

    /**
     * 保存商品的service层部分
     * @param item 商品的部分信息
     * @param desc 商品描述
     * @return json格式返回的E3Result对象
     */
    @Override
    public E3Result saveItem(TbItem item, String desc) {
        //1、生成商品 id
        long itemId = IDUtils.genItemId();
        //2、补全 TbItem 对象的属性 id
        item.setId(itemId);
        //1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //3、向商品表插入数据
        this.itemMapper.insert(item);
        //4、创建一个 TbItemDesc 对象
        TbItemDesc itemDesc = new TbItemDesc();
        //5、补全 TbItemDesc 的属性
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(itemId);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //6、向商品描述表插入数据
        itemDescMapper.insert(itemDesc);
        //7、调用E3Result.ok() 返回一个空参E3Result对象 表示插入成功
        return E3Result.ok();
    }

    /**
     * s删除商品的service层具体实现
     * 逻辑删除商品信息 将商品的状态改为3(删除)
     * @param ids 要删除的商品id
     */
    @Override
    public E3Result deleteItem(String ids) {

        return updateStatus(ids, (byte)3);
    }

    /**
     * 下架商品
     * status 1-正常，2-下架，3-删除
     * @param ids 要下架的商品们的Id
     * @return 保存下架成功与否的信息的E3Result对象
     */
    @Override
    public E3Result undercarriageItem(String ids) {

        return updateStatus(ids, (byte)2);
    }
    /**
     * 上架商品
     * status 1-正常，2-下架，3-删除
     * @param ids 要上架的商品们的Id
     * @return 保存上架成功与否的信息的E3Result对象
     */
    @Override
    public E3Result putAwayItem(String ids) {
        return updateStatus(ids, (byte)1);
    }


    /**
     * 抽取去逻辑删除 上架商品 下架商品中的共同代码 并提取出不同量作为变量传递进本函数
     * @param ids 要进行操作的id 以 , 割开
     * @param status 状态码更改为此参数
     * @return 保存操作成功与否的信息的E3Result对象
     */
    private E3Result updateStatus(String ids, Byte status) {
        //根据,拆分传递过来的id
        String[] itemIds = ids.split(",");
        try {
            for (String id : itemIds) {
                //根据id删除数据
                Long itemId = Long.parseLong(id);
                TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
                tbItem.setStatus(status);
                tbItem.setUpdated(new Date());
                itemMapper.updateByPrimaryKey(tbItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500, "下架失败");
        }
        return E3Result.ok();
    }

}
