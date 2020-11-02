package cn.tedu.sp02.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@Slf4j
public class ItemController {
    /**
     * 为了测试负载均衡，注入端口号，在浏览器访问时，页面显示端口号
     */
    @Value("${server.port}")
    private int port;
    @Autowired
    private ItemService itemService;

    @GetMapping("/{orderId}")
    public JsonResult<Item> getItems(@PathVariable String orderId) throws InterruptedException {
        log.info("获取订单商品列表");
        //随机延时
        if(Math.random()<0.9){ 
            //延迟
            long t = new Random().nextInt(5000); //5秒内的随机延迟
            log.info("延迟：" + t);
            Thread.sleep(t);
        }
        List<Item> items = itemService.getItems(orderId);
        return JsonResult.ok().msg("port="+port).data(items);
    }

    /**
     * @RequestBody 完整接收请求协议体的内容，转成商品集合
     * @param items
     * @return
     */
    @PostMapping("/decreaseNumber")
    public JsonResult<?> decreaseNumber(@RequestBody List<Item> items){
        itemService.decreaseNumbers(items);
        return JsonResult.ok().msg("减少库存成功");
    }

}
