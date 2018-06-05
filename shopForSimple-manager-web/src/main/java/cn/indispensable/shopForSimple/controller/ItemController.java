package cn.indispensable.shopForSimple.controller;

import org.springframework.stereotype.Controller;

@Controller
public class ItemController {

 /*   @Resource
    private ItemService itemService;

    *//*
     * ResponseBody 指明方法返回值的注释应该绑定到web响应体。在Servlet环境中支持带注释的处理程序方法
     * @param itemId 要查询的商品id
     * @return 有点迷糊
     *//*
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem findItemById(@PathVariable Long itemId) {

        return itemService.selectItemById(itemId);
    }*/


}
