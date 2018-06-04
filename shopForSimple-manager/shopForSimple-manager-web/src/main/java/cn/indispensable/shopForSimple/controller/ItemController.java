package cn.indispensable.shopForSimple.controller;

import cn.indispensable.shopForSimple.pojo.TbItem;
import cn.indispensable.shopForSimple.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ItemController {

    @Resource
    private ItemService itemService;

    /**
     * ResponseBody 指明方法返回值的注释应该绑定到web响应体。在Servlet环境中支持带注释的处理程序方法
     * @param itemId 要查询的商品id
     * @return 有点迷糊
     */
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem findItemById(@PathVariable Long itemId) {

        return itemService.selectItemById(itemId);
    }


}
