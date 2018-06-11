package cn.indispensable.shopForSimple.manager.controller;

import cn.indispensable.shopForSimple.common.pojo.EasyUITreeNode;
import cn.indispensable.shopForSimple.service.ItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品分类对应的controller层
 */
@Controller
public class ItemCatController {
    //注入service层对象
    @Resource
    private ItemCatService itemCatService;

    /**
     * 初始化 tree 请求的 url：/item/cat/list
     * @param parenyId （父节点 id）
     * @return List<EasyUITreeNode>以json格式进行返回
     */
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> findCatList(@RequestParam(value = "id",defaultValue = "0")Long parenyId){

        return itemCatService.findCatList(parenyId);
    }

}
