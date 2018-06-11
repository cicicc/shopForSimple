package cn.indispensable.shopForSimple.manager.controller;

import cn.indispensable.shopForSimple.common.pojo.EasyUIDataGridResult;
import cn.indispensable.shopForSimple.common.utils.E3Result;
import cn.indispensable.shopForSimple.pojo.TbItem;
import cn.indispensable.shopForSimple.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ItemController {

    //注入ItemService
    @Resource
    private ItemService itemService;

    /**
     * responseBody表示该方法的返回结果直接写入HTTP response body中。
     * 它的使用情况如下：
     * 1、一般在异步获取数据时使用，在使用@RequestMapping后，返回值通常解析为跳转路径，
     * 加上@responsebody后返回结果不会被解析为跳转路径，而是直接写入HTTP response body中。
     * 比如异步获取json数据，加上responseBody后，会直接返回json数据。
     * 2、一般是指定要response 的type。比如json 或 xml 可以直接用jackson或jaxb的包，
     * 然后就可以自动返回了，xml中也无需多的配置，就可以使用。
     * 好处是：GET模式下，这里使用了@PathVariable绑定输入参数，非常适合Restful风格。
     * 因为隐藏了参数与路径的关系，可以提升网站的安全性，静态化页面，降低恶意攻击风险。
     * POST模式下，使用@RequestBody绑定请求对象，Spring会帮你进行协议转换，将Json、Xml协议转换成你需要的对象。
     * ResponseBody可以标注任何对象，由Spring完成对象——协议的转换。
     * 坏处是：返回之前，若前端编码格式不一致，很容易导致乱码。
     * @param itemId 查询的商品id
     * @return 查询到的商品 并放入response域中 以json格式进行返回
     */
    @RequestMapping("/item/{itemId}, /item/edit")
    @ResponseBody
    public TbItem selectItemById(@PathVariable Long itemId) {
        return itemService.selectItemById(itemId);
    }


    /**
     * 调用service层获取指定页码数指定条数的数据
     * @param page 页码数
     * @param rows 每页显示的数据数目
     * @return 查询得到结果的json格式
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows){
        return itemService.getItemList(page, rows);
    }

    /**
     * 保存商品信息
     * @param item 商品的部分信息
     * @param desc 商品描述
     * @return json格式返回的E3Result对象
     */
    @RequestMapping("/item/save")
    @ResponseBody
    public E3Result saveItem(TbItem item, String desc){

        return itemService.saveItem(item, desc);
    }

    /**
     * 逻辑删除商品信息
     * @param ids  页面传递过来要进行删除的商品的ids 以逗号割开id
     * @return 封装了是否删除成功的信息的E3Result对象
     */
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public E3Result deleteItem(String ids) {
        return itemService.deleteItem(ids);
    }

    /**
     * 下架商品
     * @param ids  页面传递过来要进行下架商品的ids 以逗号割开id
     * @return 封装了是否下架成功的信息的E3Result对象
     */
    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public E3Result undercarriageItem(String ids) {
        return itemService.undercarriageItem(ids);
    }

    /**
     * 上架商品
     * @param ids  页面传递过来要进行上架商品的ids 以逗号割开id
     * @return 封装了是否上架成功的信息的E3Result对象
     */
    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public E3Result putAwayItem(String ids) {
        return itemService.putAwayItem(ids);
    }
}
