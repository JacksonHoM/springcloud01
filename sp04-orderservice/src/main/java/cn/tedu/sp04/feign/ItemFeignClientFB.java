package cn.tedu.sp04.feign;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ItemFeignClientFB implements ItemFeignClient{
    @Override
    public JsonResult<List<Item>> getItems(String orderId) {
        //模拟缓存数据
        if(Math.random()<0.5){
            //50%概率返回缓存
            Arrays.asList(new Item[]{
                    new Item(1,"缓存商品1",10),
                    new Item(2,"缓存商品2",12),
                    new Item(3,"缓存商品3",13),
                    new Item(4,"缓存商品4",104),
                    new Item(5,"缓存商品5",19),
            });
        }
        //50%概率返回错误提示
        return JsonResult.err().msg("获取订单的商品列表失败");
    }

    @Override
    public JsonResult<?> decreaseNumber(List<Item> items) {
        return JsonResult.err().msg("减少订单库存失败");
    }
}
