package cn.indispensable.shopForSimple.service.impl;

import cn.indispensable.shopForSimple.dao.TbItemMapper;
import cn.indispensable.shopForSimple.pojo.TbItem;
import cn.indispensable.shopForSimple.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
}
