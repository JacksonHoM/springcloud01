package cn.tedu.sp06.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class RibbonController {
    @Autowired
    private RestTemplate rt;
    //调用远程的商品服务
    //如果远程调用失败，跳转到另一段代码去执行
    @HystrixCommand(fallbackMethod = "getItemsFB")
    @GetMapping("/item-service/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId){
        //调用远程服务
        //{1} 是RestTemplate 定义的一中占位符格式,后面的orderId会对占位符进行填充
        return rt.getForObject("http://item-service/{1}",JsonResult.class,orderId);
    }

    @HystrixCommand(fallbackMethod = "decreaseNumbersFB")
    @PostMapping("/item-service/decreaseNumber")
    public JsonResult<List<Item>> decreaseNumbers(@RequestBody List<Item> items){
        return rt.postForObject("http://localhost:8001/decreaseNumber", items, JsonResult.class);
    }

    @HystrixCommand(fallbackMethod = "getUserFB")
    @GetMapping("/user-service/{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId){
        return  rt.getForObject("http://item-service/{1}", JsonResult.class, userId);
    }

    @HystrixCommand(fallbackMethod = "addScoreFB")
    @GetMapping("/user-service/{userId}/score")
    public JsonResult addScore(@PathVariable Integer userId, Integer score) {
        return rt.getForObject("http://user-service/{1}/score?score={2}", JsonResult.class, userId, score);
    }

    @HystrixCommand(fallbackMethod = "getOrderFB")
    @GetMapping("/order-service/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId){
        return  rt.getForObject("http://order-service/{1}", JsonResult.class, orderId);
    }

    @HystrixCommand(fallbackMethod = "addOrderFB")
    @GetMapping("/order-service")
    public JsonResult<Order> addOrder(){
        return  rt.getForObject("http://order-service/",JsonResult.class);
    }

    //===================降级方法======================
    public JsonResult<List<Item>> getItemsFB(String orderId){
        return JsonResult.err().msg("获取订单列表失败");
    }

    public JsonResult<List<Item>> decreaseNumbersFB( List<Item> items){
        return JsonResult.err().msg("减少库存失败");
    }

    public JsonResult<User> getUserFB(Integer userId){
        return JsonResult.err().msg("获取用户失败");
    }

    public JsonResult addScoreFB(Integer userId, Integer score) {
        return JsonResult.err().msg("增加积分失败");
    }

    public JsonResult<Order> getOrderFB(String orderId){
        return JsonResult.err().msg("获取订单失败");
    }

    public JsonResult<Order> addOrder(String orderId){
        return JsonResult.err().msg("保存订单失败");
    }

}
