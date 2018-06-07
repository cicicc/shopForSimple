import cn.indispensable.shopForSimple.dao.TbItemMapper;
import cn.indispensable.shopForSimple.pojo.TbItem;
import cn.indispensable.shopForSimple.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestPageHelper {
    @Test
    public void sayHello(){
        System.out.println("Hello world");
    }
    @Test
    public void testPageHelper(){
        //初始化spring容器
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //获取bean对象
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        TbItemExample tbItemExample = new TbItemExample();
        //设置分页信息
        PageHelper.startPage(1,10);
        //获取查询结果
        List<TbItem> tbItems = itemMapper.selectByExample(tbItemExample);
        //创建查询所需的pageInfo对象 获取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);
        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getPageNum());
        System.out.println(pageInfo.getFirstPage());
        System.out.println(pageInfo.getPages());
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo.getSize());

    }
}
