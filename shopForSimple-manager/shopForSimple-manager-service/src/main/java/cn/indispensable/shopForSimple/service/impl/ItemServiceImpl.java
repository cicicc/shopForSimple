package cn.indispensable.shopForSimple.service.impl;

import cn.indispensable.shopForSimple.common.pojo.EasyUIDataGridResult;
import cn.indispensable.shopForSimple.dao.TbItemMapper;
import cn.indispensable.shopForSimple.pojo.TbItem;
import cn.indispensable.shopForSimple.pojo.TbItemExample;
import cn.indispensable.shopForSimple.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    //注入TBItemMapper对象
    @Resource
    private TbItemMapper itemMapper;


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
}
